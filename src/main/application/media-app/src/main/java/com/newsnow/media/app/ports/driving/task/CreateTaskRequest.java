package com.newsnow.media.app.ports.driving.task;

public record CreateTaskRequest(byte[] image, int width, int height) {
}