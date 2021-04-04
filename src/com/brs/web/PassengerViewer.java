package com.brs.web;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import com.brs.model.*;

@WebServlet("/admin/view-passenger")
public class PassengerViewer extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        boolean isPassengerFound = false;
        String userName = request.getParameter("user-name");
        Passenger passenger = new Passenger(userName);

        ArrayList<Reservation> reservations = new ArrayList<>();

        RequestDispatcher view = request.getRequestDispatcher("view-passenger.jsp");

        try (
                Connection connection = DriverManager.getConnection(DatabaseDetails.DATABASE_NAME,
                        DatabaseDetails.USER_NAME,
                        DatabaseDetails.PASSWORD);
                Statement statement = connection.createStatement()
        ) {
            String query = "SELECT * from passengers " +
                    "WHERE user_name = \"" + userName + "\"";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                isPassengerFound = true;

                userName = resultSet.getString("user_name");
                String firstName = resultSet.getString("first_name");
                String lastName = resultSet.getString("last_name");
                char gender = resultSet.getString("gender").charAt(0);
                String phoneNumber = resultSet.getString("phone_number");
                String emailAddress = resultSet.getString("email_address");

                passenger.setUserName(userName);
                passenger.setFirstName(firstName);
                passenger.setLastName(lastName);
                passenger.setGender(gender);
                passenger.setPhoneNumber(phoneNumber);
                passenger.setEmailAddress(emailAddress);

                request.setAttribute("passenger", passenger);
            }

            request.setAttribute("isPassengerFound", isPassengerFound);

            query = "SELECT * from reservations " +
                    "INNER JOIN buses ON reservations.bus_id = buses.bus_id " +
                    "INNER JOIN routes ON buses.station = routes.station AND buses.destination = routes.destination " +
                    "WHERE user_name = \"" + userName + "\" ORDER BY reservation_date DESC";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Bus bus;

                String routeID = resultSet.getString("route_id");
                String station = resultSet.getString("station");
                String destination = resultSet.getString("destination");
                double fare = resultSet.getDouble("fare");
                String busID = resultSet.getString("bus_id");
                int numberOfSeats = resultSet.getInt("number_of_seats");
                String seatNumbers = resultSet.getString("seat_numbers");
                bus = new Bus(busID, numberOfSeats, new Route(routeID, station, destination, fare));
                bus.setSeatNumbers(seatNumbers);

                String transactionID = resultSet.getString("transaction_id");
                Date reservationDate = resultSet.getDate("reservation_date");
                Date arrivalDate = resultSet.getDate("arrival_date");
                int seatNumber = resultSet.getInt("seat_number");
                Reservation reservation = new Reservation(transactionID);
                reservation.setBus(bus);
                reservation.setArrivalDate(arrivalDate);
                reservation.setPassenger(passenger);
                reservation.setSeatNumber(seatNumber);
                reservation.setReservationDate(reservationDate);

                reservations.add(reservation);
            }

            request.setAttribute("reservations", reservations);

            view.forward(request, response);
        } catch (SQLException e) {
            out.println("<p>Error connecting to database</p>");
            e.printStackTrace();
        }
    }   //  end of doGet()
}   //  end of class