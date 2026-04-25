package com.management.system.repositories;

import com.management.system.entities.OTP;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


//JPA Specification

@Repository
public interface OtpRepository extends JpaRepository<OTP, Long> {
    Optional<OTP> findByEmployeeEmployeeId(Long employee_id);





}
