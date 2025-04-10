package com.newsnow.media.app.domain;

import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class Task {
    private Long id;
    private LocalDateTime creationDate;
    private Media newMedia;
    private Media oldMedia;
    private String status; //PENDING COMPLETED FAILED

    public Task withOldMedia(Media oldMedia) {
        this.oldMedia = oldMedia;
        return this;
    }

    public Task withNewMedia(Media newMedia) {
        this.newMedia = newMedia;
        return this;
    }
}
