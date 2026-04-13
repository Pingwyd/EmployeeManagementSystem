package com.management.system.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jspecify.annotations.Nullable;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@NoArgsConstructor
@Entity
@Data
@Table(name = "employee")
    public class Employee implements UserDetails{

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long employeeId;

        @Enumerated(EnumType.STRING)
        private Role role;

        private String firstName;
        private String lastName;
        private String email;
        private String status;
        private String password;

        @ManyToOne
        @JoinColumn(name = "department_id")
        private Department department;

        @CreatedDate
        @Column(updatable = true)
        private LocalDateTime createdAt;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public @Nullable String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}




