<%@ page import="com.brs.model.*" %>
<%@ page import="com.brs.web.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Date" %>

<%!
    String pageTitle = "Reservation Confirmation";

    String transactionID = "";
%>

    <%@ include file = "header.jsp"%>

    <%
        transactionID = (String) request.getAttribute("transactionID");
    %>

    <article>
        <h1>Reservation Confirmation</h1>

        <p>Reservation booked successfully. <a href = "view-reservation?transaction-id=<%= transactionID%>">View Reservation</a>.</p>
    </article>

    <%@ include file = "footer.jsp"%>
</body>
</html>