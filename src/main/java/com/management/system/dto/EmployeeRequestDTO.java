package com.management.system.dto;
import com.management.system.entities.Role;
import lombok.Data;

@Data
public class EmployeeRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private Long department_id;
    private String status;


}