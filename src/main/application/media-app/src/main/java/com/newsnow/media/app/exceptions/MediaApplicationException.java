package com.newsnow.media.app.exceptions;

/**
 * General system exception to describe custom error behaviors. It's at the root of the microservice's exceptions
 * hierarchy.
 *
 */
public class MediaApplicationException extends RuntimeException {

    public MediaApplicationException(String message, Throwable cause) {
        super(message, cause);

    }
    public MediaApplicationException(String message) {
        super(message);
    }
}
