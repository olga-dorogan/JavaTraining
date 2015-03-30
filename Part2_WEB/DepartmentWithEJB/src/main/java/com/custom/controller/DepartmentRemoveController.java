package com.custom.controller;

import com.custom.model.dao.DepartmentDAO;
import com.custom.model.exception.DAOBusinessException;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by olga on 23.03.15.
 */
@WebServlet("departmentRemove.do")
public class DepartmentRemoveController extends HttpServlet {
    public static final String PARAMETER_DEPARTMENT_ID = "departmentId";
    private static final String PAGE_REDIRECT_OK = "departmentAll.do";
    private static final String PAGE_ERROR = "pages/error.jsp";

    @EJB
    private DepartmentDAO departmentDAO;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String departmentIdStr = req.getParameter(PARAMETER_DEPARTMENT_ID);
        if (departmentIdStr != null) {
            try {
                Long departmentId = Long.parseLong(departmentIdStr);
                departmentDAO.delete(departmentDAO.getById(departmentId));
                resp.sendRedirect(PAGE_REDIRECT_OK);
                return;
            } catch (DAOBusinessException | IllegalArgumentException e) {
                //TODO: add block to show error with removing
                resp.sendRedirect(PAGE_REDIRECT_OK);
                return;
            } catch (Exception ignore) {
            }
        }
        resp.sendRedirect(PAGE_ERROR);
    }
}
