package com.demo;

public class Booking {

    private Date startDate;
    private Date endDate;
    private int customerID;
    private double roomPrice;
    private int roomNum;
    private String hotelName;
    private int hotelNum;
    public Booking(Date startDate, Date endDate, int customerID, double roomPrice, int roomNum, String hotelName, int hotelNum){
        this.startDate = startDate;
        this.endDate = endDate;
        this.customerID = customerID;
        this.roomPrice = roomPrice;
        this.roomNum = roomNum;
        this.hotelName = hotelName;
        this. hotelNum = hotelNum;
    }

    public double getRoomPrice() {
        return roomPrice;
    }

    public int getCustomerID() {
        return customerID;
    }

    public int getHotelNum() {
        return hotelNum;
    }

    public int getRoomNum() {
        return roomNum;
    }

    public Date getEndDate() {
        return endDate;
    }

    public String getHotelName() {
        return hotelName;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public void setHotelNum(int hotelNum) {
        this.hotelNum = hotelNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
    }

    public void setRoomPrice(double roomPrice) {
        this.roomPrice = roomPrice;
    }

    @Override
    public String toString() {
        return "booking{" +
                "startDate='" + startDate.toString() + '\'' +
                ", endDate='" + endDate.toString() + '\'' +
                ", customerID=" + customerID +
                ", roomPrice=" + roomPrice +
                ", roomNum=" + roomNum +
                ", hotelName='" + hotelName + '\'' +
                ", hotelNum=" + hotelNum +
                '}';
    }
}
