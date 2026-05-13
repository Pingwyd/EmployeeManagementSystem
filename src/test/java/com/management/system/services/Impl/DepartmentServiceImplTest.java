package com.management.system.services.Impl;

import com.management.system.dto.Department.DepartmentRequestDTO;
import com.management.system.dto.Department.DepartmentResponseDTO;
import com.management.system.dto.Department.UpdateDepartmentRequestDTO;
import com.management.system.entities.Department;
import com.management.system.entities.Employee;
import com.management.system.entities.Role;
import com.management.system.exceptions.ConflictException;
import com.management.system.exceptions.NotFoundException;
import com.management.system.repositories.DepartmentRepository;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
class DepartmentServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;

    @Mock
    private DepartmentResponseDTO departmentResponseDTO;

    @Mock
    private UpdateDepartmentRequestDTO updateDepartmentRequestDTO;

    @Mock
    private DepartmentRequestDTO departmentRequestDTO;

    @Mock
    private Employee testEmployee;

    @Mock
    private Department testDepartment;

    @InjectMocks
    private DepartmentServiceImpl departmentService;

    private final Long deptID = 2L;

    @BeforeEach
    void setUp() {

        this.testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setEmail("test@gmail.com");
        testEmployee.setPassword("testPassword");
        testEmployee.setStatus("ACTIVE");
        testEmployee.setRole(Role.ADMIN);
        testEmployee.setDepartment(testDepartment);

        this.testDepartment = new Department();
        testDepartment.setName("Test Department");
        testDepartment.setId(deptID);


        this.departmentRequestDTO = DepartmentRequestDTO.builder()
                .name("Test Department")
                .build();

        this.updateDepartmentRequestDTO = UpdateDepartmentRequestDTO.builder()
                .name("Updated Department")
                .build();


    }


    @Nested
    class getDepartment {
        @Test
        void getDepartmentTest() {

            when(departmentRepository.findAll()).thenReturn((List.of(new Department())));

            List<DepartmentResponseDTO> dto = departmentService.getDepartment();

            assertNotNull(dto);
            log.info(dto.toString());

        }

        @Test
        void getDepartmentById() {

            when(departmentRepository.findById(any())).thenReturn(Optional.of(new Department()));

            DepartmentResponseDTO dto = departmentService.getDepartmentById(deptID);

            assertNotNull(dto);
            log.info(dto.toString());

        }

        @Test
        void getDepartmentById_DepartmentNotFound() {

            when(departmentRepository.findById(any())).thenReturn(Optional.empty());

            log.error("Error:", assertThrows(NotFoundException.class, () -> departmentService.getDepartmentById(deptID)));

        }


    }

    @Nested
    class AddDepartment {


        @Test
        void addDepartmentTest() {

            when(departmentRepository.existsByName(any())).thenReturn(false);
            when(departmentRepository.save(any())).thenReturn(testDepartment);

            DepartmentResponseDTO dto = departmentService.addDepartment(departmentRequestDTO);

            assertDoesNotThrow(() -> departmentService.addDepartment(departmentRequestDTO));
            assertNotNull(dto);
            log.info(dto.toString());


        }

        @Test
        void addDepartment_DepartmentAlreadyExists() {
            when(departmentRepository.existsByName(any())).thenReturn(true);

            log.error("Error: ", assertThrows(ConflictException.class, () -> departmentService.addDepartment(departmentRequestDTO)));


        }
    }


    @Nested
    class updateDepartment {
        @Test
        void updateDepartmentTest() throws MessagingException {

            when(departmentRepository.findById(deptID)).thenReturn(Optional.of(testDepartment));
            when(departmentRepository.save(any(Department.class))).thenReturn(testDepartment);

            DepartmentResponseDTO result = departmentService.updateDepartment(deptID, updateDepartmentRequestDTO);

            assertNotNull(result);
            assertEquals("Updated Department", result.getName());

            log.info(result.toString());
        }

        @Test
        void updateDepartment_DepartmentNotFound() throws MessagingException {

            when(departmentRepository.findById(deptID)).thenReturn(Optional.empty());

            log.error("Error: ", assertThrows(NotFoundException.class, () -> departmentService.updateDepartment(deptID, updateDepartmentRequestDTO)));
        }

        @Test
        void updateDepartment_DepartmentAlreadyExists() throws MessagingException {

            when(departmentRepository.findById(deptID)).thenReturn(Optional.of(testDepartment));
            when(departmentRepository.existsByNameAndIdNot(updateDepartmentRequestDTO.getName(), deptID)).thenReturn(true);

            log.error("Error: ", assertThrows(ConflictException.class, () -> departmentService.updateDepartment(deptID, updateDepartmentRequestDTO)));
        }

    }

    @Nested
    class removeDepartment {
        @Test
        void removeDepartmentTest() {

            when(departmentRepository.existsById(deptID)).thenReturn(true);
            doNothing().when(departmentRepository).deleteById(deptID);
            String result = departmentService.removeDepartment(deptID);

            assertNotNull(result);
            assertEquals("Department Removed", result);

            log.info(result);
        }
    }
}