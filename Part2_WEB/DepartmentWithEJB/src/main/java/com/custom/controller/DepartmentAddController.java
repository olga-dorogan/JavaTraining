package com.custom.controller;

import com.custom.model.dao.DepartmentDAO;
import com.custom.model.exception.DAOBusinessException;
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
@WebServlet("departmentAdd.do")
public class DepartmentAddController extends HttpServlet {
    public final String PARAMETER_DEPARTMENT_DESCR = "description";
    public final String REDIRECT_OK = "departmentAll.do";
    public final String PAGE_ERROR = "pages/error.jsp";
    public static final String PAGE_ERROR_ADD = "pages/departments.jsp";
    public static final String ATTRIBUTE_MODEL_TO_VIEW_ERROR = "errorAddingDepartment";
    public static final String ATTRIBUTE_MODEL_TO_VIEW_LIST = "departments";

    @EJB
    private DepartmentDAO departmentDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String departmentDescription = req.getParameter(PARAMETER_DEPARTMENT_DESCR);
        if (departmentDescription != null) {
            try {
                try {
                    departmentDAO.add(new Department(departmentDescription));
                    resp.sendRedirect(REDIRECT_OK);
                    return;
                } catch (DAOBusinessException e) {
                    req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_ERROR, departmentDescription);
                    req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_LIST, departmentDAO.getAll());
                    req.getRequestDispatcher(PAGE_ERROR_ADD).forward(req, resp);
                    return;
                }
            } catch (Exception ignore) {
            }
        }
        resp.sendRedirect(PAGE_ERROR);
    }
}
