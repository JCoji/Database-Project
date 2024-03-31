<%@ page import="com.demo.BookingService" %>
<%@ page import="com.demo.Booking" %>
<%@ page import="com.demo.Date" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List" %>

<%
    BookingService bookService = new BookingService();
    List<Booking> bookings = new ArrayList<Booking>();
    Date start, end;
    int id;


    if (request.getParameter("submit") != null){
        if (request.getParameter("submit").equals("CHANGE")){
            start = new Date(request.getParameter("startDate"));
            end = new Date(request.getParameter("endDate"));
            id = Integer.parseInt(request.getParameter("customerId"));

            bookService.deleteBooking(start, end, id);
        }
    }

        try {
            bookings = bookService.getBookings();
        } catch (Exception e) {
            e.printStackTrace();
        }
%>

<html>
     <head>
            <title>BOOK TO RENT (EMPLOYEE)</title>
     </head>

     <body>
             <div>
                 <h1>UPDATE BOOKING TO RENTING</h1>
             </div>

             <div>
                    <table class="booking_table">

                      <tr>
                        <th>HOTEL NAME  </th>
                        <th>HOTEL NUMBER    </th>
                        <th>ROOM NUMBER </th>
                        <th>START DATE  </th>
                        <th>END DATE    </th>
                        <th>ROOM PRICE  </th>
                        <th>CUSTOMER ID </th>
                      </tr>

                      <%for (int i = 0; i < bookings.size(); i++) {%>
                      <tr>
                        <td><%= bookings.get(i).getHotelName()%></td>
                        <td><%= bookings.get(i).getHotelNum()%></td>
                        <td><%= bookings.get(i).getRoomNum() %></td>
                        <td><%= bookings.get(i).getStartDate() %></td>
                        <td><%= bookings.get(i).getEndDate() %></td>
                        <td><%= bookings.get(i).getRoomPrice() %></td>
                        <td><%= bookings.get(i).getCustomerID() %></td>

                        <td><form action="employee_bookingToRenting.jsp" method="POST">
                        <input type="hidden" value="<%= bookings.get(i).getStartDate() %>" name="startDate">
                        <input type="hidden" value="<%= bookings.get(i).getEndDate() %>" name="endDate">
                        <input type="hidden" value="<%= bookings.get(i).getCustomerID() %>" name="customerId">
                        <input type="submit" name="submit" value="CHANGE" onclick="alert('BOOKING DELETED.')">
                        </form></td>
                      </tr>
                    <% } %>
                    </table>
             </div>
         </body>
</html>