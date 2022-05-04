package com.mindex.challenge.service.impl;

import com.mindex.challenge.dao.EmployeeRepository;
import com.mindex.challenge.data.Employee;
import com.mindex.challenge.data.ReportingStructure;
import com.mindex.challenge.service.ReportingStructureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class ReportingStructureServiceImpl implements ReportingStructureService {
    private static final Logger LOG = LoggerFactory.getLogger(ReportingStructureServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    public ReportingStructure build(String id) {
        LOG.debug("Calculating reports for root employee [{}]", id);

        Employee employee = employeeRepository.findByEmployeeId(id);
        Set<String> reports = new HashSet<>();
        calculateNumberOfReports(employee, reports);

        // do not count root node
        reports.remove(id);

        return new ReportingStructure(employee, reports.size());
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
