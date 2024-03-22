package com.demo;

public class ChecksIn {
    private Date startDate;
    private Date endDate;
    private int customerId;
    private int employeeId;

    public ChecksIn(Date startDate, Date endDate, int customerId, int employeeId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
        this.employeeId = employeeId;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
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
