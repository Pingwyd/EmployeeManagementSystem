package com.management.system.repositories;
import com.management.system.entities.Employee;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


//JPA Specification

@Repository
public interface EmployeeRepository extends JpaRepository<Employee,Long> {

    List<Employee> findByDepartment_Id(Long department_Id);
    Optional<Employee> findByEmail(String email);
    boolean existsByEmail(String email);
//    boolean existsByFirstNameAndLastName(String firstName, String lastName);
//    boolean existsByEmailAndEmployeeIdNot(String email, Long id);
//    boolean existsByFirstNameAndLastNameAndEmployeeIdNot(String firstName, String LastName, Long id);

}
