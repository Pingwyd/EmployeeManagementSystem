package com.management.system.services.Impl;

import com.management.system.dto.Employee.EmployeeResponseDTO;
import com.management.system.entities.Employee;
import com.management.system.exceptions.NotFoundException;
import com.management.system.repositories.EmployeeRepository;
import com.management.system.services.EmployeeService;
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
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Employee Not Found"));
        return mapToDTO(employee);
    }
}

