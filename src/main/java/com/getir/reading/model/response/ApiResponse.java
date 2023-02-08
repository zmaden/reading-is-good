package com.getir.reading.model.response;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class ApiResponse<T> implements Serializable {

    private T data;
    private ErrorResponse error;
    private String time = LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toString();

    public ApiResponse() {
    }

    public static <E> ApiResponse<E> response() {
        return new ApiResponse<>();
    }

    public static <E> ApiResponse<E> response(E data) {
        ApiResponse<E> response = new ApiResponse<>();
        response.data = data;
        return response;
    }

    public ApiResponse<T> data(T data) {
        this.data = data;
        return this;
    }

    public ApiResponse<T> error(ErrorResponse error) {
        this.error = error;
        return this;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public ErrorResponse getError() {
        return error;
    }

    public void setError(ErrorResponse error) {
        this.error = error;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
