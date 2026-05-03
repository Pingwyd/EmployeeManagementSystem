package com.management.system.dto.Department;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@Data
public class DepartmentResponseDTO {
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
