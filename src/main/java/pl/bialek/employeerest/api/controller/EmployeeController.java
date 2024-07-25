package pl.bialek.employeerest.api.controller;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

import java.math.BigDecimal;

import static pl.bialek.employeerest.api.controller.EmployeeController.EMPLOYEES;

@RestController
@RequestMapping(EMPLOYEES)
@AllArgsConstructor
public class EmployeeController {
    //Normally we should use service layer, but for this task we can skip it
    public static final String EMPLOYEE_ID = "/{employeeId}";
    public static final String EMPLOYEE_UPDATE_SALARY = "/{employeeId}/salary";
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

        HttpHeaders headers = new HttpHeaders();
        headers.add("Location",
                EMPLOYEES + EMPLOYEE_ID_RESULT.
                        formatted(createdEmployee.getEmployeeId()));

      return new ResponseEntity<>(headers, HttpStatus.CREATED);
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
    ) {

        EmployeeEntity existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "EmployeeEntity not found, employeeId: [%s]".formatted(employeeId)
                ));
        employeeRepository.deleteById(existingEmployee.getEmployeeId());
        return ResponseEntity.noContent().build();

    }

    @PatchMapping
    public ResponseEntity<?> updateEmployeeSalary(
            @PathVariable Integer employeeId,
            @RequestParam(required = true) BigDecimal newSalary
    ) {
        EmployeeEntity employeeEntity = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException(employeeId));
        employeeEntity.setSalary(newSalary);
        employeeRepository.save(employeeEntity);
    return ResponseEntity.ok().build();
    }


}





















