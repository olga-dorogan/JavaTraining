package org.javatraining.integration.google.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

/**
 * Created by olga on 27.05.15.
 */
@WebServlet("testCalendar")
public class CalendarManagerController extends HttpServlet {
    private static final Logger LOGGER = LoggerFactory.getLogger(CalendarManagerController.class);
    private static final String REDIRECT_OK = "home.html";
    private static final String ATTRIBUTE_LIST = "list";
    private static final String REDIRECT_ERROR = "home.html";
    private static final String ATTRIBUTE_ADD = "add";
    private static final String ATTRIBUTE_REMOVE = "remove";
    private static final String ATTRIBUTE_REMOVE_TEACHER = "removeTeacher";
    private static final String ATTRIBUTE_ADD_TEACHER = "addTeacher";
    private static final String ATTRIBUTE_REMOVE_STUDENT = "removeStudent";
    private static final String ATTRIBUTE_ADD_STUDENT = "addStudent";
    @EJB
    org.javatraining.integration.google.calendar.CalendarManager calendarManager;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("=================================================");
        LOGGER.debug("Hello from test Servlets");
        String attributeList = req.getParameter(ATTRIBUTE_LIST);
        if (attributeList != null) {
            System.out.println("List of calendars: ");
            System.out.println(calendarManager.getListOfCalendarsSummaries().toString());
        }
        String attributeAdd = req.getParameter(ATTRIBUTE_ADD);
        if (attributeAdd != null) {
            printListEmailsForCalendar(
                    calendarManager.addCalendar(attributeAdd,
                            Arrays.asList(new String[]{}),//"dorogan.olga.test@gmail.com"}),
                            Arrays.asList(new String[]{"dorogan.olga.n@gmail.com"}))
            );
        }
        String attributeRemove = req.getParameter(ATTRIBUTE_REMOVE);
        if (attributeRemove != null) {
            List<String> calendarsIds = calendarManager.getListOfCalendarsIds();
            for (String calendarId : calendarsIds) {
                System.out.println("Before remove calendar: ");
                printListEmailsForCalendar(calendarId);
                calendarManager.removeCalendar(calendarId);
            }
        }
        String attributeRemoveTeacher = req.getParameter(ATTRIBUTE_REMOVE_TEACHER);
        if (attributeRemoveTeacher != null) {
            List<String> calendarsIds = calendarManager.getListOfCalendarsIds();
            for (String calendarId : calendarsIds) {
                calendarManager.removeTeacherFromCalendar(calendarId, "dorogan.olga.test@gmail.com");
                printListEmailsForCalendar(calendarId);
            }
            System.out.println("Teacher was removed");
        }
        String attributeAddTeacher = req.getParameter(ATTRIBUTE_ADD_TEACHER);
        if (attributeAddTeacher != null) {
            List<String> calendarsIds = calendarManager.getListOfCalendarsIds();
            for (String calendarId : calendarsIds) {
                calendarManager.addTeacherToCalendar(calendarId, "dorogan.olga.test@gmail.com");
                printListEmailsForCalendar(calendarId);
            }
            System.out.println("Teacher was added");
        }
        String attributeRemoveStudent = req.getParameter(ATTRIBUTE_REMOVE_STUDENT);
        if (attributeRemoveStudent != null) {
            List<String> calendarsIds = calendarManager.getListOfCalendarsIds();
            for (String calendarId : calendarsIds) {
                calendarManager.removeStudentFromCalendar(calendarId, "dorogan.olga.n@gmail.com");
                printListEmailsForCalendar(calendarId);
            }
            System.out.println("Student was removed");
        }
        String attributeAddStudent = req.getParameter(ATTRIBUTE_ADD_STUDENT);
        if (attributeAddStudent != null) {
            List<String> calendarsIds = calendarManager.getListOfCalendarsIds();
            for (String calendarId : calendarsIds) {
                calendarManager.addStudentToCalendar(calendarId, "dorogan.olga.n@gmail.com");
                printListEmailsForCalendar(calendarId);
            }
            System.out.println("Student was added");
        }

        resp.sendRedirect(REDIRECT_OK);
        return;
    }

    private void printListEmailsForCalendar(String calendarId) {
        System.out.println("For " + calendarId);
        System.out.println(calendarManager.getListOfCalendarSubscribers(calendarId));
    }
}
