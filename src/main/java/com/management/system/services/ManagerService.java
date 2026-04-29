package com.management.system.services;

import com.management.system.dto.Employee.EmployeeResponseDTO;

import java.util.List;

public interface ManagerService {
    EmployeeResponseDTO getMyDetails(String email);

    List<EmployeeResponseDTO> getEmployeesInMyDepartment(String email);
}
