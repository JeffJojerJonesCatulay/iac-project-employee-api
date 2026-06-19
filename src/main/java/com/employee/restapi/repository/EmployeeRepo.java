package com.employee.restapi.repository;

import com.employee.restapi.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepo extends JpaRepository<EmployeeEntity, Long> {
    @Query(value = "SELECT * FROM Employee", nativeQuery = true)
    Page<EmployeeEntity> getEmployee(Pageable pageable);
}
