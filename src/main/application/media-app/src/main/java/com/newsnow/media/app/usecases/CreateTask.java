package com.newsnow.media.app.usecases;

import com.newsnow.media.domain.facade.MediaServiceContext;
import com.newsnow.media.domain.model.Media;
import com.newsnow.media.domain.model.Task;
import com.newsnow.media.domain.model.TaskStatus;
import com.newsnow.media.domain.ports.driven.ImageProcessingPort.ImageData;
import com.newsnow.media.domain.ports.driven.TaskRepositoryPort;
import com.newsnow.media.domain.ports.driving.formanagetask.CreateTaskRequest;
import java.time.LocalDateTime;
import java.util.UUID;
import java.util.function.BiFunction;
import org.springframework.util.DigestUtils;
import reactor.core.publisher.Mono;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CreateTask implements BiFunction<CreateTaskRequest, MediaServiceContext, Mono<Task>> {

    private final TaskRepositoryPort taskRepository;
    private final TaskProcessor taskProcessor;

    public CreateTask(TaskRepositoryPort taskRepository, TaskProcessor taskProcessor) {
        this.taskRepository = taskRepository;
        this.taskProcessor = taskProcessor;
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
        return Media.builder().width(buf.getWidth()).height(buf.getHeight()).md5(DigestUtils.md5DigestAsHex(image)).build();
    }

}