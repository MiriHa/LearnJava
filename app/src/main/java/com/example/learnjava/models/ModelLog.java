package com.example.learnjava.models;

import java.util.Date;

public class ModelLog {

    public class Logging {

        private int loggingID;

        public String userOwnerId;

        private Date time;

        private String eventType;

        private String details;

        public Logging(String userOwnerId, Date time, String eventType, String details) {
            this.userOwnerId = userOwnerId;
            this.time = time;
            this.eventType = eventType;
            this.details = details;

        }

        public Logging() {
        }

        public int getLoggingID() {
            return loggingID;
        }

        public void setLoggingID(int loggingID) {
            this.loggingID = loggingID;
        }

        public Date getTime() {
            return time;
        }

        public void setTime(Date time) {
            this.time = time;
        }

        public String getEventType() {
            return eventType;
        }

        public void setEventType(String eventType) {
            this.eventType = eventType;
        }

        public String getDetails() {
            return details;
        }

        public void setDetails(String details) {
            this.details = details;
        }
    }
}
