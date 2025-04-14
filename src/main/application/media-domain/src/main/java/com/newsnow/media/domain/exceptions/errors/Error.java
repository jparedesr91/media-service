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
    private String detail;
    private LocalDateTime timestamp;

    public Error(ErrorCode code, String detail) {
        this.code = code;
        this.detail = detail;
        this.timestamp = LocalDateTime.now();
    }

    /**
     * Build an error based on the given code and detail.
     *
     * @param code The code of the error.
     * @param detail The detail of the error.
     *
     * @return An error object populated with the specified code and detail.
     */
    static public Error err(ErrorCode code, String detail) {
        return new Error(code, detail);
    }

}
