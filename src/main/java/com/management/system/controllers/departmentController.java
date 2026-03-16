package com.management.system.controllers;
import com.management.system.entities.department;
import com.management.system.services.departmentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/department")
public class departmentController {

    @Autowired
    departmentService departmentService;

    @GetMapping("/all")
    public List<department> getDepartments(){
        return departmentService.getDepartment();
    }

    @PostMapping
    public department addDepartment(@RequestBody department department){
        return departmentService.addDepartment(department);
    }

    @PutMapping("/{id}")
    public String updateDepartment(@PathVariable Long id, @RequestBody department department){
        return departmentService.updateDepartment(id,department);
    }

    @DeleteMapping("/{id}")
    public String deleteDepartment(@PathVariable Long id){
        return departmentService.removeDepartment(id);
    }



}
