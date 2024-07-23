package pl.bialek.employeerest.api.controller;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import pl.bialek.employeerest.api.dto.EmployeeDTO;
import pl.bialek.employeerest.api.dto.EmployeesDTO;
import pl.bialek.employeerest.api.mapper.EmployeeMapper;
import pl.bialek.employeerest.infrastructure.database.entity.EmployeeEntity;
import pl.bialek.employeerest.infrastructure.database.repository.EmployeeRepository;
import pl.bialek.employeerest.infrastructure.exception.EmployeeNotFoundException;

import java.net.URI;
import java.util.Optional;

import static pl.bialek.employeerest.api.controller.EmployeeController.EMPLOYEES;

@RestController
@RequestMapping(EMPLOYEES)
@AllArgsConstructor
public class EmployeeController {
    //Normally we should use service layer, but for this task we can skip it
    public static final String EMPLOYEE_ID = "/{employeeId}";
    public static final String EMPLOYEE_ID_RESULT = "/%s";
    public static final String EMPLOYEES = "/employees";
    private EmployeeRepository employeeRepository;

    private EmployeeMapper employeeMapper;


    @GetMapping //localhost:8190/RestApp/emplyees
    public EmployeesDTO employeesList() {
        return EmployeesDTO.of(employeeRepository.findAll()
                .stream()
                .map(employeeMapper::mapstructMapper)
                .toList());
    }

    @GetMapping(value = EMPLOYEE_ID, produces = {
            MediaType.APPLICATION_JSON_VALUE,
            MediaType.APPLICATION_XML_VALUE}) //localhost:8190/RestApp/emplyees/{employeeId}
    public EmployeeDTO employeeDetails(@PathVariable Integer employeeId) {
        return employeeRepository.findById(employeeId)
                .map(employeeMapper::mapstructMapper)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }


    @PostMapping
    @Transactional //Should by one service layer
    public ResponseEntity<EmployeeDTO> addEmployee(
            @RequestBody EmployeeDTO employeeDTO
    ) {
        EmployeeEntity employeeEntity = EmployeeEntity
                .builder()
                .name(employeeDTO.getName())
                .surname(employeeDTO.getSurname())
                .salary(employeeDTO.getSalary())
                .phone(employeeDTO.getPhone())
                .email(employeeDTO.getEmail())
                .build();
        EmployeeEntity createdEmployee = employeeRepository.save(employeeEntity);
        return ResponseEntity
                .created(URI.create(EMPLOYEES + EMPLOYEE_ID_RESULT.
                        formatted(createdEmployee.getEmployeeId()))).build();
        //Setting endpoint status and created resource location
    }

    @PutMapping(EMPLOYEE_ID)
    @Transactional
    public ResponseEntity<?> updateEmployee(
            @PathVariable Integer employeeId,
            @Valid @RequestBody EmployeeDTO employeeDTO
    ) {
        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId).orElseThrow(
                () -> new EmployeeNotFoundException(employeeId));
        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setSurname(employeeDTO.getSurname());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setSalary(employeeDTO.getSalary());
        existingEmployee.setPhone(employeeDTO.getPhone());
        EmployeeEntity savedEmployee = employeeRepository.save(existingEmployee);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(EMPLOYEE_ID)
    public ResponseEntity<?> deleteEmployee(
            @PathVariable Integer employeeId
    ){
        var employeeOpt = employeeRepository.findById(employeeId);
        if(employeeOpt.isPresent()){
            employeeRepository.deleteById(employeeId);
            return ResponseEntity.ok().build();
        }else {
            return ResponseEntity.notFound().build();
        }
    }

}





















