package com.management.system.repositories;
import com.management.system.entities.Employee;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> findByDepartment_Id(Long department_Id);
    Optional<Employee> findByEmail(String email);
    boolean existsByEmail(String email);
    boolean existsByFirstNameAndLastName(String firstName, String lastName);
    boolean existsByEmailAndEmployeeIdNot(String email, Long id);
    boolean existsByFirstNameAndLastNameAndEmployeeIdNot(String firstNmae, String LastNmae, Long id);
}
