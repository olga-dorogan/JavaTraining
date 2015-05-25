package com.custom;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by olga on 24.05.15.
 */
@WebServlet("/role")
public class RoleController extends HttpServlet {
    private static final String PARAM_EMAIL = "email";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        PrintWriter writer = resp.getWriter();
        String emailParam = req.getParameter(PARAM_EMAIL);
        if (emailParam == null) {
            writer.write("{ \"role\": \"guest\"}");
            return;
        } else if (emailParam.equalsIgnoreCase("dorogan.olga.test@gmail.com")) {
            writer.write("{ \"role\": \"admin\"}");
            return;
        }
        writer.write("{ \"role\": \"student \"}");
    }
}
