package com.management.system.services.Impl;

import com.management.system.dto.Employee.UpdateEmployeeRequestDTO;
import com.management.system.exceptions.BadRequestException;
import com.management.system.exceptions.ConflictException;
import com.management.system.exceptions.NotFoundException;
import com.management.system.dto.Employee.EmployeeRequestDTO;
import com.management.system.dto.Employee.EmployeeResponseDTO;
import com.management.system.dto.Otp.OtpDTO;
import com.management.system.entities.Department;
import com.management.system.entities.Employee;
import com.management.system.repositories.DepartmentRepository;
import com.management.system.repositories.EmployeeRepository;
import com.management.system.services.AdminService;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminServiceImpl implements AdminService {


    final private DepartmentRepository departmentRepository;
    final private EmployeeRepository employeeRepository;
    final private PasswordEncoder passwordEncoder;
    final private OtpService otpService;


    private EmployeeResponseDTO mapToDTO(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setDepartmentName(employee.getDepartment() != null ? employee.getDepartment().getName() : null);
        dto.setStatus(employee.getStatus());
        dto.setRole(employee.getRole());
        return dto;
    }


    @Override
    public EmployeeResponseDTO getMyDetails(String email) {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(()-> new NotFoundException( "Employee Not Found"));
        return mapToDTO(employee);
    }

    @Override
    public List<EmployeeResponseDTO> getEmployees(){
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    }

    @Override
    public List<EmployeeResponseDTO> getEmployeesByDepartment(Long departmentId){
        return employeeRepository.findByDepartment_Id(departmentId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }



    @Override
        public EmployeeResponseDTO addEmployee(EmployeeRequestDTO employeeRequest) throws MessagingException {

        if(employeeRepository.existsByEmail(employeeRequest.getEmail())){
            throw  new ConflictException("Email already exists");
        }

        Employee employee = new Employee();
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setRole(employeeRequest.getRole());
        employee.setEmail(employeeRequest.getEmail());
        employee.setEnabled(false);
        employee.setStatus(employeeRequest.getStatus());

        Department dept = departmentRepository.findById(employeeRequest.getDepartment_id()).orElseThrow(()-> new NotFoundException("Department Not Found"));
        employee.setDepartment(dept);

        Employee savedEmployee = employeeRepository.save(employee);

        OtpDTO otpDTO = new OtpDTO();
        otpDTO.setId(savedEmployee.getEmployeeId());

        otpService.sendVerificationCode(savedEmployee.getEmail(), otpService.issueOrRefreshOtp(otpDTO));

        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        responseDTO.setDepartmentName(savedEmployee.getDepartment().getName());
        responseDTO.setFirstName(savedEmployee.getFirstName());
        responseDTO.setLastName(savedEmployee.getLastName());
        responseDTO.setRole(savedEmployee.getRole());
        responseDTO.setStatus(savedEmployee.getStatus());

        return responseDTO;

    }

    @Override
    public EmployeeResponseDTO updateEmployee(Long id, UpdateEmployeeRequestDTO employeeRequest) throws MessagingException {

        Employee employee = employeeRepository.findById(id).orElseThrow(()-> new NotFoundException("Employee Not Found"));

        if(employeeRequest.getEmail() != null && !employeeRequest.getEmail().isBlank()){
            employee.setEmail(employeeRequest.getEmail());
        }
        if(employeeRequest.getRole() != null ) {
            employee.setRole(employeeRequest.getRole());
        }
        if(employeeRequest.getFirstName() != null && !employeeRequest.getFirstName().isBlank()) {
            employee.setFirstName(employeeRequest.getFirstName());
        }
        if(employeeRequest.getLastName() != null && !employeeRequest.getLastName().isBlank()) {
            employee.setLastName(employeeRequest.getLastName());
        }

        if (employeeRequest.getPassword() != null && !employeeRequest.getPassword().isBlank()) {
            employee.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
        }

        if(employeeRequest.getDepartment_id() != null) {
            Department dept = departmentRepository.findById(employeeRequest.getDepartment_id()).orElseThrow(() -> new NotFoundException("Department Not Found"));
            employee.setDepartment(dept);
        }

        if(employeeRequest.getStatus() != null && !employeeRequest.getStatus().isBlank()) {
            employee.setStatus(employeeRequest.getStatus());
        }

        Employee savedEmployee = employeeRepository.save(employee);

        OtpDTO otpDTO = new OtpDTO();
        otpDTO.setId(savedEmployee.getEmployeeId());

        otpService.sendVerificationCode(savedEmployee.getEmail(), otpService.issueOrRefreshOtp(otpDTO));


        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();

        responseDTO.setFirstName(savedEmployee.getFirstName());
        responseDTO.setLastName(savedEmployee.getLastName());
        responseDTO.setDepartmentName(savedEmployee.getDepartment().getName());
        responseDTO.setRole(savedEmployee.getRole());
        responseDTO.setStatus(savedEmployee.getStatus());


        return responseDTO;


    }


    public String removeEmployee(Long id){
        if(!employeeRepository.existsById(id)){
            throw new NotFoundException("Employee Not Found");
        }
        employeeRepository.deleteById(id);
        return "Employee Removed";
    }


}
