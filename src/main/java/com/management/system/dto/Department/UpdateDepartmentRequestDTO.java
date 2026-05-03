package com.management.system.dto.Department;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UpdateDepartmentRequestDTO {
    private String name;
}
