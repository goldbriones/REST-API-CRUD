package com.accenture.RESTAPICRUD.Controller;

import com.accenture.RESTAPICRUD.Domain.Employee;
import com.accenture.RESTAPICRUD.Exception.Exceptions;
import com.accenture.RESTAPICRUD.Exception.ResourceNotFoundException;
import com.accenture.RESTAPICRUD.Service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<Employee> saveEmployee(@RequestBody Employee employeeRequest)
            throws Exceptions {
        return new ResponseEntity<>(service.saveEmployee
                (service.validateAddRequest(employeeRequest)),
                HttpStatus.CREATED);
    }

    @GetMapping(path="/EmployeeLists", produces = "application/json")
    public ResponseEntity<List<Employee>> getAllEmployee() {
        return ResponseEntity.ok(service.getAllEmployee());
    }

    @GetMapping(path="/employee/{id}", produces = "application/json")
    public ResponseEntity<Optional<Employee>> getEmployee(@PathVariable long id)
    throws  Exception
    {
//        service.findEmployeeById(id).orElseThrow(
//                () -> new ResourceNotFoundException(
//                        "Employee NOT FOUND for this request ID : "  + id));
        return ResponseEntity.ok(service.findEmployeeById(id));
    }
    @PutMapping(path="/employee/{id}", produces = "application/json")
    public ResponseEntity<Employee>  updateEmployee(@PathVariable long id,
                                                    @RequestBody Employee employeeRequest)
            throws ResourceNotFoundException , Exceptions
    {
        service.findEmployeeById(id).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Employee NOT FOUND for this request ID : "  + id));
        Employee forUpdate = Employee.build(id,
                employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getEmailId(),
                employeeRequest.getRoleName());
        return new ResponseEntity<Employee>
                (service.updateEmployeeById(
                        service.validateUpdateRequest(forUpdate))
                        ,HttpStatus.OK);
    }
    @DeleteMapping("/employee/{id}")
    public ResponseEntity<String> deleteEmployee(
            @PathVariable(value = "id") long employeeId)
        throws ResourceNotFoundException  {
        service.findEmployeeById(employeeId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Employee NOT FOUND for this request ID : "  + employeeId));
        return ResponseEntity.ok(service.deleteEmployeeById(employeeId));
    }
}

