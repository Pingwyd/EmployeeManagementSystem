package com.management.system.entities;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
@Table(name = "OTP")
public class OTP {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long otp_id;

    @Column
    private String otp;

    @Column
    private LocalDateTime otp_expiration;

    @OneToOne
    @JoinColumn(name = "employeeId", nullable = false,unique = true)
    private Employee employee;


}
