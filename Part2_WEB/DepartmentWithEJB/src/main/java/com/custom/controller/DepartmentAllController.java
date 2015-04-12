package com.custom.controller;

import com.custom.model.dao.DepartmentDAO;
import com.custom.model.entity.Department;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by olga on 20.03.15.
 */
@WebServlet("departmentAll.do")
public class DepartmentAllController extends HttpServlet {
    public static final String ATTRIBUTE_MODEL_TO_VIEW = "departments";
    public static final String PAGE_OK = "pages/departments.jsp";
    public static final String PAGE_ERROR = "pages/error.jsp";

    @EJB
    private DepartmentDAO departmentDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Department> departments = departmentDAO.getAll();
            req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW, departments);
            req.getRequestDispatcher(PAGE_OK).forward(req, resp);
            return;
        } catch (Exception ignore) {
        }
        resp.sendRedirect(PAGE_ERROR);
    }
}
