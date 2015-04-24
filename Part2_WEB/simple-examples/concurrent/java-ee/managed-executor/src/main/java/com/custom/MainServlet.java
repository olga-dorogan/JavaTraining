package com.custom;

import com.custom.task.CallableTaskWithCallback;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedExecutorService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by olga on 23.04.15.
 */
@WebServlet("index")
public class MainServlet extends HttpServlet {
    @Resource(lookup = "java:jboss/concurrency/executor/myExecutor")
    ManagedExecutorService managedExecutorService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        /*
        managedExecutorService.execute(new SimpleTask());
        writer.write("simple task is executing");
        */

        /*
        writer.write("before simple callable\n");
        try {
            Future<Integer> result = managedExecutorService.submit(new CallableTask(10));
            int callableResult = result.get(5, TimeUnit.SECONDS);
            writer.write("after: result is " + callableResult);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (TimeoutException e) {
            e.printStackTrace();
        }
        */
        writer.write("before callable with callbacks\n");
        System.out.println("---before callable is submitted");
        CallableTaskWithCallback task = new CallableTaskWithCallback(10);
        managedExecutorService.submit(task);
        System.out.println("---after callable is submitted");
        writer.write("after callable with callbacks");

        // if methods of the callable use transactions,
        // transactions to be got from JTA (via javax.transaction.UserTransaction)
        // and they to be manually started, committed or rolled back
    }
}
