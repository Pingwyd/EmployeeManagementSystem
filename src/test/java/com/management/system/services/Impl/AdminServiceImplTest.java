package com.management.system.services.Impl;
import com.management.system.dto.Employee.EmployeeRequestDTO;
import com.management.system.dto.Employee.EmployeeResponseDTO;
import com.management.system.dto.Employee.UpdateEmployeeRequestDTO;
import com.management.system.entities.Department;
import com.management.system.entities.Employee;
import com.management.system.entities.Role;
import com.management.system.exceptions.ConflictException;
import com.management.system.exceptions.NotFoundException;
import com.management.system.repositories.DepartmentRepository;
import com.management.system.repositories.EmployeeRepository;
import jakarta.inject.Named;
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
class AdminServiceImplTest {

    @Mock
    private DepartmentRepository departmentRepository;
    @Mock
    private EmployeeRepository employeeRepository;
    @Mock
    private OtpService otpService;

    @Mock
    private EmployeeRequestDTO testEmployeeRequestDTO;
    @Mock
    private UpdateEmployeeRequestDTO testUpdateEmployeeRequestDTO;
    @Mock
    private Department testDepartment;
    @Mock
    private Employee testEmployee;

    @InjectMocks
    private AdminServiceImpl adminService;      //Class to test





    private final Long id = 2L;


    @BeforeEach
    void setUp() {
        this.testEmployeeRequestDTO = EmployeeRequestDTO.builder()
                .firstName("John")
                .lastName("Doe")
                .status("ACTIVE")
                .email("Test@gmail.com")
                .role(Role.ADMIN)
                .department_id(2L)
                .build();


        this.testUpdateEmployeeRequestDTO = UpdateEmployeeRequestDTO.builder()
                .firstName("Jane")
                .lastName("Mary")
                .status("ACTIVE")
                .email("Test@gmail.com")
                .role(Role.ADMIN)
                .department_id(2L)
                .build();


        this.testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setEmail("test@gmail.com");
        testEmployee.setPassword("testPassword");
        testEmployee.setStatus("ACTIVE");
        testEmployee.setRole(Role.ADMIN);
        testEmployee.setDepartment(testDepartment);


        this.testDepartment = new Department();
        testDepartment.setId(2L);
        testDepartment.setName("Maths");



    }



    @Nested
    class getMyDetails{

        @Test
        void getMyDetailsTest(){
            //Given
            String email = "rupsunerki@necub.com";

            when(employeeRepository.findByEmail(email)).thenReturn(Optional.of(testEmployee));

            final EmployeeResponseDTO result = adminService.getMyDetails(email);

            //Then
            assertNotNull(result);
            assertEquals("John", result.getFirstName());
            assertEquals("Doe", result.getLastName());
            assertEquals("ACTIVE", result.getStatus());
            assertEquals(Role.ADMIN, result.getRole());

        }

        @Test
        void getMyDetails_EmployeeNotFound(){

            when(employeeRepository.findByEmail(any())).thenReturn(Optional.empty());

            log.error("e: ", assertThrows(NotFoundException.class, () -> adminService.getMyDetails(any())));


        }
    }

    @Nested
    class getEmployees{

        @Test
        void getEmployeesTest(){

            when(employeeRepository.findAll()).thenReturn(List.of(new Employee()));

            final List<EmployeeResponseDTO> result = adminService.getEmployees();

            assertNotNull(result);

        }

        @Test
        void getEmployeesByDepartmentTest(){

            Long departmentId = 1L;

            when(employeeRepository.findByDepartment_Id(departmentId)).thenReturn(List.of(new Employee()));

            final List<EmployeeResponseDTO> result = adminService.getEmployeesByDepartment(departmentId);

            assertNotNull(result);

        }

    }

    @Nested
    class addEmployee{
        @Test
        void addEmployeeTest() throws MessagingException {

            when(employeeRepository.existsByEmail(testEmployeeRequestDTO.getEmail())).thenReturn(false);
            when(departmentRepository.findById(id)).thenReturn(Optional.of(testDepartment));
            when(employeeRepository.save(any(Employee.class))).thenAnswer(invocation -> invocation.getArgument(0));


            EmployeeResponseDTO result = adminService.addEmployee(testEmployeeRequestDTO);

            assertNotNull(result);
            assertEquals("John", result.getFirstName());

        }

        @Test
        void addEmployee_EmployeeAlreadyExists(){

            when(employeeRepository.existsByEmail(testEmployeeRequestDTO.getEmail())).thenReturn(true);

            log.error("e:", assertThrows(ConflictException.class, () -> adminService.addEmployee(testEmployeeRequestDTO)));

        }

        @Test
        void addEmployee_DepartmentNotFound(){

           when(departmentRepository.findById(id)).thenReturn(Optional.empty());

            log.error("e:", assertThrows(NotFoundException.class, () -> adminService.addEmployee(testEmployeeRequestDTO)));
        }


    }

    @Nested
    class updateEmployee{
        @Test
        void updateEmployeeTest() throws MessagingException {

            when(employeeRepository.findById(id)).thenReturn(Optional.of(testEmployee));
            when(departmentRepository.findById(id)).thenReturn(Optional.of(testDepartment));
            when(employeeRepository.save(any(Employee.class))).thenAnswer(testEmployee -> testEmployee.getArgument(0));

//            when(employeeRepository.save(any(Employee.class))).thenReturn(newEmployee);

            EmployeeResponseDTO result = adminService.updateEmployee(id, testUpdateEmployeeRequestDTO);

            assertNotNull(result);
            assertEquals("Jane", result.getFirstName());
            assertEquals("Mary", result.getLastName());

            log.info(result.toString());
        }

        @Test
        @Named("Employee Not Found")
        void updateEmployee_EmployeeNotFound() throws MessagingException {

            when(employeeRepository.findById(id)).thenReturn(Optional.empty());

            log.error("e: ", assertThrows(NotFoundException.class, () -> adminService.updateEmployee(id,testUpdateEmployeeRequestDTO)));
        }

        @Test
        void addEmployee_DepartmentNotFound() throws MessagingException {
            when(employeeRepository.findById(id)).thenReturn(Optional.of(testEmployee));

            when(departmentRepository.findById(id)).thenReturn(Optional.empty());

            log.error("e:", assertThrows(NotFoundException.class, () -> adminService.updateEmployee(id, testUpdateEmployeeRequestDTO)));

        }


    }

    @Nested
    class deleteEmployee{
        @Test
        void deleteEmployeeTest() {

            when(employeeRepository.existsById(id)).thenReturn(true);
            doNothing().when(employeeRepository).deleteById(id);
            String result = adminService.removeEmployee(id);

            assertNotNull(result);
            assertEquals("Employee Removed", result);

            log.info(result);
        }
    }


    }

