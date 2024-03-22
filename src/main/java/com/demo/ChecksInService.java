package com.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.sql.JDBCType.VARCHAR;

public class ChecksInService {
    public List<ChecksIn> getCheckIns() throws Exception {

        // sql query
        String sql = "SELECT * FROM checks_in;";

        // database connection object
        ConnectionDB db = new ConnectionDB();

        // list to store and return all check ins from database
        List<ChecksIn> checkIns = new ArrayList<>();

        // try to connect to DB, catch any exceptions
        try {

            Connection con = db.getConnection();

            // prepares statement
            PreparedStatement stmt = con.prepareStatement(sql);

            // execute query
            ResultSet rs = stmt.executeQuery();

            // store check in in list
            while (rs.next()) {
                // create new check in
                ChecksIn c = new ChecksIn(
                        new Date(rs.getDate("startDate").toString()),
                        new Date(rs.getDate("endDate").toString()),
                        rs.getInt("customerID"),
                        rs.getInt("employeeID")
                );

                // adds new booking to the list
                checkIns.add(c);
            }

            // close the result set
            rs.close();

            // close the statement
            stmt.close();
            con.close();
            db.close();

            // return the list of check ins
            return checkIns;

        } catch (Exception e) {

            // throw the error
            throw new Exception(e.getMessage());

        }
    }

    public String deleteCheckIn(Date sd, Date ed, Integer id) throws Exception {

        Connection con = null;
        String msg = "";

        // sql query
        String sql = "DELETE FROM checks_in WHERE startDate = ? AND endDate = ? AND customerID = ?;";

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
            throw new Exception("Error while deleting check in" + e.getMessage());

        }

        return msg;

    }

    public String createCheckIn(ChecksIn checksIn) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "INSERT INTO checks_in (startDate, endDate, customerID, employeeID) VALUES (?, ?, ?, ?);";

        // try connecting to DB, catch and trow any error
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setDate(1, java.sql.Date.valueOf(checksIn.getStartDate().toString()));
            stmt.setDate(2, java.sql.Date.valueOf(checksIn.getEndDate().toString()));
            stmt.setInt(3, checksIn.getCustomerId());
            stmt.setInt(4, checksIn.getEmployeeId());
            // execute statement
            stmt.executeUpdate();

            // close the statement
            stmt.close();

            // close connection
            con.close();
            db.close();

            return msg;

        } catch (Exception e) {

            throw new Exception("Error while adding check in: " + e.getMessage());

        }
    }

    public String updateCheckIn (Date sd, Date ed, Integer id, String attName, Object val) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "UPDATE checks_in SET ? = ? WHERE startDate = ? AND endDate = ? AND customerID = ?;";

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
