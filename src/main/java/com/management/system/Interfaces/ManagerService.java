package com.management.system.Interfaces;

import com.management.system.dto.EmployeeResponseDTO;

import java.util.List;

public interface ManagerService {
    EmployeeResponseDTO getMyDetails(String email);

    List<EmployeeResponseDTO> getEmployeesInMyDepartment(String email);
}
