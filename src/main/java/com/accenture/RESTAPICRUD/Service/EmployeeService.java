package com.accenture.RESTAPICRUD.Service;

import com.accenture.RESTAPICRUD.Entity.Employee;
import com.accenture.RESTAPICRUD.Exception.ResourceNotFoundException;
import com.accenture.RESTAPICRUD.Repository.DAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;


@Service
public class EmployeeService {
    private static final Logger log =
            LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    private DAO repository;

    public Employee addEmployee(Employee employee) {
        repository.save(employee);
        log.info("-----New employee added to repository -----" +
                employee.toString());
        return repository.save(employee);
    }

    public ResponseEntity<Employee> deleteEmployeeById(
            @PathVariable(value = "id") Long employeeId)
            throws ResourceNotFoundException {
        repository.findById(employeeId).orElseThrow(
                ()-> new ResourceNotFoundException
                        ("Employee not found for this request ID :" + employeeId));

        repository.deleteById(employeeId);
        log.info("-----Employee details deleted to repository -----"
                + employeeId.toString());
        return ResponseEntity.ok().build();
    }

    public List<Employee> getAllEmployee() {
     return repository.findAll();
    }

    public ResponseEntity<Employee> findEmployeeById(@PathVariable(value = "id") long employeeId)
            throws ResourceNotFoundException {
        Employee employee = repository.findById(employeeId).orElseThrow(
                ()-> new ResourceNotFoundException
                        ("Employee not found for this request ID :" + employeeId));
        log.info("\n- Employee ID -" + employeeId +  "\n-- Employee Details---" +
                employee.toString());
        return ResponseEntity.ok().body(employee);
    }

    public ResponseEntity<Employee> updateEmployee (
            @PathVariable(value = "id") long employeeId, @RequestBody Employee employeeDetails)
            throws ResourceNotFoundException {
        Employee employee = repository.findById(employeeId).orElseThrow(
                ()-> new ResourceNotFoundException
                        ("Employee NOT FOUND for Updating Employee ID : " + employeeId));
        employee.setFirstName(employeeDetails.getFirstName());
        employee.setLastName(employeeDetails.getLastName());
        employee.setEmailId(employeeDetails.getEmailId());
        employee.setRoleName(employeeDetails.getRoleName());
        repository.save(employee);
        log.info("\n-----Employee details updated to repository -----" + employeeId);
        return ResponseEntity.ok().body(employee);
    }
}
