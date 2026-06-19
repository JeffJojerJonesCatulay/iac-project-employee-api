package com.employee.restapi.service;

import com.employee.restapi.entity.EmployeeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface EmployeeService {
    Page<EmployeeEntity> getEmployee(Pageable pageable);
    EmployeeEntity createEmployee(EmployeeEntity employee);
    EmployeeEntity updateEmployee(EmployeeEntity employee, Long id);
    void deleteEmployee(Long id);
}
