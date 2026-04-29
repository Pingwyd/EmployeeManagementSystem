package com.management.system.auth;


import com.management.system.entities.Department;
import com.management.system.entities.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class AuthenticationResponse {
    private String username;
    private Role role;
    private Department department;
    private String token;
}
