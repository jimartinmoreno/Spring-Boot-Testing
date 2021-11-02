package com.howtodoinjava.employees.controllers;

import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.howtodoinjava.employees.model.Employee;
import com.howtodoinjava.employees.services.EmployeeService;

@RestController
public class EmployeeController {

  @Autowired
  EmployeeService employeeService;

  @PostMapping("/employee")
  public Employee create(@RequestBody Employee employee)  {
    return employeeService.save(employee);
  }

  @GetMapping("/employee")
  public Iterable<Employee> read() {
    return employeeService.findAll();
  }
  
  @GetMapping("/employee/{id}")
  public Employee getEmp(@PathVariable Integer id) {
	  System.out.println("getEmp: " + id);
	  return employeeService.findById(id);
  }


  @PutMapping("/employee")
  public Employee update(@RequestBody Employee employee) {
    return employeeService.save(employee);
  }

  @DeleteMapping("/employee/{id}")
  public void delete(@PathVariable Integer id) {
	  employeeService.deleteById(id);
  }

  @GetMapping("/wrong")
  public Employee somethingIsWrong() {
    throw new ValidationException("Something is wrong");
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ValidationException.class)
  public String exceptionHandler(ValidationException e) {
    return e.getMessage();
  }
}
