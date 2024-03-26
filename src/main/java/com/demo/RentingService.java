package com.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.sql.JDBCType.VARCHAR;

public class RentingService {

    public List<Renting> getRentings() throws Exception {

        // sql query
        String sql = "SELECT * FROM renting";

        // database connection object
        ConnectionDB db = new ConnectionDB();

        //List to store and return all bookings from database
        List<Renting> rentings = new ArrayList<>();

        //Try to connect to DB, catch any exceptions
        try {

            Connection con = db.getConnection();

            //Prepares statement
            PreparedStatement stmt = con.prepareStatement(sql);

            // execute query
            ResultSet rs = stmt.executeQuery();

            // store bookings in list
            while (rs.next()) {
                // create new renting
                Renting r = new Renting(
                        new Date(rs.getDate("startDate").toString()),
                        new Date(rs.getDate("endDate").toString()),
                        rs.getInt("customerID"),
                        rs.getInt("employeeID"),
                        rs.getBoolean("status_of_payment"),
                        rs.getInt("room_num"),
                        rs.getDouble("room_price"),
                        rs.getString("hotel_name"),
                        rs.getInt("hotel_num")
                );

                // adds new renting to the list
                rentings.add(r);
            }

            // close the result set
            rs.close();

            // close the statement
            stmt.close();
            con.close();
            db.close();

            // return the list of rentings
            return rentings;

        } catch (Exception e) {

            // throw the error
            throw new Exception(e.getMessage());

        }
    }

    public String deleteRenting(Date sd, Date ed, Integer id) throws Exception {

        Connection con = null;
        String msg = "";

        // sql query
        String sql = "DELETE FROM renting WHERE startDate = ? AND endDate = ? AND customerID = ?;";

        // database connection object
        ConnectionDB db = new ConnectionDB();

        // try to connect to DB, catch exceptions
        try {

            // get connection
            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);

            // set the ? of the statement
            stmt.setDate(1, java.sql.Date.valueOf(sd.toString()));
            stmt.setDate(2, java.sql.Date.valueOf(ed.toString()));
            stmt.setInt(3, id);

            // execute query
            stmt.executeUpdate();

            // close the statement
            stmt.close();
            con.close();
            db.close();

        } catch (Exception e) {

            // throw exception
            throw new Exception("Error while deleting renting" + e.getMessage());

        }

        return msg;

    }

    public String createRenting(Renting renting) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "INSERT INTO renting (startDate, endDate, customerID, status_of_payment, room_num, room_price, hotel_name, hotel_num) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        // try connecting to DB, catch and trow any error
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(renting.getStartDate().toString()));
            stmt.setDate(2, java.sql.Date.valueOf(renting.getEndDate().toString()));
            stmt.setInt(3, renting.getCustomerID());
            stmt.setInt(4, renting.getEmployeeId());
            stmt.setBoolean(5, renting.isPaid());
            stmt.setInt(6, renting.getRoomNum());
            stmt.setDouble(7, renting.getRoomPrice());
            stmt.setObject(8, renting.getHotelName(), VARCHAR, 45);
            stmt.setInt(9, renting.getHotelNum());

            // execute statement
            stmt.executeUpdate();

            // close the statement
            stmt.close();

            // close connection
            con.close();
            db.close();

            return msg;

        } catch (Exception e) {

            throw new Exception("Error while adding renting: " + e.getMessage());

        }
    }

    public String updateRenting (Date sd, Date ed, Integer id, String attName, Object val) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "UPDATE renting SET ? = ? WHERE startDate = ? AND endDate = ? AND customerID = ?;";

        // try to connect to DB, catch any errors
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, attName);
            stmt.setObject(2, val);
            stmt.setDate(3, java.sql.Date.valueOf(sd.toString()));
            stmt.setDate(4, java.sql.Date.valueOf(ed.toString()));
            stmt.setInt(5, id);

            // execute statement
            stmt.executeUpdate();

            // close statement
            stmt.close();

            //close connection
            con.close();
            db.close();

            return msg;

        } catch (Exception e) {

            throw new Exception("Error while updating renting: " + e.getMessage());

        }

    }

}
