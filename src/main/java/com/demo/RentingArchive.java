package com.demo;

public class RentingArchive {
    private Date startDate;
    private Date endDate;
    private int customerId;

    public RentingArchive(Date startDate, Date endDate, int customerId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
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

    @Override
    public String toString() {
        return "renting_archive{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", customerId=" + customerId +
                '}';
    }

}
