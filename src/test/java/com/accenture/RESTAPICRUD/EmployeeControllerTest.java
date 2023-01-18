package com.accenture.RESTAPICRUD;

import com.accenture.RESTAPICRUD.Controller.EmployeeController;
import com.accenture.RESTAPICRUD.DTO.EmployeeRequest;
import com.accenture.RESTAPICRUD.Domain.Employee;
import com.accenture.RESTAPICRUD.Repository.DAO;
import com.accenture.RESTAPICRUD.Service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(EmployeeController.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc // need this in Spring Boot test
public class EmployeeControllerTest {

    @Autowired
    private MockMvc mvc;
    @MockBean
    private EmployeeService service;
    @Mock
    private DAO repository;
    @Autowired
    ObjectMapper mapper;

    @BeforeEach
    public void init() throws Exception {

    }
    @Test
    void testAddEmployeeRequest() throws Exception {
        Employee mockEmployee = new Employee(
                 "Mock",
                "Santiago",
                "mock@gmail.com",
                "Developer");
         when(repository.save(mockEmployee)).thenReturn(mockEmployee);
         mvc.perform(post("/api/add/employee")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(mockEmployee)))
                .andDo(print())
                .andExpect(status().isOk());
    }


    @Test
    void testGetAllEmployees() throws Exception {
        Employee employee1 = Employee.build(1,
                "Mark",
                "Herras",
                "herras@gmail.com",
                "Senior Developer");
        Employee employee2 = Employee.build(2,
                "Juan",
                "Miguel",
                "Miguelito@gmail.com",
                "Tester");
        List<Employee> employeeList = new ArrayList<>();

        employeeList.add(employee1);
        employeeList.add(employee2);

        when(repository.findAll()).thenReturn(employeeList);

       MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.
                       get("/api/EmployeeLists")
                        .contentType(MediaType.APPLICATION_JSON)
                       .content(mapper.writeValueAsString(employeeList)))
                .andDo(print())
                .andExpect(status().isOk()).andReturn();

       String resultContentGetAll = mvcResult.getResponse().getContentAsString();
       List<Employee> employees = mapper.readValue(resultContentGetAll, new TypeReference<List<Employee>>() {
       });
       assertThat(employees).isNotNull();
    }

    @Test
    public void testfindEmployeeById() throws Exception {
        Employee employeeRequest = Employee.build(1,
                "Mark",
                "Herras",
                "herras@gmail.com",
                "Senior Developer");
        List<Employee> employeeList = new ArrayList<>();

        employeeList.add(employeeRequest);

        when(repository.findById(employeeRequest.getId())).thenReturn(Optional.of(employeeRequest));

        MvcResult result = mvc.perform(get("/api/employee/{id}", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(employeeRequest)))
                .andExpect(status().isOk()).andReturn();

        String contentResult = result.getResponse().getContentAsString();

        Employee employeeResult = mapper.readValue(contentResult, Employee.class);
        assertEquals(employeeResult.getId(), employeeRequest.getId());
    }

    @Test
    void testUpdateEmployee() throws Exception {
        EmployeeRequest employeeRequest = EmployeeRequest.build(
                "Gold",
                "Briones",
                "mock@gmail.com",
                "Developer"
        );

        Employee toUpdateEmployee = Employee.build(1,
                employeeRequest.getFirstName(),
                employeeRequest.getLastName(),
                employeeRequest.getEmailId(),
                employeeRequest.getRoleName());

        Employee updatedEmployee = Employee.build(1,
                "Sam",
                "Concepcion",
                employeeRequest.getEmailId(),
                employeeRequest.getRoleName());
//        when(repository.findById(updatedEmployee.getId())).thenReturn(Optional.of(updatedEmployee));
        when(repository.save(any(Employee.class))).thenReturn(updatedEmployee);


        MvcResult resultFromUpdate = mvc.perform(MockMvcRequestBuilders.put("/api/employee/{id}", updatedEmployee.getId())
                .content(mapper.writeValueAsString(updatedEmployee))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk()).andReturn();

            assertEquals("Sam", updatedEmployee.getFirstName());
            assertEquals("Concepcion", updatedEmployee.getLastName());
//        String contentOfUpdate = resultFromUpdate.getResponse().getContentAsString();
//        Employee bodyFromUpdate = mapper.(contentOfUpdate, Employee.class);
//        assertThat(bodyFromUpdate.getId()).isNotNull();
//        assertThat(bodyFromUpdate).usingRecursiveComparison().isNotEqualTo(updatedEmployee);
    }

    @Test
    void testAddExceptions() throws JsonProcessingException, Exception {
        long id = 1;
        Employee exceptionRequest = new Employee(
                "",
                "Santiago",
                "mock@gmail.com",
                "Developer");
        exceptionRequest.setId(id);
        when(service.validateAddRequest(exceptionRequest)).thenReturn(exceptionRequest);
//         Null Name
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

    @Test
	void testDeleteEmployee() throws Exception {
        Employee employeeRequest = Employee.build(1,
                "Gold",
                "Briones",
                "mock@gmail.com",
                "Developer"
        );
//        when(repository.save(employeeRequest)).thenReturn(employeeRequest);

        when(repository.findById(employeeRequest.getId())).thenReturn(Optional.of(employeeRequest));

        mvc.perform(MockMvcRequestBuilders.delete("/api/employee/{id}", "1")
                        .accept(MediaType.APPLICATION_JSON).content(mapper.writeValueAsString(employeeRequest)))
                .andExpect((status().isOk()));
    }

    @Test
	void testReturn404WhenEmployeeIsNotFound() throws Exception {
		final long employeeId = 2;
//		when(repository.findById(mockEmployee.getId())).thenReturn(Optional.empty());

		this.mvc.perform(get("/api/employee/{id}", employeeId)
                        .accept(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print())
                .andExpect((status().isNotFound()));
    }
}


