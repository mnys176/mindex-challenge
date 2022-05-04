package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class EmployeeServiceImplTest {

    private String employeeUrl;
    private String employeeIdUrl;
    private Employee testEmployee;

    @Autowired
    private EmployeeService employeeService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = "http://localhost:" + port + "/employee";
        employeeIdUrl = "http://localhost:" + port + "/employee/{id}";

        testEmployee = new Employee();
        testEmployee.setFirstName("John");
        testEmployee.setLastName("Doe");
        testEmployee.setDepartment("Engineering");
        testEmployee.setPosition("Developer");
    }

    @Test
    public void testCreateEmployeeCorrect() {
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        assertEquals(testEmployee, createdEmployee);
    }

    @Test
    public void testCreateEmployeeIdNotNull() {
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        assertNotNull(createdEmployee.getEmployeeId());
    }

    @Test
    public void testReadEmployeeIdCorrect() {
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        Employee readEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();
        assertEquals(createdEmployee.getEmployeeId(), readEmployee.getEmployeeId());
    }

    @Test
    public void testReadEmployeeCorrect() {
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        Employee readEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();
        assertEquals(createdEmployee, readEmployee);
    }

    @Test
    public void testUpdate() {
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, testEmployee, Employee.class).getBody();
        Employee readEmployee = restTemplate.getForEntity(employeeIdUrl, Employee.class, createdEmployee.getEmployeeId()).getBody();
        readEmployee.setPosition("Development Manager");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Employee updatedEmployee = restTemplate.exchange(employeeIdUrl, HttpMethod.PUT, new HttpEntity<>(readEmployee, headers), Employee.class, readEmployee.getEmployeeId()).getBody();
        assertEquals(readEmployee, updatedEmployee);
    }
}
