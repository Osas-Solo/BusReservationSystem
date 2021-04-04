<%@ page import="com.brs.model.*" %>
<%@ page import="com.brs.web.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

<%!
    String pageTitle = "Passenger";

    boolean isPassengerFound = false;
    Passenger passenger = null;

    ArrayList reservations;
%>

    <%@ include file = "header.jsp"%>

    <article>
        <h1>Passenger</h1>

        <%
            isPassengerFound = (Boolean) request.getAttribute("isPassengerFound");

            passenger = (Passenger) request.getAttribute("passenger");

            reservations = (ArrayList) request.getAttribute("reservations");

            SimpleDateFormat reverseDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (!isPassengerFound) {
        %>

        <p>Passenger <%= passenger.getUserName() %> not found.</p>

        <%
            } else {
        %>
        <p>
            <table>
                <caption>Customer Details</caption>

                <tr>
                    <td>Username:</td>
                    <td><%= passenger.getUserName()%></td>
                </tr>

                <tr>
                    <td>First Name:</td>
                    <td><%= passenger.getFirstName()%></td>
                </tr>

                <tr>
                    <td>Last Name:</td>
                    <td><%= passenger.getLastName()%></td>
                </tr>

                <tr>
                    <td>Gender:</td>
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
                    <td>Phone Number:</td>
                    <td><%= passenger.getPhoneNumber()%></td>
                </tr>

                <tr>
                    <td>Email Address:</td>
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
                    <td><a href = "../view-reservation?transaction-id=<%= transactionID%>"><%= transactionID%></a></td>
                    <td><%= reservationDate%></td>
                    <td><%= arrivalDate%></td>
                </tr>

                <%
                    }
                %>
            </table>
        </p>
        <%
            }   //  end of else
        %>

    </article>

    <%@ include file = "../footer.jsp"%>
</body>
</html>