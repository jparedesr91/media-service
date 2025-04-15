package com.newsnow.media.app.usecases;

import com.newsnow.media.app.facade.config.MediaServiceContext;
import com.newsnow.media.app.domain.Task;
import com.newsnow.media.app.ports.driven.image.ImageProcessingPort;
import com.newsnow.media.app.ports.driven.image.ImageProcessingPort.ImageData;
import com.newsnow.media.app.ports.driven.image.ImageStoragePort;
import com.newsnow.media.app.ports.driven.task.TaskRepositoryPort;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

public class TaskProcessorUseCase {

    private final ImageProcessingPort imageProcessor;
    private final ImageStoragePort imageStorage;
    private final TaskRepositoryPort taskRepository;

    private final Sinks.Many<ProcessingJob> processingSink = Sinks.many().unicast().onBackpressureBuffer();

    public TaskProcessorUseCase(ImageProcessingPort imageProcessor, ImageStoragePort imageStorage, TaskRepositoryPort taskRepository) {
        this.imageProcessor = imageProcessor;
        this.imageStorage = imageStorage;
        this.taskRepository = taskRepository;
        initializeProcessingPipeline();
    }

    private void initializeProcessingPipeline() {
        processingSink.asFlux()
            .flatMap(this::processJob)
            .subscribeOn(Schedulers.boundedElastic())
            .subscribe();
    }

    private Mono<Task> processJob(ProcessingJob job) {
        return taskRepository.getById(job.task().getId())
            .flatMap(task -> {
                task.startProcessing();
                return taskRepository.save(task);
            })
            .flatMap(task -> imageProcessor.resize(job.image, task.getOldMedia().getWidth(), task.getNewMedia().getHeight()))
            .flatMap(imageStorage::uploadImage)
            .flatMap(url -> taskRepository.getById(job.task().getId())
                .flatMap(task -> {
                    task.completeProcessing(url);
                    return taskRepository.save(task);
                }))
            .onErrorResume(e -> taskRepository.getById(job.task().getId())
                .flatMap(task -> {
                    task.markFailed();
                    return taskRepository.save(task);
                }));
    }

    public void enqueueTask(Task task, MediaServiceContext mediaServiceContext, ImageData image) {
        processingSink.emitNext(new ProcessingJob(task, mediaServiceContext, image), Sinks.EmitFailureHandler.FAIL_FAST);
    }

    private record ProcessingJob(Task task, MediaServiceContext mediaServiceContext, ImageData image) {}
}
