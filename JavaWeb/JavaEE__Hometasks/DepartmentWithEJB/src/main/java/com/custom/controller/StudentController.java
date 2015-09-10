package com.custom.controller;

import com.custom.model.dao.DepartmentGroupDAO;
import com.custom.model.dao.StudentDAO;
import com.custom.model.exception.DAOBusinessException;
import com.custom.model.entity.Department;
import com.custom.model.entity.DepartmentGroup;
import com.custom.model.entity.Student;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by olga on 29.03.15.
 */
@WebServlet(urlPatterns = {"studentAll.do", "studentAdd.do", "studentRemove.do"})
public class StudentController extends HttpServlet {
    private static final String PARAMETER_GROUP_ID = "groupId";
    private static final String PAGE_OK = "pages/students.jsp";
    private static final String PAGE_ERROR = "pages/error.jsp";
    private static final String ATTRIBUTE_SESSION_GROUP_CURRENT = "groupCurrent";
    private static final String ATTRIBUTE_MODEL_TO_VIEW_GROUPS = "groups";
    private static final String ATTRIBUTE_MODEL_TO_VIEW_STUDENTS = "groupStudents";
    /// constants for 'add' method
    private static final String PARAMETER_STUDENT_FNAME = "studentFirstName";
    private static final String PARAMETER_STUDENT_LNAME = "studentLastName";
    private static final String PARAMETER_STUDENT_AGE = "studentAge";
    private static final String PAGE_REDIRECT_OK = "studentAll.do?" + PARAMETER_GROUP_ID + "=";
    private static final String ATTRIBUTE_SESSION_DEPARTMENT_CURRENT = "departmentCurrent";
    private static final String ATTRIBUTE_MODEL_TO_VIEW_ERROR_ADD_STUDENT = "errorAddStudentMessage";
    /// constants for 'remove' method
    private static final String PARAMETER_STUDENT_ID = "studentId";


    @EJB
    private StudentDAO studentDAO;
    @EJB
    private DepartmentGroupDAO departmentGroupDAO;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getRequestURL().toString().endsWith("studentAll.do")) {
            String groupIdStr = req.getParameter(PARAMETER_GROUP_ID);
            if (groupIdStr != null) {
                try {
                    long groupId = Long.parseLong(groupIdStr);
                    DepartmentGroup groupCurrent = departmentGroupDAO.getById(groupId);
                    HttpSession session = req.getSession(false);
                    if (groupCurrent != null && session != null) {
                        session.setAttribute(ATTRIBUTE_SESSION_GROUP_CURRENT, groupCurrent);
                        Department departmentCurrent = (Department) session.getAttribute(ATTRIBUTE_SESSION_DEPARTMENT_CURRENT);
                        if (departmentCurrent != null) {
                            req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_GROUPS, departmentCurrent.getGroups());
                            req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_STUDENTS, studentDAO.getAll(groupCurrent));
                            req.getRequestDispatcher(PAGE_OK).forward(req, resp);
                            return;
                        }
                    }
                } catch (Exception ignore) {
                }
            }
        }
        resp.sendRedirect(PAGE_ERROR);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String requestURL = req.getRequestURL().toString();
        if (requestURL.endsWith("studentAdd.do")) {
            doAddStudent(req, resp);
            return;
        }
        if (requestURL.endsWith("studentRemove.do")) {
            doRemoveStudent(req, resp);
            return;
        }

    }

    private void doRemoveStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String studentIdStr = req.getParameter(PARAMETER_STUDENT_ID);
        HttpSession session = req.getSession(false);
        if (session != null && studentIdStr != null) {
//            try {
                studentDAO.delete(studentDAO.getById(Long.parseLong(studentIdStr)));
                DepartmentGroup groupCurrent = (DepartmentGroup) session.getAttribute(ATTRIBUTE_SESSION_GROUP_CURRENT);
                resp.sendRedirect(PAGE_REDIRECT_OK + groupCurrent.getId());
                return;
//            } catch (Exception ignore) {
//            }
        }
//        resp.sendRedirect(PAGE_ERROR);
    }

    private void doAddStudent(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        String firstName = req.getParameter(PARAMETER_STUDENT_FNAME);
        String lastName = req.getParameter(PARAMETER_STUDENT_LNAME);
        String ageStr = req.getParameter(PARAMETER_STUDENT_AGE);
        HttpSession session = req.getSession(false);
        if (session != null && firstName != null && lastName != null && ageStr != null) {
            DepartmentGroup groupCurrent = null;
            try {
                groupCurrent = (DepartmentGroup) session.getAttribute(ATTRIBUTE_SESSION_GROUP_CURRENT);
                int age = Integer.parseInt(ageStr);
                studentDAO.addToGroup(groupCurrent, new Student(firstName, lastName, age));
                resp.sendRedirect(PAGE_REDIRECT_OK + groupCurrent.getId());
                return;
            } catch (DAOBusinessException e) {
                try {
                    Department departmentCurrent = (Department) session.getAttribute(ATTRIBUTE_SESSION_DEPARTMENT_CURRENT);
                    if (departmentCurrent != null) {
                        req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_ERROR_ADD_STUDENT, e.getMessage());
                        req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_GROUPS, departmentCurrent.getGroups());
                        req.setAttribute(ATTRIBUTE_MODEL_TO_VIEW_STUDENTS, studentDAO.getAll(groupCurrent));
                        req.getRequestDispatcher(PAGE_OK).forward(req, resp);
                        return;
                    }
                } catch (Exception ignore) {
                }

            } catch (Exception ignore) {

            }
        }
        resp.sendRedirect(PAGE_ERROR);
    }
}
