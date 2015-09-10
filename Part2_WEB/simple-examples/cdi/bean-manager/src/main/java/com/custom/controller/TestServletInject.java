package com.custom.controller;

import com.custom.Greeting;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;

/**
 * Created by olga on 06.04.15.
 */
@WebServlet(urlPatterns = {"/TestServletInject"})
public class TestServletInject extends HttpServlet {
    @Inject
    BeanManager beanManager;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>BeanManager using Injection</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>BeanManager using Injection</h1>");
            Set<Bean<?>> beans = beanManager.getBeans(Greeting.class);
            for (Bean<?> b : beans) {
                out.print(b.getBeanClass().getName() + "<br>");
            }
            out.println("</body>");
            out.println("</html>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        processRequest(req, resp);
    }
}
