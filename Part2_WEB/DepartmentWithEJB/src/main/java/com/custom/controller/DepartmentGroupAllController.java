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

/**
 * Created by olga on 20.03.15.
 */
@WebServlet("departmentGroupAll.do")
public class DepartmentGroupAllController extends HttpServlet {
    private static final String PARAMETER_DEPARTMENT_ID = "departmentId";
    private static final String ATTRIBUTE_SESSION_DEPARTMENT_CURRENT = "departmentCurrent";
    private static final String ATTRIBUTE_MODEL_TO_VIEW_GROUPS = "departmentGroups";
    private static final String ATTRIBUTE_MODEL_TO_VIEW_DEPARTMENTS = "departments";
    private static final String PAGE_REDIRECT_OK = "pages/departmentGroups.jsp";
    private static final String PAGE_ERROR = "pages/error.jsp";

    @EJB
    private DepartmentDAO departmentDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String departmentIdStr = req.getParameter(PARAMETER_DEPARTMENT_ID);
        if (departmentIdStr != null) {
            try {
                long id = Long.parseLong(departmentIdStr);
                Department department = departmentDAO.getById(id);
                if (department != null) {
                    req.getSession(true).setAttribute(ATTRIBUTE_SESSION_DEPARTMENT_CURRENT, department);
                    req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_GROUPS, department.getGroups());
                    req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_DEPARTMENTS, departmentDAO.getAll());
                    req.getRequestDispatcher(PAGE_REDIRECT_OK).forward(req, resp);
                    return;
                }
            } catch (Exception ignore) {

            }
        }
        resp.sendRedirect(PAGE_ERROR);
    }
}
