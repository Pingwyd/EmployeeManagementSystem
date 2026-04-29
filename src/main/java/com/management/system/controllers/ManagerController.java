package com.management.system.controllers;

import com.management.system.services.ManagerService;
import com.management.system.dto.Employee.EmployeeResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/manager/employees")
public class ManagerController {


    private final ManagerService managerService;


    @GetMapping("/me")
    public ResponseEntity <EmployeeResponseDTO> getMyDetails(Authentication authentication) {
        String email = authentication.getName();
        EmployeeResponseDTO employee = managerService.getMyDetails(email);
        return ResponseEntity.ok(employee);

    }
    @GetMapping("/myDepartment")
    public ResponseEntity<List<EmployeeResponseDTO>> getEmployeeByDepartment(Authentication authentication) {
        String email = authentication.getName();
          List<EmployeeResponseDTO> temp = managerService.getEmployeesInMyDepartment(email);
        return ResponseEntity.ok(temp);
    }





}
