package pl.bialek.employeerest.api.DTO;

import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.*;

import java.math.BigDecimal;

@Data
@With
@Builder
@NoArgsConstructor
@AllArgsConstructor(staticName = "of")
public class EmployeeDTO {
    private Integer employeeId;
    private String name;
    private String surname;
    private BigDecimal salary;
    private String phone;
    private String email;
}
