package com.management.system.repositories;
import com.management.system.entities.department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface departmentRepository extends JpaRepository<department,Long> {
}
