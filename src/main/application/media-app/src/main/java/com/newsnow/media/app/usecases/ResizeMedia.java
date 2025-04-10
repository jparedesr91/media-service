package com.newsnow.media.app.usecases;

import com.newsnow.media.app.domain.Media;
import com.newsnow.media.app.domain.Task;
import com.newsnow.media.app.ports.driven.formanagedata.ForManageData;
import com.newsnow.media.app.ports.driven.formanagemedia.ForManageMedia;
import com.newsnow.media.lib.javautils.ImageUtils;
import com.newsnow.media.lib.reactive.UnitReactive;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

import java.awt.*;
import java.io.IOException;
import java.util.Objects;
import java.util.function.Function;

public class ResizeMedia implements Function<Long, Mono<Void>> {

    private final Sinks.Many<Long> taskSink = Sinks.many().unicast().onBackpressureBuffer();
    private final ForManageData manageData;
    private final ForManageMedia manageMedia;

    public ResizeMedia(ForManageData manageData, ForManageMedia manageMedia) {
        this.manageData = manageData;
        this.manageMedia = manageMedia;
        initializeProcessingPipeline();
    }

    private void initializeProcessingPipeline() {
        taskSink.asFlux()
                .flatMap(this::apply)
                .subscribe();
    }

    @Override
    public Mono<Void> apply(Long taskId) {
         manageData.getTaskById(taskId)
                .filter(task -> !Objects.equals(task.getStatus(), "PENDING"))
                .flatMap(this::resizeImageAndUploadImage)
                .flatMap(this::updateTaskSuccess)
                .onErrorResume(e -> Mono.empty());
    }
    public Mono<Task> resizeImageAndUploadImage(Task taskToResize) {
        return Mono.just(taskToResize)
                .flatMap(task -> manageMedia.downloadImage(taskToResize.getOldMedia().getUrl()))
                .<byte[]>handle((image, sink) -> {
                    try {
                        sink.next(ImageUtils.resizeImage(image, taskToResize.getNewMedia().getWidth(), taskToResize.getNewMedia().getHeight()));
                    } catch (IOException e) {
                        sink.error(new RuntimeException(e));
                    }
                }).flatMap(manageMedia::uploadImage)
                .map(url -> {
                    Media newMedia = taskToResize.getNewMedia();
                    newMedia.setUrl(url);
                    return taskToResize.withNewMedia(newMedia);
                });
    }

    private Mono<Task> updateTaskSuccess(Task task) {
        task.setStatus("COMPLETED");
        return manageData.updateTask(task);
    }

    private Mono<Task> updateTaskFailure(Task task, Throwable error) {
        task.setStatus("FAILED");
        return manageData.updateTask(task);
    }

    public void submitTask(Long taskId, byte[] imageBytes) {
        taskSink.emitNext(taskId, Sinks.EmitFailureHandler.FAIL_FAST);
    }

}
