package com.demo;

public class checks_in {
    private date startDate;
    private date endDate;
    private int customerId;
    private int employeeId;

    public checks_in(date startDate, date endDate, int customerId, int employeeId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
        this.employeeId = employeeId;
    }

    public date getStartDate() {
        return startDate;
    }

    public void setStartDate(date startDate) {
        this.startDate = startDate;
    }

    public date getEndDate() {
        return endDate;
    }

    public void setEndDate(date endDate) {
        this.endDate = endDate;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    @Override
    public String toString() {
        return "checks_in{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", customerId=" + customerId +
                ", employeeId=" + employeeId +
                '}';
    }
}
