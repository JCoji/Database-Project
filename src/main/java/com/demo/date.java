package com.demo;

public class date {

    private int year;
    private int month;
    private int day;

    public date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public date(String totalDate) {
        String[] dates = totalDate.split("-");
        year = Integer.parseInt(dates[0]);
        month = Integer.parseInt(dates[1]);
        day = Integer.parseInt(dates[2]);
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return  year +
                "-" + month +
                "-"+ day;
    }
}
