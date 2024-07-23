package pl.bialek.employeerest.infrastructure.database.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.bialek.employeerest.infrastructure.database.entity.EmployeeEntity;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {
    List<EmployeeEntity> findAll();
    Optional<EmployeeEntity> findById(Integer integer);
}
