package com.accenture.RESTAPICRUD;

import com.accenture.RESTAPICRUD.Controller.EmployeeController;
import com.accenture.RESTAPICRUD.Entity.Employee;
import com.accenture.RESTAPICRUD.Repository.DAO;
import com.accenture.RESTAPICRUD.Service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.internal.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = EmployeeController.class)
@ActiveProfiles("test")
class RestApiCrudApplicationTests {

	@Autowired
	private MockMvc mvc;

	@MockBean
	DAO repository;

	@MockBean
	private EmployeeService employeeService;
	private List<Employee> employeeList;
	@BeforeEach
	void setUp() throws Exception {
		this.employeeList = new ArrayList<>();
		this.employeeList.add(new Employee(
				"Gold",
				"Briones",
				"goldbriones@gmail.com",
				"Java Developer"));
		this.employeeList.add(new Employee(
				"Juan",
				"Dela Cruz",
				"JuanD@gmail.com",
				"Team Lead"));
		this.employeeList.add(new Employee(
				"Miku",
				"Salamanca",
				"MikuS@gmail.com",
				"Senior Developer"));
	}

	@Test
	void testGetAllEmployees() throws Exception {
		when(employeeService.getAllEmployee()).thenReturn(employeeList);

		mvc.perform(MockMvcRequestBuilders
						.get("/api/EmployeeLists")
						.accept(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.size()",
						is(employeeList.size())).exists());
	}


//		@Test
//		void testGetEmployeeById() throws Exception {
//			final long employeeId = 4;
//			final Employee employee4 = new Employee(
//					4,
//					"Gold",
//					"Briones",
//					"goldbriones@gmail.com",
//					"Java Developer");
//
//			given(employeeService.findEmployeeById(employeeId)).willReturn(Optional.of(employee4));
//
//			this.mvc.perform(MockMvcRequestBuilders.get("/api/employee/{id}", employeeId)
//					.accept(MediaType.APPLICATION_JSON))
//					.andDo(print())
//					.andExpect((status().isOk()))
//					.andExpect(jsonPath("$.firstName" , is(employee4.getFirstName())))
//					.andExpect(jsonPath("$.lastName" , is(employee4.getLastName())))
//					.andExpect(jsonPath("$.emailId" , is(employee4.getEmailId())))
//					.andExpect(jsonPath("$.roleName" , is(employee4.getRoleName())));
//
//	}

//	@Test
//	void testReturn404WhenEmployeeIsNotFound() throws Exception {
//		final long employeeId = 1;
//
//		given(employeeService.findEmployeeById(employeeId)).willReturn(Optional.empty());
//
//		this.mvc.perform(MockMvcRequestBuilders.get("/api/employee/{id}", employeeId))
//				.andExpect(status().isNotFound());
//	}


//	@Test
//	void testDeleteEmployee() throws Exception {
//		long employeeId = 1;
//		Employee employeeNew = new Employee(employeeId,
//				"Mark",
//				"Herras",
//				"Mock@gmail.com",
//				"Java Developer");
//
//		given(employeeService.deleteEmployeeById(employeeId)).willReturn(Optional.of(employeeNew));
//		doNothing().when(employeeService).deleteEmployeeById(employeeNew.getId());
//
//		this.mvc.perform(MockMvcRequestBuilders.get("/api/delete/{id}", employeeId)
//						.accept(MediaType.APPLICATION_JSON))
//				.andDo(print())
//				.andExpect((status().isOk()))
//				.andExpect(jsonPath("$.firstName", is(employeeNew.getFirstName())))
//				.andExpect(jsonPath("$.lastName", is(employeeNew.getLastName())))
//				.andExpect(jsonPath("$.emailId", is(employeeNew.getEmailId())))
//				.andExpect(jsonPath("$.roleName", is(employeeNew.getRoleName())));
//	}

}


