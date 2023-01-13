package com.accenture.RESTAPICRUD;

import com.accenture.RESTAPICRUD.Controller.EmployeeController;
import com.accenture.RESTAPICRUD.Domain.Employee;
import com.accenture.RESTAPICRUD.Repository.DAO;
import com.accenture.RESTAPICRUD.Service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {


    @Autowired
    private MockMvc mvc;
    @MockBean
    private EmployeeService service;
    @Mock
    private DAO repository;
    @Autowired
    ObjectMapper mapper;

    Employee mockEmployee;

    @BeforeEach
    public void init() {
         mockEmployee = new Employee(
                 "Mock",
                "Santiago",
                "mock@gmail.com",
                "Developer");
         when(repository.save(mockEmployee)).thenReturn(mockEmployee);
    }

    @Test
    void testAddEmployeeRequest() throws Exception {

        mvc.perform(post("/api/add/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockEmployee)))
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

        when(repository.findAll()).thenReturn(employeeList);

        mvc.perform(get("/api/EmployeeLists")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
//                .andExpect(jsonPath("$", hasSize(2)));

//       ***** You can set also this expectations ******
//         .andExpect(jsonPath("$", hasSize(2)))
//                .andExpect(jsonPath("$[0].name", is("Ann")))
//                .andExpect(jsonPath("$[0].products", hasSize(2)))
//                .andExpect(jsonPath("$[1].name", is("John")))
//                .andExpect(jsonPath("$[1].products", hasSize(1)));
    }

    @Test
    public void testfindEmployeeById() throws Exception {

//        when(repository.findOne(mockEmployee.getId())).thenReturn(Optional.of(mockEmployee));

        mvc.perform(get("/api/employee/{id}", mockEmployee.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockEmployee)))
                .andExpect(status().isOk());
    }

    @Test
    void testUpdateEmployee() throws Exception {
//        long employeeId = 1;
//        Employee updateRequest = new Employee(
//                "Gold",
//                "Briones",
//                "mock@gmail.com",
//                "Developer"
//        );
//        updateRequest.setId(employeeId);
//        Employee employeeUpdate1 = Employee.build(
//                1,
//                "Gold",
//                "Briones",
//                "mock@gmail.com",
//                "Java Developer"
//        );
        Employee employeeUpdate2 =  Employee.build(
                1,
                "Gold",
                "Briones",
                "pgs@gmail.com",
                "Java Analyst"
        );

        when(repository.save(mockEmployee)).thenReturn(mockEmployee);

        mvc.perform(put("/api/employee/{id}", mockEmployee.getId())
                .content(mapper.writeValueAsString(employeeUpdate2))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

        assertThat(mockEmployee).usingRecursiveComparison().isNotEqualTo(employeeUpdate2);
    }

    @Test
    void testAddExceptions() throws JsonProcessingException, Exception {
        Employee exceptionRequest = new Employee(
                null,
                "Briones",
                "@gmail.com",
                "Java Developer");

        when(repository.save(exceptionRequest)).thenReturn(exceptionRequest);

        // Null Name
        mvc.perform(post("/api/add/employee").content(
                mapper.writeValueAsString(exceptionRequest))
                     .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isBadRequest()).andReturn();



        // Name Less Than equal 2 Char
        exceptionRequest.setFirstName("x");
        mvc.perform(post("/api/add/employee").content(
                mapper.writeValueAsString(exceptionRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).
                         andExpect(status().isBadRequest()).andReturn();

        // Name More Than 30 Char
        exceptionRequest.setFirstName("Wolfeschlegelsteinhausenbergerdorff");
        mvc.perform(post("/api/add/employee").content(
                                mapper.writeValueAsString(exceptionRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isBadRequest()).andReturn();

        // Name Invalid Char
        exceptionRequest.setFirstName("Hubert 23X");
        mvc.perform(post("/api/add/employee").content(
                                mapper.writeValueAsString(exceptionRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isBadRequest()).andReturn();

        // Name Whitespace Only
        exceptionRequest.setFirstName("         ");
        mvc.perform(post("/api/add/employee").content(
                                mapper.writeValueAsString(exceptionRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isBadRequest()).andReturn();

        exceptionRequest.setFirstName("Gold");


        // Email Validation
        exceptionRequest.setEmailId("androidX!*@gmail.com");
        mvc.perform(post("/api/add/employee").content(
                                mapper.writeValueAsString(exceptionRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isBadRequest()).andReturn();
        exceptionRequest.setEmailId("pgsbriones@gmail.com");
        //RoleName validation
        exceptionRequest.setRoleName("Seni0r Devel0per");
        mvc.perform(post("/api/add/employee").content(
                                mapper.writeValueAsString(exceptionRequest))
                        .contentType(MediaType.APPLICATION_JSON_VALUE)).
                andExpect(status().isBadRequest()).andReturn();

        exceptionRequest.setEmailId("pgsbriones@gmail.com");
    }

//    @Test
//	void testDeleteEmployee() throws Exception {
//        when(repository.deleteById(mockEmployee.getId())).thenReturn("Employee ID " + mockEmployee.getId() + " is successfully Deleted in system.");
//
//        mvc.perform(delete("/api/employee/{id}", "1")
//                        .accept(MediaType.APPLICATION_JSON)
//                        .content(mapper.writeValueAsString(mockEmployee)))
//                .andExpect((status().isOk()));
//    }

    @Test
	void testReturn404WhenEmployeeIsNotFound() throws Exception {
		final long employeeId = 2;
		when(repository.findById(mockEmployee.getId())).thenReturn(Optional.empty());

		this.mvc.perform(get("/api/employee/{id}", employeeId)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect((status().isNotFound()));
    }
}


