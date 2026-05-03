package com.management.system.services.Impl;

import com.management.system.dto.Employee.EmployeeResponseDTO;
import com.management.system.entities.Department;
import com.management.system.entities.Employee;
import com.management.system.entities.Role;
import com.management.system.exceptions.NotFoundException;
import com.management.system.repositories.EmployeeRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.NotActiveException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;



@Slf4j
@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {


    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private Employee testemployee;

    @Mock
    private Department testDepartment;

    @InjectMocks
    private EmployeeServiceImpl employeeService;


    @BeforeEach
    void setUp() {
        this.testemployee = new Employee();

        testemployee.setFirstName("John");
        testemployee.setLastName("Doe");
        testemployee.setEmail("test@gmail.com");
        testemployee.setPassword("testPassword");
        testemployee.setStatus("ACTIVE");
        testemployee.setRole(Role.ADMIN);
        testemployee.setDepartment(testDepartment);

        this.testDepartment = new Department();
        testDepartment.setName("Test Department");
        testDepartment.setId(2L);

    }

    @Nested
    class getMyDetails{

    @Test
    void getMyDetailsTest() {

        String email = "test@gmail.com";

        when(employeeRepository.findByEmail(email)).thenReturn(Optional.of(testemployee));

        EmployeeResponseDTO result = employeeService.getMyDetails(email);

        assertNotNull(result);
        log.info(result.toString());

    }

    @Test
    void getMyDetailsTest_EmployeeNotFound() {

        String email = "test@gmail.com";

        when(employeeRepository.findByEmail(email)).thenReturn(Optional.empty());

        log.error("Error: ", assertThrows(NotFoundException.class, () -> employeeService.getMyDetails(email)));

    }
}



}