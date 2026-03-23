package com.management.system.controllers;
import com.management.system.dto.DepartmentRequestDTO;
import com.management.system.dto.DepartmentResponseDTO;
import com.management.system.entities.Department;
import com.management.system.repositories.DepartmentRepository;
import com.management.system.services.DepartmentService;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/departments")
public class DepartmentController {


    private final DepartmentService departmentService;

    @GetMapping("/all")
    public ResponseEntity<?> getDepartments(){
        List<DepartmentResponseDTO>  temp=  departmentService.getDepartment();
        return ResponseEntity.ok(temp);

    }

    @PostMapping
    public ResponseEntity<?> addDepartment(@RequestBody DepartmentRequestDTO department){
        DepartmentResponseDTO saved = departmentService.addDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody DepartmentRequestDTO department){
         departmentService.updateDepartment(id,department);
         String message  = "Updated Successfully";
         return ResponseEntity.ok(message);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDepartment(@PathVariable Long id){
         departmentService.removeDepartment(id);
         String message = "Removed Successfully";
         return ResponseEntity.ok(message);
    }



}
