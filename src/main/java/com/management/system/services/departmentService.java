package com.management.system.services;
import com.management.system.repositories.departmentRepository;
import com.management.system.entities.department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
public class departmentService {
    @Autowired
    departmentRepository departmentRepository;


    public List<department> getDepartment(){
        return departmentRepository.findAll();
    }

    public department addDepartment(department department){
        return departmentRepository.save(department);
    }

    public String updateDepartment(Long id , department updateddepartment){
        department currentdepartment = departmentRepository.findById(id).orElseThrow();

        currentdepartment.setName(updateddepartment.getName());
        currentdepartment.setId(updateddepartment.getId());

        departmentRepository.save(currentdepartment);
        return "Department Updated";
    }

    public String removeDepartment(Long id){
        departmentRepository.deleteById(id);
        return "Removed Successfully";
    }





}
