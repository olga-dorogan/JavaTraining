package com.custom.controller;

/**
 * Created by olga on 21.05.15.
 */
public class Utils {
    public static String buildHtmlIFrame(String calendarId) {
        StringBuilder sb  = new StringBuilder();
        sb.append("<iframe src=\"https://www.google.com/calendar/embed?src=")
                .append(calendarId)
                .append("&ctz=Europe/Kiev\" " +
                        "style=\"border: 0\" width=\"800\" height=\"600\" frameborder=\"0\" scrolling=\"no\"></iframe>");
        return sb.toString();
    }
    private Utils() {
    }
}
