package com.custom;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ContextService;
import javax.enterprise.concurrent.ManagedThreadFactory;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by olga on 24.04.15.
 */
// TODO: what benefit can be got from a contextual proxy
@WebServlet("servletWithProxy")
public class MainServlet extends HttpServlet {
    @Resource
    ContextService contextService;
    @Resource
    ManagedThreadFactory managedThreadFactory;
    @Inject
    TestCDIBean bean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.write("test cdi bean: " + bean.greeting() + "\n");
        ExecutorService executorService = Executors.newFixedThreadPool(1, managedThreadFactory);
        Runnable runnable = contextService.createContextualProxy(new RunnableTask(), Runnable.class);
        executorService.execute(runnable);
        writer.print("runnable is executed");

    }
}
