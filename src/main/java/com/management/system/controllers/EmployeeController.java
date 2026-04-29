package com.management.system.controllers;

import com.management.system.services.EmployeeService;
import com.management.system.dto.Employee.EmployeeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {


    private final EmployeeService employeeService;

    @GetMapping("/me")
    public ResponseEntity <EmployeeResponseDTO> getMyDetails(Authentication authentication) {
        String email = authentication.getName();
        EmployeeResponseDTO employee = employeeService.getMyDetails(email);
        return ResponseEntity.ok(employee);

    }







}
