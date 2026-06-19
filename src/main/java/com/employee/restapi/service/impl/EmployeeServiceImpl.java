package com.employee.restapi.service.impl;

import com.employee.restapi.entity.EmployeeEntity;
import com.employee.restapi.repository.EmployeeRepo;
import com.employee.restapi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeRepo repo;

    @Override
    public Page<EmployeeEntity> getEmployee(Pageable pageable) {
        return repo.getEmployee(pageable);
    }

    @Override
    public EmployeeEntity createEmployee(EmployeeEntity employee) {
        return repo.save(employee);
    }

    @Override
    public EmployeeEntity updateEmployee(EmployeeEntity employee, Long id) {
        employee.setId(id);
        return repo.save(employee);
    }

    @Override
    public void deleteEmployee(Long id) {
        repo.deleteById(id);
    }
}
