package com.management.system.dto.Department;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class DepartmentRequestDTO {
    @NotNull(message = "Department name is Required")
    private String name;
}
