package com.getir.reading.model.response;

import lombok.Data;
import org.springframework.http.HttpStatus;
@Data
public class ErrorResponse {
    private HttpStatus errorCode;
    private String message;

    public ErrorResponse(String message) {
        this.message = message;
    }

    public ErrorResponse(final String message, final HttpStatus errorCode) {
        this.message = message;
        this.errorCode = errorCode;
    }
}
