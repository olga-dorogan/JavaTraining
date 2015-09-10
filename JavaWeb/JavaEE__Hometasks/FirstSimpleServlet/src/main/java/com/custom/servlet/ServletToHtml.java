package com.custom.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Enumeration;

/**
 * Created by olga on 12.03.15.
 */
@WebServlet("/info.byHtml")
public class ServletToHtml extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        Writer writer = resp.getWriter();

        writer.write("<!DOCTYPE html>" +
                "<html>" +
                "<head lang=\"en\">" +
                "    <meta charset=\"UTF-8\">" +
                "    <title></title>" +
                "</head>" +
                "<body>");
        writer.write("<h1>Headers</h1>");
        writer.write("<ul>");
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            writer.write("<li>" + name + ": " + req.getHeader(name) + "</li>");
        }
        writer.write("</ul>");
        writer.write("<h1>Parameters</h1>");
        writer.write("<ul>");
        Enumeration<String> paramNames = req.getParameterNames();
        while (paramNames.hasMoreElements()) {
            String name = paramNames.nextElement();
            writer.write("<li>" + name + ": " + req.getParameter(name) + "</li>");
        }
        writer.write("</ul>");
        writer.write("</body>" +
                "</html>");
        writer.close();
    }
}
