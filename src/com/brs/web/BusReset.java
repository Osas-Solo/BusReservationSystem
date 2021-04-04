package com.brs.web;

import com.brs.model.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

@WebServlet("/admin/reset-bus")
public class BusReset extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        RequestDispatcher view = request.getRequestDispatcher("reset-bus.jsp");

        String busID = request.getParameter("bus-id");

        Bus bus = null;
        String seatNumbers = "";

        try (
                Connection connection = DriverManager.getConnection(DatabaseDetails.DATABASE_NAME,
                        DatabaseDetails.USER_NAME,
                        DatabaseDetails.PASSWORD);
                Statement statement = connection.createStatement()
        ) {
            String query = "SELECT * from buses " +
                    "INNER JOIN routes ON buses.station = routes.station AND buses.destination = routes.destination " +
                    "WHERE bus_id = \"" + busID + "\"";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                String routeID = resultSet.getString("route_id");
                String station = resultSet.getString("station");
                String destination = resultSet.getString("destination");
                double fare = resultSet.getDouble("fare");
                busID = resultSet.getString("bus_id");
                int numberOfSeats = resultSet.getInt("number_of_seats");
                seatNumbers = "";

                for (int i = 0; i < numberOfSeats; i++) {
                    seatNumbers += i + 1;

                    if (i != numberOfSeats - 1) {
                        seatNumbers += "-";
                    }
                }

                bus = new Bus(busID, numberOfSeats, new Route(routeID, station, destination, fare));
                bus.setSeatNumbers(seatNumbers);

                request.setAttribute("bus", bus);
            }

            query = "UPDATE buses SET seat_numbers = \"" + seatNumbers + "\" WHERE bus_id = \"" + bus.getBusID() + "\"";
            statement.executeUpdate(query);

            view.forward(request, response);
            response.sendRedirect("/view-bus?bus-id=" + busID);
        } catch (SQLException e) {
            out.println("<p>Error connecting to database</p>");
            e.printStackTrace();
        }
    }   //  end of doGet()
}   //  end of class
