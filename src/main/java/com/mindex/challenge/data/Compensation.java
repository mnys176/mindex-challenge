package com.mindex.challenge.data;

import java.util.Date;

public class Compensation {
    final private Employee employee;
    final private double salary;
    final private Date effectiveDate;

    public Compensation(Employee employee, double salary, Date effectiveDate) {
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    public Employee getEmployee() {return this.employee;}

    public double getSalary() {return this.salary;}

    public Date getEffectiveDate() {return this.effectiveDate;}
}
