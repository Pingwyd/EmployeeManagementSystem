package com.management.system.dto;
import com.management.system.entities.Department;
import lombok.Data;

@Data
public class EmployeeResponseDTO {
    private String firstName;
    private String lastName;
    private String status;
    private String role;
    private String departmentName;
}
