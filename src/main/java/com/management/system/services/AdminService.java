package com.management.system.services;

import com.management.system.dto.EmployeeRequestDTO;
import com.management.system.dto.EmployeeResponseDTO;
import com.management.system.entities.Department;
import com.management.system.entities.Employee;
import com.management.system.repositories.DepartmentRepository;
import com.management.system.repositories.EmployeeRepository;
import com.management.system.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class AdminService {


    final private DepartmentRepository departmentRepository;
    final private EmployeeRepository employeeRepository;
    final private PasswordEncoder passwordEncoder;
    final private JwtUtil jwtUtil;


    private EmployeeResponseDTO mapToDTO(Employee employee) {
        EmployeeResponseDTO dto = new EmployeeResponseDTO();
        dto.setFirstName(employee.getFirstName());
        dto.setLastName(employee.getLastName());
        dto.setDepartmentName(employee.getDepartment() != null ? employee.getDepartment().getName() : null);
        dto.setStatus(employee.getStatus());
        dto.setRole(employee.getRole());
        return dto;
    }


    public EmployeeResponseDTO getMyDetails(String email) {
        Employee employee = employeeRepository.findByEmail(email).orElseThrow(()-> new RuntimeException( "Employee Not Found"));
        return mapToDTO(employee);
    }


    public List<EmployeeResponseDTO> getEmployees(){
        return employeeRepository.findAll()
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());

    }

    public List<EmployeeResponseDTO> getEmployeesByDepartment(Long departmentId){
        return employeeRepository.findByDepartment_Id(departmentId)
                .stream()
                .map(employee -> {
                    EmployeeResponseDTO dto = new EmployeeResponseDTO();
                    dto.setFirstName(employee.getFirstName());
                    dto.setLastName(employee.getLastName());
                    dto.setDepartmentName(employee.getDepartment() != null ? employee.getDepartment().getName() : null) ;
                    dto.setStatus(employee.getStatus());
                    dto.setRole(employee.getRole());
                    return dto;
                })
                .collect(Collectors.toList());
    }




        public EmployeeResponseDTO addEmployee(EmployeeRequestDTO employeeRequest){
//        if (employeeRepository.existsByFirstNameAndLastName(employeeRequest.getFirstName(),employeeRequest.getLastName())){
//                throw new RuntimeException("Employee already exist");
//            }

        if(employeeRepository.existsByEmail(employeeRequest.getEmail())){
            throw  new RuntimeException("Email already exists");
        }

        Employee employee = new Employee();
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        employee.setRole(employeeRequest.getRole());
        employee.setEmail(employeeRequest.getEmail());
        if (employeeRequest.getPassword() == null || employeeRequest.getPassword().isBlank()) {
            throw new RuntimeException("Password is required");
        }
        employee.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
        employee.setStatus(employeeRequest.getStatus());

        Department dept = departmentRepository.findById(employeeRequest.getDepartment_id()).orElseThrow();
        employee.setDepartment(dept);

        Employee savedEmployee = employeeRepository.save(employee);

        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        responseDTO.setDepartmentName(savedEmployee.getDepartment().getName());
        responseDTO.setFirstName(savedEmployee.getFirstName());
        responseDTO.setLastName(savedEmployee.getLastName());
        responseDTO.setRole(savedEmployee.getRole());
        responseDTO.setStatus(savedEmployee.getStatus());

        return responseDTO;

    }

    public EmployeeResponseDTO updateEmployee(Long id, EmployeeRequestDTO employeeRequest){

        Employee employee = employeeRepository.findById(id).orElseThrow();
// Don't Update email
//        if (employeeRepository.existsByEmailAndEmployeeIdNot(employeeRequest.getEmail(), employee.getEmployeeId())){
//            throw new RuntimeException("Email already exists");
//        }

//        if(employeeRepository.existsByFirstNameAndLastNameAndEmployeeIdNot(employeeRequest.getFirstName(), employeeRequest.getLastName(), employee.getEmployeeId())){
//            throw new RuntimeException("Employee already exists");
//        }

        employee.setEmail(employeeRequest.getEmail());
        employee.setRole(employeeRequest.getRole());
        employee.setFirstName(employeeRequest.getFirstName());
        employee.setLastName(employeeRequest.getLastName());
        if (employeeRequest.getPassword() != null && !employeeRequest.getPassword().isBlank()) {
            employee.setPassword(passwordEncoder.encode(employeeRequest.getPassword()));
        }
        Department dept = departmentRepository.findById(employeeRequest.getDepartment_id()).orElseThrow();
        employee.setDepartment(dept);

        employeeRepository.save(employee);

        EmployeeResponseDTO responseDTO = new EmployeeResponseDTO();
        responseDTO.setFirstName(employee.getFirstName());
        responseDTO.setLastName(employee.getLastName());
        responseDTO.setDepartmentName(employee.getDepartment().getName());
        responseDTO.setRole(employee.getRole());
        responseDTO.setStatus(employee.getStatus());


        return responseDTO;


    }


    public String removeEmployee(Long id){
        employeeRepository.deleteById(id);
        return "Employee Removed";
    }


}
