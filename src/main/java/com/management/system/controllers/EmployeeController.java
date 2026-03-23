package com.management.system.controllers;
import com.management.system.dto.EmployeeRequestDTO;
import com.management.system.dto.EmployeeResponseDTO;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.management.system.entities.Employee;
import com.management.system.services.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employees")
public class EmployeeController {


    private final EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity <?> getEmployees(){
        List<EmployeeResponseDTO> temp  = employeeService.getEmployees();
        return ResponseEntity.ok(temp);
    }
    @GetMapping("/department/{id}")
    public ResponseEntity<?> getEmployeeByDepartment(@PathVariable Long id){
          List<EmployeeResponseDTO> temp = employeeService.getEmployeesByDepartment(id);
        return ResponseEntity.ok(temp);
    }

    @PostMapping
    public ResponseEntity<?> addEmployee(@RequestBody EmployeeRequestDTO employee){
        EmployeeResponseDTO saved = employeeService.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee (@PathVariable Long id, @RequestBody EmployeeRequestDTO employee){
        employeeService.updateEmployee(id,employee);
        String message =  "Updated Sucessfully";
        return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEmployee(@PathVariable Long id){
        employeeService.removeEmployee(id);
        String message =  "Deleted Successfully";
        return ResponseEntity.ok(message);
    }




}
