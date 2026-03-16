package com.management.system.repositories;
import com.management.system.entities.employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface employeeRepository extends JpaRepository<employee,Long> {

    List<employee> findByDepartment_Id(Long department_Id);
}
