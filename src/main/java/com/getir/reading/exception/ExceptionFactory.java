package com.getir.reading.exception;

import org.springframework.http.HttpStatus;

/**
 * The type Exception factory.
 */
public class ExceptionFactory {

    private ExceptionFactory() {
    }

    /**
     * Throw exception.
     *
     * @param message the message
     */
    public static void throwBadRequestException(String message) {
        throw new com.getir.reading.exception.BaseException(message, HttpStatus.BAD_REQUEST);
    }

    /**
     * Throw exception.
     *
     * @param message the message
     */
    public static void throwNotFoundException(String message) {
        throw new com.getir.reading.exception.BaseException(message, HttpStatus.NOT_FOUND);
    }

    /**
     * Throw exception.
     *
     * @param message the message
     */
    public static void throwConflictException(String message) {
        throw new com.getir.reading.exception.BaseException(message, HttpStatus.CONFLICT);
    }
}
