<%@ page import="com.brs.model.*" %>
<%@ page import="com.brs.web.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

<%!
    String pageTitle = "Dashboard";

    boolean isPassengerFound = false;
    boolean isPasswordValid = false;

    Passenger passenger = null;

    ArrayList reservations;
%>

    <%@ include file = "header.jsp"%>

    <article>
        <h1>Dashboard</h1>

        <%
            isPassengerFound = (Boolean) request.getAttribute("isPassengerFound");
            isPasswordValid = (Boolean) request.getAttribute("isPasswordValid");
            boolean isLoginValid = isPassengerFound && isPasswordValid;

            passenger = (Passenger) request.getAttribute("passenger");

            reservations = (ArrayList) request.getAttribute("reservations");

            SimpleDateFormat reverseDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (!isLoginValid) {
        %>
        <p>User not found. <a href = "signup.html">Click here to signup</a>.</p>
        <%
            } else {
        %>
        <p>
            <table>
                <caption>Customer Details</caption>

                <tr>
                    <th>Username</th>
                    <td><%= passenger.getUserName()%></td>
                </tr>

                <tr>
                    <th>First Name</th>
                    <td><%= passenger.getFirstName()%></td>
                </tr>

                <tr>
                    <th>Last Name</th>
                    <td><%= passenger.getLastName()%></td>
                </tr>

                <tr>
                    <th>Gender</th>
                    <td>
                    <%
                        switch (Character.toUpperCase(passenger.getGender())) {
                            case 'M':
                                out.print("Male");
                                break;

                            case 'F':
                                out.print("Female");
                                break;
                        }
                    %>
                    </td>
                </tr>

                <tr>
                    <th>Phone Number</th>
                    <td><%= passenger.getPhoneNumber()%></td>
                </tr>

                <tr>
                    <th>Email Address</th>
                    <td><%= passenger.getEmailAddress()%></td>
                </tr>
            </table>
        </p>

        <p>
            <table>
                <caption>Reservations</caption>

                <tr>
                    <th>Transaction ID</th>
                    <th>Reservation Date</th>
                    <th>Arrival Date</th>
                </tr>

                <%
                    for (int i = 0; i < reservations.size(); i++) {
                        Reservation currentReservation = (Reservation) reservations.get(i);
                        String transactionID = currentReservation.getTransactionID();
                        String reservationDate = reverseDateFormat.format(currentReservation.getReservationDate());
                        String arrivalDate = reverseDateFormat.format(currentReservation.getArrivalDate());
                %>

                <tr>
                    <td><a href = "view-reservation?transaction-id=<%= transactionID%>"><%= transactionID%></a></td>
                    <td><%= reservationDate%></td>
                    <td><%= arrivalDate%></td>
                </tr>

                <%
                    }
                %>
            </table>
        </p>

        <form action = "logout" method = "POST">
            <button type = "submit">Logout</button>
        </form>
        <%
            }
        %>

    </article>

    <%@ include file = "footer.jsp"%>
</body>
</html>