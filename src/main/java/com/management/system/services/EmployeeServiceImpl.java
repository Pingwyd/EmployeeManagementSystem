package com.management.system.services;

import com.management.system.Interfaces.AdminService;
import com.management.system.Interfaces.EmployeeService;
import com.management.system.dto.EmployeeResponseDTO;
import com.management.system.entities.Employee;
import com.management.system.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmployeeServiceImpl implements EmployeeService {


    final private EmployeeRepository employeeRepository;


    private EmployeeResponseDTO mapToDTO(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setDepartmentName(employee.getDepartment().getName());
        dto.setStatus(employee.getStatus());
        dto.setRole(employee.getRole());
        return dto;
    }


    public EmployeeResponseDTO getMyDetails(String email) {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Employee Not Found"));
        return mapToDTO(employee);
    }
}

