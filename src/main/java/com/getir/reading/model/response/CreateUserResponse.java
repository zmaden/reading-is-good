package com.getir.reading.model.response;

import lombok.Data;

@Data
public class CreateUserResponse {
    private String email;
    private String role;

    public CreateUserResponse() {

    }

    public CreateUserResponse(String email, String role) {
        this.email = email;
        this.role = role;
    }
}
