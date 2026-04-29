package com.management.system.dto.Department;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DepartmentResponseDTO {
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
