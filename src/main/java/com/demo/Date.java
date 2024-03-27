package com.demo;

public class Date {

    private int year;
    private int month;
    private int day;

    public Date(int year, int month, int day) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    public Date(String totalDate) {
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

    public boolean overlaps(Date end1, Date start2, Date end2){
        int startA = Integer.parseInt("" + year + month + day);
        int endA = Integer.parseInt("" +  end1.getYear() + end1.getMonth() + end1.getDay());
        int startB =  Integer.parseInt("" +  start2.getYear() + start2.getMonth() + start2.getDay());
        int endB = Integer.parseInt("" +  end2.getYear() + end2.getMonth() + end2.getDay());

        return startA <= endB && endA >= startB;
    }

}
