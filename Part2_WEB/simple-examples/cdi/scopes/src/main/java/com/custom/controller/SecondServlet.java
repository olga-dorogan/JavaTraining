package com.custom.controller;

import com.custom.bean.MyApplicationScopedBean;
import com.custom.bean.MyRequestScopedBean;
import com.custom.bean.MySessionScopedBean;
import com.custom.bean.MySingletonScopedBean;

import javax.inject.Inject;
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
@WebServlet(urlPatterns = {"/SecondServlet"})
public class SecondServlet extends HttpServlet {
    @Inject
    MyRequestScopedBean requestBean;
    @Inject
    MyRequestScopedBean requestBean2;

    @Inject
    MySessionScopedBean sessionBean;
    @Inject
    MySessionScopedBean sessionBean2;

    @Inject
    MyApplicationScopedBean applicationBean;
    @Inject
    MySingletonScopedBean singletonBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try (PrintWriter out = response.getWriter()) {
            out.println("<b>Request-scoped bean</b>");
            out.println("<br><br>(1): " + requestBean.getID());
            out.println("<br>(2): " + requestBean2.getID());
            out.println("<br><br><b>Session-scoped bean</b>");
            out.println("<br><br>(1): " + sessionBean.getID());
            out.println("<br>(2): " + sessionBean2.getID());
            out.println("<br><br><b>Application-scoped bean</b>: " + applicationBean.getID());
            out.println("<br><br><b>Singleton-scoped bean</b>: " + singletonBean.getID());
        }
    }
}
