package com.management.system.controllers;

import com.management.system.dto.Employee.EmployeeRequestDTO;
import com.management.system.dto.Employee.EmployeeResponseDTO;
import com.management.system.services.AdminService;
import com.management.system.dto.Employee.UpdateEmployeeRequestDTO;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/admin/employees")
public class AdminController {


  private final AdminService adminService;

    @GetMapping("/me")
    public ResponseEntity <EmployeeResponseDTO> getMyDetails(Authentication authentication) {
        String email = authentication.getName();
        EmployeeResponseDTO employee = adminService.getMyDetails(email);
        return ResponseEntity.ok(employee);

    }
    @GetMapping("/all")
    public ResponseEntity <?> getEmployees(){
        List<EmployeeResponseDTO> temp  = adminService.getEmployees();
        return ResponseEntity.ok(temp);
    }

    @GetMapping("/department/{id}")
    public ResponseEntity<?> getEmployeeByDepartment(@PathVariable Long id){
          List<EmployeeResponseDTO> temp = adminService.getEmployeesByDepartment(id);
        return ResponseEntity.ok(temp);
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(@Valid @RequestBody EmployeeRequestDTO employee) throws MessagingException {
        EmployeeResponseDTO saved = adminService.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateEmployee (@PathVariable Long id, @RequestBody UpdateEmployeeRequestDTO employee) throws MessagingException {
        adminService.updateEmployee(id,employee);
        String message =  "Updated Successfully";
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable Long id){
        adminService.removeEmployee(id);
        String message =  "Deleted Successfully";
        return ResponseEntity.ok(message);
    }




}
