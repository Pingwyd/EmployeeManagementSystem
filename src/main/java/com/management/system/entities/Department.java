package com.management.system.entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Department")
public class Department {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

}
