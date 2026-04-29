package com.management.system.services;

import com.management.system.dto.Employee.EmployeeRequestDTO;
import com.management.system.dto.Employee.EmployeeResponseDTO;
import com.management.system.dto.Employee.UpdateEmployeeRequestDTO;
import jakarta.mail.MessagingException;

import java.util.List;

public interface AdminService {
    EmployeeResponseDTO getMyDetails(String email);

    List<EmployeeResponseDTO> getEmployees();

    List<EmployeeResponseDTO> getEmployeesByDepartment(Long id);

    EmployeeResponseDTO addEmployee(EmployeeRequestDTO employee) throws MessagingException;

    EmployeeResponseDTO updateEmployee(Long id, UpdateEmployeeRequestDTO employee) throws MessagingException;

    String removeEmployee(Long id);
}
