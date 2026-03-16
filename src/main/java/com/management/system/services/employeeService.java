package com.management.system.services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.management.system.repositories.employeeRepository;
import com.management.system.entities.employee;

import java.util.List;

@Service
public class employeeService {

    @Autowired
    employeeRepository employeeRepository;

    public List<employee> getEmployees(){
        return employeeRepository.findAll();
    }

    public List<employee> getEmployeesByDepartment(Long departmentId){
        return employeeRepository.findByDepartment_Id(departmentId);
    }

    public employee addEmployee(employee employee){
        return employeeRepository.save(employee);
    }

    public String removeEmployee(Long id){
        employeeRepository.deleteById(id);
        return "Employee Removed";
    }

    public String updateEmployee(Long id, employee updatedEmployee){
        employee currentEmployee = employeeRepository.findById(id).orElseThrow();

        currentEmployee.setRole(updatedEmployee.getRole());
        currentEmployee.setEmail(updatedEmployee.getEmail());
        currentEmployee.setStatus(updatedEmployee.getStatus());
        currentEmployee.setFirstName(updatedEmployee.getFirstName());
        currentEmployee.setLastName(updatedEmployee.getLastName());
        currentEmployee.setCreatedAt(updatedEmployee.getCreatedAt());

        employeeRepository.save(currentEmployee);
        return "Successfully Updated";
    }




}
