package com.demo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import static java.sql.JDBCType.VARCHAR;

public class RoomService {
    public List<Room> getRooms() throws Exception {

        // sql query
        String sql = "SELECT * FROM room";

        // database connection object
        ConnectionDB db = new ConnectionDB();

        //List to store and return all bookings from database
        List<Room> rooms = new ArrayList<>();

        //Try to connect to DB, catch any exceptions
        try {

            Connection con = db.getConnection();

            //Prepares statement
            PreparedStatement stmt = con.prepareStatement(sql);

            // execute query
            ResultSet rs = stmt.executeQuery();

            // store rooms in list
            while (rs.next()) {
                // create new room
                Room r = new Room(
                    rs.getInt("room_num"),
                    rs.getString("hotel_name"),
                    rs.getInt("hotel_num"),
                    rs.getInt("capacity"),
                    rs.getInt("price"),
                    rs.getString("amenities"),
                    rs.getBoolean("expandable"),
                    rs.getString("problems")
                );

                // adds new room to the list
                rooms.add(r);
            }

            // close the result set
            rs.close();

            // close the statement
            stmt.close();
            con.close();
            db.close();

            // return the list of rooms
            return rooms;

        } catch (Exception e) {

            // throw the error
            throw new Exception(e.getMessage());

        }
    }

    public String deleteRoom(Integer rn, String hotelName, Integer hotelNum) throws Exception {

        Connection con = null;
        String msg = "";

        // sql query
        String sql = "DELETE FROM room WHERE room_num = ? AND hotel_name = ? AND hotel_num = ?;";

        // database connection object
        ConnectionDB db = new ConnectionDB();

        // try to connect to DB, catch exceptions
        try {

            // get connection
            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);

            // set the ? of the statement
            stmt.setInt(1, rn);
            stmt.setString(2, hotelName);
            stmt.setInt(3, hotelNum);

            // execute query
            stmt.executeUpdate();

            // close the statement
            stmt.close();
            con.close();
            db.close();

        } catch (Exception e) {

            // throw exception
            throw new Exception("Error while deleting room" + e.getMessage());

        }

        return msg;

    }

    public String createRoom(Room room) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "INSERT INTO room (room_num, hotel_name, hotel_num, capacity, price, amenities, expandable, porblems) VALUES (?, ?, ?, ?, ?, ?, ?, ?);";

        // try connecting to DB, catch and trow any error
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, room.getRoomNum());
            stmt.setString(2, room.getHotelName());
            stmt.setInt(3, room.getHotelNum());
            stmt.setInt(4, room.getCapacity());
            stmt.setInt(5, room.getPrice());
            stmt.setObject(6, room.getAmenities(), VARCHAR, 100);
            stmt.setBoolean(7, room.isExpandable());
            stmt.setObject(8, room.getProblems(), VARCHAR, 100);

            // execute statement
            stmt.executeUpdate();

            // close the statement
            stmt.close();

            // close connection
            con.close();
            db.close();

            return msg;

        } catch (Exception e) {

            throw new Exception("Error while adding room: " + e.getMessage());

        }
    }

    public String updateRoom (Integer rn, String hotelName, Integer hotelNum, String attName, Object val) throws Exception {

        String msg = "";
        Connection con = null;

        // connection object
        ConnectionDB db = new ConnectionDB();

        // sql query
        String sql = "UPDATE room SET ? = ? WHERE room_num = ? AND hotel_name = ? AND hotel_num = ?;";

        // try to connect to DB, catch any errors
        try {

            con = db.getConnection();

            // prepare statement
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, attName);
            stmt.setObject(2, val);
            stmt.setInt(3, rn);
            stmt.setString(4, hotelName);
            stmt.setInt(5, hotelNum);


            // execute statement
            stmt.executeUpdate();

            // close statement
            stmt.close();

            //close connection
            con.close();
            db.close();

            return msg;

        } catch (Exception e) {

            throw new Exception("Error while updating room: " + e.getMessage());

        }

    }

}
