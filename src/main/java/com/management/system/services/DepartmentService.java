package com.management.system.services;
import com.management.system.dto.DepartmentRequestDTO;
import com.management.system.dto.DepartmentResponseDTO;
import com.management.system.repositories.DepartmentRepository;
import com.management.system.entities.Department;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DepartmentService {

    final private DepartmentRepository departmentRepository;


    public List<DepartmentResponseDTO> getDepartment(){
        return departmentRepository.findAll()
                .stream()
                .map(department ->
                {
                    DepartmentResponseDTO dto  = new DepartmentResponseDTO();
                    dto.setName(department.getName());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public DepartmentResponseDTO addDepartment(DepartmentRequestDTO departmentRequest){
        if(departmentRepository.existsByName(departmentRequest.getName())){
            throw new RuntimeException("Department Already exists");
        }

        Department dept = new Department();
        dept.setId(departmentRequest.getId());
        dept.setName(departmentRequest.getName());

        Department newDept = departmentRepository.save(dept);

        DepartmentResponseDTO dro = new DepartmentResponseDTO();
        dro.setName(newDept.getName());

        return dro;


    }

    public DepartmentResponseDTO updateDepartment(Long id , DepartmentRequestDTO departmentRequestDTO) {
        Department department = departmentRepository.findById(id).orElseThrow();

        if (departmentRepository.existsByNameAndIdNot(departmentRequestDTO.getName(), id)){
            throw new RuntimeException("Department Already Exists");
        }

        department.setName(departmentRequestDTO.getName());
        department.setId(departmentRequestDTO.getId());

        Department updatedDept = departmentRepository.save(department);

        DepartmentResponseDTO dro = new DepartmentResponseDTO();
        dro.setName(updatedDept.getName());

        return dro;
    }

    public String removeDepartment(Long id){
        departmentRepository.deleteById(id);
        return "Removed Successfully";
    }



}
