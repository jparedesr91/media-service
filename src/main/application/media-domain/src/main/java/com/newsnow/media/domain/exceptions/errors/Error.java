package com.newsnow.media.domain.exceptions.errors;

import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;

/**
 * Represents an error occurred within the system. Provides the details related to it.
 *
 */
@Setter
@Getter
public class Error implements Serializable {

    @Serial
    private static final long serialVersionUID = 5973471280896101028L;
    private ErrorCode code;
    private String message;

    public Error(ErrorCode code, String message) {
        this.code = code;
        this.message = message;
    }

    /**
     * Build an error based on the given code and detail.
     *
     * @param code The code of the error.
     * @param detail The detail of the error.
     *
     * @return An error object populated with the specified code and detail.
     */
    static public Error err(ErrorCode code, String message) {
        return new Error(code, message);
    }

}
