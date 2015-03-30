package com.custom.controller;

import com.custom.model.dao.DepartmentDAO;
import com.custom.model.dao.DepartmentGroupDAO;
import com.custom.model.entity.Department;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by olga on 24.03.15.
 */
@WebServlet("departmentGroupRemove.do")
public class DepartmentGroupRemoveController extends HttpServlet {
    private static final String PARAMETER_GROUP_ID = "groupId";
    private static final String ATTRIBUTE_SESSION_DEPARTMENT_CURRENT = "departmentCurrent";
    private static final String PAGE_REDIRECT_OK = "departmentGroupAll.do?departmentId=";
    private static final String PAGE_ERROR = "pages/error.jsp";


    @EJB
    private DepartmentGroupDAO departmentGroupDAO;
    @EJB
    protected DepartmentDAO departmentDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String groupIdStr = req.getParameter(PARAMETER_GROUP_ID);
        if (groupIdStr != null) {
            try {
                long groupId = Long.parseLong(groupIdStr);
                departmentGroupDAO.delete(departmentGroupDAO.getById(groupId));
                HttpSession session = req.getSession(false);
                if (session != null) {
                    Department department = (Department) session.getAttribute(ATTRIBUTE_SESSION_DEPARTMENT_CURRENT);
                    if (department != null) {
                        resp.sendRedirect(PAGE_REDIRECT_OK + department.getId());
                        return;
                    }
                }
            } catch (Exception ignore) {
            }
        }
        resp.sendRedirect(PAGE_ERROR);
    }
}
