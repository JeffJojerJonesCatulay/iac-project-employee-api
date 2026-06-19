package com.employee.restapi.controller;

import com.employee.restapi.entity.EmployeeEntity;
import com.employee.restapi.responseHandler.Response;
import com.employee.restapi.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("${api.url.mapping}")
public class main {

    @Autowired
    private EmployeeService service;

    ResponseEntity<Object> response;

    @GetMapping("/get/")
    public ResponseEntity<Object> getEmployee(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());
            Page<EmployeeEntity> data = service.getEmployee(pageable);
            response = Response.generateResponse("Success", HttpStatus.OK, data);
        } catch (DataIntegrityViolationException | IllegalArgumentException e) {
            response = Response.generateResponse("ERROR", HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            response = Response.generateResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }

        return response;
    }

    @PostMapping("/create/")
    public ResponseEntity<Object> createEmployee(@RequestBody EmployeeEntity employee) {
        try {
            EmployeeEntity responses = service.createEmployee(employee);
            response = Response.generateResponse("Success", HttpStatus.OK, responses);
        } catch (DataIntegrityViolationException e) {
            response = Response.generateResponse("ERROR", HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            response = Response.generateResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return response;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Object> updateEmployee(@PathVariable Long id, @RequestBody EmployeeEntity employee) {
        try {
            employee.setId(id);
            EmployeeEntity responses = service.createEmployee(employee);
            response = Response.generateResponse("Success", HttpStatus.OK, responses);
        } catch (DataIntegrityViolationException e) {
            response = Response.generateResponse("ERROR", HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            response = Response.generateResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return response;
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Long id){
        try {
            service.deleteEmployee(id);
            response = Response.generateResponse("Success", HttpStatus.OK);
        } catch (DataIntegrityViolationException e) {
            response = Response.generateResponse("ERROR", HttpStatus.BAD_REQUEST, null);
        } catch (Exception e) {
            response = Response.generateResponse("ERROR", HttpStatus.INTERNAL_SERVER_ERROR, null);
        }
        return response;
    }
}

