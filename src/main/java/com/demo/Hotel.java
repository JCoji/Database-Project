package com.demo;

public class Hotel {

    private String name;
    private String chainName;
    private String province;
    private String city;
    private String streetName;
    private int streetNum;
    private int numOfRooms;
    private int rating;
    private String email;
    private String phoneNum;

    public Hotel(String name, String chainName, String province, String city, String streetName, int streetNum, int numOfRooms, int rating, String email, String phoneNum) {
        this.name = name;
        this.chainName = chainName;
        this.province = province;
        this.city = city;
        this.streetName = streetName;
        this.streetNum = streetNum;
        this.numOfRooms = numOfRooms;
        this.rating = rating;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public Hotel(String name, String chainName, String province, String streetName, int streetNum, int numOfRooms, String email, String phoneNum) {
        this.name = name;
        this.chainName = chainName;
        this.province = province;
        this.streetName = streetName;
        this.streetNum = streetNum;
        this.numOfRooms = numOfRooms;
        this.email = email;
        this.phoneNum = phoneNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChainName() {
        return chainName;
    }

    public void setChainName(String chainName) {
        this.chainName = chainName;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public void setCity(String city) {this.city = city;}

    public String getCity() {return this.city;}

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

    public int getNumOfRooms() {
        return numOfRooms;
    }

    public void setNumOfRooms(int numOfRooms) {
        this.numOfRooms = numOfRooms;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    @Override
    public String toString() {
        return "hotel{" +
                "name='" + name + '\'' +
                ", chainName='" + chainName + '\'' +
                ", province='" + province + '\'' +
                ", city='" + city + '\'' +
                ", streetName='" + streetName + '\'' +
                ", streetNum=" + streetNum +
                ", numOfRooms=" + numOfRooms +
                ", rating=" + rating +
                ", email='" + email + '\'' +
                ", phoneNum=" + phoneNum +
                '}';
    }

}
