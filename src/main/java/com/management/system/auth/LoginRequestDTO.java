package com.management.system.auth;


import lombok.Data;

@Data
public class LoginRequestDTO {
    private String username;
    private String password;
}
