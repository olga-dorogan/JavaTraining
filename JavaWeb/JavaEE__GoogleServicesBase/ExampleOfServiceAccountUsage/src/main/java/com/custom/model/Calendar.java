package com.custom.model;

/**
 * Created by olga on 21.05.15.
 */
public class Calendar {
    private String id;
    private String summary;
    private String description;

    public Calendar(String id, String summary, String description) {
        this.id = id;
        this.summary = summary;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFrame() {
        StringBuilder sb = new StringBuilder();
        sb.append("<iframe src=\"https://www.google.com/calendar/embed?height=300&amp;wkst=2&amp;bgcolor=%23ffffff&amp;src=")
                .append(id)
                .append("&amp;color=%235F6B02&amp;ctz=Europe%2FKiev\"  style=\" border:solid 1px #777 \"" +
                        " width=\"450\" height=\"300\" frameborder=\"0\" scrolling=\"no\"></iframe>");
        return sb.toString();
    }
}
