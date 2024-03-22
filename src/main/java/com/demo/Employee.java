package com.demo;



public class Employee {
    private int id;
    private String fName;
    private String sName;
    private String city;
    private String province;
    private String streetName;
    private int streetNum;
    private String position;
    private String hotelName;
    private int hotelNum;

    public Employee(int id, String fName, String sName, String city, String province, String streetName, int streetNum, String position, String hotelName, int hotelNum) {
        this.id = id;
        this.fName = fName;
        this.sName = sName;
        this.city = city;
        this.province = province;
        this.streetName = streetName;
        this.streetNum = streetNum;
        this.position = position;
        this.hotelName = hotelName;
        this.hotelNum = hotelNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public int getStreetNum() {
        return streetNum;
    }

    public void setStreetNum(int streetNum) {
        this.streetNum = streetNum;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
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
        return "employee{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", sName='" + sName + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", streetName='" + streetName + '\'' +
                ", streetNum=" + streetNum +
                ", position='" + position + '\'' +
                ", hotelName='" + hotelName + '\'' +
                ", hotelNum=" + hotelNum +
                '}';
    }
}
