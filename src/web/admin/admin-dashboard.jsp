<%@ page import="com.brs.model.*" %>
<%@ page import="com.brs.web.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

<%!
    String pageTitle = "Admin Dashboard";

    boolean isAdminFound = false;

    Admin admin;

    ArrayList passengers;
    ArrayList buses;
    ArrayList reservations;
%>

    <%@ include file = "header.jsp"%>

    <article>
        <h1>Dashboard</h1>

        <%
            isAdminFound = (Boolean) request.getAttribute("isAdminFound");

            admin = (Admin) request.getAttribute("admin");

            passengers = (ArrayList) request.getAttribute("passengers");
            buses = (ArrayList) request.getAttribute("buses");
            reservations = (ArrayList) request.getAttribute("reservations");

            SimpleDateFormat reverseDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            if (!isAdminFound) {
        %>
        <p>Admin not found. <a href = "../admin">Click here to login</a>.</p>
        <%
            } else {
        %>
        <p>
            <table>
                <caption>Admin Details</caption>

                <tr>
                    <td>Username:</td>
                    <td><%= admin.getUserName()%></td>
                </tr>

                <tr>
                    <td>First Name:</td>
                    <td><%= admin.getFirstName()%></td>
                </tr>

                <tr>
                    <td>Last Name:</td>
                    <td><%= admin.getLastName()%></td>
                </tr>

                <tr>
                    <td>Gender:</td>
                    <td>
                    <%
                        switch (Character.toUpperCase(admin.getGender())) {
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
                    <td><%= admin.getPhoneNumber()%></td>
                </tr>

                <tr>
                    <td>Email Address:</td>
                    <td><%= admin.getEmailAddress()%></td>
                </tr>
            </table>
        </p>

        <p>
            <table>
                <caption>Passengers</caption>

                <tr>
                    <th>Username</th>
                    <th>First Name</th>
                    <th>Last Name</th>
                </tr>

                <%
                    for (int i = 0; i < passengers.size(); i++) {
                        Passenger currentPassenger = (Passenger) passengers.get(i);
                        String userName = currentPassenger.getUserName();
                        String firstName = currentPassenger.getFirstName();
                        String lastName = currentPassenger.getLastName();
                %>

                <tr>
                    <td><a href = "view-passenger?user-name=<%= userName%>"><%= userName %></a></td>
                    <td><%= firstName %></td>
                    <td><%= lastName %></td>
                </tr>

                <%
                    }
                %>
            </table>
        </p>

        <p>
            <table>
                <caption>Buses</caption>

                <tr>
                    <th>Bus ID</th>
                    <th>Station</th>
                    <th>Destination</th>
                    <th>Number of Seats</th>
                </tr>

                <%
                    for (int i = 0; i < buses.size(); i++) {
                        Bus currentBus = (Bus) buses.get(i);
                        String busID = currentBus.getBusID();
                        String station = currentBus.getRoute().getStation();
                        String destination = currentBus.getRoute().getDestination();
                        int numberOfSeats = currentBus.getNumberOfSeats();
                %>

                <tr>
                    <td><a href = "view-bus?bus-id=<%= busID%>"><%= busID %></a></td>
                    <td><%= station %></td>
                    <td><%= destination %></td>
                    <td><%= numberOfSeats %></td>
                </tr>

                <%
                    }
                %>
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
            }
        %>

    </article>

    <%@ include file = "/footer.jsp"%>
</body>
</html>