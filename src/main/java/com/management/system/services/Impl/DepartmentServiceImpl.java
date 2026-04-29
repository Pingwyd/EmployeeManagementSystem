package com.management.system.services.Impl;
import com.management.system.dto.Department.DepartmentRequestDTO;
import com.management.system.dto.Department.DepartmentResponseDTO;
import com.management.system.repositories.DepartmentRepository;
import com.management.system.entities.Department;
import com.management.system.services.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DepartmentServiceImpl implements DepartmentService {

    final private DepartmentRepository departmentRepository;


    public List<DepartmentResponseDTO> getDepartment(){
        return departmentRepository.findAll()
                .stream()
                .map(department ->
                {
                    DepartmentResponseDTO dto  = new DepartmentResponseDTO();
                    dto.setName(department.getName());
                    dto.setCreatedAt(department.getCreatedAt());
                    dto.setUpdatedAt(department.getUpdatedAt());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    public DepartmentResponseDTO getDepartmentById(Long id) {
        Department dept = departmentRepository.findById(id)
                .orElseThrow(()-> new UsernameNotFoundException("Department Not Found"));

        DepartmentResponseDTO dto = new DepartmentResponseDTO();
        dto.setName(dept.getName());
        dto.setCreatedAt(dept.getCreatedAt());
        dto.setUpdatedAt(dept.getUpdatedAt());
        return dto;
    }


    public DepartmentResponseDTO addDepartment(DepartmentRequestDTO departmentRequest){
        if(departmentRepository.existsByName(departmentRequest.getName())){
            throw new RuntimeException("Department Already exists");
        }

        Department dept = new Department();
        dept.setName(departmentRequest.getName());

        Department newDept = departmentRepository.save(dept);

        DepartmentResponseDTO dro = new DepartmentResponseDTO();
        dro.setName(newDept.getName());
        dro.setCreatedAt(newDept.getCreatedAt());
        dro.setUpdatedAt(newDept.getUpdatedAt());

        return dro;


    }

    public DepartmentResponseDTO updateDepartment(Long id , DepartmentRequestDTO departmentRequestDTO) {
        Department department = departmentRepository.findById(id).orElseThrow();

        if (departmentRepository.existsByNameAndIdNot(departmentRequestDTO.getName(), id)){
            throw new RuntimeException("Department Already Exists");
        }

        department.setName(departmentRequestDTO.getName());

        Department updatedDept = departmentRepository.save(department);

        DepartmentResponseDTO dro = new DepartmentResponseDTO();
        dro.setName(updatedDept.getName());
        dro.setCreatedAt(updatedDept.getCreatedAt());
        dro.setUpdatedAt(updatedDept.getUpdatedAt());

        return dro;
    }

    public String removeDepartment(Long id){
        departmentRepository.deleteById(id);
        return "Removed Successfully";
    }



}
