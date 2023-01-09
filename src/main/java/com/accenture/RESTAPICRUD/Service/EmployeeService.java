package com.accenture.RESTAPICRUD.Service;

import com.accenture.RESTAPICRUD.Entity.Employee;
import com.accenture.RESTAPICRUD.Exception.Exceptions;
import com.accenture.RESTAPICRUD.Exception.ResourceNotFoundException;
import com.accenture.RESTAPICRUD.Repository.DAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;


@Service
public class EmployeeService {
    private static final Logger log =
            LoggerFactory.getLogger(EmployeeService.class);
    @Autowired
    private DAO repository;

    //New Service without ResponseEntity
    public Employee saveEmployee(Employee employeeRequest) {
        Employee employee = Employee.build(0,
                employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getEmailId(),
                employeeRequest.getRoleName());
        Employee savedEmployee = repository.save(employee);
        log.info(savedEmployee.toString());
        return savedEmployee;
    }

    public List<Employee> getAllEmployee() {
        return repository.findAll();
    }

    public Optional<Employee> GetEmployee(Long employeeId) {
        return repository.findById(employeeId);
    }


    public Employee updateEmployeeById(Employee employeeRequestWithID) {
        return repository.save(employeeRequestWithID);

    }

    public Employee validateAddRequest (Employee employeeRequest) throws Exceptions {

        // FirstName Validation****
        // Not Empty
        if (employeeRequest.getFirstName().isEmpty()) {
            throw new Exceptions(Exceptions.NULL_FIRSTNAME);
        }
        // More Than 3 Characters
        if (employeeRequest.getFirstName().length() <= 2) {
            throw new Exceptions(Exceptions.SHORT_FIRSTNAME);
        }
        // Less Than 30 Characters
        if (employeeRequest.getFirstName().length() > 30) {
            throw new Exceptions(Exceptions.LONG_FIRSTNAME);
        }
        // A-Z a-z with whitespace
        if (!Pattern.matches("[a-zA-Z\\s]*", employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_FIRST_NAME_FORMAT);
        }
        // check if only contains space
        if (Pattern.matches("[\\s]*", employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_FIRST_NAME_FORMAT);
        }

        //LastName****
        if (employeeRequest.getLastName().isEmpty()) {
            throw new Exceptions(Exceptions.NULL_LASTNAME);
        }
        // More Than 3 Characters
        if (employeeRequest.getLastName().length() <= 2) {
            throw new Exceptions(Exceptions.SHORT_LASTNAME);
        }
        // Less Than 30 Characters
        if (employeeRequest.getFirstName().length() > 30) {
            throw new Exceptions(Exceptions.LONG_LASTNAME);
        }
        // A-Z a-z with whitespace
        if (!Pattern.matches("[a-zA-Z\\s]*", employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_LAST_NAME_FORMAT);
        }
        // check if only contains space
        if (Pattern.matches("[\\s]*", employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_LAST_NAME_FORMAT);
        }
        //RoleName********
        if (employeeRequest.getLastName().isEmpty()) {
            throw new Exceptions(Exceptions.NULL_ROLENAME);
        }
        // More Than 3 Characters
        if (employeeRequest.getLastName().length() <= 1) {
            throw new Exceptions(Exceptions.SHORT_ROLENAME);
        }
        // Less Than 30 Characters
        if (employeeRequest.getFirstName().length() > 40) {
            throw new Exceptions(Exceptions.LONG_ROLENAME);
        }
        // A-Z a-z with whitespace
        if (!Pattern.matches("[a-zA-Z\\s]*", employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_ROLE_NAME_FORMAT);
        }
        // check if only contains space
        if (Pattern.matches("[\\s]*", employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_ROLE_NAME_FORMAT);
        }
        // Email Validation*******
        // Regex A-Z a-z 0-9 _ . - and with @ .
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", employeeRequest.getEmailId())) {
            throw new Exceptions(Exceptions.INVALID_EMAIL_FORMAT);
        }

        return employeeRequest;
    }

    public Employee validateUpdateRequest(Employee employeeRequest) throws Exceptions {

        // FirstName Validation****
        // Not Empty
        if (employeeRequest.getFirstName().isEmpty()) {
            throw new Exceptions(Exceptions.NULL_FIRSTNAME);
        }
        // More Than 3 Characters
        if (employeeRequest.getFirstName().length() <= 2) {
            throw new Exceptions(Exceptions.SHORT_FIRSTNAME);
        }
        // Less Than 30 Characters
        if (employeeRequest.getFirstName().length() > 30) {
            throw new Exceptions(Exceptions.LONG_FIRSTNAME);
        }
        // A-Z a-z with whitespace
        if (!Pattern.matches("[a-zA-Z\\s]*", employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_FIRST_NAME_FORMAT);
        }
        // check if only contains space
        if (Pattern.matches("[\\s]*", employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_FIRST_NAME_FORMAT);
        }

        //LastName****
        if (employeeRequest.getLastName().isEmpty()) {
            throw new Exceptions(Exceptions.NULL_LASTNAME);
        }
        // More Than 3 Characters
        if (employeeRequest.getLastName().length() <= 2) {
            throw new Exceptions(Exceptions.SHORT_LASTNAME);
        }
        // Less Than 30 Characters
        if (employeeRequest.getFirstName().length() > 30) {
            throw new Exceptions(Exceptions.LONG_LASTNAME);
        }
        // A-Z a-z with whitespace
        if (!Pattern.matches("[a-zA-Z\\s]*", employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_LAST_NAME_FORMAT);
        }
        // check if only contains space
        if (Pattern.matches("[\\s]*", employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_LAST_NAME_FORMAT);
        }
        //RoleName********
        if (employeeRequest.getLastName().isEmpty()) {
            throw new Exceptions(Exceptions.NULL_ROLENAME);
        }
        // More Than 3 Characters
        if (employeeRequest.getLastName().length() <= 1) {
            throw new Exceptions(Exceptions.SHORT_ROLENAME);
        }
        // Less Than 30 Characters
        if (employeeRequest.getFirstName().length() > 40) {
            throw new Exceptions(Exceptions.LONG_ROLENAME);
        }
        // A-Z a-z with whitespace
        if (!Pattern.matches("[a-zA-Z\\s]*", employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_ROLE_NAME_FORMAT);
        }
        // check if only contains space
        if (Pattern.matches("[\\s]*", employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_ROLE_NAME_FORMAT);
        }
        // Email Validation*******
        // Regex A-Z a-z 0-9 _ . - and with @ .
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", employeeRequest.getEmailId())) {
            throw new Exceptions(Exceptions.INVALID_EMAIL_FORMAT);
        }

        return employeeRequest;
    }

    public ResponseEntity<Employee> deleteById(
            @PathVariable(value = "id") long employeeId)
            throws ResourceNotFoundException {
        repository.findById(employeeId).orElseThrow(
                ()-> new ResourceNotFoundException
                        ("Employee NOT FOUND for this request ID :" + employeeId));

        repository.deleteById(employeeId);
        log.info("-----Employee details deleted to repository -----"
                + employeeId);
        return ResponseEntity.ok().build();
    }

}
