package com.management.system.dto;


import lombok.Data;

@Data
public class EmployeeRequestDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String role;
    private Long department_id;
    private String status;


}