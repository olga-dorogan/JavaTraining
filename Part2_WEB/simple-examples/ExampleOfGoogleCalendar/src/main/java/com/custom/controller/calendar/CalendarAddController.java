package com.custom.controller.calendar;

import com.custom.controller.login.Utils;
import com.custom.service.calendar.CalendarService;
import com.google.api.services.calendar.model.AclRule;
import com.google.api.services.calendar.model.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by olga on 21.05.15.
 */
@WebServlet("calendarAdd")
public class CalendarAddController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalendarAddController.class);
    private static final String PARAMETER_TITLE = "title";
    private static final String ATTRIBUTE_CALENDAR_TITLE = "calendarTitle";
    private static final String ATTRIBUTE_CALENDAR_FRAME = "calendarFrame";
    private static final String REDIRECT_OK = "calendar/calendarAdded.jsp";
    private static final String REDIRECT_ACCESS_DENIED = "home.html";
    private static final String REDIRECT_ERROR = "calendarList";

    @Inject
    CalendarService calendarService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) {
            resp.sendRedirect(REDIRECT_ACCESS_DENIED);
            return;
        }
        String calendarTitle = req.getParameter(PARAMETER_TITLE);
        if (calendarTitle == null) {
            resp.sendRedirect(REDIRECT_ERROR);
            return;
        }
        com.google.api.services.calendar.Calendar calendarClient = Utils.loadCalendarClient(req);
        Calendar entry = new Calendar();
        entry.setSummary(calendarTitle);
        com.google.api.services.calendar.model.Calendar executedEntry = calendarClient.calendars().insert(entry).execute();

        LOGGER.debug("Calendar is added {}", executedEntry == null ? "with error" : "successfully");

        AclRule rule = new AclRule();
        AclRule.Scope scope = new AclRule.Scope();
        scope.setType("user").setValue("dorogan.olga.n@gmail.com");
        rule.setScope(scope).setRole("reader");
        AclRule executedRule = calendarClient.acl().insert(executedEntry.getId(), rule).execute();

        LOGGER.debug("Rule is added {}", executedRule == null ? "with error" : "successfully");

        calendarService.addCourseCalendarId(executedEntry.getId());

        session.setAttribute(ATTRIBUTE_CALENDAR_TITLE, executedEntry.getSummary());
        session.setAttribute(ATTRIBUTE_CALENDAR_FRAME, com.custom.controller.Utils.buildHtmlIFrame(executedEntry.getId()));
        resp.sendRedirect(REDIRECT_OK);
        return;
    }


}
