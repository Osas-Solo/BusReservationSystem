<%@ page import="com.brs.model.*" %>
<%@ page import="com.brs.web.*" %>
<%@ page import="java.util.*" %>
<%@ page import="java.text.*" %>

<%!
    String pageTitle = "Signup";

    Passenger passenger;

    boolean isPassengerFound = false;
    boolean isFirstNameValid = false;
    boolean isLastNameValid = false;
    boolean isPasswordValid = false;
    boolean isPasswordConfirmed = false;
    boolean isPhoneNumberValid = false;
    boolean isEmailAddressValid = false;
    boolean isRegistrationValid = false;
%>

    <%@ include file = "header.jsp"%>

    <article>
        <h1>Become A Customer</h1>

        <%
            if (request.getAttribute("passenger") != null) {
                passenger = (Passenger) request.getAttribute("passenger");
            } else {
                passenger = new Passenger("");
            }

            if (request.getAttribute("isPassengerFound") != null) {
                isPassengerFound = (Boolean) request.getAttribute("isPassengerFound");
                isFirstNameValid = (Boolean) request.getAttribute("isFirstNameValid");
                isLastNameValid = (Boolean) request.getAttribute("isLastNameValid");
                isPasswordValid = (Boolean) request.getAttribute("isPasswordValid");
                isPasswordConfirmed = (Boolean) request.getAttribute("isPasswordConfirmed");
                isPhoneNumberValid = (Boolean) request.getAttribute("isPhoneNumberValid");
                isEmailAddressValid = (Boolean) request.getAttribute("isEmailAddressValid");
                isRegistrationValid = (Boolean) request.getAttribute("isRegistrationValid");
            }
        %>

        <form action = "register" method = "POST">
            <fieldset class = "personal-details-fieldset">
                <label for = "first-name">First Name:</label>
                <input type = "text" name = "first-name" id = "first-name" value = "<%
                    if (isFirstNameValid) {
                        out.print(passenger.getFirstName());
                    }
                %>">
                <div id = "first-name-error-message">
                    <%
                        if (!isFirstNameValid  && passenger.getUserName() != "") {
                            out.print("Please enter a first name");
                        }
                    %>
                </div>
            </fieldset>

            <fieldset class = "personal-details-fieldset">
                <label for = "last-name">Last Name:</label>
                <input type = "text" name = "last-name" id = "last-name" value = "<%
                    if (isLastNameValid) {
                        out.print(passenger.getLastName());
                    }
                %>">
                <div id = "last-name-error-message">
                    <%
                        if (!isLastNameValid && passenger.getUserName() != "") {
                            out.print("Please enter a last name");
                        }
                    %>
                </div>
            </fieldset>

            <fieldset class = "personal-details-fieldset">
                <label for = "username">Username:</label>
                <input type = "text" name = "username" id = "username" value = "<%= passenger.getUserName() %>">
                <div id = "username-error-message">
                    <%
                        if (isPassengerFound && passenger.getUserName() != "") {
                            out.print("The username, " + passenger.getUserName()  + " is already in use");
                        }
                    %>
                </div>
            </fieldset>

            <fieldset class = "personal-details-fieldset">
                <label for = "password">Password:</label>
                <input type = "password" name = "password" id = "password">
                <div id = "password-error-message">
                    Password length should be at least 8 characters but not more than 20 characters.
                    Password must contain a lowercase character, uppercase character and a digit)
                    <%
                        if (!isPasswordValid && passenger.getUserName() != "") {
                            out.print("Please enter a valid password");
                        }
                    %>
                </div>
            </fieldset>

            <fieldset class = "personal-details-fieldset">
                <label for = "confirm-password">Confirm Password:</label>
                <input type = "password" name = "confirm-password" id = "confirm-password">
                <div id = "confirm-password-error-message">
                    <%
                        if (!isPasswordConfirmed && passenger.getUserName() != "") {
                            out.print("Passwords do not match");
                        }
                    %>
                </div>
            </fieldset>

            <fieldset class = "personal-details-fieldset">
                <label>Gender:</label>
                <input type = "radio" name = "gender" id = "male" value = "M"> Male
                <input type = "radio" name = "gender" id = "female" value = "F"> Female
            </fieldset>

            <fieldset class = "personal-details-fieldset">
                <label for = "phone-number">Phone Number:</label>
                <input type = "tel" name = "phone-number" id = "phone-number" value = "<%
                     if (isPhoneNumberValid) {
                         out.print(passenger.getPhoneNumber());
                     }
                 %>">
               <div id = "phone-number-error-message">
                    <%
                        if (!isPhoneNumberValid && passenger.getUserName() != "") {
                            out.print("Please enter a valid phone number (phone number should contain 11 digits)");
                        }
                    %>
                </div>
            </fieldset>

            <fieldset class = "personal-details-fieldset">
                <label for = "email">Email:</label>
                <input type = "email" name = "email" id = "email" value = "<%
                    if (isEmailAddressValid) {
                        out.print(passenger.getEmailAddress());
                    }
                %>">
                <div id = "email-address-error-message">
                    <%
                        if (!isEmailAddressValid && passenger.getUserName() != "") {
                            out.print("Please enter a valid email address");
                        }
                    %>
                </div>
            </fieldset>

            <button type = "submit">Register</button>
        </form>

        <div class = "alternate-page-prompt">Already a customer? <a href = "login">Login</a></div>
    </article>

    <%@ include file = "footer.jsp"%>
</body>
</html>