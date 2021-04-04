<%@ page import="java.util.*" %>
<%@ page import="java.sql.*" %>
<%@ page import="com.brs.model.*" %>
<%@ page import="com.brs.web.*" %>

<%!
    String station = "";
    String destination = "";
    Route route = null;

    ArrayList buses = new ArrayList();

    String pageTitle = "Seat Reserver";
%>

    <%@ include file = "header.jsp"%>

    <article>

        <%
            station = (String) request.getAttribute("station");
            destination = (String) request.getAttribute("destination");
            route = (Route) request.getAttribute("route");

            buses = (ArrayList) request.getAttribute("buses");
        %>

        <form action = "checkout" method = "POST">

            <input type = "text" name = "station" value = "<%= station %>" style = "display: none">

            <input type = "text" name = "destination" value = "<%= destination %>" style = "display: none">

            <%
                if (route != null) {
                    for (int i = 0; i < buses.size(); i++) {
                        Bus currentBus = ((Bus) buses.get(i));
            %>
                        <label for = "bus"><%= currentBus.getBusID() + ": " + currentBus.getNumberOfSeats() + " seats"%></label>
                        <input type = "radio" name = "bus" id = "bus" value = "<%= currentBus.getBusID() %>">

                        <div id = "<%= currentBus.getBusID() + "-fieldset" %>">
                        <label>Select seat number:</label>
                        <select name = "<%= currentBus.getBusID() %>">

            <%
                        for (int j = 0; j < currentBus.getSeatNumbers().size(); j++) {
                            int currentSeatNumber = currentBus.getSeatNumbers().get(j);

                            if (currentSeatNumber != 0) {
            %>
                            <option value = "<%= currentSeatNumber %>"><%= currentSeatNumber %></option>
            <%
                            }   //  end of if currentSeatNumber != 0
                        }   //  end of for j
            %>
                        </select>
                        </div>

                        <label for = "fare">Fare:</label>
                        <input type = "number" name = "fare" id = "fare" value = "<%= String.format("%.2f", route.getFare()) %>" readonly>
            <%
                    }   //  end of for i
            %>

            <label for = "arrival-date">Arrival Date:</label>
            <input type = "date" name = "arrival-date" id = "arrival-date">

            <button type = "submit">Proceed to Checkout</button>

            <%
                }   //  end of if route != null
                else {
            %>
            <p>
               We do not currently offer a route from <%= station + " to " + destination %>
            </p>
            <%
                }   //  end of else
            %>

        </form>        

    </article>

    <%@ include file = "footer.jsp"%>
</body>
</html>