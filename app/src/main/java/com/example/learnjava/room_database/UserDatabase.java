package com.example.learnjava.room_database;


import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Logging.class, ModelUserProgress.class}, version = 1)
@TypeConverters({TypeConverter.class})
public abstract class UserDatabase extends RoomDatabase {

    private static final String DB_NAME = "logging_db";
    private static UserDatabase instance;

    public DaoLogging loggingDao;
    public DaoUserProgress userDao;

    public static synchronized UserDatabase getInstance(Context context){
        if(instance == null){
            //TODO applicationcontext???
            instance = Room.databaseBuilder(context.getApplicationContext(), UserDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract DaoLogging getLoggingDao();
    public abstract DaoUserProgress getUserDao();
//
//    public void clearDb() {
//        if (instance != null) {
//            new PopulateDbAsync(instance).execute();
//        }
//    }
}
