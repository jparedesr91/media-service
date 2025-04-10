package com.newsnow.media.app.ports.driving.formanagetask;

import lombok.Data;

@Data
public class CreateTaskRequest {
    private byte[] image;
    private int width;
    private int height;
}
