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
            /*else if (Integer.parseInt(answer) == 1){//CUSTOMER

            }**/
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
    //Z - Return to main menu (employee or customer).
    public static void employee(){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("WELCOME EMPLOYEE");
        System.out.println("A - MOVE BOOKINGS INTO RENTINGS");
        System.out.println("B - CREATE RENTINGS");
        System.out.println("C - UPDATE CUSTOMER PAYMENTS");
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
                bookings = bookService.getBookings();
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

            System.out.println("\nNEW RENTING CREATED.");
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
                rentService.updateRenting(rentings.get(updateRent).getStartDate(), rentings.get(updateRent).getEndDate(),
                        rentings.get(updateRent).getCustomerID(), "status_of_payment", "TRUE");
            }
            catch (Exception e){
                e.printStackTrace();
            }

            System.out.print("RENTING PAYMENT UPDATED: ");

            return;
        }

        else if(answer.compareToIgnoreCase("Z") == 0){
            return;
        }

        else{
            System.out.println("INCORRECT INPUT, PLEASE TRY AGAIN.");
            System.out.print("ENTER: ");
            answer = keyboard.nextLine();
        }
    }

    //When user chooses option 2, they get sent here.
    //A - Search rooms.
    //B - Create a booking.
    //Z - Return to main menu.
    public static void customer(){
        Scanner keyboard = new Scanner(System.in);
        System.out.println("WELCOME CUSTOMER");
        System.out.println("A - SEARCH ROOMS");
        System.out.println("B - CREATE A BOOKING");
        System.out.println("Z - RETURN");
        System.out.print("ENTER: ");

        String answer = keyboard.nextLine();

        if(answer.compareToIgnoreCase("A") == 0) {//SEARCH ROOMS
            //GET ALL SEARCH KEYS
            //CHECK HOW MANY KEYS YOU HAVE
            //ITERATE THROUGH LIST -2, EACH ELEMENT CHECK THE BIG CASE LIST
            //KEEP ADDING TO STRING THAT WILL LATER BECOME THE QUERY I THINK
            //FINISH ITERATING AND NOW THE SEARCH SHOULD BE START + END + WHATEVER ELSE OR NOT
            ArrayList<String> search = new ArrayList<String>();

            String startDate, endDate, roomNum, hotelName, hotelNum, capacity, price, hotelRoomNum;
            Scanner keyboard2 = new Scanner(System.in);



            return;
        }

        else if(answer.compareToIgnoreCase("B") == 0) {//CREATING A BOOKING
            return;
        }

        else if(answer.compareToIgnoreCase("Z") == 0) {
            return;
        }

        else{
            System.out.println("INCORRECT INPUT, PLEASE TRY AGAIN.");
            System.out.print("ENTER: ");
            answer = keyboard.nextLine();
        }
    }
}
