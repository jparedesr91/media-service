package com.newsnow.media.domain.ports.driving.formanagetask;

public record CreateTaskRequest(byte[] image, int width, int height) {
}