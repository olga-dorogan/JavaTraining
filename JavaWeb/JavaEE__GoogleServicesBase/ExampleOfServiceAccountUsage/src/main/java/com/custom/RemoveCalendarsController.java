package com.custom;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 22.05.15.
 */
@WebServlet("clear")
public class RemoveCalendarsController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoveCalendarsController.class);
    private static final String REDIRECT_OK = "success.html";
    private static final String REDIRECT_ERROR = "home.html";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            com.google.api.services.calendar.Calendar calendarService = Utils.getCalendarService(req);
            CalendarList calendarList = calendarService.calendarList().list().execute();
            List<String> calendarsIds = new ArrayList<>();
            if (calendarList != null) {
                for (CalendarListEntry entry : calendarList.getItems()) {
                    calendarsIds.add(entry.getId());
                }
            }
            for (String calendarId : calendarsIds) {
                calendarService.calendarList().delete(calendarId).execute();
            }
            LOGGER.debug("Calendars list is cleared successfully");
            resp.sendRedirect(REDIRECT_OK);
            return;
        } catch (GeneralSecurityException e) {
            LOGGER.warn(e.getMessage());
        }
        resp.sendRedirect(REDIRECT_ERROR);
    }
}
