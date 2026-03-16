package com.management.system.controllers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import com.management.system.entities.employee;
import com.management.system.services.*;

@RestController
@RequestMapping("/api/employees")
public class employeeController {

    @Autowired
    employeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity <?> getEmplooyees(){
        List<employee> temp  = employeeService.getEmployees();
        return ResponseEntity.ok(temp);
    }
    @GetMapping("/department/{id}")
    public ResponseEntity<?> getEmployeeByDepartment(@PathVariable Long id){
          List<employee> temp = employeeService.getEmployeesByDepartment(id);
        return ResponseEntity.ok(temp);
    }

    @PostMapping
    public ResponseEntity<employee> addEmployee(@RequestBody employee employee){
        employee saved = employeeService.addEmployee(employee);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateEmployee (@PathVariable Long id, @RequestBody employee employee){
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
