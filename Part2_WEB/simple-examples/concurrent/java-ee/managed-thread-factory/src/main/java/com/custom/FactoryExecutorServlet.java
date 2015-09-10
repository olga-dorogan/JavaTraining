package com.custom;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by olga on 24.04.15.
 */
@WebServlet("factoryExecutor")
public class FactoryExecutorServlet extends HttpServlet {
    @Resource
    ManagedThreadFactory threadFactory;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        Thread thread = threadFactory.newThread(new RunnableTask());
        thread.setName("Custom thread");
        thread.setPriority(Thread.MAX_PRIORITY);
        thread.start();
        writer.write("Thread is started.");
    }
}
