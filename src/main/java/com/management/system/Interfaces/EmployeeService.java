package com.management.system.Interfaces;

import com.management.system.dto.EmployeeResponseDTO;

public interface EmployeeService {
    EmployeeResponseDTO getMyDetails(String email);
}
