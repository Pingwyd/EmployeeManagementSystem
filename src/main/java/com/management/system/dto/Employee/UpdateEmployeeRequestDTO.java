package com.management.system.dto.Employee;
import com.management.system.entities.Role;
import lombok.Builder;
import lombok.Data;

@Data
//@Builder
public class UpdateEmployeeRequestDTO {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Role role;

    private Long department_id;

    private String status;


}