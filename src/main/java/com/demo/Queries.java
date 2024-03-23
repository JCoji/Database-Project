package com.demo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

//Backend queries, answers for Question 2c
public class Queries {

    public static void main(String[] args) throws Exception {

        /*
        query1();
        System.out.println();
        query2();
        System.out.println();
        query3();
        System.out.println();
        query4();
        */

        HotelService hs = new HotelService();
        List<Hotel> hotels = new ArrayList<>();

        hs.createHotel(new Hotel("test", "Marriott", "Ontario", "Ottawa", "Rideau", 1, 1, 1, "Stinky@hotel.com", "1234567678990"));

        //hs.deleteHotel("test", 1);
        hs.updateHotel("test", 1, "streetName", "Byward");
        try{
            hotels = hs.getHotels();

            while(!hotels.isEmpty()){
                Hotel h = hotels.remove(0);
                System.out.print(h.getName());
                System.out.println(" "+ h.getStreetName());
            }


        }catch (Exception e) {

            // throw the error
            throw new Exception(e.getMessage());

        }

    }

    public static void query1()
    {
        try {
            Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres"
                    , "postgres", "admin");

            db.setSchema("Project Test 3"); //Change this later for whatever schema is default i.e public
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("SELECT name,chain_name FROM hotel Where city = 'Toronto'");

            System.out.println("Query 1: All hotels and their chain within toronto:");
            System.out.println("Hotel Name         Chain Name:");

            while (rs.next())
            {
                System.out.println(rs.getString(1)+", "+rs.getString(2));
            }
            rs.close();
            st.close();
            db.close();
        }
        catch(SQLException exception) {
            System.out.println("An exception was thrown:"+exception.getMessage());
        }

    }

    public static void query2()
    {
        try {
            Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres"
                    , "postgres", "admin");

            db.setSchema("Project Test 3"); //Change this later for whatever schema is default i.e public
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("SELECT avg(price) FROM room NATURAL LEFT OUTER JOIN hotelChain WHERE  hotelChain.name = 'Marriott'");

            System.out.println("Query 2: Average price of rooms at Marriott Hotels:");

            while (rs.next())
            {
                System.out.println(rs.getString(1));
            }
            rs.close();
            st.close();
            db.close();
        }
        catch(SQLException exception) {
            System.out.println("An exception was thrown:"+exception.getMessage());
        }

    }

    public static void query3()
    {
        try {
            Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres"
                    , "postgres", "admin");

            db.setSchema("Project Test 3"); //Change this later for whatever schema is default i.e public
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("SELECT hotel_name, price FROM room ORDER BY price");

            System.out.println("Query 3: All hotel Rooms sorted by cheapest");
            System.out.println("Hotel', Price");

            while (rs.next())
            {
                System.out.println(rs.getString(1)+", "+rs.getString(2));
            }
            rs.close();
            st.close();
            db.close();
        }
        catch(SQLException exception) {
            System.out.println("An exception was thrown:"+exception.getMessage());
        }

    }

    public static void query4()
    {
        try {
            Connection db = DriverManager.getConnection("jdbc:postgresql://localhost:5432/postgres"
                    , "postgres", "admin");

            db.setSchema("Project Test 3"); //Change this later for whatever schema is default i.e public
            Statement st = db.createStatement();
            ResultSet rs = st.executeQuery("SELECT hotel_name, price\n" +
                                                "FROM room " +
                                                "WHERE (price < 250) AND " +
                                                "(hotel_name, hotel_num) in (SELECT name, streetNum " +
                                                    "FROM hotel " +
                                                    "WHERE chain_name = 'Four Seasons')");

            System.out.println("Query 4: All Hotel Rooms under $250 and owned by Four Seasons");
            System.out.println("Hotel, Price");

            while (rs.next())
            {
                System.out.println(rs.getString(1)+", "+rs.getString(2));
            }
            rs.close();
            st.close();
            db.close();
        }
        catch(SQLException exception) {
            System.out.println("An exception was thrown:"+exception.getMessage());
        }

    }

}


