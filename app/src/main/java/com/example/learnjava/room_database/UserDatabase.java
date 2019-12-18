package com.example.learnjava.room_database;


import android.content.Context;
import android.util.Log;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Logging.class, ModelUserProgress.class}, version = 1, exportSchema = false)
@TypeConverters({TypeConverter.class})
public abstract class UserDatabase extends RoomDatabase {

    private static final String DB_NAME = "PROGRESS_DB";
    private static UserDatabase instance;

    public DaoLogging loggingDao;
    public DaoUserProgress userDao;

    public static synchronized UserDatabase getInstance(Context context){
        if(instance == null){
            Log.i("M_USER_DATABASE","Instanziate the database");
            instance = Room.databaseBuilder(context, UserDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    //TODO not very practical
                    .allowMainThreadQueries()
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
