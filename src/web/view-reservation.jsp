<%@ page import="com.brs.model.*" %>
<%@ page import="com.brs.web.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Date" %>

<%!
    String pageTitle = "Reservation";

    boolean isReservationFound = false;

    Reservation reservation;
    Bus bus;
    Passenger passenger;

    String passengerName;
%>

    <%@ include file = "header.jsp"%>

    <article>
        <h1>Reservation</h1>

        <%
            isReservationFound = (Boolean) request.getAttribute("isReservationFound");

            reservation = (Reservation) request.getAttribute("reservation");
            bus = (Bus) reservation.getBus();
            passenger = (Passenger) reservation.getPassenger();

            passengerName = (String) request.getAttribute("passengerName");

            SimpleDateFormat reverseDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (!isReservationFound) {
        %>

        <p>Reservation <%= reservation.getTransactionID() %> not found.</p>

        <%
            }   else {
        %>

        <table>
            <tr>
                <td>Transaction ID:</td>
                <td><%= reservation.getTransactionID()%></td>
            </tr>

            <tr>
                <td>Reservation Date:</td>
                <td><%= reverseDateFormat.format(reservation.getReservationDate())%></td>
            </tr>

            <tr>
                <td>Bus ID:</td>
                <td><%= bus.getBusID()%></td>
            </tr>

            <tr>
                <td>Fare:</td>
                <td><%= String.format("%.2f", bus.getRoute().getFare())%></td>
            </tr>

            <tr>
                <td>Seat Number:</td>
                <td><%= reservation.getSeatNumber()%></td>
            </tr>

            <tr>
                <td>Passenger Name:</td>
                <td><%= passengerName%></td>
            </tr>

            <tr>
                <td>Station:</td>
                <td><%= bus.getRoute().getStation()%></td>
            </tr>

            <tr>
                <td>Destination:</td>
                <td><%= bus.getRoute().getDestination()%></td>
            </tr>

            <tr>
                <td>Arrival Date:</td>
                <td><%= reverseDateFormat.format(reservation.getArrivalDate())%></td>
            </tr>
        </table>

        <%
            }
        %>
    </article>

    <%@ include file = "footer.jsp"%>
</body>
</html>