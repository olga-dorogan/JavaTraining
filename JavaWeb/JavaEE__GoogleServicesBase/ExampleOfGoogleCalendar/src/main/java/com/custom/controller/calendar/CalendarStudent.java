package com.custom.controller.calendar;

import com.custom.controller.Utils;
import com.custom.service.calendar.CalendarService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by olga on 21.05.15.
 */
@WebServlet("calendarStudent")
public class CalendarStudent extends HttpServlet {
    private static final String ATTRIBUTE_CALENDAR_FRAME = "calendarFrame";
    private static final String REDIRECT_OK = "calendar/calendarStudent.jsp";
    private static final String REDIRECT_ERROR = "home.html";
    @Inject
    CalendarService calendarService;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<String> calendars = calendarService.getCourseCalendarIds();
        if (calendars.size() > 0) {
            HttpSession session = req.getSession(false);
            if (session == null) {
                resp.sendRedirect(REDIRECT_ERROR);
                return;
            }
            session.setAttribute(ATTRIBUTE_CALENDAR_FRAME, Utils.buildHtmlIFrame(calendars.get(0)));
        }
        resp.sendRedirect(REDIRECT_OK);
    }
}
