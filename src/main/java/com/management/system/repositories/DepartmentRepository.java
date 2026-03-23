package com.management.system.repositories;
import com.management.system.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepartmentRepository extends JpaRepository<Department,Long> {
    boolean existsByName(String name);
    boolean existsByNameAndIdNot(String name , Long id);
}
