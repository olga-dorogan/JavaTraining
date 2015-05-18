package com.custom.controller;

import com.custom.model.vo.UserInfoVO;
import com.custom.service.UserInfoService;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by olga on 15.05.15.
 */
@WebServlet("/updateTasks")
public class UpdateUserTasksController extends HttpServlet {
    private static final String PARAM_TASKS = "tasks";
    private static final String SESSION_ATTRIBUTE_USER = "user";
    @EJB
    private UserInfoService userInfoService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect("/html/home.html");
        }
        UserInfoVO user = (UserInfoVO) session.getAttribute(SESSION_ATTRIBUTE_USER);
        if (user == null || req.getParameterValues(PARAM_TASKS) == null) {
            resp.sendRedirect("/html/home.html");
        }
        List<String> setTasks = Arrays.asList(req.getParameterValues(PARAM_TASKS));
        System.out.println(">>>>>>>>>>>>>>>>Tasks from request: " + setTasks);
        System.out.println(">>>>>>>>>>>>>>>>Tasks from user: " + user.getTasksStates());
        if (setTasks != null && user.getTasksStates() != null) {
            for (int i = 0; i < UserInfoService.TASKS_CNT; i++) {
                if (setTasks.contains(String.valueOf(i))) {
                    user.getTasksStates().set(i, true);
                } else {
                    user.getTasksStates().set(i, false);
                }
            }
        }
        System.out.println(">>>>>>>>>>>>>>>>Tasks from user: " + user.getTasksStates());
        UserInfoVO updatedUserInfoVO = userInfoService.updateTasks(user);
        System.out.println(">>>>>>>>>>>>>>>>Tasks from updated user: " + updatedUserInfoVO.getTasksStates());
        session.setAttribute(SESSION_ATTRIBUTE_USER, updatedUserInfoVO);
        resp.sendRedirect("welcomeUser.jsp");
    }
}
