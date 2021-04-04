<%@ page import="com.brs.model.*" %>
<%@ page import="com.brs.web.*" %>
<%@ page import="java.text.*" %>
<%@ page import="java.util.Date" %>

<%!
    String pageTitle = "Bus Reset";

    Bus bus;
%>

    <%@ include file = "header.jsp"%>

    <article>
        <h1>Bus</h1>

        <%
            bus = (Bus) request.getAttribute("bus");
        %>

        <p>Bus <%= bus.getBusID() %> has been reset successfully.</p>

    </article>

    <%@ include file = "../footer.jsp"%>
</body>
</html>