package com.cynthia.apiRest.apiRest.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateUserRequest {
    @Size(min = 2, max = 50)
    private String nombre;

    @Email
    private String email;

    @Size(min = 6, max = 20)
    private String password;
}