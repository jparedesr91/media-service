package com.newsnow.media.app.exceptions;

/**
 * Thrown to indicate that a -possible- required entity wasn't found.
 *
 */
public class EntityNotFoundException extends MediaApplicationException {

    public static final String MESSAGE_CODE = "seed.service.entity_not_found";

    public EntityNotFoundException(String message) {
        this(message, null);
    }

    public EntityNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}
