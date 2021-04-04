package com.brs.web;

import com.brs.model.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.Date;

@WebServlet("/view-reservation")
public class ReservationViewer extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();

        RequestDispatcher view = request.getRequestDispatcher("view-reservation.jsp");

        String userName = (String) session.getAttribute("userName");

        boolean isReservationFound = false;

        Bus bus;
        Reservation reservation = null;
        Passenger passenger;

        try (
                Connection connection = DriverManager.getConnection(DatabaseDetails.DATABASE_NAME,
                        DatabaseDetails.USER_NAME,
                        DatabaseDetails.PASSWORD);
                Statement statement = connection.createStatement()
        ) {
            String query = "SELECT * from reservations " +
                            "INNER JOIN buses ON reservations.bus_id = buses.bus_id " +
                            "INNER JOIN routes ON buses.station = routes.station AND buses.destination = routes.destination " +
                            "INNER JOIN passengers ON reservations.user_name = passengers.user_name " +
                            "WHERE transaction_id = \"" + request.getParameter("transaction-id") + "\"";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                isReservationFound = true;

                String routeID = resultSet.getString("route_id");
                String station = resultSet.getString("station");
                String destination = resultSet.getString("destination");
                double fare = resultSet.getDouble("fare");
                String busID = resultSet.getString("bus_id");
                int numberOfSeats = resultSet.getInt("number_of_seats");
                String seatNumbers = resultSet.getString("seat_numbers");
                bus = new Bus(busID, numberOfSeats, new Route(routeID, station, destination, fare));
                bus.setSeatNumbers(seatNumbers);

                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                char gender = resultSet.getString("gender").charAt(0);
                String phoneNumber = resultSet.getString("phone_number");
                String emailAddress = resultSet.getString("email_address");

                passenger = new Passenger(userName);
                passenger.setFirstName(firstName);
                passenger.setLastName(lastName);
                passenger.setGender(gender);
                passenger.setPhoneNumber(phoneNumber);
                passenger.setEmailAddress(emailAddress);

                String transactionID = resultSet.getString("transaction_id");
                Date reservationDate = resultSet.getDate("reservation_date");
                Date arrivalDate = resultSet.getDate("arrival_date");
                int seatNumber = resultSet.getInt("seat_number");
                reservation = new Reservation(transactionID);
                reservation.setBus(bus);
                reservation.setArrivalDate(arrivalDate);
                reservation.setPassenger(passenger);
                reservation.setSeatNumber(seatNumber);
                reservation.setReservationDate(reservationDate);

                String passengerName = resultSet.getString("passenger_name");
                request.setAttribute("passengerName", passengerName);

                request.setAttribute("reservation", reservation);
            }

            request.setAttribute("isReservationFound", isReservationFound);

            view.forward(request, response);
        } catch (SQLException e) {
            out.println("<p>Error connecting to database</p>");
            e.printStackTrace();
        }
    }   //  end of doGet()
}   //  end of class
