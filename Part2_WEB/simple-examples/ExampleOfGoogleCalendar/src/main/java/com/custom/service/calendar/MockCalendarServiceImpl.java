package com.custom.service.calendar;

import javax.enterprise.context.ApplicationScoped;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by olga on 21.05.15.
 */
@ApplicationScoped
public class MockCalendarServiceImpl implements CalendarService {
    private static List<String> calendars = Collections.synchronizedList(new ArrayList<String>());
    public List<String> getCourseCalendarIds() {
        return Collections.unmodifiableList(calendars);
    }

    public void addCourseCalendarId(String id) {
        calendars.add(id);
    }
}
