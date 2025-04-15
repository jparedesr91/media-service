package com.newsnow.media.app.exceptions;

/**
 * Thrown to indicate that there is a failure during the communication with a datasource.
 *
 */
public class DataSourceCommunicationException extends MediaApplicationException {

    private static final String MESSAGE_CODE = "seed.service.datasource_communication";

    public DataSourceCommunicationException(String message) {
        this(message, null);
    }

    public DataSourceCommunicationException(String message, Throwable cause) {
        super(message, cause);
    }
}
