package com.acopl.microservice_user.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class UserDTO {

    private Long id;
    private String name;
    private String email;
    private String rol;
    private List<String> permisos;

}
