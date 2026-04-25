package com.management.system.dto;


import lombok.Data;

@Data
public class VerifyUserDTO {
    private String email;
    private String emailVerificationCode;
}
