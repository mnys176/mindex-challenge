package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Compensation;
import com.mindex.challenge.data.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CompensationServiceTest {
    final private static String BASE_URL = "http://localhost:";

    private String employeeUrl;
    private String compensationUrl;
    private String compensationUrlId;

    private Employee employee;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = BASE_URL + port + "/employee";
        compensationUrl = BASE_URL + port + "/compensation";
        compensationUrlId = compensationUrl + "/{id}";
        employee = restTemplate.postForEntity(employeeUrl, buildEmployee(), Employee.class).getBody();
    }

    @Test
    public void testCreateCompensation() {
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, employee, Employee.class).getBody();
        Compensation testCompensation = new Compensation(createdEmployee, 100.2, new Date(new Timestamp(System.currentTimeMillis()).getTime()));
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
        assertEquals(createdEmployee.getEmployeeId(), createdCompensation.getEmployee().getEmployeeId());
    }

    @Test
    public void testReadCompensation() {
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, employee, Employee.class).getBody();
        Compensation testCompensation = new Compensation(createdEmployee, 100.2, new Date(new Timestamp(System.currentTimeMillis()).getTime()));
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();

        // my implementation returns a list of all compensations associated with an employee ID
        Compensation[] readCompensation = restTemplate.getForEntity(compensationUrlId, Compensation[].class, createdCompensation.getEmployee().getEmployeeId()).getBody();
        for (Compensation c : readCompensation) {
            assertEquals(createdEmployee.getEmployeeId(), c.getEmployee().getEmployeeId());
        }
    }

    private Employee buildEmployee() {
        Employee output = new Employee();
        output.setFirstName("John");
        output.setLastName("Doe");
        output.setDepartment("Engineering");
        output.setPosition("Developer");
        return output;
    }
}
