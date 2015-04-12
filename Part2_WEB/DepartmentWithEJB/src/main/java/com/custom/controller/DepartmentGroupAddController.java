package com.custom.controller;

import com.custom.model.dao.DepartmentDAO;
import com.custom.model.dao.DepartmentGroupDAO;
import com.custom.model.entity.Department;
import com.custom.model.entity.DepartmentGroup;
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
 * Created by olga on 20.03.15.
 */
@WebServlet("departmentGroupAdd.do")
public class DepartmentGroupAddController extends HttpServlet {
    private static final String PARAMETER_GROUP_NAME = "groupName";
    private static final String PARAMETER_GROUP_COURSE = "groupCourse";

    private static final String PAGE_REDIRECT_OK = "departmentGroupAll.do?departmentId=";
    private static final String PAGE_ERROR = "pages/error.jsp";
    private static final String PAGE_FORWARD_ADD_GROUP_ERROR = "pages/departmentGroups.jsp";

    private static final String ATTRIBUTE_SESSION_DEPARTMENT_CURRENT = "departmentCurrent";
    private static final String ATTRIBUTE_MODEL_TO_VIEW_ERROR_ADD_GROUP = "errorAddGroupMessage";
    private static final String ATTRIBUTE_MODEL_TO_VIEW_GROUPS = "departmentGroups";
    private static final String ATTRIBUTE_MODEL_TO_VIEW_DEPARTMENTS = "departments";

    @EJB
    private DepartmentGroupDAO departmentGroupDAO;
    @EJB
    private DepartmentDAO departmentDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session != null) {
            Department departmentCurrent = (Department) session.getAttribute(ATTRIBUTE_SESSION_DEPARTMENT_CURRENT);
            String groupName = req.getParameter(PARAMETER_GROUP_NAME);
            String groupCourseStr = req.getParameter(PARAMETER_GROUP_COURSE);
            try {
                try {
                    if (departmentCurrent != null && groupName != null && groupCourseStr != null) {
                        int groupCourse = Integer.parseInt(groupCourseStr);
                        departmentGroupDAO.addToDepartment(departmentCurrent, new DepartmentGroup(groupName, groupCourse));
                        resp.sendRedirect(PAGE_REDIRECT_OK + departmentCurrent.getId());
                        return;
                    }
                } catch (DAOBusinessException e) {
                    req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_ERROR_ADD_GROUP, e.getMessage());
                    req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_GROUPS, departmentCurrent.getGroups());
                    req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_DEPARTMENTS, departmentDAO.getAll());
                    req.getRequestDispatcher(PAGE_FORWARD_ADD_GROUP_ERROR).forward(req, resp);
                    return;
                }
            } catch (Exception ignore) {
            }
        }
        resp.sendRedirect(PAGE_ERROR);
    }
}
