package com.management.system.auth;

import com.management.system.entities.Employee;
import com.management.system.entities.Role;
import com.management.system.repositories.EmployeeRepository;
import com.management.system.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthenticationService {

    private final EmployeeRepository employeeRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;


    public AuthenticationResponse authenticate(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
        );
        var employee = employeeRepository.findByEmail(request.getEmail()).orElseThrow();
        var Jwt = jwtUtil.generateToken(employee);
        return AuthenticationResponse.builder()
                .token(Jwt)
                .build();
    }
}
