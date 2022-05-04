package com.mindex.challenge.service.impl;

import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReportingStructureServiceImplTest {
    final private static String BASE_URL = "http://localhost:";

    private String employeeUrl;
    private String reportingStructureUrl;

    private Employee employee;

    @Autowired
    private ReportingStructureService reportingStructureService;

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setup() {
        employeeUrl = BASE_URL + port + "/employee";
        reportingStructureUrl = BASE_URL + port + "/reporting-structure/{id}";
        employee = restTemplate.postForEntity(employeeUrl, buildEmployee(), Employee.class).getBody();
    }

    @Test
    public void testGetReportingStructureEmployeeCorrect() {
        ReportingStructure readReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, employee.getEmployeeId()).getBody();
        assertEquals(readReportingStructure.getEmployee(), employee);
    }

    @Test
    public void testGetReportingStructureNumberOfReports0() {
        ReportingStructure readReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, employee.getEmployeeId()).getBody();
        assertEquals(readReportingStructure.getNumberOfReports(), 0);
    }

    // TODO: Create tree generator to help with testing more combinations

    @Test
    public void testGetReportingStructureNumberOfReports4() {
        ReportingStructure readReportingStructure = restTemplate.getForEntity(reportingStructureUrl, ReportingStructure.class, "16a596ae-edd3-4847-99fe-c4518e82c86f").getBody();
        assertEquals(readReportingStructure.getNumberOfReports(), 4);
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