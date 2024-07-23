package pl.bialek.employeerest.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import pl.bialek.employeerest.api.DTO.EmployeeDTO;
import pl.bialek.employeerest.infrastructure.database.entity.EmployeeEntity;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface EmployeeMapper {
    EmployeeDTO toDTO(EmployeeEntity employeeEntity);

}
