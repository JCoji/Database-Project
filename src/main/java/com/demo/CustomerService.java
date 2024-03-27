package com.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.sql.JDBCType.VARCHAR;

public class CustomerService {

    public List<Customer> getCustomers() throws Exception {

        // sql query
        String sql = "SELECT * FROM customer";

        // database connection object
        ConnectionDB db = new ConnectionDB();

        //List to store and return all bookings from database
        List<Customer> customers = new ArrayList<>();

        //Try to connect to DB, catch any exceptions
        try {
            Connection con = db.getConnection();

            //Prepares statement
            PreparedStatement stmt = con.prepareStatement(sql);

            // execute query
            ResultSet rs = stmt.executeQuery();

            // store customers in list
            while (rs.next()) {
                // create new customer
                Customer c = new Customer(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("surName"),
                        rs.getString("city"),
                        rs.getString("province"),
                        rs.getString("streetName"),
                        rs.getInt("streetNum"),
                        new Date(rs.getDate("registration_Date").toString())
                );

                // adds new customer to the list
                customers.add(c);
            }

            // close the result set
            rs.close();

            // close the statement
            stmt.close();
            con.close();
            db.close();

            // return the list of customers
            return customers;

        } catch (Exception e) {

            // throw the error
            throw new Exception(e.getMessage());

        }
    }

    public String deleteCustomer(Integer id) throws Exception {

        Connection con = null;
        String msg = "";

        // sql query
        String sql = "DELETE FROM customer WHERE id = ?;";

        // database connection object
        ConnectionDB db = new ConnectionDB();

        // try to connect to DB, catch exceptions
        try {

            // get connection
            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);

            // set the ? of the statement
            stmt.setInt(1, id);

            // execute query
            stmt.executeUpdate();

            // close the statement
            stmt.close();
            con.close();
            db.close();

        } catch (Exception e) {

            // throw exception
            throw new Exception("Error while deleting customer" + e.getMessage());
        }

        return msg;

    }

    public String createCustomer(Customer customer) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "INSERT INTO customer (id, firstName, surName, city, province, streetName, streetNum, registration_date) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";

        // try connecting to DB, catch and trow any error
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, customer.getId());
            stmt.setString(2, customer.getfName());
            stmt.setString(3, customer.getsName());
            stmt.setString(4, customer.getCity());
            stmt.setString(5, customer.getProvince());
            stmt.setString(6, customer.getStreetName());
            stmt.setInt(7, customer.getStreetNum());
            stmt.setDate(8, java.sql.Date.valueOf(customer.getRegDate().toString()));

            // execute statement
            stmt.executeUpdate();

            // close the statement
            stmt.close();

            // close connection
            con.close();
            db.close();

            return msg;

        } catch (Exception e) {

            throw new Exception("Error while adding customer: " + e.getMessage());

        }
    }

    public String updateCustomer (Integer id, String attName, Object val) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "UPDATE customer SET " + attName + " = '" + val + "' WHERE id = ?;";

        // try to connect to DB, catch any errors
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, id);

            // execute statement
            stmt.executeUpdate();

            // close statement
            stmt.close();

            //close connection
            con.close();
            db.close();

            return msg;

        } catch (Exception e) {

            throw new Exception("Error while updating customer: " + e.getMessage());

        }

    }

    public boolean customerExists(int enteredID) throws Exception {

        // sql query
        String sql = "SELECT * FROM customer";

        // database connection object
        ConnectionDB db = new ConnectionDB();

        //List to store and return all bookings from database
        boolean result = false;

        //Try to connect to DB, catch any exceptions
        try {
            Connection con = db.getConnection();

            //Prepares statement
            PreparedStatement stmt = con.prepareStatement(sql);

            // execute query
            ResultSet rs = stmt.executeQuery();

            // store customers in list
            while (rs.next()) {
                // create new customer
                Customer c = new Customer(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("surName"),
                        rs.getString("city"),
                        rs.getString("province"),
                        rs.getString("streetName"),
                        rs.getInt("streetNum"),
                        new Date(rs.getDate("registration_Date").toString())
                );

                // adds new customer to the list
                if(rs.getInt("id") == enteredID){
                    result = true;
                }
            }

            // close the result set
            rs.close();

            // close the statement
            stmt.close();
            con.close();
            db.close();

            // return the list of customers
            return result;

        } catch (Exception e) {

            // throw the error
            throw new Exception(e.getMessage());

        }
    }

}
