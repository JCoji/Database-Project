package com.demo;

public class Reserves {
    private Date startDate;
    private Date endDate;
    private int roomNum;
    private String hotelName;
    private int hotelNum;

    public Reserves(Date startDate, Date endDate, int roomNum, String hotelName, int hotelNum) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.roomNum = roomNum;
        this.hotelName = hotelName;
        this.hotelNum = hotelNum;
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

    public int getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(int roomNum) {
        this.roomNum = roomNum;
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
        return "reserves{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                ", roomNum=" + roomNum +
                ", hotelName='" + hotelName + '\'' +
                ", hotelNum=" + hotelNum +
                '}';
    }

}
