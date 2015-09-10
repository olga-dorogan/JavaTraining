package com.custom.controller;

import com.custom.model.service.UniversityService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by olga on 29.03.15.
 */
@WebServlet("universityInfo.do")
public class UniversityController extends HttpServlet {
    private static final String ATTRIBUTE_MODEL_TO_VIEW_DEPARTMENTS = "departments";
    private static final String ATTRIBUTE_MODEL_TO_VIEW_GROUPS = "groupsFromSelectedDepartments";
    private static final String PARAMETER_SELECTED_DEPARTMENTS = "selectedDepartments";
    private static final String PAGE_OK = "pages/university.jsp";
    private static final String PARAMETER_GET_GROUPS_CNT = "getGroupsCnt";
    private static final String PARAMETER_GET_GROUPS_FROM_DEPS = "getGroupsFromDeps";
    private static final String ATTRIBUTE_MODEL_TO_VIEW_CNT_GROUPS = "cntsGroupPerDepartment";
    private static final String ATTRIBUTE_MODEL_TO_VIEW_LAST_SELECTED_DEPS = "lastSelectedDepartments";
    private static final String PARAMETER_GET_STUDENTS_CNT = "getStudentsCnt";
    private static final String ATTRIBUTE_MODEL_TO_VIEW_STUDENTS = "cntsStudentsPerDepartment";

    @EJB
    private UniversityService service;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_DEPARTMENTS, service.getDepartments());
        // get params to choose get type
        String paramGroupsFromDeps = req.getParameter(PARAMETER_GET_GROUPS_FROM_DEPS);
        String paramCntCroups = req.getParameter(PARAMETER_GET_GROUPS_CNT);
        String paramCntStudents = req.getParameter(PARAMETER_GET_STUDENTS_CNT);
        // get selected department ids
        Collection<Long> departmentIds = null;
        String[] selectedDepartments = req.getParameterValues(PARAMETER_SELECTED_DEPARTMENTS);
        if (selectedDepartments != null) {
            departmentIds = new ArrayList<>(selectedDepartments.length);
            for (String idStr : selectedDepartments) {
                departmentIds.add(Long.parseLong(idStr));
            }
            Map<Long, Boolean> attrIdsMap = new HashMap<>(departmentIds.size());
//            for (Long id : departmentIds) {
//                attrIdsMap.put(id, true);
//            }
            req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_LAST_SELECTED_DEPS, attrIdsMap);
        }
        if (paramGroupsFromDeps != null) {
                req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_GROUPS, service.getGroupsFromDepartments(departmentIds));
        }
        else if (paramCntCroups != null) {
            req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_CNT_GROUPS, service.getCntGroupsInDepartments(departmentIds));
        } else if (paramCntStudents != null) {
            req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_STUDENTS, service.getCntStudentsInDepartments(departmentIds));
        }

        req.getRequestDispatcher(PAGE_OK).forward(req, resp);
    }
}
