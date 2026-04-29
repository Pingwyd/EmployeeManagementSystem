package com.management.system.dto.Otp;


import lombok.Data;

@Data
public class VerifyUserDTO {
    private String email;
    private String emailVerificationCode;
}
