<%@ page import="com.demo.RentingService" %>
<%@ page import="com.demo.Renting" %>
<%@ page import="com.demo.Date" %>

<%
RentingService rentService = new RentingService();
Date startDate, endDate;
int customerId, employeeId, roomNum, hotelNum;
boolean paid;//CHANGE PAYMENT IN PAYMENT UPDATE
double roomPrice;
String hotelName;

if (request.getParameter("submit") != null){
         startDate = new Date(request.getParameter("startDate"));
         endDate = new Date(request.getParameter("endDate"));
         customerId = Integer.parseInt(request.getParameter("customerId"));
         employeeId = Integer.parseInt(request.getParameter("employeeId"));
         roomNum = Integer.parseInt(request.getParameter("roomNum"));
         hotelNum = Integer.parseInt(request.getParameter("hotelNumber"));
         roomPrice = Double.parseDouble(request.getParameter("roomPrice"));
         hotelName =  request.getParameter("hotelName");

         paid = false;

         rentService.createRenting(new Renting(startDate, endDate, customerId, employeeId,
         paid, roomNum, roomPrice, hotelName, hotelNum));
    }
%>

<!DOCTYPE HTML>
<html>
     <head>
            <title>NEW RENT CREATE (EMPLOYEE)</title>
     </head>

     <body>
             <div>
                 <h1>CREATE NEW RENTING</h1>
             </div>

             <div>
                <form>
                    <label for="startDate">START DATE: </label>
                    <input type="date" id="startDate" name="startDate"><br>

                    <label for="endDate">END DATE: </label>
                    <input type="date" id="endDate" name="endDate"><br>

                    <label for="customerId">CUSTOMER ID: </label>
                    <input type="text" id="customerId" name="customerId"><br>

                    <label for="employeeId">EMPLOYEE ID: </label>
                    <input type="text" id="employeeId" name="employeeId"><br>

                    <label for="roomNum">ROOM NUMBER: </label>
                    <input type="text" id="roomNum" name="roomNum"><br>

                    <label for="roomPrice">ROOM PRICE: </label>
                    <input type="text" id="roomPrice" name="roomPrice"><br>

                    <label for="hotelName">HOTEL NAME: </label>
                    <input type="text" id="hotelName" name="hotelName"><br>

                    <label for="hotelNumber">HOTEL NUMBER: </label>
                    <input type="text" id="hotelNumber" name="hotelNumber"><br>

                    <input type="submit" name="submit" value="CREATE" onclick="alert('RENTING CREATED.')">
                    <input type="reset" name="reset" value="RESET"><br>
                </form>
             </div>
         </body>
</html>
