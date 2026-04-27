package com.management.system.Interfaces;

import com.management.system.dto.DepartmentRequestDTO;
import com.management.system.dto.DepartmentResponseDTO;

import java.util.List;

public interface DepartmentService {
    List<DepartmentResponseDTO> getDepartment();

    DepartmentResponseDTO getDepartmentById(Long id);

    DepartmentResponseDTO addDepartment(DepartmentRequestDTO department);

    DepartmentResponseDTO updateDepartment(Long id, DepartmentRequestDTO department);

    String removeDepartment(Long id);
}
