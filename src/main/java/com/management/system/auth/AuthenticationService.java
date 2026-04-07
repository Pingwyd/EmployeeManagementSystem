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
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;

    public AuthenticationResponse register(RegisterRequest request) {
        var employee = Employee.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(Role.EMPLOYEE)
                .build();
        employeeRepository.save(employee);
        var JwtToken = jwtUtil.generateToken(employee);
        return AuthenticationResponse.builder()
                .token(JwtToken)
                .build();
    }

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
