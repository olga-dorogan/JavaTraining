package com.custom.executor;

import com.custom.RunnableTask;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.ExecutorService;

/**
 * Created by olga on 24.04.15.
 */
@WebServlet("poolExecutor")
public class PoolExecutorServlet extends HttpServlet {
    @EJB
    PoolExecutorBean poolExecutorBean;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        ExecutorService poolExecutor = poolExecutorBean.getPoolExecutor();
        int n = poolExecutorBean.getMaximumPoolSize() * 2;
        for (int i = 0; i < n; i++) {
            poolExecutor.execute(new RunnableTask(i));
        }
        writer.write(n + " tasks are executed");
    }
}
