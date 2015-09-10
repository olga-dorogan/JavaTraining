package com.custom;

import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;

/**
 * Created by olga on 22.04.15.
 */
@WebServlet("index")
public class MyServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        PrintWriter writer = resp.getWriter();
        writer.write("<html><head><title>Batchlet example</title></head>");
        writer.write("<body>");
        writer.write("<h1>Batchlet example start</h1>");
        JobOperator jobOperator = BatchRuntime.getJobOperator();
        jobOperator.start("job", new Properties());
        writer.write("To get result, see log");
        writer.write("</body></html>");
    }
}
