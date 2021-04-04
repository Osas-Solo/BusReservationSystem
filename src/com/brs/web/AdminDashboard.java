package com.brs.web;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.sql.Date;
import java.util.*;
import com.brs.model.*;

@WebServlet("/admin/dashboard")
public class AdminDashboard extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();

        String firstName;
        String lastName;
        String userName;
        String password = "";
        char gender;
        String phoneNumber;
        String emailAddress;

        Admin admin = null;

        boolean isAdminFound = false;

        ArrayList<Passenger> passengers = new ArrayList<>();
        ArrayList<Bus> buses = new ArrayList<>();
        ArrayList<Reservation> reservations = new ArrayList<>();

        RequestDispatcher view = request.getRequestDispatcher("admin-dashboard.jsp");

        if (isRequestLogin(request)) {
            userName = request.getParameter("username");
            password = request.getParameter("password");
        }   else {
            userName = (String) session.getAttribute("userName");
        }

        try (
                Connection connection = DriverManager.getConnection(DatabaseDetails.DATABASE_NAME,
                        DatabaseDetails.USER_NAME,
                        DatabaseDetails.PASSWORD);
                Statement statement = connection.createStatement()
        ) {
            String query = "SELECT * from admins WHERE user_name = '" + userName + "'";
            query += (isRequestLogin(request)) ? " AND password = SHA('" + password + "')" : "";

            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                isAdminFound = true;

                firstName = resultSet.getString("first_name");
                lastName = resultSet.getString("last_name");
                userName = resultSet.getString("user_name");
                gender = resultSet.getString("gender").charAt(0);
                phoneNumber = resultSet.getString("phone_number");
                emailAddress = resultSet.getString("email_address");

                admin = new Admin(userName);
                admin.setFirstName(firstName);
                admin.setLastName(lastName);
                admin.setGender(gender);
                admin.setPhoneNumber(phoneNumber);
                admin.setEmailAddress(emailAddress);

                request.setAttribute("admin", admin);

                synchronized(session) {
                    session.setAttribute("userName", userName);
                }
            }

            request.setAttribute("isAdminFound", isAdminFound);

            query = "SELECT * from passengers ORDER BY user_name";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Passenger passenger;

                firstName = resultSet.getString("first_name");
                lastName = resultSet.getString("last_name");
                userName = resultSet.getString("user_name");
                gender = resultSet.getString("gender").charAt(0);
                phoneNumber = resultSet.getString("phone_number");
                emailAddress = resultSet.getString("email_address");

                passenger = new Passenger(userName);
                passenger.setFirstName(firstName);
                passenger.setLastName(lastName);
                passenger.setGender(gender);
                passenger.setPhoneNumber(phoneNumber);
                passenger.setEmailAddress(emailAddress);

                passengers.add(passenger);
            }

            request.setAttribute("passengers", passengers);

            query = "SELECT * from buses " +
                    "INNER JOIN routes ON buses.station = routes.station AND buses.destination = routes.destination " +
                    "ORDER BY bus_id";
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

                buses.add(bus);
            }

            request.setAttribute("buses", buses);

            query = "SELECT * from reservations " +
                    "INNER JOIN passengers ON reservations.user_name = passengers.user_name " +
                    "INNER JOIN buses ON reservations.bus_id = buses.bus_id " +
                    "INNER JOIN routes ON buses.station = routes.station AND buses.destination = routes.destination " +
                    "ORDER BY reservation_date DESC";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Bus bus;
                Passenger passenger;

                String routeID = resultSet.getString("route_id");
                String station = resultSet.getString("station");
                String destination = resultSet.getString("destination");
                double fare = resultSet.getDouble("fare");
                String busID = resultSet.getString("bus_id");
                int numberOfSeats = resultSet.getInt("number_of_seats");
                String seatNumbers = resultSet.getString("seat_numbers");
                bus = new Bus(busID, numberOfSeats, new Route(routeID, station, destination, fare));
                bus.setSeatNumbers(seatNumbers);

                firstName = resultSet.getString("first_name");
                lastName = resultSet.getString("last_name");
                userName = resultSet.getString("user_name");
                gender = resultSet.getString("gender").charAt(0);
                phoneNumber = resultSet.getString("phone_number");
                emailAddress = resultSet.getString("email_address");
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
    }   //  end of doPost()

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    private static boolean isRequestLogin(HttpServletRequest request) {
        return request.getParameter("username") != null && request.getParameter("password") != null;
    }
}   //  end of class
