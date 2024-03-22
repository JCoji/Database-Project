package com.demo;

public class HotelChain {

    private String name;
    private String city;
    private String province;
    private String streetName;
    private int streetNum;
    private int numberOfHotels;
    public HotelChain(String name, String city, String province, String streetName, int streetNum, int numberOfHotels){
        this.name = name;
        this.city = city;
        this.province = province;
        this.streetName = streetName;
        this.streetNum = streetNum;
        this.numberOfHotels = numberOfHotels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public int getNumberOfHotels() {
        return numberOfHotels;
    }

    public void setNumberOfHotels(int numberOfHotels) {
        this.numberOfHotels = numberOfHotels;
    }

    @Override
    public String toString() {
        return "hotelChain{" +
                "name='" + name + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", streetName='" + streetName + '\'' +
                ", numberOfHotels=" + numberOfHotels +
                '}';
    }

}
