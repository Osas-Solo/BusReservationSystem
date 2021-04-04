<%@ page import="com.brs.model.*" %>
<%@ page import="com.brs.web.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Date" %>

<%!
    String pageTitle = "Bus";

    boolean isBusFound = false;

    Bus bus;
%>

    <%@ include file = "header.jsp"%>

    <article>
        <h1>Bus</h1>

        <%
            isBusFound = (Boolean) request.getAttribute("isBusFound");
            bus = (Bus) request.getAttribute("bus");

            if (!isBusFound) {
        %>

        <p>Bus <%= bus.getBusID() %> not found</p>

        <%
            }   else {
        %>

        <table>
            <tr>
                <td>Bus ID:</td>
                <td><%= bus.getBusID() %></td>
            </tr>

            <tr>
                <td>Station:</td>
                <td><%= bus.getRoute().getStation() %></td>
            </tr>

            <tr>
                <td>Destination:</td>
                <td><%= bus.getRoute().getDestination() %></td>
            </tr>

            <tr>
                <td>Fare:</td>
                <td><%= String.format("%.2f", bus.getRoute().getFare()) %></td>
            </tr>

            <tr>
                <td>Available Seats:</td>
                <td>
                <%
                    for (int i = 0; i < bus.getSeatNumbers().size(); i++) {
                        int currentSeatNumber = bus.getSeatNumbers().get(i);

                        if (currentSeatNumber != 0) {
                            out.print(currentSeatNumber);

                            if (i != bus.getSeatNumbers().size() - 1) {
                                out.print(", ");
                            }   //  end of if currentSeatNumber != bus.getSeatNumbers().size() - 1
                        }   //  end of if currentSeatNumber != 0
                    }   //  end of for
                %>
                </td>
            </tr>
        </table>

        <form action = "reset-bus" method = "POST">
            <input type = "text" name = "bus-id" value = "<%= bus.getBusID() %>" style = "display: none">
            <button type = "submit">Reset Bus</button>
        </form>

        <%
            }   //  end of else
        %>
    </article>

    <%@ include file = "../footer.jsp"%>
</body>
</html>