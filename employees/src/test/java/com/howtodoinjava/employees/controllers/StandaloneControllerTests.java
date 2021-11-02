package com.howtodoinjava.employees.controllers;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

import java.util.Arrays;
import java.util.List;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.event.annotation.BeforeTestExecution;
import org.springframework.test.context.event.annotation.BeforeTestMethod;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import com.howtodoinjava.employees.model.Employee;
import com.howtodoinjava.employees.services.EmployeeService;

/**
 * Test standalone sobre el controller EmployeeController que no carga todo el
 * contexto e utiliza un mock de EmployeeService
 * 
 * @author jimar
 *
 */

@ExtendWith(SpringExtension.class)
@WebMvcTest(EmployeeController.class)
public class StandaloneControllerTests {

	@MockBean
	EmployeeService employeeService;

	@Autowired
	MockMvc mockMvc;

	@BeforeEach
	void beforeMethod() throws Exception {

	}

	@Test
	void testfindAll() throws Exception {

		Employee employee = new Employee("Lokesh", "Gupta");

		List<Employee> employees = Arrays.asList(employee);
		Mockito.when(employeeService.findAll()).thenReturn(employees);

		mockMvc.perform(get("/employee")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$", Matchers.hasSize(1)))
				.andExpect(jsonPath("$[0].firstName", Matchers.is("Lokesh")))
				.andExpect(jsonPath("$[0].lastName", Matchers.is("Gupta")));

	}

	@Test
	void testgetEmp() throws Exception {

		Employee employee = new Employee("Nacho", "Martin");
		Mockito.when(employeeService.findById(1)).thenReturn(employee);
	
		
		mockMvc.perform(get("/employee/1")).andExpect(status().isOk())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.firstName", Matchers.is("Nacho")))
				.andExpect(jsonPath("$.lastName", Matchers.is("Martin")));

		System.out.println(content());
	}

}
