package com.example.learnjava.models;

/**
 * Model for saving a Log to Firebase
 */
public class ModelLog {

    private String time;

    private String event_Type;

    private String eventDetails;

    public ModelLog( String time, String eventType, String details) {
        this.time = time;
        this.event_Type = eventType;
        this.eventDetails = details;

    }

    public ModelLog() {
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getEventType() {
        return event_Type;
    }

    public void setEventType(String eventType) {
        this.event_Type = eventType;
    }

    public String getDetails() {
        return eventDetails;
    }

    public void setDetails(String details) {
        this.eventDetails = details;
    }
}

