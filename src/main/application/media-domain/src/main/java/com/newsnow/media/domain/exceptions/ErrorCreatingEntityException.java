package com.newsnow.media.domain.exceptions;

public class ErrorCreatingEntityException extends MediaApplicationException {

    private static final String MESSAGE_CODE = "seed.service.error_creating_entity";

    public ErrorCreatingEntityException(String message) {
        this(message, null);
    }

    public ErrorCreatingEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
