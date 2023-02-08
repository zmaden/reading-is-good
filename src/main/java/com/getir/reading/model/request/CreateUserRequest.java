package com.getir.reading.model.request;

import lombok.Data;

@Data
public class CreateUserRequest {
    private String email;
    private String password;

    public CreateUserRequest() {
    }

    public CreateUserRequest(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
