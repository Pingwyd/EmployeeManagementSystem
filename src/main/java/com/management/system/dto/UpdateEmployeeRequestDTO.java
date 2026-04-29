package com.management.system.dto;
import com.management.system.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UpdateEmployeeRequestDTO {

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private Role role;

    private Long department_id;

    private String status;


}