package com.custom.controller;

import com.custom.model.entity.Department;
import com.custom.model.dao.DepartmentDAO;
import com.custom.model.exception.DAOBusinessException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by olga on 23.03.15.
 */
@WebServlet("departmentEdit.do")
public class DepartmentEditController extends HttpServlet {
    private static final String PARAMETER_DEPARTMENT_DESCRIPTION = "departmentDescription";

    private static final String PAGE_REDIRECT_OK = "departmentGroupAll.do?departmentId=";
    private static final String PAGE_ERROR = "pages/error.jsp";
    private static final String PAGE_ERROR_EDIT = "pages/departmentGroups.jsp";

    private static final String ATTRIBUTE_SESSION_DEPARTMENT_CURRENT = "departmentCurrent";
    private static final String ATTRIBUTE_MODEL_TO_VIEW_EDIT_DEPARTMENT_ERROR = "errorEditDepartmentMessage";
    private static final String ATTRIBUTE_MODEL_TO_VIEW_GROUPS = "departmentGroups";
    private static final String ATTRIBUTE_MODEL_TO_VIEW_DEPARTMENTS = "departments";

    @EJB
    private DepartmentDAO departmentDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String departmentDescription = req.getParameter(PARAMETER_DEPARTMENT_DESCRIPTION);
        if (departmentDescription != null) {
            HttpSession session = req.getSession(false);
            if (session != null) {
                Department oldDepartment = (Department) session.getAttribute(ATTRIBUTE_SESSION_DEPARTMENT_CURRENT);
                oldDepartment.setDescription(departmentDescription);
                try {
                    try {
                        Department newDepartment = departmentDAO.update(oldDepartment);
                        resp.sendRedirect(PAGE_REDIRECT_OK + newDepartment.getId());
                        return;
                    } catch (DAOBusinessException e) {
                        req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_EDIT_DEPARTMENT_ERROR, e.getMessage());
                        req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_GROUPS, oldDepartment.getGroups());
                        req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_DEPARTMENTS, departmentDAO.getAll());
                        req.getRequestDispatcher(PAGE_ERROR_EDIT).forward(req, resp);
                        return;
                    }
                } catch (Exception ignore) {
                }
            }
        }
        resp.sendRedirect(PAGE_ERROR);
    }
}
