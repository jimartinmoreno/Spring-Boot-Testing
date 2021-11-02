package com.howtodoinjava.employees;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.howtodoinjava.employees.model.Employee;

public class SystemTests {

	@Test
	public void testCreateReadDelete() {
		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8080/employee";

		Employee employee = new Employee("Lokesh", "Gupta");
		ResponseEntity<Employee> entity = restTemplate.postForEntity(url, employee, Employee.class);

		System.out.println("entity: " + entity);
		System.out.println("entity.getBody: " + entity.getBody());
		System.out.println("entity.getStatusCodeValue: " + entity.getStatusCodeValue());
		System.out.println("entity.getClass: " + entity.getClass());

		Employee[] employees = restTemplate.getForObject(url, Employee[].class);
		// Assertions.assertThat(employees).extracting(Employee::getFirstName).containsOnly("Lokesh");
		// Assertions.assertThat(employees).contains(entity.getBody());

		restTemplate.delete(url + "/" + entity.getBody().getId());
		Assertions.assertThat(restTemplate.getForObject(url, Employee[].class)).isEmpty();
		Assertions.assertThat(restTemplate.getForObject(url, Employee[].class)).isNotEmpty();
	}

	@Test
	public void testErrorHandlingReturnsBadRequest() {

		RestTemplate restTemplate = new RestTemplate();

		String url = "http://localhost:8080/wrong";

		try {
			restTemplate.getForEntity(url, String.class);
		} catch (HttpClientErrorException e) {
			Assertions.assertThat(e.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
		}
	}

}
