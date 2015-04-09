package com.custom;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by olga on 09.04.15.
 */
@WebServlet("/index")
public class MyServlet extends HttpServlet {
    @Inject
    private MyStatelessBean statelessBeanInjected1;
    @Inject
    private MyStatelessBean statelessBeanInjected2;

    @Inject
    private MyStatefulBean statefulBean1;
    @Inject
    private MyStatefulBean statefulBean2;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TestServlet</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Stateless bean: invoke testMethod (first invocation)</h1>");
            statelessBeanInjected1.testMethod();
            out.println("<h1>Stateless bean: invoke testMethod (second invocation)</h1>");
            statelessBeanInjected1.testMethod();

            out.println("<h1>Stateless bean: invoke testMethod (first invocation)</h1>");
            statelessBeanInjected2.testMethod();
            out.println("<h1>Stateless bean: invoke testMethod (second invocation)</h1>");
            statelessBeanInjected2.testMethod();

            out.println("<h1>Stateful bean</h1>");
            statefulBean1.addItem("red");
            for (String s : statefulBean1.items()) {
                out.println(s + "<br>");
            }
            out.println("<h1>Stateful bean</h1>");
            statefulBean2.addItem("red");
            for (String s : statefulBean2.items()) {
                out.println(s + "<br>");
            }
            out.println("<p><p>Look for output in server.log");
            out.println("</body>");
            out.println("</html>");
        }
    }
}
