package com.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.sql.JDBCType.VARCHAR;

public class EmployeeService {
    public List<Employee> getEmployees() throws Exception {

        // sql query
        String sql = "SELECT * FROM employee";

        // database connection object
        ConnectionDB db = new ConnectionDB();

        //List to store and return all bookings from database
        List<Employee> employees = new ArrayList<>();

        //Try to connect to DB, catch any exceptions
        try {
            Connection con = db.getConnection();

            //Prepares statement
            PreparedStatement stmt = con.prepareStatement(sql);

            // execute query
            ResultSet rs = stmt.executeQuery();

            // store employees in list
            while (rs.next()) {
                // create new employee
                Employee e = new Employee(
                        rs.getInt("id"),
                        rs.getString("firstName"),
                        rs.getString("surName"),
                        rs.getString("city"),
                        rs.getString("province"),
                        rs.getString("streetName"),
                        rs.getInt("streetNum"),
                        rs.getString("position"),
                        rs.getString("hotel_name"),
                        rs.getInt("hotel_num")
                );

                // adds new employee to the list
                employees.add(e);
            }

            // close the result set
            rs.close();

            // close the statement
            stmt.close();
            con.close();
            db.close();

            // return the list of employees
            return employees;

        } catch (Exception e) {

            // throw the error
            throw new Exception(e.getMessage());

        }
    }

    public String deleteEmployee(Integer id) throws Exception {

        Connection con = null;
        String msg = "";

        // sql query
        String sql = "DELETE FROM employee WHERE id = ?;";

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
            throw new Exception("Error while deleting employee:" + e.getMessage());

        }

        return msg;

    }

    public String createEmployee(Employee employee) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "INSERT INTO employee (id, firstName, surName, city, province, streetName, streetNum, position, hotel_name, hotel_num) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        // try connecting to DB, catch and trow any error
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, employee.getId());
            stmt.setObject(2, employee.getfName(), VARCHAR, 45);
            stmt.setObject(3, employee.getsName(), VARCHAR, 45);
            stmt.setObject(4, employee.getCity(), VARCHAR, 45);
            stmt.setObject(5, employee.getProvince(), VARCHAR, 45);
            stmt.setObject(6, employee.getStreetName(),VARCHAR, 45);
            stmt.setInt(7, employee.getStreetNum());
            stmt.setObject(8, employee.getPosition(), VARCHAR, 45);
            stmt.setObject(9, employee.getHotelName(), VARCHAR, 45);
            stmt.setInt(10, employee.getHotelNum());

            // execute statement
            stmt.executeUpdate();

            // close the statement
            stmt.close();

            // close connection
            con.close();
            db.close();

            return msg;

        } catch (Exception e) {

            throw new Exception("Error while adding employee: " + e.getMessage());

        }
    }

    public String updateEmployee (Integer id, String attName, Object val) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "UPDATE employee SET ? = ? WHERE id = ?;";

        // try to connect to DB, catch any errors
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setObject(1, val);
            stmt.setString(2, attName);
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

            throw new Exception("Error while updating employee: " + e.getMessage());

        }

    }

}
