package com.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.sql.JDBCType.VARCHAR;

public class BookingService {

    public List<Booking> getBookings() throws Exception {

        // sql query
        String sql = "SELECT * FROM booking";

        // database connection object
        ConnectionDB db = new ConnectionDB();

        //List to store and return all bookings from database
        List<Booking> bookings = new ArrayList<>();

        //Try to connect to DB, catch any exceptions
        try {

            Connection con = db.getConnection();

            //Prepares statement
            PreparedStatement stmt = con.prepareStatement(sql);

            // execute query
            ResultSet rs = stmt.executeQuery();

            // store bookings in list
            while (rs.next()) {
                // create new booking
                Booking b = new Booking(
                    new Date(rs.getDate("startDate").toString()),
                    new Date(rs.getDate("endDate").toString()),
                    rs.getInt("customerID"),
                    rs.getDouble("room_price"),
                    rs.getInt("room_num"),
                    rs.getString("hotel_name"),
                    rs.getInt("hotel_num")
                );

                // adds new booking to the list
                bookings.add(b);
            }

            // close the result set
            rs.close();

            // close the statement
            stmt.close();
            con.close();
            db.close();

            // return the list of bookings
            return bookings;

        } catch (Exception e) {

            // throw the error
            throw new Exception(e.getMessage());

        }
    }

    public String deleteBooking(Date sd, Date ed, Integer id) throws Exception {

        Connection con = null;
        String msg = "";

        // sql query
        String sql = "DELETE FROM booking WHERE startDate = ? AND endDate = ? AND customerID = ?;";

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
            throw new Exception("Error while deleting booking" + e.getMessage());

        }

        return msg;

    }

    public String createBooking(Booking booking) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "INSERT INTO booking (startDate, endDate, customerID, room_price, room_num, hotel_name, hotel_num) VALUES (?, ?, ?, ?, ?, ?, ?);";

        // try connecting to DB, catch and trow any error
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(booking.getStartDate().toString()));
            stmt.setDate(2, java.sql.Date.valueOf(booking.getEndDate().toString()));
            stmt.setInt(3, booking.getCustomerID());
            stmt.setDouble(4, booking.getRoomPrice());
            stmt.setInt(5, booking.getRoomNum());
            stmt.setObject(6, booking.getHotelName(), VARCHAR, 45);
            stmt.setInt(7, booking.getHotelNum());

            // execute statement
            stmt.executeUpdate();

            // close the statement
            stmt.close();

            // close connection
            con.close();
            db.close();

            return msg;

        } catch (Exception e) {

            throw new Exception("Error while adding booking: " + e.getMessage());

        }
    }

    public String updateBooking (Date sd, Date ed, Integer id, String attName, Object val) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "UPDATE booking SET ? = ? WHERE startDate = ? AND endDate = ? AND customerID = ?;";

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

            throw new Exception("Error while updating: " + e.getMessage());

        }

    }

}
