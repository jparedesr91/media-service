package com.newsnow.media.app.exceptions;

/**
 * Thrown to indicate that the data integrity was violated.
 *
 */
public class DataIntegrityException extends MediaApplicationException {

    private static final String MESSAGE_CODE = "seed.service.data_integrity_violated";

    public DataIntegrityException(String message) {
        this(message, null);
    }

    public DataIntegrityException(String message, Throwable cause) {
        super(message, cause);
    }
}
