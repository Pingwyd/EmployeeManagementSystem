package com.management.system.bootstrap;

import com.management.system.entities.Department;
import com.management.system.entities.Employee;
import com.management.system.entities.Role;
import com.management.system.repositories.DepartmentRepository;
import com.management.system.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateAccounts implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final PasswordEncoder passwordEncoder;



    @Override
    public void run(String... args) throws Exception {

        Department temp = new Department();
        temp.setName("Operations");

        Department defaultDept = departmentRepository.findByName("Operations").orElseGet(()-> departmentRepository.save(temp));

        seedAdmin(defaultDept);
        seedManager(defaultDept);
        seedEmployee(defaultDept);
    }

    private void seedAdmin(Department department) {
        if(employeeRepository.existsByEmail("admin@company.com")){
            return;
        }
        else {
            Employee admin = new Employee();
            admin.setEmail("admin@company.com");
            admin.setFirstName("System");
            admin.setLastName("Admin");
            admin.setPassword(passwordEncoder.encode("Admin@123"));
            admin.setRole(Role.ADMIN);
            admin.setDepartment(department);
            admin.setStatus("ACTIVE");
            admin.setEnabled(true);

            employeeRepository.save(admin);
        }
    }

    private void seedManager(Department department) {
        if(employeeRepository.existsByEmail("manager@company.com")){
            return;
        }
        else {
            Employee manager = new Employee();
            manager.setEmail("manager@company.com");
            manager.setFirstName("Manager");
            manager.setLastName("Manager");
            manager.setPassword(passwordEncoder.encode("Manager@123"));
            manager.setRole(Role.MANAGER);
            manager.setDepartment(department);
            manager.setStatus("ACTIVE");
            manager.setEnabled(true);
            employeeRepository.save(manager);

        }
    }

    private void seedEmployee(Department department) {
        if(employeeRepository.existsByEmail("employee@company.com")){
            return;
        }
        else {
            Employee employee = new Employee();
            employee.setEmail("employee@company.com");
            employee.setFirstName("Employee");
            employee.setLastName("Employee");
            employee.setPassword(passwordEncoder.encode("Employee@123"));
            employee.setRole(Role.EMPLOYEE);
            employee.setStatus("ACTIVE");
            employee.setDepartment(department);
            employee.setEnabled(true);
            employeeRepository.save(employee);
        }
    }


}
