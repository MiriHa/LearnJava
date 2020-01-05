package com.example.learnjava.models;

public class ModelLog {

        private String loggingID;

        //public String userOwnerId;

        //private Date time;
        private String time;

        private String event_Type;

        private String eventDetails;

        public ModelLog(String id, String time, String eventType, String details) {
           // this.userOwnerId = userOwnerId;
            this.loggingID = id;
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

    public String getLoggingID() {
        return loggingID;
    }

    public void setLoggingID(String loggingID) {
        this.loggingID = loggingID;
    }

//    public String getUserOwnerId() {
//        return userOwnerId;
//    }
//
//    public void setUserOwnerId(String userOwnerId) {
//        this.userOwnerId = userOwnerId;
//    }
}

