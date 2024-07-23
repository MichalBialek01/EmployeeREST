package pl.bialek.employeerest.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.bialek.employeerest.api.dto.EmployeeDTO;
import pl.bialek.employeerest.infrastructure.database.entity.EmployeeEntity;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {
//    Fixed issue - How to fix MapStruct in Spring Boot when return null object -
//    Lombok dependency must be added before mapstruct
    EmployeeDTO mapstructMapper(EmployeeEntity employeeEntity);


}
