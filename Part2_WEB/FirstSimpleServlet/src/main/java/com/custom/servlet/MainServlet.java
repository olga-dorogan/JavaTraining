package com.custom.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

/**
 * Created by olga on 01.03.15.
 */
@WebServlet("/")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter writer = resp.getWriter();
        writer.print("" +
                "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head lang=\"en\">\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Example</title>\n" +
                "</head>\n" +
                "<body>\n");

        writer.print("" +
                "    <h3>Headers:</h3>\n");
        writer.print("" +
                "    <ul>\n");
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            writer.print("" +
                    "   <li>" + headerName + " == " + req.getHeader(headerName) + "</li>");
        }
        writer.print("" +
                "    </ul>\n");

        writer.print("" +
                "    <h3>Parameters:</h3>\n");
        writer.print("" +
                "    <ul>\n");
        Enumeration<String> parameterNames = req.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            writer.print("" +
                    "   <li>" + paramName + " == " + req.getParameter(paramName) + "</li>");
        }
        writer.print("" +
                "    </ul>\n");

        writer.print("" +
                "    <h3>Server threads:</h3>\n");
        synchronized (this) {
            int active = Thread.activeCount();
            Thread[] allThreads = new Thread[active];
            if (Thread.enumerate(allThreads) != active) {
                writer.print("<p> Error with receiving info about threads");
            } else {
                writer.print("<ul>");
                for (int i = 0; i < allThreads.length; i++) {
                    writer.print("<li>" + allThreads[i].toString() + "</li>");
                }
                writer.print("</ul>");
            }
        }
        writer.print("" +
                "</body>\n" +
                "</html>");
    }
}
