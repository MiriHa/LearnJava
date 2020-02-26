package com.lmu.learnjava.models;

/**
 * Model for saving a Log to Firebase
 */
public class ModelLog {

    private String time;

    private String event_Type;

    private String eventDetails;

    private String duration;

    public ModelLog( String time, String eventType, String details) {
        this.time = time;
        this.event_Type = eventType;
        this.eventDetails = details;
        duration = "0";

    }

    public ModelLog( String time, String eventType, String details, String duration) {
        this.time = time;
        this.event_Type = eventType;
        this.eventDetails = details;
        this.duration = duration;

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

    public void setEventType(String event_Type) {
        this.event_Type = event_Type;
    }

    public String getEventDetails() {
        return eventDetails;
    }

    public void setEventDetails(String eventDetails) {
        this.eventDetails = eventDetails;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}

