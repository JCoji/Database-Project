package com.demo;

public class Customer {
    private int id;
    private String fName;
    private String sName;
    private String city;
    private String province;
    private String streetName;
    private int streetNum;
    private Date regDate;

    public Customer(int id, String fName, String sName, String city, String province, String streetName, int streetNum, Date regDate) {
        this.id = id;
        this.fName = fName;
        this.sName = sName;
        this.city = city;
        this.province = province;
        this.streetName = streetName;
        this.streetNum = streetNum;
        this.regDate = regDate;
    }

    //ADD CONSTRUCTOR WITHOUT DATE AND SET TO CURRENT DATE?


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

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }

    @Override
    public String toString() {
        return "customer{" +
                "id=" + id +
                ", fName='" + fName + '\'' +
                ", sName='" + sName + '\'' +
                ", city='" + city + '\'' +
                ", province='" + province + '\'' +
                ", streetName='" + streetName + '\'' +
                ", streetNum=" + streetNum +
                ", regDate=" + regDate.toString() +
                '}';
    }
}
