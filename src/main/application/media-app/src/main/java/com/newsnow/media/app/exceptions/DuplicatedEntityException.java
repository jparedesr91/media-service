package com.newsnow.media.app.exceptions;

/**
 * Thrown to indicate that a duplicated entity was detected.
 *
 */
public class DuplicatedEntityException extends MediaApplicationException {

    private static final String MESSAGE_CODE = "seed.service.duplicated_entity";

    public DuplicatedEntityException(String message) {
        this(message, null);
    }

    public DuplicatedEntityException(String message, Throwable cause) {
        super(message, cause);
    }
}
