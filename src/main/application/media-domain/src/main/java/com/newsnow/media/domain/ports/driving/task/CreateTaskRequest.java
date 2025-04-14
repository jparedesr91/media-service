package com.newsnow.media.domain.ports.driving.task;

public record CreateTaskRequest(byte[] image, int width, int height) {
}