package com.accenture.RESTAPICRUD;

import com.accenture.RESTAPICRUD.Controller.EmployeeController;
import com.accenture.RESTAPICRUD.Domain.Employee;
import com.accenture.RESTAPICRUD.Exception.Exceptions;
import com.accenture.RESTAPICRUD.Service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private EmployeeService service;
    @Autowired
    ObjectMapper mapper;

    @Test
    void testAddEmployeeRequest() throws Exceptions,Exception {
        Employee newEmployee = new Employee(
                "Gold",
                "Briones",
                "Mockemail@gmail.com",
                "Java Developer");

        when(service.saveEmployee(newEmployee)).thenReturn(newEmployee);

        mvc.perform(post("/api/add/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(newEmployee)))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    void testGetAllEmployees() throws Exception {
        List<Employee> employeeList = Arrays.asList(new Employee
                (
                        "Mark",
                        "Herras",
                        "herras@gmail.com",
                        "Senior Developer"), new Employee
                (
                        "Juan",
                        "Miguel",
                        "Miguelito@gmail.com",
                        "Tester"));

        when(service.getAllEmployee()).thenReturn(employeeList);

        mvc.perform(get("/api/EmployeeLists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));

//       ***** You can set also this expectations ******
//         .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].name", is("Ann")))
//                .andExpect(jsonPath("$[0].products", hasSize(2)))
//                .andExpect(jsonPath("$[1].name", is("John")))
//                .andExpect(jsonPath("$[1].products", hasSize(1)));
    }

    @Test
    public void testfindEmployeeById() throws Exception {
        long employeeId = 1;
        Employee newEmployee = new Employee(
                "Shaina",
                "Dominguez",
                "Shaina@gmail.com",
                "Java Developer"
        );
        newEmployee.setId(employeeId);
        when(service.findEmployeeById(employeeId)).thenReturn(Optional.of(newEmployee));

        Employee request = service.findEmployeeById(employeeId).get();

        assertEquals("Shaina", request.getFirstName());
        assertEquals("Dominguez", request.getLastName());
        assertEquals("Shaina@gmail.com", request.getEmailId());
        assertEquals("Java Developer", request.getRoleName());
    }

//        mvc.perform(get("/employee/{id}", 1L)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(newEmployee)))
//                .andDo(print())
//                .andExpect(status().isOk());



    @Test
    public void testDeleteEmployeeRequest() throws Exception {
        long employeeId = 1;
        List<Employee> employee1 = new ArrayList<>();

        Employee forDeleteId = new Employee
                (
                        "Mark",
                        "Herras",
                        "herras@gmail.com",
                        "Senior Developer");
        forDeleteId.setId(employeeId);
        employee1.add(forDeleteId);

        assertTrue(employee1.contains(forDeleteId));
        when(service.deleteEmployeeById(employeeId)).thenReturn(String.valueOf(true));

    }


    @Test
    public void testUpdateEmployeeRequest() throws Exceptions,  Exception {
        long employeeId = 1;
        Employee newEmployee = new Employee(
                "Shaina",
                "Dominguez",
                "Shaina@gmail.com",
                "Java Developer"
        );
        newEmployee.setId(employeeId);

        Employee updateEmployee = new Employee(
                "Diego",
                "Maninding",
                "Diego@gmail.com",
                "Java Developer"
        );
        updateEmployee.setId(employeeId);

        when(service.saveEmployee(newEmployee)).thenReturn(newEmployee);

        Employee existingEmployee = service.saveEmployee(newEmployee);
        existingEmployee.setFirstName(updateEmployee.getFirstName());
        existingEmployee.setLastName(updateEmployee.getLastName());
        existingEmployee.setEmailId(updateEmployee.getEmailId());
        existingEmployee.setRoleName(updateEmployee.getRoleName());
        existingEmployee.setId(employeeId);

        assertNotNull(existingEmployee);

        when(service.updateEmployeeById(updateEmployee))
                .thenReturn(existingEmployee);

        Employee updateEmployeeFromDb = service.updateEmployeeById(updateEmployee
        );

        assertEquals(updateEmployeeFromDb, existingEmployee);
    }

    @Test
    @Transactional
    void testAddExceptions() throws JsonProcessingException, Exception {
        Employee exceptionRespond = Employee.build(1,
                " ",
                "Briones",
                "pgsbriones@gmail.com",
                "Java Developer");

        // Null Name
        mvc.perform(post("/add/employee").content(
                mapper.writeValueAsString(exceptionRespond))
                     .contentType(MediaType.APPLICATION_JSON_VALUE)).
                     andExpect(status().isBadRequest()).andReturn();

        // Name Less Than equal 2 Char
        exceptionRespond.setFirstName("x");
        mvc.perform(post("/add/employee").content(
                mapper.writeValueAsString(exceptionRespond))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).
                         andExpect(status().isBadRequest()).andReturn();

        // Name More Than 30 Char
        exceptionRespond.setFirstName("Wolfeschlegelsteinhausenbergerdorff");
        mvc.perform(post("/add/employee").content(
                                mapper.writeValueAsString(exceptionRespond))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isBadRequest()).andReturn();

        // Name Invalid Char
        exceptionRespond.setFirstName("Hubert 23X");
        mvc.perform(post("/add/employee").content(
                                mapper.writeValueAsString(exceptionRespond))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isBadRequest()).andReturn();

        // Name Whitespace Only
        exceptionRespond.setFirstName("         ");
        mvc.perform(post("/add/employee").content(
                                mapper.writeValueAsString(exceptionRespond))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isBadRequest()).andReturn();

        exceptionRespond.setFirstName(" Gold ");


        // Email Validation
        exceptionRespond.setEmailId("androidX!*@gmail.com");
        mvc.perform(post("/add/employee").content(
                                mapper.writeValueAsString(exceptionRespond))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isBadRequest()).andReturn();
        exceptionRespond.setEmailId("pgsbriones@gmail.com");
        //RoleName validation
        exceptionRespond.setRoleName("Seni0r Devel0per");
        mvc.perform(post("/add/employee").content(
                                mapper.writeValueAsString(exceptionRespond))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isBadRequest()).andReturn();

        exceptionRespond.setEmailId("Senior Developer");
    }
    @Test
	void testDeleteEmployee() throws Exception {
        long employeeId = 1;
        Employee employeeNew = new Employee(
                "Mark",
                "Herras",
                "Mock@gmail.com",
                "Java Developer");

        given(service.deleteEmployeeById(employeeId)).willReturn(String.valueOf(Optional.of(employeeNew)));

        this.mvc.perform(MockMvcRequestBuilders.get("/employee/{id}", employeeId)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect((status().isOk()))
                .andExpect((ResultMatcher) jsonPath("$.firstName", is(employeeNew.getFirstName())))
                .andExpect((ResultMatcher) jsonPath("$.lastName", is(employeeNew.getLastName())))
                .andExpect((ResultMatcher) jsonPath("$.emailId", is(employeeNew.getEmailId())))
                .andExpect((ResultMatcher) jsonPath("$.roleName", is(employeeNew.getRoleName())));

    }

    @Test
	void testReturn404WhenEmployeeIsNotFound() throws Exception {
        long employeeId = 1;
        Employee newEmployee = new Employee(
                "Shaina",
                "Dominguez",
                "Shaina@gmail.com",
                "Java Developer"
        );
        newEmployee.setId(employeeId);;

		given(service.findEmployeeById(employeeId)).willReturn(Optional.empty());

		this.mvc.perform(MockMvcRequestBuilders.get("/api/employee/{id}", employeeId)
                        .accept(MediaType.APPLICATION_JSON))
                        .andDo(print())
                        .andExpect((status().isNotFound()));
    }
}


