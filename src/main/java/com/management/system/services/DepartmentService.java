package com.management.system.services;

import com.management.system.dto.Department.DepartmentRequestDTO;
import com.management.system.dto.Department.DepartmentResponseDTO;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponseDTO> getDepartment();

    DepartmentResponseDTO getDepartmentById(Long id);

    DepartmentResponseDTO addDepartment(DepartmentRequestDTO department);

    DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO department);

    String removeDepartment(Long id);
}
