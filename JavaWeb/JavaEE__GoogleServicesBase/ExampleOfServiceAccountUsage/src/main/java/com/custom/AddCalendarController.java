package com.custom;

import com.google.api.services.calendar.model.AclRule;
import com.google.api.services.calendar.model.Calendar;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;

/**
 * Created by olga on 22.05.15.
 */
@WebServlet("addCalendar")
public class AddCalendarController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(AddCalendarController.class);

    private static final String PARAMETER_CALENDAR = "summary";
    private static final String REDIRECT_ERROR = "home.html";
    private static final String REDIRECT_OK = "getCalendars";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String calendarSummary = req.getParameter(PARAMETER_CALENDAR);
        if (calendarSummary == null) {
            resp.sendRedirect(REDIRECT_ERROR);
            return;
        }
        try {
            com.google.api.services.calendar.Calendar calendarService = Utils.getCalendarService(req);
            Calendar entry = new com.google.api.services.calendar.model.Calendar();
            entry.setSummary(calendarSummary);
            Calendar executedEntry = calendarService.calendars().insert(entry).execute();
            LOGGER.debug("Calendar is added {}", executedEntry == null ? "with error" : "successfully");

            AclRule rule = new AclRule();
            AclRule.Scope scope = new AclRule.Scope();
            scope.setType("default");
            rule.setScope(scope).setRole("reader");
            calendarService.acl().insert(executedEntry.getId(), rule).execute();

            rule = new AclRule();
            scope = new AclRule.Scope();
            scope.setType("user").setValue("dorogan.olga.test@gmail.com");
            rule.setScope(scope).setRole("owner");
            calendarService.acl().insert(executedEntry.getId(), rule).execute();

            scope.setType("user").setValue("dorogan.olga.n@gmail.com");
            rule.setScope(scope).setRole("reader");
            AclRule executedRule = calendarService.acl().insert(executedEntry.getId(), rule).execute();

            LOGGER.debug("Roles are added {}", executedRule == null ? "with error" : "successfully");
            resp.sendRedirect(REDIRECT_OK);
            return;

        } catch (GeneralSecurityException e) {
            LOGGER.warn(e.getMessage());
        }
        resp.sendRedirect(REDIRECT_ERROR);
    }
}
