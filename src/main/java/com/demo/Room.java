package com.demo;

public class Room {

    private int roomNum;
    private String hotelName;
    private int hotelNum;
    private int capacity;
    private int price;
    private String amenities;
    private boolean expandable;
    private String problems = "";
    private boolean isAvailable;

    public Room(int roomNum, String hotelName, int hotelNum, int capacity, int price, String amenities, boolean expandable, String problems, boolean isAvailable) {
        this.roomNum = roomNum;
        this.hotelName = hotelName;
        this.hotelNum = hotelNum;
        this.capacity = capacity;
        this.price = price;
        this.amenities = amenities;
        this.expandable = expandable;
        this.problems = problems;
        this.isAvailable = isAvailable;
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getAmenities() {
        return amenities;
    }

    public void setAmenities(String amenities) {
        this.amenities = amenities;
    }

    public boolean isExpandable() {
        return expandable;
    }

    public void setExpandable(boolean expandable) {
        this.expandable = expandable;
    }

    public String getProblems() {
        return problems;
    }

    public void setProblems(String problems) {
        this.problems = problems;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String toString() {
        return "room{" +
                "roomNum=" + roomNum +
                ", hotelName='" + hotelName + '\'' +
                ", hotelNum='" + hotelNum + '\'' +
                ", capacity=" + capacity +
                ", price=" + price +
                ", amenities='" + amenities + '\'' +
                ", expandable=" + expandable +
                ", problems='" + problems + '\'' +
                '}';
    }

}
