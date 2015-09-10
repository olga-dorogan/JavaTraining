package com.custom;

import com.custom.task.RunnableTask;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.TimeUnit;

/**
 * Created by olga on 24.04.15.
 */
@WebServlet("scheduled")
public class ServletWithScheduledExecutor extends HttpServlet {
    @Resource
    ManagedScheduledExecutorService executorService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        System.out.println("---before schedule: " + System.currentTimeMillis());
        executorService.schedule(new RunnableTask(), 10, TimeUnit.SECONDS);
    }
}
