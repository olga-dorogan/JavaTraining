package com.custom.controller.calendar;

import com.custom.controller.login.Utils;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.CalendarList;
import com.google.api.services.calendar.model.CalendarListEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by olga on 21.05.15.
 */
@WebServlet("/calendarList")
public class CalendarListController extends HttpServlet {
    private static final Logger LOGGER = LogManager.getLogger(CalendarListController.class);
    private static final String ATTRIBUTE_CALENDARS = "calendars";
    private static final String FORWARD_JSP = "calendar/calendarList.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<com.custom.model.Calendar> calendars = new ArrayList<com.custom.model.Calendar>();
        Calendar calendar = Utils.loadCalendarClient(req);
        CalendarList feed = calendar.calendarList().list().execute();
        LOGGER.debug("User calendars: ");
        if (feed.getItems() != null) {
            for (CalendarListEntry entry : feed.getItems()) {
                //doLogInfoAboutCalendarEntry(entry);
                calendars.add(new com.custom.model.Calendar(entry.getId(), entry.getSummary(), entry.getDescription()));
            }
        }
        req.setAttribute(ATTRIBUTE_CALENDARS, calendars);
        req.getRequestDispatcher(FORWARD_JSP).forward(req, resp);
        return;
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
