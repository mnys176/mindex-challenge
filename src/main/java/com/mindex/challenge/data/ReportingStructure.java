package com.mindex.challenge.data;

public class ReportingStructure {
    final private Employee employee;
    final private int numberOfReports;

    public ReportingStructure(Employee employee, int numberOfReports) {
        this.employee = employee;
        this.numberOfReports = numberOfReports;
    }

    public Employee getEmployee() { return employee; }

    public int getNumberOfReports() {
        return numberOfReports;
    }
}
