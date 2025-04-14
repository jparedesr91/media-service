package com.newsnow.media.domain.exceptions;

import com.newsnow.media.domain.exceptions.errors.ErrorCode;

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
