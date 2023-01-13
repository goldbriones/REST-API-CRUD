package com.accenture.RESTAPICRUD.Service;

import com.accenture.RESTAPICRUD.Domain.Employee;
import com.accenture.RESTAPICRUD.Exception.Exceptions;
import com.accenture.RESTAPICRUD.Repository.DAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<Employee> findEmployeeById(long employeeId) {
        return repository.findById(employeeId);
    }


    public Employee updateEmployeeById(Employee employeeUpdate)  {
        return repository.save(employeeUpdate);
    }

    public Employee validateAddRequest (Employee employeeRequest) throws Exceptions {

        // FirstName Validation****
        // Not Empty
        if (employeeRequest.getFirstName().isEmpty()
                && employeeRequest.getLastName().isEmpty()
                && employeeRequest.getRoleName().isEmpty()
                && employeeRequest.getEmailId().isEmpty()) {
            throw new Exceptions(Exceptions.NULL_ERROR);
        }
        // More Than 3 Characters & Less Than 30 Characters
        if (employeeRequest.getFirstName().length() <= 2 || employeeRequest.getFirstName().length() >=30
                 && employeeRequest.getLastName().length() <=2 || employeeRequest.getLastName().length() >=30
                && employeeRequest.getRoleName().length() <=2 || employeeRequest.getRoleName().length() >=40) {
            throw new Exceptions(Exceptions.NUMBER_OF_CHAR_FIRSTNAME);
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
        // A-Z a-z with whitespace
        if (!Pattern.matches("[a-zA-Z\\s]*", employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_LAST_NAME_FORMAT);
        }
        // check if only contains space
        if (Pattern.matches("[\\s]*", employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_LAST_NAME_FORMAT);
        }
        //RoleName********
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
        if (employeeRequest.getFirstName().isEmpty()
        && employeeRequest.getLastName().isEmpty()
        && employeeRequest.getRoleName().isEmpty()
        && employeeRequest.getEmailId().isEmpty()) {
            throw new Exceptions(Exceptions.NULL_ERROR);
        }
        // More Than 3 Characters & Less Than 30 Characters
        if (employeeRequest.getFirstName().length() <= 2 || employeeRequest.getFirstName().length() >=30
                && employeeRequest.getLastName().length() <=2 || employeeRequest.getLastName().length() >=30
                && employeeRequest.getRoleName().length() <=2 || employeeRequest.getRoleName().length() >=40) {
            throw new Exceptions(Exceptions.NUMBER_OF_CHAR_FIRSTNAME);
        }

        // A-Z a-z with whitespace
        if (!Pattern.matches("[a-zA-Z\\s]*" , employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_FIRST_NAME_FORMAT);
        }
        // check if only contains space
        if (Pattern.matches("[\\s]*", employeeRequest.getFirstName())) {
            throw new Exceptions(Exceptions.INVALID_FIRST_NAME_FORMAT);
        }
        //LastName****
        // A-Z a-z with whitespace
        if (!Pattern.matches("[a-zA-Z\\s]*", employeeRequest.getLastName())) {
            throw new Exceptions(Exceptions.INVALID_LAST_NAME_FORMAT);
        }
        // check if only contains space
        if (Pattern.matches("[\\s]*", employeeRequest.getLastName())) {
            throw new Exceptions(Exceptions.INVALID_LAST_NAME_FORMAT);
        }
        //RoleName********
        // A-Z a-z with whitespace
        if (!Pattern.matches("[a-zA-Z\\s]*", employeeRequest.getRoleName())) {
            throw new Exceptions(Exceptions.INVALID_ROLE_NAME_FORMAT);
        }
        // check if only contains space
        if (Pattern.matches("[\\s]*", employeeRequest.getRoleName())) {
            throw new Exceptions(Exceptions.INVALID_ROLE_NAME_FORMAT);
        }
        // Email Validation*******
        // Regex A-Z a-z 0-9 _ . - and with @ .
        if (!Pattern.matches("^[A-Za-z0-9+_.-]+@(.+)$", employeeRequest.getEmailId())) {
            throw new Exceptions(Exceptions.INVALID_EMAIL_FORMAT);
        }
        return employeeRequest;
    }

    public String deleteEmployeeById(long id) {
        repository.deleteById(id);
        return "Employee ID " + id + " is successfully Deleted in system.";
    }
}
