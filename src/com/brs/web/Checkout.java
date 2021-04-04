package com.brs.web;

import com.brs.model.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.text.*;
import java.util.Date;

@WebServlet("/checkout")
public class Checkout extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();

        RequestDispatcher view = request.getRequestDispatcher("checkout.jsp");

        String transactionID = "";
        String busId = request.getParameter("bus");
        String station = request.getParameter("station");
        String destination = request.getParameter("destination");
        int seatNumber = Integer.parseInt(request.getParameter(busId));
        double fare = Double.parseDouble(request.getParameter("fare"));
        Date reservationDate = new Date();
        Date arrivalDate = new Date();

        String userName = (String) session.getAttribute("userName");

        Bus bus = null;
        Reservation reservation = null;
        Passenger passenger = null;

        try {
            arrivalDate = new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("arrival-date"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        try (
                Connection connection = DriverManager.getConnection(DatabaseDetails.DATABASE_NAME,
                        DatabaseDetails.USER_NAME,
                        DatabaseDetails.PASSWORD);
                Statement statement = connection.createStatement();
        ) {
            String query = "SELECT * from buses INNER JOIN routes " +
                            "ON buses.station = routes.station AND buses.destination = routes.destination " +
                            "WHERE bus_id = '" + busId + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                int numberOfSeats = resultSet.getInt("number_of_seats");
                String seatNumbers = resultSet.getString("seat_numbers");
                String routeID = resultSet.getString("route_id");

                bus = new Bus(busId, numberOfSeats, new Route(routeID, station, destination, fare));
                bus.setSeatNumbers(seatNumbers);
            }

            query = "SELECT * from passengers WHERE user_name = '" + userName + "'";
            resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
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
            }

            transactionID = new SimpleDateFormat("yyyy-MM-dd").format(arrivalDate) + "-" + busId + "-" + seatNumber;

            reservation = new Reservation(transactionID);
            reservation.setBus(bus);
            reservation.setArrivalDate(arrivalDate);
            reservation.setPassenger(passenger);
            reservation.setSeatNumber(seatNumber);
            reservation.setReservationDate(reservationDate);

            request.setAttribute("reservation", reservation);

            view.forward(request, response);
        } catch (SQLException e) {
            out.println("<p>Error connecting to database</p>");
            e.printStackTrace();
        }
    }   //  end of doPost()
}   //  end of class
