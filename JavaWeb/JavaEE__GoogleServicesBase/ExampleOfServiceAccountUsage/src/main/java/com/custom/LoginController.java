package com.custom;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.AclRule;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
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
 * Created by olga on 21.05.15.
 */
@WebServlet("login")
public class LoginController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(LoginController.class);
    private static final String REDIRECT_ERROR = "home.html";
    private static final String REDIRECT_OK = "success.html";
    private static final String CALENDAR_TITLE = "CMS calendar";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Calendar calendarService = Utils.getCalendarService(req);
            CalendarList feed = calendarService.calendarList().list().execute();
            LOGGER.debug("User calendars: ");
            if (feed.getItems() != null) {
                for (CalendarListEntry entry : feed.getItems()) {
                    doLogInfoAboutCalendarEntry(entry);
                }
            }

            com.google.api.services.calendar.model.Calendar entry = new com.google.api.services.calendar.model.Calendar();
            entry.setSummary(CALENDAR_TITLE);
            com.google.api.services.calendar.model.Calendar executedEntry = calendarService.calendars().insert(entry).execute();

            LOGGER.debug("Calendar is added {}", executedEntry == null ? "with error" : "successfully");

            AclRule rule = new AclRule();
            AclRule.Scope scope = new AclRule.Scope();
            scope.setType("user").setValue("dorogan.olga.test@gmail.com");
            rule.setScope(scope).setRole("owner");
            AclRule executedRule = calendarService.acl().insert(executedEntry.getId(), rule).execute();

            scope.setType("user").setValue("dorogan.olga.n@gmail.com");
            rule.setScope(scope).setRole("reader");
            executedRule = calendarService.acl().insert(executedEntry.getId(), rule).execute();

            LOGGER.debug("Rule is added {}", executedRule == null ? "with error" : "successfully");

            resp.sendRedirect(REDIRECT_OK);
            return;
        } catch (GeneralSecurityException e) {
            LOGGER.warn(e.getMessage());
        }
        resp.sendRedirect(REDIRECT_ERROR);
    }

    private void doLogInfoAboutCalendarEntry(CalendarListEntry entry) {
        LOGGER.debug("ID: " + entry.getId());
        LOGGER.debug("Summary: " + entry.getSummary());
        if (entry.getDescription() != null) {
            LOGGER.debug("Description: " + entry.getDescription());
        }
        LOGGER.debug("-----------------------------------------------");
    }

}
