package com.management.system.dto.auth;


import com.management.system.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationResponseDTO {
//    private String email;
    private String username;
    private Role role;
    private String department;
    private String token;
}
