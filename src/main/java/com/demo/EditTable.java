package com.demo;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class EditTable {
    Scanner keyboard = new Scanner(System.in);

    public EditTable(){

    }
//----------------------------------------CUSTOMER----------------------------------------
public void createCustomer(int id){
    //Getting all the information to create a new hotel.
    CustomerService custService = new CustomerService();
    String fName, sName, city, province, streetName;
    Date regDate;
    int streetNum, hotelNum;
    int cId = id;

    System.out.println(("\n--------------- CREATE NEW CUSTOMER ---------------"));

    System.out.print("ENTER FIRST NAME: ");
    fName = keyboard.nextLine();
    System.out.print("ENTER SURNAME: ");
    sName = keyboard.nextLine();
    System.out.print("ENTER CITY: ");
    city = keyboard.nextLine();
    System.out.print("ENTER PROVINCE: ");
    province = keyboard.nextLine();
    System.out.print("ENTER STREET NAME: ");
    streetName = keyboard.nextLine();
    System.out.print("ENTER STREET NUMBER: ");
    streetNum = Integer.parseInt(keyboard.nextLine());
    System.out.print("ENTER REGISTRATION DATE (YYYY-MM-DD): ");
    regDate = new Date(keyboard.nextLine());

    //Creating new customer.
    Customer newCust = new Customer(cId, fName, sName, city, province, streetName, streetNum,
            regDate);

    //Adding employee.
    try{
        custService.createCustomer(newCust);
    }
    catch (Exception e){
        e.printStackTrace();
    }

    System.out.println("\nNEW CUSTOMER ADDED.\n");
    System.out.println("\nWELCOME.\n");
}

    public void deleteCustomer(int id){
        System.out.println(("\n--------------- DELETE CUSTOMER ---------------"));

        CustomerService custService = new CustomerService();
        List<Customer> customers = new ArrayList<Customer>();

        try {
            custService.deleteCustomer(id);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\nCUSTOMER PROFILE DELETED.\n");
    }

    public void editCustomer(int id){
        System.out.println(("\n--------------- EDIT CUSTOMER ---------------"));
        int attribute;
        int valInt = 0;
        String att = "";
        String valString =null;
        CustomerService custService = new CustomerService();
        List<Customer> customers = new ArrayList<Customer>();
        int edit;

        System.out.println("CUSTOMER: ");

        System.out.println("1 - ID ");
        System.out.println("2 - FIRST NAME");
        System.out.println("3 - SURNAME");
        System.out.println("4 - CITY");
        System.out.println("5 - PROVINCE");
        System.out.println("6 - STREET NAME");
        System.out.println("7 - STREET NUMBER");
        System.out.println("8 - REGISTRATION");
        System.out.print("ENTER: ");

        attribute = Integer.parseInt(keyboard.nextLine());

        //Numbers match the list above.
        switch(attribute) {
            case 1:
                att = "id";
                System.out.print("ENTER NEW VALUE: ");
                valInt = Integer.parseInt(keyboard.nextLine());
                break;
            case 2:
                att = "firstName";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 3:
                att = "surName";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 4:
                att = "city";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 5:
                att = "province";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 6:
                att = "streetName";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 7:
                att = "streetNum";
                System.out.print("ENTER NEW VALUE: ");
                valInt = Integer.parseInt(keyboard.nextLine());
                break;
            case 8:
                att = "registration_date";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            default:
                System.out.println("INVALID INPUT, PLEASE TRY AGAIN.");
                System.out.print("ENTER: ");
                attribute = Integer.parseInt(keyboard.nextLine());

        }

        if (attribute == 1 || attribute == 7){
            try {
                custService.updateCustomer(id, att, valInt);
                System.out.println("\nEMPLOYEE EDIT COMPLETED.\n");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (attribute == 8){
            try {
                Date newDate = new Date(valString);
                custService.updateCustomer(id, att, newDate);
                System.out.println("\nCUSTOMER EDIT COMPLETED.\n");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                custService.updateCustomer(id, att, valString);
                System.out.println("\nCUSTOMER EDIT COMPLETED.\n");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//----------------------------------------ROOM----------------------------------------
public void createRoom(){
    //Getting all the information to create a new room.
    RoomService roomService = new RoomService();
    String hotelName, amenities, problems;
    int roomNum, hotelNum, capacity, price, opt, opt2;
    boolean expandable = true;
    boolean isAvailable = true;

    System.out.println(("\n--------------- CREATE NEW ROOM ---------------"));

    System.out.print("ENTER ROOM NUMBER: ");
    roomNum = Integer.parseInt(keyboard.nextLine());
    System.out.print("ENTER HOTEL NAME: ");
    hotelName = keyboard.nextLine();
    System.out.print("ENTER HOTEL NUMBER: ");
    hotelNum = Integer.parseInt(keyboard.nextLine());
    System.out.print("ENTER CAPACITY: ");
    capacity = Integer.parseInt(keyboard.nextLine());
    System.out.print("ENTER PRICE: ");
    price = Integer.parseInt(keyboard.nextLine());
    System.out.print("ENTER AMENITIES: ");
    amenities = keyboard.nextLine();
    System.out.print("ENTER IF EXPANDABLE (0 - YES)(1 - NO): ");
    opt = Integer.parseInt(keyboard.nextLine());
    if (opt == 0){
        expandable = true;
    }
    else if (opt == 1){
        expandable = false;
    }
    else{
        System.out.println("INCORRECT INPUT.");
        System.out.print("ENTER IF EXPANDABLE (0 - YES)(1 - NO): ");
    }
    System.out.print("ENTER ROOM PROBLEMS: ");
    problems = keyboard.nextLine();
    System.out.print("ENTER AVAILABILITY (0 - YES)(1 - NO): ");
    opt2 = Integer.parseInt(keyboard.nextLine());
    if (opt2 == 0){
        isAvailable = true;
    }
    else if (opt2 == 1){
        isAvailable = false;
    }
    else{
        System.out.println("INCORRECT INPUT.");
        System.out.print("ENTER AVAILABILITY (0 - YES)(1 - NO): ");
    }

    //Creating new room.
    Room newRoom = new Room(roomNum, hotelName, hotelNum, capacity, price, amenities,
            expandable, problems, isAvailable);

    //Adding room.
    try{
        roomService.createRoom(newRoom);
    }
    catch (Exception e){
        e.printStackTrace();
    }

    System.out.println("\nNEW ROOM ADDED.\n");
}
    public void deleteRoom(){
        System.out.println(("\n--------------- DELETE ROOM ---------------"));

        RoomService roomService = new RoomService();
        List<Room> rooms = new ArrayList<Room>();
        int delRoom;

        System.out.println("ROOMS: ");
        try {
            rooms = roomService.getRooms();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < rooms.size(); i++){//Printing all hotels.
            System.out.println(i + ": " + rooms.get(i));
        }

        System.out.print("ENTER WHICH ROOM TO DELETE: ");
        delRoom = Integer.parseInt(keyboard.nextLine());

        try {
            roomService.deleteRoom(rooms.get(delRoom).getRoomNum(),
                    rooms.get(delRoom).getHotelName(), rooms.get(delRoom).getHotelNum());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\nROOM DELETED.\n");
    }

    public void editRoom(){
        System.out.println(("\n--------------- EDIT ROOM ---------------"));
        int attribute, opt;
        int valInt = 0;
        String att = "";
        String valString =null;
        Boolean valBool = false;
        RoomService roomService = new RoomService();
        List<Room> rooms = new ArrayList<Room>();
        int edit;

        System.out.println("ROOMS: ");
        try {
            rooms = roomService.getRooms();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < rooms.size(); i++){//Printing all hotels.
            System.out.println(i + ": " + rooms.get(i));
        }

        System.out.print("ENTER WHICH ROOM TO EDIT: ");
        edit = Integer.parseInt(keyboard.nextLine());

        System.out.println("1 - ROOM NUMBER");
        System.out.println("2 - HOTEL NAME");
        System.out.println("3 - HOTEL NUMBER");
        System.out.println("4 - CAPACITY");
        System.out.println("5 - PRICE");
        System.out.println("6 - AMENITIES");
        System.out.println("7 - EXPANDABLE");
        System.out.println("8 - PROBLEMS");
        System.out.println("9 - AVAILABILITY");
        System.out.print("ENTER: ");

        attribute = Integer.parseInt(keyboard.nextLine());

        //Numbers match the list above.
        switch(attribute) {
            case 1:
                att = "room_num";
                System.out.print("ENTER NEW VALUE: ");
                valInt = Integer.parseInt(keyboard.nextLine());
                break;
            case 2:
                att = "hotel_name";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 3:
                att = "hotel_num";
                System.out.print("ENTER NEW VALUE: ");
                valInt = Integer.parseInt(keyboard.nextLine());
                break;
            case 4:
                att = "capacity";
                System.out.print("ENTER NEW VALUE: ");
                valInt = Integer.parseInt(keyboard.nextLine());
                break;
            case 5:
                att = "price";
                System.out.print("ENTER NEW VALUE: ");
                valInt = Integer.parseInt(keyboard.nextLine());
                break;
            case 6:
                att = "amenities";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 7:
                att = "expandable";
                System.out.print("ENTER (0 - YES) (1 - NO): ");
                opt = Integer.parseInt(keyboard.nextLine());
                if (opt == 0){
                    valBool = true;
                }
                else if (opt == 1){
                    valBool = false;
                }
                else{
                    System.out.println("INCORRECT INPUT.");
                    System.out.print("ENTER (0 - YES) (1 - NO): ");
                }
                break;
            case 8:
                att = "problems";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 9:
                att = "isAvailable";
                System.out.print("ENTER (0 - YES) (1 - NO): ");
                opt = Integer.parseInt(keyboard.nextLine());
                if (opt == 0){
                    valBool = true;
                }
                else if (opt == 1){
                    valBool = false;
                }
                else{
                    System.out.println("INCORRECT INPUT.");
                    System.out.print("ENTER (0 - YES) (1 - NO): ");
                }
                break;
            default:
                System.out.println("INVALID INPUT, PLEASE TRY AGAIN.");
                System.out.print("ENTER: ");
                attribute = Integer.parseInt(keyboard.nextLine());

        }

        if (attribute == 1 || attribute == 3 || attribute == 4 || attribute == 5){
            try {
                roomService.updateRoom(rooms.get(edit).getRoomNum(), rooms.get(edit).getHotelName(),
                        rooms.get(edit).getHotelNum(), att, valInt);
                System.out.println("\nROOM EDIT COMPLETED.\n");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else if (attribute == 7 || attribute == 9){
            try {
                roomService.updateRoom(rooms.get(edit).getRoomNum(), rooms.get(edit).getHotelName(),
                        rooms.get(edit).getHotelNum(), att, valBool);
                System.out.println("\nROOM EDIT COMPLETED.\n");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                roomService.updateRoom(rooms.get(edit).getRoomNum(), rooms.get(edit).getHotelName(),
                        rooms.get(edit).getHotelNum(), att, valString);
                System.out.println("\nROOM EDIT COMPLETED.\n");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//----------------------------------------BOOKING----------------------------------------

//----------------------------------------EMPLOYEE----------------------------------------
public void createEmployee(){
    //Getting all the information to create a new hotel.
    EmployeeService empService = new EmployeeService();
    String fName, sName, city, province, streetName, position, hotelName;
    int streetNum, hotelNum, id;

    System.out.println(("\n--------------- CREATE NEW EMPLOYEE ---------------"));

    System.out.print("ENTER CUSTOMER ID: ");
    id = Integer.parseInt(keyboard.nextLine());
    System.out.print("ENTER FIRST NAME: ");
    fName = keyboard.nextLine();
    System.out.print("ENTER SURNAME: ");
    sName = keyboard.nextLine();
    System.out.print("ENTER CITY: ");
    city = keyboard.nextLine();
    System.out.print("ENTER PROVINCE: ");
    province = keyboard.nextLine();
    System.out.print("ENTER STREET NAME: ");
    streetName = keyboard.nextLine();
    System.out.print("ENTER STREET NUMBER: ");
    streetNum = Integer.parseInt(keyboard.nextLine());
    System.out.print("ENTER POSITION: ");
    position = keyboard.nextLine();
    System.out.print("ENTER HOTEL NAME: ");
    hotelName = keyboard.nextLine();
    System.out.print("ENTER HOTEL NUMBER: ");
    hotelNum = Integer.parseInt(keyboard.nextLine());

    //Creating new employee.
    Employee newEmployee = new Employee(id, fName, sName, city, province, streetName, streetNum,
            position, hotelName, hotelNum);

    //Adding employee.
    try{
        empService.createEmployee(newEmployee);
    }
    catch (Exception e){
        e.printStackTrace();
    }

    System.out.println("\nNEW EMPLOYEE ADDED.\n");
}

    public void deleteEmployee(){
        System.out.println(("\n--------------- DELETE EMPLOYEE ---------------"));

        EmployeeService empService = new EmployeeService();
        List<Employee> employees = new ArrayList<Employee>();
        int delEmp;

        System.out.println("EMPLOYEES: ");
        try {
            employees = empService.getEmployees();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < employees.size(); i++){//Printing all hotels.
            System.out.println(i + ": " + employees.get(i));
        }

        System.out.print("ENTER WHICH EMPLOYEE TO DELETE: ");
        delEmp = Integer.parseInt(keyboard.nextLine());

        try {
            empService.deleteEmployee(employees.get(delEmp).getId());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\nEMPLOYEE DELETED.\n");
    }

    public void editEmployee(){
        System.out.println(("\n--------------- EDIT EMPLOYEE ---------------"));
        int attribute;
        int valInt = 0;
        String att = "";
        String valString =null;
        EmployeeService empService = new EmployeeService();
        List<Employee> employees = new ArrayList<Employee>();
        int edit;

        System.out.println("EMPLOYEES: ");
        try {
            employees = empService.getEmployees();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < employees.size(); i++){//Printing all hotels.
            System.out.println(i + ": " + employees.get(i));
        }

        System.out.print("ENTER WHICH EMPLOYEE TO EDIT: ");
        edit = Integer.parseInt(keyboard.nextLine());

        System.out.println("1 - ID ");
        System.out.println("2 - FIRST NAME");
        System.out.println("3 - SURNAME");
        System.out.println("4 - CITY");
        System.out.println("5 - PROVINCE");
        System.out.println("6 - STREET NAME");
        System.out.println("7 - STREET NUMBER");
        System.out.println("8 - POSITION");
        System.out.println("9 - HOTEL NAME");
        System.out.println("10 - HOTEL NUMBER");
        System.out.print("ENTER: ");

        attribute = Integer.parseInt(keyboard.nextLine());

        //Numbers match the list above.
        switch(attribute) {
            case 1:
                att = "id";
                System.out.print("ENTER NEW VALUE: ");
                valInt = Integer.parseInt(keyboard.nextLine());
                break;
            case 2:
                att = "firstName";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 3:
                att = "surName";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 4:
                att = "city";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 5:
                att = "province";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 6:
                att = "streetName";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 7:
                att = "streetNum";
                System.out.print("ENTER NEW VALUE: ");
                valInt = Integer.parseInt(keyboard.nextLine());
                break;
            case 8:
                att = "position";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 9:
                att = "hotel_name";
                System.out.print("ENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 10:
                att = "hotel_num";
                System.out.print("ENTER NEW VALUE: ");
                valInt = Integer.parseInt(keyboard.nextLine());
                break;
            default:
                System.out.println("INVALID INPUT, PLEASE TRY AGAIN.");
                System.out.print("ENTER: ");
                attribute = Integer.parseInt(keyboard.nextLine());

        }

        if (attribute == 1 || attribute == 7 || attribute == 10){
            try {
                empService.updateEmployee(employees.get(edit).getId(), att, valInt);
                System.out.println("\nEMPLOYEE EDIT COMPLETED.\n");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                empService.updateEmployee(employees.get(edit).getId(), att, valString);
                System.out.println("\nEMPLOYEE EDIT COMPLETED.\n");
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
//----------------------------------------HOTELS----------------------------------------
    public void createHotel(){
        //Getting all the information to create a new hotel.
        HotelService hotelService = new HotelService();
        String name, chainName, province, city, streetName, email, phoneNum;
        int streetNum, numOfRooms, rating;

        System.out.println(("\n--------------- CREATE NEW HOTEL ---------------"));

        System.out.print("ENTER HOTEL NAME: \n");
        name = keyboard.nextLine();

        System.out.print("ENTER HOTEL CHAIN NAME: \n");
        chainName = keyboard.nextLine();

        System.out.print("ENTER PROVINCE: \n");
        province = keyboard.nextLine();

        System.out.print("ENTER CITY: \n");
        city = keyboard.nextLine();

        System.out.print("ENTER STREET NAME: \n");
        streetName = keyboard.nextLine();

        System.out.print("ENTER STREET NUMBER: \n");
        streetNum = Integer.parseInt(keyboard.nextLine());

        System.out.print("ENTER NUMBER OF ROOMS: \n");
        numOfRooms = Integer.parseInt(keyboard.nextLine());

        System.out.print("ENTER RATING (NUMBER): \n");
        rating = Integer.parseInt(keyboard.nextLine());

        System.out.print("ENTER EMAIL: \n");
        email = keyboard.nextLine();

        System.out.print("ENTER PHONE NUMBER: \n");
        phoneNum = keyboard.nextLine();

        //Creating new hotel.
        Hotel newHotel = new Hotel(name, chainName, province, city, streetName, streetNum,
                numOfRooms, rating, email, phoneNum);

        //Adding hotel.
        try{
            hotelService.createHotel(newHotel);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        System.out.println("\nNEW HOTEL ADDED.\n");
    }

    public void deleteHotel(){
        System.out.println(("\n--------------- DELETE HOTEL ---------------"));

        HotelService hotelService = new HotelService();
        List<Hotel> hotels = new ArrayList<Hotel>();
        int delHotel;

        System.out.println("HOTELS: ");
        try {
            hotels = hotelService.getHotels();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < hotels.size(); i++){//Printing all hotels.
            System.out.println(i + ": " + hotels.get(i));
        }

        System.out.print("ENTER WHICH HOTEL TO DELETE: ");
        delHotel = Integer.parseInt(keyboard.nextLine());

        try {
            hotelService.deleteHotel(hotels.get(delHotel).getName(),
                    hotels.get(delHotel).getStreetNum());
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("\nHOTEL DELETED.\n");
    }

    public void editHotel(){
        System.out.println(("\n--------------- EDIT HOTEL ---------------"));
        int attribute;
        int valInt = 0;
        String att = "";
        String valString =null;
        HotelService hotelService = new HotelService();
        List<Hotel> hotels = new ArrayList<Hotel>();
        int edit;

        System.out.println("HOTELS: ");
        try {
            hotels = hotelService.getHotels();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        for (int i = 0; i < hotels.size(); i++){//Printing all hotels.
            System.out.println(i + ": " + hotels.get(i));
        }

        System.out.print("ENTER WHICH HOTEL TO EDIT: ");
        edit = Integer.parseInt(keyboard.nextLine());

        System.out.println("1 - NAME ");
        System.out.println("2 - CHAIN NAME");
        System.out.println("3 - PROVINCE");
        System.out.println("4 - CITY");
        System.out.println("5 - STREET NAME");
        System.out.println("6 - STREET NUMBER");//INT
        System.out.println("7 - NUMBER OF ROOMS");//INT
        System.out.println("8 - RATING");//INT
        System.out.println("9 - EMAIL");
        System.out.println("10 - PHONE NUMBER");
        System.out.print("ENTER: ");

        attribute = Integer.parseInt(keyboard.nextLine());

        //Numbers match the list above.
        switch(attribute) {
            case 1:
                att = "name";
                System.out.print("\nENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 2:
                att = "chain_name";
                System.out.print("\nENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 3:
                att = "city";
                System.out.print("\nENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 4:
                att = "province";
                System.out.print("\nENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 5:
                att = "streetName";
                System.out.print("\nENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 6:
                att = "streetNum";
                System.out.print("\nENTER NEW VALUE: ");
                valInt = Integer.parseInt(keyboard.nextLine());
                break;
            case 7:
                att = "number_of_rooms";
                System.out.print("\nENTER NEW VALUE: ");
                valInt = Integer.parseInt(keyboard.nextLine());
                break;
            case 8:
                att = "rating";
                System.out.print("\nENTER NEW VALUE: ");
                valInt = Integer.parseInt(keyboard.nextLine());
                break;
            case 9:
                att = "email";
                System.out.print("\nENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            case 10:
                att = "phone_num";
                System.out.print("\nENTER NEW VALUE: ");
                valString = keyboard.nextLine();
                break;
            default:
                System.out.println("\nINVALID INPUT, PLEASE TRY AGAIN.");
                System.out.print("ENTER: ");
                attribute = Integer.parseInt(keyboard.nextLine());

        }

            if (attribute == 6 || attribute == 7 || attribute == 8){
                try {
                    hotelService.updateHotel(hotels.get(edit).getName(), hotels.get(edit).getStreetNum(),
                            att, valInt);
                    System.out.println("\nHOTEL EDIT COMPLETED.\n");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                try {
                    hotelService.updateHotel(hotels.get(edit).getName(), hotels.get(edit).getStreetNum(),
                            att, valString);
                    System.out.println("\nHOTEL EDIT COMPLETED.\n");
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }
}
