package com.demo;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;

public class DatabaseProject {
    public static void main(String[] args){
        Boolean runMain = true;

        while (runMain){
            Scanner keyboard = new Scanner(System.in);

            System.out.println("WELCOME TO HOTEL DATABASE");
            System.out.println("0 - EMPLOYEE");
            System.out.println("1 - CUSTOMER");
            System.out.println("2 - END PROGRAM");
            System.out.print("ENTER: ");

            String answer = keyboard.nextLine();

            if (Integer.parseInt(answer) == 0){//EMPLOYEE
                employee();
            }
            else if (Integer.parseInt(answer) == 1){//CUSTOMER
                customer();
            }
            else if (Integer.parseInt(answer) == 2){
                System.out.println("ENDING PROGRAM.");
                runMain = false;

            }
            else{
                System.out.println("INCORRECT INPUT, PLEASE TRY AGAIN.");
                System.out.print("ENTER: ");
                answer = keyboard.nextLine();
            }
        }
    }

    //When user chooses option 1, this function is called.
    //A - Lists all bookings and can delete one using start date/end date/customer id.
    //B - Can create new rentings.
    //C - Can update payments.
    //D = Create/edit/delete hotel information.
    //E = Create/edit/delete employee information.
    //F = Create/edit/delete room information.
    //Z - Return to main menu (employee or customer).
    public static void employee(){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("WELCOME EMPLOYEE");
        System.out.println("A - MOVE BOOKINGS INTO RENTINGS");
        System.out.println("B - CREATE RENTINGS");
        System.out.println("C - UPDATE CUSTOMER PAYMENTS");
        System.out.println("D - CREATE/EDIT/DELETE HOTEL INFORMATION");
        System.out.println("E - CREATE/EDIT/DELETE EMPLOYEE INFORMATION");
        System.out.println("F - CREATE/EDIT/DELETE ROOM INFORMATION");
        System.out.println("Z - RETURN");
        System.out.print("ENTER: ");

        String answer = keyboard.nextLine();

        if (answer.compareToIgnoreCase("A") == 0){//MOVE BOOKINGS INTO RENTINGS
            Scanner keyboard2 = new Scanner(System.in);
            String hotelName;
            int streetNum, deleteBooking;

            System.out.println("\n--------------- MOVE BOOKINGS INTO RENTINGS ---------------");
            //Get info to make specific list of bookings based on hotel.
            System.out.print("ENTER HOTEL NAME: ");
            hotelName = keyboard2.nextLine();
            System.out.print("ENTER HOTEL STREET NUMBER: ");
            streetNum = Integer.parseInt(keyboard2.nextLine());

            //Listing bookings.
            System.out.println("BOOKINGS: ");
            BookingService bookService = new BookingService();
            List<Booking> bookings = new ArrayList<Booking>();
            try {
                bookings = bookService.getBookingsFromHotel(hotelName, streetNum);
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < bookings.size(); i++){//Printing all bookings.
                System.out.println(i + ": " + bookings.get(i));
            }

            System.out.print("ENTER WHICH BOOKING TO TURN INTO A RENTING: ");
            deleteBooking = Integer.parseInt(keyboard2.nextLine());
            try{
                bookService.deleteBooking(bookings.get(deleteBooking).getStartDate(), bookings.get(deleteBooking).getEndDate(), bookings.get(deleteBooking).getCustomerID());
            }
            catch (Exception e){
                e.printStackTrace();
            }

            System.out.print("BOOKINGS UPDATED: ");
            for (int i = 0; i < bookings.size(); i++){//Printing all bookings.
                System.out.println(i + ": " + bookings.get(i));
            }
            return;

        }

        else if(answer.compareToIgnoreCase("B") == 0){//CREATING RENTINGS
            System.out.println(("\n--------------- RENTING CREATION ---------------"));
            Date start,end;
            int cId, eId, roomNum, hotelNum;
            boolean paid = false;
            String hotelName;
            double roomPrice;
            RentingService rentService = new RentingService();
            Scanner keyboard2 = new Scanner(System.in);

            //Getting all the information for a new renting.
            System.out.print("Enter start date (YYYY-MM-DD): ");
            start = new Date(keyboard2.nextLine());

            System.out.print("Enter end date (YYYY-MM-DD): ");
            end = new Date(keyboard2.nextLine());

            System.out.print("Enter customer ID: ");
            cId = Integer.parseInt(keyboard2.nextLine());

            System.out.print("Enter employee ID: ");
            eId = Integer.parseInt(keyboard2.nextLine());

            System.out.print("Enter room number: ");
            roomNum = Integer.parseInt(keyboard2.nextLine());

            System.out.print("Enter room price: ");
            roomPrice = Double.parseDouble(keyboard2.nextLine());

            System.out.print("Enter hotel name: ");
            hotelName = keyboard2.nextLine();

            System.out.print("Enter hotel number: ");
            hotelNum = Integer.parseInt(keyboard2.nextLine());

            //Renting rent = new Renting(start, end, cId, eId, paid, roomNum, roomPrice, hotelName, hotelNum);

            try{
                rentService.createRenting(new Renting(start, end, cId, eId, paid, roomNum, roomPrice, hotelName, hotelNum));
            }
            catch (Exception e){
                e.printStackTrace();
            }

            System.out.println("\nNEW RENTING CREATED.\n");
            return;
        }

        else if(answer.compareToIgnoreCase("C") == 0){//Update payment
            System.out.println(("\n--------------- PAYMENT UPDATES ---------------"));
            Scanner keyboard2 = new Scanner(System.in);
            int updateRent;

            //Listing rentings.
            //Should be all rentings with false as their payment.
            System.out.println("RENTINGS: ");
            RentingService rentService = new RentingService();
            List<Renting> rentings = new ArrayList<Renting>();

            try {
                rentings = rentService.getRentings();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            for (int i = 0; i < rentings.size(); i++){//Printing all rentings.
                System.out.println(i + ": " + rentings.get(i));
            }

            System.out.print("ENTER WHICH RENTING TO UPDATE: ");
            updateRent = Integer.parseInt(keyboard2.nextLine());

            //Change status_of_payment value to true.
            try{
                rentService.updatePaymentStatus(rentings.get(updateRent).getStartDate(), rentings.get(updateRent).getEndDate(),
                        rentings.get(updateRent).getCustomerID(), true);
            }
            catch (Exception e){
                e.printStackTrace();
            }

            System.out.println("\nRENTING PAYMENT UPDATED.\n");

            return;
        }

        else if(answer.compareToIgnoreCase("D") == 0) {
            System.out.println(("\n--------------- CREATE/DELETE/EDIT HOTEL INFORMATION ---------------"));
            Scanner keyboard2 = new Scanner(System.in);
            EditTable hotelEditor = new EditTable();
            String opt;

            System.out.println("A - CREATE A NEW HOTEL");
            System.out.println("B - DELETE A HOTEL");
            System.out.println("C - EDIT A HOTEL");
            System.out.print("ENTER: ");

            opt = keyboard2.nextLine();

            if (opt.compareToIgnoreCase("A") == 0){//CREATE HOTEL
                hotelEditor.createHotel();
            }
            else if (opt.compareToIgnoreCase("B") == 0){//DELETE HOTEL
                hotelEditor.deleteHotel();
            }
            else if (opt.compareToIgnoreCase("C") == 0){//EDIT HOTEL
                hotelEditor.editHotel();
            }
            return;
        }

        else if(answer.compareToIgnoreCase("E") == 0) {
            System.out.println(("\n--------------- CREATE/DELETE/EDIT EMPLOYEE INFORMATION ---------------"));
            Scanner keyboard2 = new Scanner(System.in);
            EditTable employeeEditor = new EditTable();
            String opt;

            System.out.println("A - CREATE A NEW EMPLOYEE");
            System.out.println("B - DELETE AN EMPLOYEE");
            System.out.println("C - EDIT AN EMPLOYEE");
            System.out.print("ENTER: ");

            opt = keyboard2.nextLine();

            if (opt.compareToIgnoreCase("A") == 0){//CREATE EMPLOYEE
                employeeEditor.createEmployee();
            }
            else if (opt.compareToIgnoreCase("B") == 0){//DELETE EMPLOYEE
                employeeEditor.deleteEmployee();
            }
            else if (opt.compareToIgnoreCase("C") == 0){//EDIT EMPLOYEE
                employeeEditor.editEmployee();
            }

            return;
        }

        else if(answer.compareToIgnoreCase("F") == 0){
            System.out.println(("\n--------------- CREATE/DELETE/EDIT ROOM INFORMATION ---------------"));
            Scanner keyboard2 = new Scanner(System.in);
            EditTable roomEditor = new EditTable();
            String opt;

            System.out.println("A - CREATE A NEW ROOM");
            System.out.println("B - DELETE A ROOM");
            System.out.println("C - EDIT A ROOM");
            System.out.print("ENTER: ");

            opt = keyboard2.nextLine();

            if (opt.compareToIgnoreCase("A") == 0){//CREATE ROOM
                roomEditor.createRoom();
            }
            else if (opt.compareToIgnoreCase("B") == 0){//DELETE ROOM
                roomEditor.deleteRoom();
            }
            else if (opt.compareToIgnoreCase("C") == 0){//EDIT ROOM
                roomEditor.editRoom();
            }

            return;
        }

        else{
            System.out.println("INCORRECT INPUT, PLEASE TRY AGAIN.");
            System.out.print("ENTER: ");
            answer = keyboard.nextLine();
        }
    }

    //----------------------------------------CUSTOMER----------------------------------------
    //When user chooses option 2, they get sent here.
    //Check if they are in the database, if not forces them to create a new profile.
    //A - Search rooms.
    //B - Create a booking.
    //Z - Return to main menu.
    public static void customer(){
        int id;
        Scanner keyboard = new Scanner(System.in);
        CustomerService custService = new CustomerService();
        EditTable customerEditor = new EditTable();

        System.out.println("WELCOME CUSTOMER");
        System.out.print("ENTER ID: ");
        id = Integer.parseInt(keyboard.nextLine());

        try {
            if (!custService.customerExists(id)) {
                System.out.println("CUSTOMER PROFILE NOT FOUND.");
                System.out.println("PLEASE CREATE A PROFILE.");
                customerEditor.createCustomer(id);
            }
            else {//Profile already exists.
                System.out.println("WELCOME CUSTOMER");
                System.out.println("A - SEARCH ROOMS AND CREATE A BOOKING");
                System.out.println("B - EDIT CUSTOMER PROFILE");
                System.out.println("C - DELETE CUSTOMER PROFILE");
                System.out.println("Z - RETURN");
                System.out.print("ENTER: ");

                String answer = keyboard.nextLine();

                if (answer.compareToIgnoreCase("A") == 0) {//SEARCH ROOMS+CREATE BOOKINGS
                    String startDate, endDate, capacity, city, hotelChain, rating, hotelRoomNum;
                    List <String> search = new ArrayList<String>();
                    Scanner keyboard2 = new Scanner(System.in);

                    System.out.print("ENTER BOOKING/RENTING START DATE: ");
                    startDate = keyboard2.nextLine();
                    search.add(startDate);
                    System.out.print("ENTER BOOKING/RENTING END DATE: ");
                    endDate = keyboard2.nextLine();
                    search.add(endDate);
                    System.out.print("ENTER ROOM CAPACITY: ");
                    capacity = keyboard2.nextLine();
                    search.add(capacity);
                    System.out.print("ENTER CITY: ");
                    city = keyboard2.nextLine();
                    search.add(city);
                    System.out.print("ENTER HOTEL CHAIN: ");
                    hotelChain = keyboard2.nextLine();
                    search.add(hotelChain);
                    System.out.print("ENTER RATING: ");
                    rating = keyboard2.nextLine();
                    search.add(rating);
                    System.out.print("ENTER NUMBER OF ROOMS: ");
                    hotelRoomNum = keyboard2.nextLine();
                    search.add(hotelRoomNum);

                    return;
                } else if (answer.compareToIgnoreCase("B") == 0) {//EDIT CUSTOMER PROFILE
                    customerEditor.editCustomer(id);
                    return;
                } else if (answer.compareToIgnoreCase("C") == 0) {//DELETE CUSTOMER PROFILE
                    customerEditor.deleteCustomer(id);
                    return;
                } else if (answer.compareToIgnoreCase("Z") == 0) {
                    return;
                } else {
                    System.out.println("INCORRECT INPUT, PLEASE TRY AGAIN.");
                    System.out.print("ENTER: ");
                    answer = keyboard.nextLine();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
