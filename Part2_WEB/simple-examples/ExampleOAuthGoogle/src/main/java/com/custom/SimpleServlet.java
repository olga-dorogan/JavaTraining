package com.custom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by olga on 14.05.15.
 */
@WebServlet("/index")
public class SimpleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write(
                "<!DOCTYPE html>\n" +
                        "<html>\n" +
                        "<head lang=\"en\">\n" +
                        "    <meta charset=\"UTF-8\">\n" +
                        "    <title></title>\n" +
                        "</head>\n" +
                        "<body>\n" +
                        "<a href=\"" + req.getContextPath() + "\\login.html\">Login</a>\n" +
                        "Hello\n" +
                        "</body>\n" +
                        "</html>");
    }
}
