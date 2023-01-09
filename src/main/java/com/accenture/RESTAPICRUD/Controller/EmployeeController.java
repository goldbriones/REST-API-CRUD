package com.accenture.RESTAPICRUD.Controller;

import com.accenture.RESTAPICRUD.Entity.Employee;
import com.accenture.RESTAPICRUD.Exception.Exceptions;
import com.accenture.RESTAPICRUD.Exception.ResourceNotFoundException;
import com.accenture.RESTAPICRUD.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class EmployeeController {
    @Autowired
    private EmployeeService service;

    @PostMapping(path="/add/employee", consumes = "application/json", produces = "application/json")
    public Employee saveEmployee(@RequestBody Employee employeeRequest) throws Exceptions {
        return service.saveEmployee(service.validateAddRequest(employeeRequest));
    }

    @GetMapping(path="/EmployeeLists", produces = "application/json")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        return ResponseEntity.ok(service.getAllEmployee());
    }

    @GetMapping(path="/employee/{id}", produces = "application/json")
    public ResponseEntity<Optional<Employee>> getEmployee(@PathVariable long id) {
        return ResponseEntity.ok(service.GetEmployee(id));
    }
    @PutMapping(path="/employee/{id}", produces = "application/json")
    public Employee updateEmployee(@PathVariable long id, @RequestBody Employee employeeRequest)
            throws Exceptions {
        Employee forUpdate = Employee.build(id,
                employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getEmailId(),
                employeeRequest.getRoleName());
        return service.updateEmployeeById(service.validateUpdateRequest(forUpdate));
    }
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Employee> deleteEmployee(
            @PathVariable(value = "id") long employeeId) throws ResourceNotFoundException {
        return service.deleteById(employeeId);
    }
    }

