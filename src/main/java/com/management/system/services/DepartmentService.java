package com.management.system.services;

import com.management.system.dto.Department.DepartmentRequestDTO;
import com.management.system.dto.Department.DepartmentResponseDTO;
import com.management.system.dto.Department.UpdateDepartmentRequestDTO;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponseDTO> getDepartment();

    DepartmentResponseDTO getDepartmentById(Long id);

    DepartmentResponseDTO addDepartment(DepartmentRequestDTO department);

    DepartmentResponseDTO updateDepartment(Long id, UpdateDepartmentRequestDTO department);

    String removeDepartment(Long id);
}
