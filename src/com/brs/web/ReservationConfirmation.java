package com.brs.web;

import com.brs.model.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.Date;

@WebServlet("/confirm-reservation")
public class ReservationConfirmation extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();

        RequestDispatcher view = request.getRequestDispatcher("confirm-reservation.jsp");

        String transactionID = request.getParameter("transaction-id");
        String busId = request.getParameter("bus-id");
        String station = request.getParameter("station");
        String destination = request.getParameter("destination");
        int seatNumber = Integer.parseInt(request.getParameter("seat-number"));
        String seatNumbers = "";
        double fare = Double.parseDouble(request.getParameter("fare"));
        Date reservationDate = new Date();
        Date arrivalDate = new Date();
        SimpleDateFormat reverseDateFormat = new SimpleDateFormat("yyyy-MM-dd");

        String userName = (String) session.getAttribute("userName");
        String passengerName = "";

        Bus bus = null;
        Reservation reservation;
        Passenger passenger = null;

        try {
            reservationDate = reverseDateFormat.parse(request.getParameter("reservation-date"));
            arrivalDate = reverseDateFormat.parse(request.getParameter("arrival-date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try (
                Connection connection = DriverManager.getConnection(DatabaseDetails.DATABASE_NAME,
                        DatabaseDetails.USER_NAME,
                        DatabaseDetails.PASSWORD);
                Statement statement = connection.createStatement()
        ) {
            String query = "SELECT * from buses INNER JOIN routes " +
                    "ON buses.station = routes.station AND buses.destination = routes.destination " +
                    "WHERE bus_id = '" + busId + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int numberOfSeats = resultSet.getInt("number_of_seats");
                seatNumbers = resultSet.getString("seat_numbers");
                String routeID = resultSet.getString("route_id");

                bus = new Bus(busId, numberOfSeats, new Route(routeID, station, destination, fare));
                bus.setSeatNumbers(seatNumbers);
                bus.getSeatNumbers().set(bus.getSeatNumbers().indexOf(seatNumber), 0);
                seatNumbers = "";

                for (int i = 0; i < bus.getSeatNumbers().size(); i++) {
                    seatNumbers += bus.getSeatNumbers().get(i);

                    if (i != bus.getSeatNumbers().size() - 1) {
                        seatNumbers += "-";
                    }
                }
            }

            query = "SELECT * from passengers WHERE user_name = '" + userName + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                passengerName = firstName + " " + lastName;
                char gender = resultSet.getString("gender").charAt(0);
                String phoneNumber = resultSet.getString("phone_number");
                String emailAddress = resultSet.getString("email_address");

                passenger = new Passenger(userName);
                passenger.setFirstName(firstName);
                passenger.setLastName(lastName);
                passenger.setGender(gender);
                passenger.setPhoneNumber(phoneNumber);
                passenger.setEmailAddress(emailAddress);
            }

            reservation = new Reservation(transactionID);
            reservation.setBus(bus);
            reservation.setArrivalDate(arrivalDate);
            reservation.setPassenger(passenger);
            reservation.setSeatNumber(seatNumber);
            reservation.setReservationDate(reservationDate);

            request.setAttribute("transactionID", reservation.getTransactionID());

            query = String.format("INSERT INTO reservations (transaction_id, reservation_date, fare, bus_id, seat_number," +
                    "user_name, passenger_name, station, destination, arrival_date) VALUES " +
                    "(\"%s\", \"%s\", \"%.2f\", \"%s\", \"%d\", \"%s\", \"%s\", \"%s\", \"%s\", \"%s\")",
                    reservation.getTransactionID(), reverseDateFormat.format(reservationDate), bus.getRoute().getFare(),
                    bus.getBusID(), reservation.getSeatNumber(), passenger.getUserName(), passengerName,
                    bus.getRoute().getStation(), bus.getRoute().getDestination(), reverseDateFormat.format(arrivalDate));
            statement.executeUpdate(query);

            query = "UPDATE buses SET seat_numbers = \"" + seatNumbers + "\" WHERE bus_id = \"" + bus.getBusID() + "\"";
            statement.executeUpdate(query);

            view.forward(request, response);
        } catch (SQLException e) {
            out.println("<p>Error connecting to database</p>");
            e.printStackTrace();
        }
    }   //  end of doGet()
}   //  end of class
