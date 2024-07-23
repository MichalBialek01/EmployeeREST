package pl.bialek.employeerest.api.DTO;

import lombok.*;
import java.util.List;
@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class EmployeesDTO {
    private List<EmployeeDTO> employees;
}
