package com.getir.reading.exception;

import org.springframework.http.HttpStatus;

import java.util.Date;

/**
 * The type Base exception.
 */
public class BaseException extends RuntimeException {

    private HttpStatus statusCode;
    private Date timestamp;

    /**
     * Instantiates a new Base exception.
     *
     * @param message    the message
     * @param statusCode the status code
     */
    public BaseException(String message, HttpStatus statusCode) {
        super(message);
        this.statusCode = statusCode;
        this.timestamp = new Date();
    }

    /**
     * Gets status code.
     *
     * @return the status code
     */
    public HttpStatus getStatusCode() {
        return statusCode;
    }

    /**
     * Sets status code.
     *
     * @param statusCode the status code
     */
    public void setStatusCode(HttpStatus statusCode) {
        this.statusCode = statusCode;
    }

    /**
     * Gets timestamp.
     *
     * @return the timestamp
     */
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Sets timestamp.
     *
     * @param timestamp the timestamp
     */
    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }
}
