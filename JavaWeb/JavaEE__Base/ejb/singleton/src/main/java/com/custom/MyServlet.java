package com.custom;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Created by olga on 10.04.15.
 */
@WebServlet(urlPatterns = {"test"})
public class MyServlet extends HttpServlet {
    @Inject
    MySingletonBean mySingleton;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Entered SingletonTestServlet/doGet() on " + new Date().toString() +
                " . Servlet instance " + this.hashCode() + " Thread : " + Thread.currentThread().getName());
        mySingleton.act();
    }
}
