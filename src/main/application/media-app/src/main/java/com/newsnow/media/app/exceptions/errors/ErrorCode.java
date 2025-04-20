package com.newsnow.media.app.exceptions.errors;

import static java.lang.String.format;
import static java.util.Arrays.stream;

import com.newsnow.media.app.exceptions.MediaApplicationException;
import lombok.Getter;

/**
 * System error codes enum.
 *
 */
@Getter
public enum ErrorCode {
    // 1xx Validation errors
    INVALID_VALUE(100, "Invalid value"),
    INVALID_FORMAT(101, "Invalid format"),
    INVALID_INPUT(102, "Invalid format"),
    INCONSISTENCY_OPERATION(103, "Inconsistency operation"),
    INVALID_REFERENCE(104, "Invalid reference"),
    ABSENT_FIELD(105, "Absent field"),
    UNDEFINED_VALIDATION(106, "Undefined validation"),

    // 2xx Backend errors
    INTERNAL_ERROR(200, "Internal Error"),
    NOT_IMPLEMENTED_YET(201,"Not Implemented Yet");

    private final int value;
    private final String detail;

    ErrorCode(int value, String detail) {
        this.value = value;
        this.detail = detail;
    }

  /**
     * Whether this error code is in the Error series {@link Series#VALIDATION VALIDATION}.
     * This is a shortcut for checking the value of {@link #series()}.
     */
    public boolean is1xxValidation() {
        return Series.VALIDATION.equals(series());
    }

    /**
     * Whether this error code is in the Error series {@link Series#BACKEND BACKEND}.
     * This is a shortcut for checking the value of {@link #series()}.
     */
    public boolean is2xxBackEnd() {
        return Series.BACKEND.equals(series());
    }

    /**
     * Returns the Error Code series of this error code.
     * @see Series
     */
    public Series series() {
        return Series.valueOf(this);
    }


    /**
     * Enumeration of error codes series.
     * <p>Retrievable via {@link ErrorCode#series()}.
     */
    public enum Series {
        VALIDATION(1),
        BACKEND(2);

        private final int value;

        Series(int value) {
            this.value = value;
        }

        /**
         * Return the integer value of this status series. Ranges from 1 to 2.
         */
        public int value() {
            return this.value;
        }

        public static Series valueOf(int status) {
            int seriesCode = status / 100;
            return stream(values())
                    .filter(series -> series.value == seriesCode)
                    .findAny()
                    .orElseThrow(() -> new IllegalArgumentException(format("No matching constant for '%s'", status)));
        }

        public static Series valueOf(ErrorCode code) {
            return valueOf(code.value);
        }
    }
}
