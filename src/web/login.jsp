<%@ page import="com.brs.model.*" %>
<%@ page import="com.brs.web.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

<%!
    String pageTitle = "Login";

    boolean isPassengerFound = false;
    boolean isPasswordValid = false;

    String userName = "";
%>

    <%@ include file = "header.jsp"%>

    <article>
        <h1>Login</h1>

        <%
            userName = (String) request.getAttribute("userName");

            if (request.getAttribute("isPassengerFound") != null) {
                isPassengerFound = (Boolean) request.getAttribute("isPassengerFound");
                isPasswordValid = (Boolean) request.getAttribute("isPasswordValid");
            }
        %>

        <form action = "dashboard" method = "POST">
            <fieldset class = "personal-details-fieldset">
                <label for = "username">Username:</label>
                <input type = "text" name = "username" id = "username" value = "<%
                    if (userName != null) {
                        out.print(userName);
                    }
                %>">
                <div id = "username-error-message">
                <%
                    if (request.getAttribute("isPassengerFound") != null) {
                        if (!isPassengerFound && userName != null) {
                            out.print("The username, " + userName + " is not found");
                        }
                    }
                %>
                </div>
            </fieldset>

            <fieldset class = "personal-details-fieldset">
                <label for = "password">Password:</label>
                <input type = "password" name = "password" id = "password">
                <div id = "password-error-message">
                <%
                    if (request.getAttribute("isPasswordValid") != null) {
                        if (!isPasswordValid && userName != null) {
                            out.print("Invalid password");
                        }
                    }
                %>
                </div>
            </fieldset>

            <button type = "submit">Login</button>
        </form>

        <div class = "alternate-page-prompt">Not a customer yet? <a href = "signup">Signup</a></div>

    </article>

    <%@ include file = "footer.jsp"%>
</body>
</html>