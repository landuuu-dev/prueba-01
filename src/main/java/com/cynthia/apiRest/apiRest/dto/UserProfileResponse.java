package com.cynthia.apiRest.apiRest.dto;

import com.cynthia.apiRest.apiRest.model.Role;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {
    private Integer id;
    private String email;
    private String nombre;
    private Role role;
}