package pl.bialek.employeerest.api.controller;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import pl.bialek.employeerest.api.dto.EmployeeDTO;
import pl.bialek.employeerest.api.dto.EmployeesDTO;
import pl.bialek.employeerest.api.mapper.EmployeeMapper;
import pl.bialek.employeerest.infrastructure.database.repository.EmployeeRepository;
import pl.bialek.employeerest.infrastructure.exception.EmployeeNotFoundException;

import static pl.bialek.employeerest.api.controller.EmployeeController.EMPLOYEES;

@RestController
@RequestMapping(EMPLOYEES)
@AllArgsConstructor
public class EmployeeController {

    //Normally we should use service layer, but for this task we can skip it
    private EmployeeRepository employeeRepository;

    private EmployeeMapper employeeMapper;

    public static final String EMPLOYEES = "/employees";

    public static final String EMPLOYEE_ID = "/{employeeId}";


    @GetMapping //localhost:8190/RestApp/emplyees
    public EmployeesDTO employeesList(){
      return  EmployeesDTO.of(employeeRepository.findAll()
                .stream()
                .map(employeeMapper::mapstructMapper)
                .toList());
    }

    @GetMapping(EMPLOYEE_ID) //localhost:8190/RestApp/emplyees/{employeeId}
    public EmployeeDTO employeeDetails(@PathVariable Integer employeeId){
        return employeeRepository.findById(employeeId)
                .map(employeeMapper::mapstructMapper)
                .orElseThrow(()-> new EmployeeNotFoundException(employeeId));
    }

}





















