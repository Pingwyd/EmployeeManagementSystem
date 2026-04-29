package com.management.system.dto.Employee;
import com.management.system.entities.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmployeeRequestDTO {
    @NotBlank(message = "First Name is Required")
    private String firstName;

    @NotBlank(message = "Last Name is Required")
    private String lastName;

    @Email(message = "invalid email")
    @NotBlank(message = "Email is Required")
    private String email;

    @NotNull
    @Size(min = 6, message ="Password Must be at least 6 characters" )
    private String password;

    @NotNull(message = "Role is Required")
    private Role role;

    @NotNull(message = "Department Id is Required")
    private Long department_id;

    @NotBlank(message = "Status is Required")
    private String status;


}