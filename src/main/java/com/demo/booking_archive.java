package com.demo;

public class booking_archive {

    private date startDate;
    private date endDate;
    private int customerId;

    public booking_archive(date startDate, date endDate, int customerId) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
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

    @Override
    public String toString() {
        return "renting_archive{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", customerId=" + customerId +
                '}';
    }
}
