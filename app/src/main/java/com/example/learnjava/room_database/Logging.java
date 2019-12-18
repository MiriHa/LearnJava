package com.example.learnjava.room_database;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.sql.Timestamp;
import java.util.Date;

import static androidx.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity = ModelUserProgress.class,
                                  parentColumns = "user_ID",
                                  childColumns = "User_Owner_ID",
                                  onDelete = CASCADE),
        indices = {@Index("Log_ID"), @Index("User_Owner_ID")})
public class Logging {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "Log_ID")
    private int loggingID;

    @ColumnInfo(name = "User_Owner_ID")
    public String userOwnerId;


    @ColumnInfo(name = "Time")
    @TypeConverters({TypeConverter.class})
    private Date time;

    @ColumnInfo(name = "Event")
    private String eventType;

    @ColumnInfo(name = "Details")
    private String details;

    public Logging(String userOwnerId, Date time, String eventType, String details) {
        this.userOwnerId = userOwnerId;
        this.time = time;
        this.eventType = eventType;
        this.details = details;

    }

    @Ignore
    public Logging(){}

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
