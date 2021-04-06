package com.brs.web;

import com.brs.model.Passenger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;
import java.util.regex.*;

@WebServlet("/register")
public class Registration extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        String firstName = request.getParameter("first-name");
        String lastName = request.getParameter("last-name");
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String passwordConfirmer = request.getParameter("confirm-password");
        char gender = request.getParameter("gender").charAt(0);
        String phoneNumber = request.getParameter("phone-number");
        String emailAddress = request.getParameter("email");

        boolean isPassengerFound = false;

        RequestDispatcher view = request.getRequestDispatcher("register.jsp");

        try (
                Connection connection = DriverManager.getConnection(DatabaseDetails.DATABASE_NAME,
                        DatabaseDetails.USER_NAME,
                        DatabaseDetails.PASSWORD);
                Statement statement = connection.createStatement()
        ) {
            String query = "SELECT user_name from passengers WHERE user_name = '" + userName + "'";
            ResultSet resultSet = statement.executeQuery(query);

            if (resultSet.next()) {
                isPassengerFound = true;
            }

            request.setAttribute("isPassengerFound", isPassengerFound);

            Passenger passenger = new Passenger(userName);
            passenger.setFirstName(firstName);
            passenger.setLastName(lastName);
            passenger.setGender(gender);
            passenger.setPhoneNumber(phoneNumber);
            passenger.setEmailAddress(emailAddress);

            request.setAttribute("passenger", passenger);

            boolean isRegistrationValid = (isPassengerFound == false) && (userName != null)
                                            && (firstName.length() > 0) && (lastName.length() > 0)
                                            && isPasswordValid(password) && isPasswordConfirmed(password, passwordConfirmer)
                                            && isPhoneNumberValid(phoneNumber) && isEmailAddressValid(emailAddress);

            request.setAttribute("isFirstNameValid", firstName.length() > 0);
            request.setAttribute("isLastNameValid", lastName.length() > 0);
            request.setAttribute("isPasswordValid", isPasswordValid(password));
            request.setAttribute("isPasswordConfirmed", isPasswordConfirmed(password, passwordConfirmer));
            request.setAttribute("isPhoneNumberValid", isPhoneNumberValid(phoneNumber));
            request.setAttribute("isEmailAddressValid", isEmailAddressValid(emailAddress));
            request.setAttribute("isRegistrationValid", isRegistrationValid);

            if (isRegistrationValid) {
                query = String.format("INSERT INTO passengers (user_name, first_name, last_name, gender, phone_number, email_address, password) " +
                                "VALUES (\"%s\", \"%s\", \"%s\", \"%c\", \"%s\", \"%s\", SHA(\"%s\"))",
                        userName, firstName, lastName, gender, phoneNumber, emailAddress, password) ;
                statement.executeUpdate(query);
            } else {
                view = request.getRequestDispatcher("signup.jsp");
            }

            view.forward(request, response);
        } catch (SQLException e) {
            out.println("<p>Error connecting to database</p>");
            e.printStackTrace();
        }
    }   //  end of doPost()

    private static boolean isPasswordValid(String password) {
        return isPasswordRequiredLength(password) && doesPasswordContainLowerCase(password) &&
                doesPasswordContainUpperCase(password) && doesPasswordContainDigit(password);
    }   //  end of isPasswordValid()

    public static boolean isPasswordRequiredLength(String password) {
        return password.length() >= 8 && password.length() <= 20;
    }   //  end of isPasswordRequiredLength()

    public static boolean doesPasswordContainLowerCase(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isLowerCase(password.charAt(i))) {
                return true;
            }
        }

        return false;
    }   //  end of containsLowerCase()

    public static boolean doesPasswordContainUpperCase(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isUpperCase(password.charAt(i))) {
                return true;
            }
        }

        return false;
    }   //  end of containsUpperCase()

    public static boolean doesPasswordContainDigit(String password) {
        for (int i = 0; i < password.length(); i++) {
            if (Character.isDigit(password.charAt(i))) {
                return true;
            }
        }

        return false;
    }   //  end of containsDigit()

    private static boolean isPasswordConfirmed(String password, String passwordConfirmer) {
        return password.equals(passwordConfirmer);
    }

    private static boolean isPhoneNumberValid(String phoneNumber){
        int digitCount = 0;

        for (int i = 0; i < phoneNumber.length(); i++) {
            if (Character.isDigit(phoneNumber.charAt(i))) {
                digitCount++;
            }
        }

        return digitCount == 11 && phoneNumber.length() == 11;
    }   //  end of isPhoneNumberValid()

    private static boolean isEmailAddressValid(String emailAddress){
        String emailRegex = "^[A-Za-z0-9+_.-]+@(.+)$";
        Pattern emailPattern = Pattern.compile(emailRegex);

        Matcher emailPatternMatcher = emailPattern.matcher(emailAddress);

        return emailPatternMatcher.matches();
    }   //  end of isEmailAddressValid()

}   //  end of class