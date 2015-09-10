package com.custom.controller;

import com.custom.Greeting;

import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.naming.InitialContext;
import javax.naming.NamingException;
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
@WebServlet(urlPatterns = {"/TestServletJNDI"})
public class TestServletJNDI extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>BeanManager using JNDI</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>BeanManager using JNDI</h1>");
            try {
                InitialContext context = new InitialContext();
                BeanManager beanManager = (BeanManager) context.lookup("java:comp/BeanManager");
                Set<Bean<?>> beans = beanManager.getBeans(Greeting.class);
                for (Bean<?> b : beans) {
                    out.print(b.getBeanClass().getName() + "<br>");
                }
            } catch (NamingException e) {
                e.printStackTrace();
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
