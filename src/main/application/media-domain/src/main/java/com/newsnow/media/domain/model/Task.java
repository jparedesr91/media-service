package com.newsnow.media.domain.model;

import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Task {
    private String id;
    private LocalDateTime createdAt;
    private Media newMedia;
    private Media oldMedia;
    private TaskStatus status;

    public void startProcessing() {
        if (this.status != TaskStatus.PENDING) {
            throw new IllegalStateException("Task already in progress");
        }
        this.status = TaskStatus.PROCESSING;
    }

    public void completeProcessing(String url) {
        this.newMedia.setUrl(url);
        this.status = TaskStatus.COMPLETED;
    }

    public void markFailed() {
        this.status = TaskStatus.FAILED;
    }
}
