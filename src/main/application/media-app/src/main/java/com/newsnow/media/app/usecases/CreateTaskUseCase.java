package com.newsnow.media.app.usecases;

import com.newsnow.media.domain.facade.MediaServiceContext;
import com.newsnow.media.domain.model.Media;
import com.newsnow.media.domain.model.Task;
import com.newsnow.media.domain.model.TaskStatus;
import com.newsnow.media.domain.ports.driven.image.ImageProcessingPort;
import com.newsnow.media.domain.ports.driven.image.ImageProcessingPort.ImageData;
import com.newsnow.media.domain.ports.driven.task.TaskRepositoryPort;
import com.newsnow.media.domain.ports.driving.task.CreateTaskRequest;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiFunction;
import reactor.core.publisher.Mono;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CreateTaskUseCase implements BiFunction<CreateTaskRequest, MediaServiceContext, Mono<Task>> {

    private final TaskRepositoryPort taskRepository;
    private final TaskProcessorUseCase taskProcessor;
    private final ImageProcessingPort imageProcessing;

    public CreateTaskUseCase(TaskRepositoryPort taskRepository, TaskProcessorUseCase taskProcessor, ImageProcessingPort imageProcessing) {
        this.taskRepository = taskRepository;
        this.taskProcessor = taskProcessor;
        this.imageProcessing = imageProcessing;
    }

    @Override
    public Mono<Task> apply(CreateTaskRequest request, MediaServiceContext mediaServiceContext) {
        return taskRepository.save(
            Task.builder()
                .id(UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .status(TaskStatus.PENDING)
                .oldMedia(createMedia(request.image()))
                .newMedia(Media.builder()
                    .width(request.width())
                    .height(request.height())
                    .build())
                .build()
        ).flatMap(task -> {
            taskProcessor.enqueueTask(task, mediaServiceContext, new ImageData(request.image(), task.getOldMedia().getMd5(), "application/json"));
            return Mono.just(task);
        });
    }

    private Media createMedia(byte[] image) {
        InputStream in = new ByteArrayInputStream(image);
        BufferedImage buf = null;
        try {
            buf = ImageIO.read(in);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Media.builder()
                .id(UUID.randomUUID().toString())
                .width(buf.getWidth())
                .height(buf.getHeight())
                .md5(imageProcessing.commuteMD5(image))
                .build();
    }

}