package com.newsnow.media.app.exceptions;

import com.newsnow.media.app.exceptions.errors.ErrorCode;

/**
 * Thrown to indicate that the validation rule was violated.
 *
 */
public class ValidationException extends MediaApplicationException {

    private final ErrorCode code;

    public ValidationException(String message, ErrorCode code) {
        this(message, code, null);
    }

    public ValidationException(String message, ErrorCode code, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public ErrorCode getCode() {
        return code;
    }
}
