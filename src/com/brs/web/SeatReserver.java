package com.brs.web;

import com.brs.model.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.*;

@WebServlet("/reserve-seat")
public class SeatReserver extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        HttpSession session = request.getSession();

        String station = request.getParameter("station");
        String destination = request.getParameter("destination");

        Route route = null;
        ArrayList<Bus> buses = new ArrayList<>();

        try (
                Connection connection = DriverManager.getConnection(DatabaseDetails.DATABASE_NAME,
                        DatabaseDetails.USER_NAME,
                        DatabaseDetails.PASSWORD);
                Statement statement = connection.createStatement()
        ) {
            String query = "SELECT * from routes WHERE station = '" + station + "'" +
                    " AND destination = '" + destination + "'";
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String routeID = resultSet.getString("route_id");
                station = resultSet.getString("station");
                destination = resultSet.getString("destination");
                double fare = resultSet.getDouble("fare");

                route = new Route(routeID, station, destination, fare);

                request.setAttribute("route", route);
            }

            query = "SELECT * from buses WHERE station = '" + station + "'" +
                    " AND destination = '" + destination + "'";
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                String busID = resultSet.getString("bus_id");
                int numberOfSeats = resultSet.getInt("number_of_seats");
                String seatNumbers = resultSet.getString("seat_numbers");

                Bus bus = new Bus(busID, numberOfSeats, route);
                bus.setSeatNumbers(seatNumbers);

                buses.add(bus);
            }

            request.setAttribute("station", station);
            request.setAttribute("destination", destination);
            request.setAttribute("buses", buses);
        } catch (SQLException e) {
            out.println("<p>Error connecting to database</p>");
            e.printStackTrace();
        }

        if (session.getAttribute("userName") == null) {
            response.sendRedirect("login");
        }   else {
            RequestDispatcher view = request.getRequestDispatcher("seat-reserver.jsp");
            view.forward(request, response);
        }
    }   //  end of doGet()
}   //  end of class
