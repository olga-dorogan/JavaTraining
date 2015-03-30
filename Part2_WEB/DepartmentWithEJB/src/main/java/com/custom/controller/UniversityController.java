package com.custom.controller;

import com.custom.model.dao.DepartmentGroupDAO;
import com.custom.model.service.UniversityService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by olga on 29.03.15.
 */
@WebServlet("universityInfo.do")
public class UniversityController extends HttpServlet {
    private static final String ATTRIBUTE_MODEL_TO_VIEW_GROUPS = "departmentGroups";
    private static final String PAGE_OK = "pages/university.jsp";
   // @EJB
   // private UniversityService service;
    @EJB
    private DepartmentGroupDAO departmentGroupDAO;
    @EJB
    private UniversityService service;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        List<DepartmentGroup> groups = service.getGroupsFromDepartment(1, true);
        req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_GROUPS, service.getGroupsFromDepartment(1, false));
        req.getRequestDispatcher(PAGE_OK).forward(req, resp);
    }
}
