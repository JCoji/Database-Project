package com.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HotelChainService {
    public List<HotelChain> getHotelChains() throws Exception {

        // sql query
        String sql = "SELECT * FROM hotelChain";

        // database connection object
        ConnectionDB db = new ConnectionDB();

        //List to store and return all hotel chains from database
        List<HotelChain> hotelChains = new ArrayList<>();

        //Try to connect to DB, catch any exceptions
        try {
            Connection con = db.getConnection();

            //Prepares statement
            PreparedStatement stmt = con.prepareStatement(sql);

            // execute query
            ResultSet rs = stmt.executeQuery();

            // store hotels in list
            while (rs.next()) {
                // create new hotel chain
                HotelChain hc = new HotelChain(
                        rs.getString("name"),
                        rs.getString("city"),
                        rs.getString("province"),
                        rs.getString("streetName"),
                        rs.getInt("streetNum"),
                        rs.getInt("number_of_hotels")
                );

                // adds new hotel chain to the list
                hotelChains.add(hc);
            }

            // close the result set
            rs.close();

            // close the statement
            stmt.close();
            con.close();
            db.close();

            // return the list of hotels chains
            return hotelChains;

        } catch (Exception e) {

            // throw the error
            throw new Exception(e.getMessage());

        }
    }

    public String deleteHotelChain(String name) throws Exception {

        Connection con = null;
        String msg = "";

        // sql query
        String sql = "DELETE FROM hotelChain WHERE name = ?;";

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

            // execute query
            stmt.executeUpdate();

            // close the statement
            stmt.close();
            con.close();
            db.close();

        } catch (Exception e) {

            // throw exception
            throw new Exception("Error while deleting hotel chain:" + e.getMessage());
        }

        return msg;

    }

    public String createHotelChain(HotelChain hc) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "INSERT INTO hotelChain (name, city, province, streetName, streetNum, number_of_hotels) VALUES (?, ?, ?, ?, ?, ?);";

        // try connecting to DB, catch and trow any error
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, hc.getName());
            stmt.setString(2, hc.getCity());
            stmt.setString(3, hc.getProvince());
            stmt.setString(4, hc.getStreetName());
            stmt.setInt(5, hc.getStreetNum());
            stmt.setInt(6, hc.getNumberOfHotels());

            // execute statement
            stmt.executeUpdate();

            // close the statement
            stmt.close();

            // close connection
            con.close();
            db.close();

            return msg;

        } catch (Exception e) {

            throw new Exception("Error while adding hotel chain: " + e.getMessage());

        }
    }

    public String updateHotelChain (String name, String attName, Object val) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "UPDATE hotelChain SET " + attName + " = '" + val + "' WHERE name = ? ;";

        // try to connect to DB, catch any errors
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, name);


            // execute statement
            stmt.executeUpdate();

            // close statement
            stmt.close();

            //close connection
            con.close();
            db.close();

            return msg;

        } catch (Exception e) {

            throw new Exception("Error while updating hotel chain: " + e.getMessage());

        }

    }

}
