package com.custom;

import javax.batch.operations.JobOperator;
import javax.batch.operations.JobSecurityException;
import javax.batch.operations.JobStartException;
import javax.batch.runtime.BatchRuntime;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by olga on 21.04.15.
 */
@WebServlet("index")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = resp.getWriter()) {
            out.println("<html>");
            out.println("<head>");
            out.println("<title>CSV-to-Database Chunk Job</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>CSV-to-Database Chunk Job</h1>");

            JobOperator jo = BatchRuntime.getJobOperator();
            long jid = jo.start("myJobs", new Properties());

            out.println("Job submitted: " + jid + "<br>");
            out.println("<br><br>Check server.log for output, also look at \"myJobs.xml\" for Job XML.");
            out.println("</body>");
            out.println("</html>");

        } catch (JobStartException | JobSecurityException ex) {
            Logger.getLogger(MainServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
