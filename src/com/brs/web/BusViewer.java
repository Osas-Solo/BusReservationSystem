package com.brs.web;

import com.brs.model.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/admin/view-bus")
public class BusViewer extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        RequestDispatcher view = request.getRequestDispatcher("view-bus.jsp");

        boolean isBusFound = false;
        Bus bus;

        try (
                Connection connection = DriverManager.getConnection(DatabaseDetails.DATABASE_NAME,
                        DatabaseDetails.USER_NAME,
                        DatabaseDetails.PASSWORD);
                Statement statement = connection.createStatement()
        ) {
            String query = "SELECT * from buses " +
                    "INNER JOIN routes ON buses.station = routes.station AND buses.destination = routes.destination " +
                    "WHERE bus_id = \"" + request.getParameter("bus-id") + "\"";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                isBusFound = true;

                String routeID = resultSet.getString("route_id");
                String station = resultSet.getString("station");
                String destination = resultSet.getString("destination");
                double fare = resultSet.getDouble("fare");
                String busID = resultSet.getString("bus_id");
                int numberOfSeats = resultSet.getInt("number_of_seats");
                String seatNumbers = resultSet.getString("seat_numbers");

                bus = new Bus(busID, numberOfSeats, new Route(routeID, station, destination, fare));
                bus.setSeatNumbers(seatNumbers);

                request.setAttribute("bus", bus);
            }

            request.setAttribute("isBusFound", isBusFound);

            view.forward(request, response);
        } catch (SQLException e) {
            out.println("<p>Error connecting to database</p>");
            e.printStackTrace();
        }
    }   //  end of doGet()
}   //  end of class
