package com.management.system.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "employee")
    public class employee {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long employeeId;

        private String firstName;
        private String lastName;
        private String email;
        private String status;
        private String createdAt;
        private String role;

        @ManyToOne
        @JoinColumn(name = "department_id")
        private department department;
    }




