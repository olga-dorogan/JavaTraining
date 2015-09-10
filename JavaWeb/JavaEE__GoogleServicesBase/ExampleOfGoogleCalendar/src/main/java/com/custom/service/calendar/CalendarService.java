package com.custom.service.calendar;

import java.util.List;

/**
 * Created by olga on 21.05.15.
 */
public interface CalendarService {
    List<String> getCourseCalendarIds();

    void addCourseCalendarId(String id);
}
