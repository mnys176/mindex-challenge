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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
    public void testCreateCompensationEmployeeCorrect() {
        Employee createdEmployee = restTemplate.postForEntity(employeeUrl, employee, Employee.class).getBody();
        Compensation testCompensation = new Compensation(createdEmployee, 100.2, new Date(new Timestamp(System.currentTimeMillis()).getTime()));
        Compensation createdCompensation = restTemplate.postForEntity(compensationUrl, testCompensation, Compensation.class).getBody();
        assertEquals(createdEmployee.getEmployeeId(), createdCompensation.getEmployee().getEmployeeId());
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
