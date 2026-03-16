package com.management.system.controllers;
import com.management.system.entities.department;
import com.management.system.services.departmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/departments")
public class departmentController {

    @Autowired
    departmentService departmentService;


    @GetMapping("/all")
    public ResponseEntity<?> getDepartments(){
        List<department>  temp=  departmentService.getDepartment();
        return ResponseEntity.ok(temp);

    }

    @PostMapping
    public ResponseEntity<department> addDepartment(@RequestBody department department){
        department saved = departmentService.addDepartment(department);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateDepartment(@PathVariable Long id, @RequestBody department department){
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
