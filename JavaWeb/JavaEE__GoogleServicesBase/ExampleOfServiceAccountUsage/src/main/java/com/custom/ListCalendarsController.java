package com.custom;

import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 21.05.15.
 */
@WebServlet("getCalendars")
public class ListCalendarsController extends HttpServlet {

    private static final Logger LOGGER = LoggerFactory.getLogger(ListCalendarsController.class);
    private static final String REDIRECT_ERROR = "home.html";
    private static final String REDIRECT_OK = "success.html";
    private static final String CALENDAR_TITLE = "CMS calendar";
    private static final String ATTRIBUTE_CALENDARS = "calendars";
    private static final String FORWARD_CALENDAR_LIST = "calendarList.jsp";

    @Inject
    ServletContext servletContext;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            Calendar calendarService = Utils.getCalendarService(servletContext);
            CalendarList feed = calendarService.calendarList().list().execute();
            List<com.custom.model.Calendar> calendarModels = new ArrayList<>();
            LOGGER.debug("User calendars: ");
            if (feed.getItems() != null) {
                for (CalendarListEntry entry : feed.getItems()) {
                    doLogInfoAboutCalendarEntry(entry);
                    calendarModels.add(new com.custom.model.Calendar(entry.getId(), entry.getSummary(), entry.getDescription()));
                }
            }
            req.setAttribute(ATTRIBUTE_CALENDARS, calendarModels);
            req.getRequestDispatcher(FORWARD_CALENDAR_LIST).forward(req,resp);
            return;
/*
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
*/
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
