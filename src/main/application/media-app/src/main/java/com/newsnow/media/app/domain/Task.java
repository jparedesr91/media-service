package com.newsnow.media.app.domain;

import java.time.LocalDateTime;

import com.newsnow.media.app.exceptions.DataIntegrityException;
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
    private String statusDetail;


    public void startProcessing() {
        if (this.status != TaskStatus.PENDING) {
            throw new DataIntegrityException("seed.service.data_integrity_violated");
        }
        this.status = TaskStatus.PROCESSING;
    }

    public void completeProcessing(String url) {
        this.newMedia.setUrl(url);
        this.status = TaskStatus.COMPLETED;
    }

    public void markFailed(String statusDetail) {
        this.status = TaskStatus.FAILED;
        this.statusDetail = statusDetail;
    }
}
