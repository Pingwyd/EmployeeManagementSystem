package com.management.system.dto;
import com.management.system.entities.Department;
import com.management.system.entities.Role;
import lombok.Data;

@Data
public class EmployeeResponseDTO {
    private String firstName;
    private String lastName;
    private String status;
    private Role role;
    private String departmentName;
}
