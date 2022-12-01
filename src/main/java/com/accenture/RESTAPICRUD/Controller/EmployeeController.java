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

@RestController
@RequestMapping("/api")
public class EmployeeController {
    //ResponseEntity - controller
    @Autowired
    private EmployeeService service;

    @PostMapping(path="/employee", consumes = "application/json", produces = "application/json")
    public Employee addEmployee(@RequestBody Employee employee) throws Exceptions {
        return service.addEmployee(employee);
    }

    @GetMapping(path="/employee/{id}", produces = "application/json")
    public ResponseEntity<Employee> getEmployeeById(
            @PathVariable(value = "id") Long employeeId, @RequestBody Employee employee)
    throws ResourceNotFoundException{
        return service.findEmployeeById(employeeId);
    }

    @DeleteMapping("/employee/{id}")
    public ResponseEntity<Employee> deleteEmployee(
            @PathVariable(value = "id") long employeeId) throws ResourceNotFoundException {
        return service.deleteEmployeeById(employeeId);
    }

    @PutMapping("/employee/{id}")
    public ResponseEntity<Employee> updateEmployee(
            @PathVariable Long id, @RequestBody Employee employee)
            throws Exception{
        return service.updateEmployee(id, employee);
    }

    @GetMapping(path ="/allEmployees", produces = "application/json")
    public List<Employee> getAllEmployee(Employee employee) {
        return service.getAllEmployee();
    }

}
