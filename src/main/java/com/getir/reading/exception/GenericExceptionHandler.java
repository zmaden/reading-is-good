package com.getir.reading.exception;

import com.getir.reading.model.response.ApiResponse;
import com.getir.reading.model.response.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * The type Generic exception handler.
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GenericExceptionHandler extends ResponseEntityExceptionHandler {

    private final Logger logger = LoggerFactory.getLogger(GenericExceptionHandler.class);

    /**
     * Handle challenge exception handler response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(com.getir.reading.exception.BaseException.class)
    public ResponseEntity<ApiResponse<Object>> handleChallengeExceptionHandler(BaseException ex) {
        logger.error("Error: ", ex);
        ApiResponse<Object> response = new ApiResponse<>();
        ErrorResponse error = new ErrorResponse(ex.getMessage(), ex.getStatusCode());
        response.setError(error);
        return new ResponseEntity<>(response, ex.getStatusCode());
    }

    /**
     * Handle challenge exception handler response entity.
     *
     * @param ex the ex
     * @return the response entity
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleChallengeExceptionHandler(Exception ex) {
        logger.error("Error: ", ex);
        ApiResponse<Object> response = new ApiResponse<>();
        ErrorResponse error = new ErrorResponse(ex.getMessage());
        response.setError(error);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
