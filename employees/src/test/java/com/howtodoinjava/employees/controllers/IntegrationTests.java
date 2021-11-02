package com.howtodoinjava.employees.controllers;

import javax.validation.ValidationException;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.howtodoinjava.employees.model.Employee;

/**
 * Test de integración que carga todo el contexto e inyecta EmployeeController
 * @author jimar
 *
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class IntegrationTests {

	@Autowired
	EmployeeController employeeController;

	@Test
	public void testCreateReadDelete() {
		Employee employee = new Employee("Lokesh", "Gupta");

		Employee employeeResult = employeeController.create(employee);

		Iterable<Employee> employees = employeeController.read();
		//Assertions.assertThat(employees).first().hasFieldOrPropertyWithValue("firstName", "Lokesh");
		Assertions.assertThat(employees).last().hasFieldOrPropertyWithValue("firstName", "Lokesh");

		employeeController.delete(employeeResult.getId());
		Assertions.assertThat(employeeController.read()).isNotEmpty();
		//Assertions.assertThat(employeeController.read()).isEmpty();
	}

	@Test
	public void errorHandlingValidationExceptionThrown() {

		Assertions.assertThatExceptionOfType(ValidationException.class)
				.isThrownBy(() -> employeeController.somethingIsWrong());
	}
}
