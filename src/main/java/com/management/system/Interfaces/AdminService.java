package com.management.system.Interfaces;

import com.management.system.dto.EmployeeRequestDTO;
import com.management.system.dto.EmployeeResponseDTO;
import jakarta.mail.MessagingException;

import java.util.List;

public interface AdminService {
    EmployeeResponseDTO getMyDetails(String email);

    List<EmployeeResponseDTO> getEmployees();

    List<EmployeeResponseDTO> getEmployeesByDepartment(Long id);

    EmployeeResponseDTO addEmployee(EmployeeRequestDTO employee) throws MessagingException;

    EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employee) throws MessagingException;

    String removeEmployee(Long id);
}
