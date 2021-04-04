<%@ page import="com.brs.model.*" %>
<%@ page import="com.brs.web.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Date" %>

<%!
    String pageTitle = "Checkout";

    String userName = "";
    String firstName = "";
    String lastName = "";
    String passengerName = "";
    char gender = ' ';
    String phoneNumber = "";
    String emailAddress = "";

    Reservation reservation;
    Bus bus;
    Passenger passenger;

    String transactionID;
    int seatNumber;
    double fare;

    String reservationDate;
    String arrivalDate;
    String station;
    String destination;
%>

    <%@ include file = "header.jsp"%>

    <article>
        <h1>Checkout</h1>

        <%
            reservation = (Reservation) request.getAttribute("reservation");
            bus = (Bus) reservation.getBus();
            passenger = (Passenger) reservation.getPassenger();

            userName = passenger.getUserName();;
            firstName = passenger.getFirstName();
            lastName = passenger.getLastName();
            gender = passenger.getGender();
            phoneNumber = passenger.getPhoneNumber();
            emailAddress = passenger.getEmailAddress();
            passengerName = firstName + " " + lastName;

            transactionID = reservation.getTransactionID();
            seatNumber = reservation.getSeatNumber();
            fare = bus.getRoute().getFare();

            reservationDate = new SimpleDateFormat("yyyy-MM-dd").format(reservation.getReservationDate());
            arrivalDate = new SimpleDateFormat("yyyy-MM-dd").format(reservation.getArrivalDate());

            station = bus.getRoute().getStation();
            destination = bus.getRoute().getDestination();
        %>

        <form action = "confirm-reservation" method = "POST">
            <div>
                <label for = "transaction-id">Transaction ID:</label>
                <input type = "text" name = "transaction-id" id = "transaction-id"  value = "<%= transactionID%>" readonly>
            </div>

            <div>
                <label for = "reservation-date">Reservation Date:</label>
                <input type = "text" name = "reservation-date" id = "reservation-date"  value = "<%= reservationDate%>" readonly>
            </div>

            <div>
                <label for = "fare">Fare:</label>
                <input type = "number" name = "fare" id = "fare"  value = "<% out.print(String.format("%.2f", fare));%>" readonly>
            </div>

            <div>
                <label for = "bus-id">Bus ID:</label>
                <input type = "text" name = "bus-id" id = "bus-id"  value = "<%= bus.getBusID()%>" readonly>
            </div>

            <div>
                <label for = "seat-number">Seat Number:</label>
                <input type = "number" name = "seat-number" id = "seat-number"  value = "<%= seatNumber%>" readonly>
            </div>

            <div>
                <label for = "passenger-name">Passenger Name:</label>
                <input type = "text" name = "passenger-name" id = "passenger-name"  value = "<%= passengerName%>" readonly>
            </div>

            <div>
                <label for = "station">Station:</label>
                <input type = "text" name = "station" id = "station"  value = "<%= station%>" readonly>
            </div>

            <div>
                <label for = "destination">Destination:</label>
                <input type = "text" name = "destination" id = "destination"  value = "<%= destination%>" readonly>
            </div>

            <div>
                <label for = "arrival-date">Arrival Date:</label>
                <input type = "text" name = "arrival-date" id = "arrival-date"  value = "<%= arrivalDate%>" readonly>
            </div>

            <button type = "submit">Confirm Reservation</button>
        </form>

    </article>

    <%@ include file = "footer.jsp"%>
</body>
</html>