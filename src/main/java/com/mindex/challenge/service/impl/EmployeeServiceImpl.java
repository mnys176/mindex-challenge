package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.service.EmployeeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public Employee create(Employee employee) {
        LOG.debug("Creating employee [{}]", employee);

        employee.setEmployeeId(UUID.randomUUID().toString());
        employeeRepository.insert(employee);

        return employee;
    }

    @Override
    public Employee read(String id) {
        LOG.debug("Creating employee with id [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);

        if (employee == null) {
            throw new RuntimeException("Invalid employeeId: " + id);
        }

        return employee;
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Updating employee [{}]", employee);

        return employeeRepository.save(employee);
    }

    @Override
    public int calculateReports(String id) {
        LOG.debug("Calculating reports for root employee [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);
        Set<String> reports = new HashSet<>();
        calculateNumberOfReports(employee, reports);

        // do not count root node
        reports.remove(id);

        return reports.size();
    }

    private void calculateNumberOfReports(Employee node, Set<String> visited) {
        visited.add(node.getEmployeeId());
        if (node.getDirectReports() == null) return;

        for (Employee e : node.getDirectReports()) {
            // fill in the rest of the employee
            e = employeeRepository.findByEmployeeId(e.getEmployeeId());
            calculateNumberOfReports(e, visited);
        }
    }
}
