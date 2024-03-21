package com.demo;


public class renting {
    private date startDate;
    private date endDate;
    private int customerId;
    private boolean paid;
    private int roomNum;
    private double roomPrice;
    private String hotelName;
    private int hotelNum;

    public renting(date startDate, date endDate, int customerId, boolean paid, int roomNum, double roomPrice, String hotelName, int hotelNum) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerId = customerId;
        this.paid = paid;
        this.roomNum = roomNum;
        this.roomPrice = roomPrice;
        this.hotelName = hotelName;
        this.hotelNum = hotelNum;
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

    public boolean isPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public int getHotelNum() {
        return hotelNum;
    }

    public void setHotelNum(int hotelNum) {
        this.hotelNum = hotelNum;
    }

    @Override
    public String toString() {
        return "renting{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", customerId=" + customerId +
                ", paid=" + paid +
                ", roomNum=" + roomNum +
                ", roomPrice=" + roomPrice +
                ", hotelName='" + hotelName + '\'' +
                ", hotelNum=" + hotelNum +
                '}';
    }
}
