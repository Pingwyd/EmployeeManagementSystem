package com.management.system.services;

import com.management.system.dto.EmployeeResponseDTO;
import com.management.system.entities.Employee;
import com.management.system.repositories.DepartmentRepository;
import com.management.system.repositories.EmployeeRepository;
import com.management.system.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class ManagerService {



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


    public List<EmployeeResponseDTO> getEmployeesInMyDepartment(String email) {
        Employee manager = employeeRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Employee Not Found"));
        Long departmentId = manager.getDepartment().getId();

        return employeeRepository.findByDepartment_Id(departmentId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
}
