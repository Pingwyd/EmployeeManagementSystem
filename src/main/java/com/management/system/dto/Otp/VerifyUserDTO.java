package com.management.system.dto.Otp;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class VerifyUserDTO {

    @NotBlank(message = "Email is required")
    private String email;

    @NotBlank(message = "Code is required")
    private String emailVerificationCode;

    @NotBlank
    @Size(min = 6 ,  message = "Password Must be at Least 6 characters")
    private String password;
}
