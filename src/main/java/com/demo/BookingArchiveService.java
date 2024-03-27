package com.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.sql.JDBCType.VARCHAR;

public class BookingArchiveService {
    public List<BookingArchive> getBookingArchives() throws Exception {

        // sql query
        String sql = "SELECT * FROM booking_archive";

        // database connection object
        ConnectionDB db = new ConnectionDB();

        //List to store and return all bookings from database
        List<BookingArchive> bArchives = new ArrayList<>();

        //Try to connect to DB, catch any exceptions
        try {

            Connection con = db.getConnection();

            //Prepares statement
            PreparedStatement stmt = con.prepareStatement(sql);

            // execute query
            ResultSet rs = stmt.executeQuery();

            // store bookings in list
            while (rs.next()) {

                // create new booking archive
                BookingArchive b = new BookingArchive(
                        new Date(rs.getDate("startDate").toString()),
                        new Date(rs.getDate("endDate").toString()),
                        rs.getInt("customerID")
                );

                // adds new booking to the list
                bArchives.add(b);
            }

            // close the result set
            rs.close();

            // close the statement
            stmt.close();
            con.close();
            db.close();

            // return the list of bookings
            return bArchives;

        } catch (Exception e) {

            // throw the error
            throw new Exception(e.getMessage());

        }
    }

    public String deleteBookingArchive(Date sd, Date ed, Integer id) throws Exception {

        Connection con = null;
        String msg = "";

        // sql query
        String sql = "DELETE FROM booking_archive WHERE startDate = ? AND endDate = ? AND customerID = ?;";

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
            throw new Exception("Error while deleting booking archive" + e.getMessage());

        }

        return msg;

    }

    public String createBookingArchive (BookingArchive ba) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "INSERT INTO booking_archive (startDate, endDate, customerID) VALUES (?, ?, ?);";

        // try connecting to DB, catch and trow any error
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(ba.getStartDate().toString()));
            stmt.setDate(2, java.sql.Date.valueOf(ba.getEndDate().toString()));
            stmt.setInt(3, ba.getCustomerID());

            // execute statement
            stmt.executeUpdate();

            // close the statement
            stmt.close();

            // close connection
            con.close();
            db.close();

            return msg;

        } catch (Exception e) {

            throw new Exception("Error while adding booking archive: " + e.getMessage());

        }
    }

    public String updateBooking (Date sd, Date ed, Integer id, String attName,Object val) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "UPDATE booking_archive SET " + attName + " = '" + val + "' WHERE startDate = ? AND endDate = ? AND customerID = ?;";

        // try to connect to DB, catch any errors
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(sd.toString()));
            stmt.setDate(2, java.sql.Date.valueOf(ed.toString()));
            stmt.setInt(3, id);


            // execute statement
            stmt.executeUpdate();

            // close statement
            stmt.close();

            //close connection
            con.close();
            db.close();

            return msg;

        } catch (Exception e) {

            throw new Exception("Error while updating booking archive: " + e.getMessage());

        }

    }

}
