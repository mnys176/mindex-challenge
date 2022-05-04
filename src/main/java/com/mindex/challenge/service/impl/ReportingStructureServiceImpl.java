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

        // use a HashSet to guarantee uniqueness and quick access
        Set<String> reports = new HashSet<>();
        countNodesFromRoot(employee, reports);

        // do not count the root node
        int result = reports.size() - 1;
        return new ReportingStructure(employee, result);
    }

    private void countNodesFromRoot(Employee root, Set<String> visited) {
        visited.add(root.getEmployeeId());

        // recursively count nodes in the tree starting from the root
        if (root.getDirectReports() == null) return;
        for (Employee e : root.getDirectReports()) {

            // fill in the rest of the employee
            e = employeeRepository.findByEmployeeId(e.getEmployeeId());
            countNodesFromRoot(e, visited);
        }
    }
}
