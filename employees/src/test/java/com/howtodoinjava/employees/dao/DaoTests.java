package com.howtodoinjava.employees.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.howtodoinjava.employees.model.Employee;

/**
 * @DataJpaTest Annotation for a JPA test that focuses only on JPA components.
 * 
 *              Using this annotation will disable full auto-configuration and
 *              instead apply only configuration relevant to JPA tests.
 * 
 *              By default, tests annotated with @DataJpaTest are transactional
 *              and roll back at the end of each test. They also use an embedded
 *              in-memory database (replacing any explicit or usually
 *              auto-configured DataSource). The @AutoConfigureTestDatabase
 *              annotation can be used to override these settings.
 *
 * @AutoConfigureTestDatabase Annotation that can be applied to a test class to
 *                            configure a test database to use instead of the
 *                            application-defined or auto-configured DataSource.
 * 
 */
@ExtendWith(SpringExtension.class)
@DataJpaTest
//Don't replace the application default DataSource
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class DaoTests {

	@Autowired
	EmployeeRepository employeeRepository;

	@Test
	public void testCreateReadDelete() {
		Employee employee = new Employee("Lokesh", "Gupta");

		employeeRepository.save(employee);

		Iterable<Employee> employees = employeeRepository.findAll();
		Assertions.assertThat(employees).extracting(Employee::getFirstName).containsOnly("Lokesh");

		employeeRepository.deleteAll();
		Assertions.assertThat(employeeRepository.findAll()).isEmpty();
	}
}
