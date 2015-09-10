package com.custom.controller;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by olga on 06.04.15.
 */
@WebServlet(urlPatterns = { "/FirstServlet" })
public class FirstServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>CDI Scopes</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>CDI Scopes</h1>");
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher("/SecondServlet");
            out.println("<h2>First request</h2>");
            dispatcher.include(request, response);
            out.println("<h2>Second request</h2>");
            dispatcher.include(request, response);
            out.println("</body>");
            out.println("</html>");
        }
    }
}
