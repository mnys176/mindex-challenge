package com.mindex.challenge.data;

import java.util.Date;
import java.util.Objects;

public class Compensation {
    final private Employee employee;
    final private double salary;
    final private Date effectiveDate;

    public Compensation(Employee employee, double salary, Date effectiveDate) {
        this.employee = employee;
        this.salary = salary;
        this.effectiveDate = effectiveDate;
    }

    public Employee getEmployee() { return this.employee; }

    public double getSalary() { return this.salary; }

    public Date getEffectiveDate() { return this.effectiveDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;
        Compensation that = (Compensation) o;
        return that.salary == salary && employee.equals(that.employee) && effectiveDate.equals(that.effectiveDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(employee, salary, effectiveDate);
    }
}
