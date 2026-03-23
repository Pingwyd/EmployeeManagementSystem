package com.management.system.entities;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Data
@Table(name = "employee")
    public class Employee {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long employeeId;

        private String firstName;
        private String lastName;
        private String email;
        private String status;

        private String role;
        private String password;

        @ManyToOne
        @JoinColumn(name = "department_id")
        private Department department;

        @CreatedDate
        @Column(updatable = true)
        private LocalDateTime createdAt;
    }




