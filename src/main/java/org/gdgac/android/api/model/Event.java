package org.gdgac.android.api.model;

import org.joda.time.DateTime;

import java.util.ArrayList;

/**
 * GDG Aachen
 * org.gdgac.android.api.model
 * <p/>
 * User: maui
 * Date: 22.04.13
 * Time: 22:44
 */
public class Event implements Response {
    private ArrayList<String> className;
    private DateTime start, end;
    private boolean allDay;
    private String location, title, url, iconUrl, id;

    public Event() {
        className = new ArrayList<String>();
    }

    public ArrayList<String> getClassName() {
        return className;
    }

    public DateTime getStart() {
        return start;
    }

    public DateTime getEnd() {
        return end;
    }

    public boolean isAllDay() {
        return allDay;
    }

    public String getLocation() {
        return location;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getIconUrl() {
        return iconUrl;
    }

    public String getId() {
        return id;
    }
}
