package com.demo;

import com.demo.Booking;
import com.demo.ConnectionDB;
import com.demo.Date;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.sql.JDBCType.VARCHAR;

public class HotelService {

    public List<Hotel> getHotels() throws Exception {

        // sql query
        String sql = "SELECT * FROM hotel";

        // database connection object
        ConnectionDB db = new ConnectionDB();

        //List to store and return all bookings from database
        List<Hotel> hotels = new ArrayList<>();

        //Try to connect to DB, catch any exceptions
        try {
            Connection con = db.getConnection();

            //Prepares statement
            PreparedStatement stmt = con.prepareStatement(sql);

            // execute query
            ResultSet rs = stmt.executeQuery();

            // store hotels in list
            while (rs.next()) {
                // create new hotel
                Hotel h = new Hotel(
                    rs.getString("name"),
                    rs.getString("chain_name"),
                    rs.getString("province"),
                    rs.getString("city"),
                    rs.getString("streetName"),
                    rs.getInt("streetNum"),
                    rs.getInt("number_of_rooms"),
                    rs.getInt("rating"),
                    rs.getString("email"),
                    rs.getInt("phone_num")
                );

                // adds new hotel to the list
                hotels.add(h);
            }

            // close the result set
            rs.close();

            // close the statement
            stmt.close();
            con.close();
            db.close();

            // return the list of hotels
            return hotels;

        } catch (Exception e) {

            // throw the error
            throw new Exception(e.getMessage());

        }
    }

    public String deleteHotel(String name, Integer streetNum) throws Exception {

        Connection con = null;
        String msg = "";

        // sql query
        String sql = "DELETE FROM hotel WHERE name = ? AND streetNum = ?;";

        // database connection object
        ConnectionDB db = new ConnectionDB();

        // try to connect to DB, catch exceptions
        try {

            // get connection
            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);

            // set the ? of the statement
            stmt.setString(1, name);
            stmt.setInt(2, streetNum);

            // execute query
            stmt.executeUpdate();

            // close the statement
            stmt.close();
            con.close();
            db.close();

        } catch (Exception e) {

            // throw exception
            throw new Exception("Error while deleting hotel" + e.getMessage());
        }

        return msg;

    }

    public String createHotel(Hotel hotel) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "INSERT INTO hotel (name, chain_name, province, city, streetName, streetNum, number_of_rooms, rating, email, phone_num) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        // try connecting to DB, catch and trow any error
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, hotel.getName());
            stmt.setString(2, hotel.getChainName());
            stmt.setString(3, hotel.getProvince());
            stmt.setString(4, hotel.getCity());
            stmt.setString(5, hotel.getStreetName());
            stmt.setInt(6, hotel.getStreetNum());
            stmt.setInt(7, hotel.getNumOfRooms());
            stmt.setInt(8, hotel.getRating());
            stmt.setString(9, hotel.getEmail());
            stmt.setInt(10, hotel.getPhoneNum());

            // execute statement
            stmt.executeUpdate();

            // close the statement
            stmt.close();

            // close connection
            con.close();
            db.close();

            return msg;

        } catch (Exception e) {

            throw new Exception("Error while adding hotel: " + e.getMessage());

        }
    }

    public String updateHotel (String name, Integer streetNum, String attName, Object val) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "UPDATE hotel SET " + attName + " = ? WHERE name = ? AND streetNum = ?;";

        // try to connect to DB, catch any errors
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setObject(1, val);
            stmt.setString(2, name);
            stmt.setInt(3, streetNum);

            // execute statement
            stmt.executeUpdate();

            // close statement
            stmt.close();

            //close connection
            con.close();
            db.close();

            return msg;

        } catch (Exception e) {

            throw new Exception("Error while updating hotel: " + e.getMessage());

        }

    }

}
