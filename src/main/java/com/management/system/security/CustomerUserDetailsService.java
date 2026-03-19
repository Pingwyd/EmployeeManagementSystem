package com.management.system.security;
import com.management.system.entities.employee;
import com.management.system.repositories.employeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomerUserDetailsService implements UserDetailsService {

    @Autowired
    employeeRepository employeeRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        employee emp = employeeRepository.findByEmail(username)
                .orElseThrow(()-> new UsernameNotFoundException("User not Found with email "+ username));

        return User.builder()
                .username(emp.getEmail())
                .password(emp.getPassword())
                .roles(emp.getRole())
                .build();
    }
}
