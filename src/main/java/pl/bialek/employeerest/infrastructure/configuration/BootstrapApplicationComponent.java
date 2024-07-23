package pl.bialek.employeerest.infrastructure.configuration;

import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pl.bialek.employeerest.infrastructure.database.entity.EmployeeEntity;
import pl.bialek.employeerest.infrastructure.database.repository.EmployeeRepository;

import java.math.BigDecimal;

@Slf4j
@Component
@AllArgsConstructor
public class BootstrapApplicationComponent implements ApplicationListener<ContextRefreshedEvent> {

    private final EmployeeRepository employeeRepository;

    @Override
    @Transactional
    public void onApplicationEvent(final @NonNull ContextRefreshedEvent event) {
        if (employeeRepository.findAll().isEmpty()) {
            log.info("No employees found in the database. Initializing data...");
            addInitialData();
        } else {
            log.info("Employees already exist in the database. Skipping initialization.");
        }
    }

    private void addInitialData() {
        employeeRepository.save(EmployeeEntity.builder()
                .name("Michał")
                .surname("Zajavka")
                .salary(new BigDecimal("52322.00"))
                .phone("+48 548 665 441")
                .email("zajavka@zajavka.com")
                .build());

        employeeRepository.save(EmployeeEntity.builder()
                .name("Agnieszka")
                .surname("Spring")
                .salary(new BigDecimal("62341.00"))
                .phone("+48 854 115 332")
                .email("mail@mail.com")
                .build());

        employeeRepository.save(EmployeeEntity.builder()
                .name("Tomasz")
                .surname("Hibernate")
                .salary(new BigDecimal("53231.00"))
                .phone("+48 745 554 445")
                .email("zajavka@email.com")
                .build());
    }
}
