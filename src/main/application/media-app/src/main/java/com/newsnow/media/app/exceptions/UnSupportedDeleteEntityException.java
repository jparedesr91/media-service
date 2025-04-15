package com.newsnow.media.app.exceptions;

/**
 * Thrown to indicate that a duplicated entity was detected.
 *
 */
public class UnSupportedDeleteEntityException extends MediaApplicationException {

    private static final String MESSAGE_CODE = "seed.service.unsupported_delete";

    public UnSupportedDeleteEntityException(String message) {
        this(message, null);
    }

    public UnSupportedDeleteEntityException(String message, Throwable cause) {
        super(message, cause);
    }

}

