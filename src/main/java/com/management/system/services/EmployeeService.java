package com.management.system.services;

import com.management.system.dto.Employee.EmployeeResponseDTO;

public interface EmployeeService {
    EmployeeResponseDTO getMyDetails(String email);
}
