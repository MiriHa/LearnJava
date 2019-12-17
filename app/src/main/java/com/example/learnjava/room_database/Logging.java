package com.example.learnjava.room_database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Timestamp;
import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = ModelUserProgress.class,
                                  parentColumns = "userId",
                                  childColumns = "userOwnerId",
                                  onDelete = CASCADE))
public class Logging {

    @PrimaryKey(autoGenerate = true)
    private int loggingID;

    public String userOwnerId;


    @ColumnInfo(name = "Time")
    @TypeConverters({TypeConverter.class})
    private Date timestamp;

    @ColumnInfo(name = "Event")
    private String eventType;

    @ColumnInfo(name = "Details")
    private String details;

    public Logging(String userOwnerId, Date time, String eventType, String details) {
        this.userOwnerId = userOwnerId;
        this.timestamp = time;
        this.eventType = eventType;
        this.details = details;

    }

    public int getLoggingID() {
        return loggingID;
    }

    public void setLoggingID(int loggingID) {
        this.loggingID = loggingID;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
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

//    @Entity
//    public class Movies {
//        @NonNull
//        @PrimaryKey
//        private String movieId;
//        private String movieName;
//
//        public Movies() {
//        }
//
//        public String getMovieId() {
//            return movieId;
//        }
//
//        public void setMovieId(String movieId) {
//            this.movieId = movieId;
//        }
//
//        public String getMovieName() {
//            return movieName;
//        }
//
//        public void setMovieName(String movieName) {
//            this.movieName = movieName;
//        }
//    }
}
