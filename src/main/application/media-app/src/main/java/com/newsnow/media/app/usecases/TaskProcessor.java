package com.newsnow.media.app.usecases;

import com.newsnow.media.domain.facade.MediaServiceContext;
import com.newsnow.media.domain.model.Task;
import com.newsnow.media.domain.ports.driven.ImageProcessingPort;
import com.newsnow.media.domain.ports.driven.ImageProcessingPort.ImageData;
import com.newsnow.media.domain.ports.driven.ImageStoragePort;
import com.newsnow.media.domain.ports.driven.TaskRepositoryPort;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;
import reactor.core.scheduler.Schedulers;

public class TaskProcessor {

    private final ImageProcessingPort imageProcessor;
    private final ImageStoragePort imageStorage;
    private final TaskRepositoryPort taskRepository;

    private final Sinks.Many<ProcessingJob> processingSink = Sinks.many().unicast().onBackpressureBuffer();

    public TaskProcessor(ImageProcessingPort imageProcessor, ImageStoragePort imageStorage, TaskRepositoryPort taskRepository) {
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
