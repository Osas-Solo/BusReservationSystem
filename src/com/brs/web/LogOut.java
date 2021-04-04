package com.brs.web;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;
import java.io.*;

@WebServlet("/logout")
public class LogOut extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        synchronized (session) {
            session.removeAttribute("userName");
        }

        response.sendRedirect("login");
    }   //  end of doPost()

}   //  end of class
