package com.management.system.controllers;
import org.springframework.beans.factory.annotation.Autowired;
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
    public List<employee> getEmplooyees(){
        return employeeService.getEmployees();
    }
    @GetMapping("/department/{id}")
    public List<employee> getEmployeeByDepartment(@PathVariable Long id){
        return  employeeService.getEmployeesByDepartment(id);
    }

    @PostMapping
    public employee addEmployee(@RequestBody employee employee){
        return employeeService.addEmployee(employee);
    }

    @PutMapping("/{id}")
    public String updateEmployee (@PathVariable Long id, @RequestBody employee employee){
        employeeService.updateEmployee(id,employee);
        return "Updated Sucessfully";
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable Long id){
        employeeService.removeEmployee(id);
        return "Deleted Successfully";
    }




}
