package com.management.system.repositories;
import com.management.system.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name , Long id);
    Optional<Department> findByName(String name);
}
