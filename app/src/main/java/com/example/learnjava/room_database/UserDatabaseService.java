package com.example.learnjava.room_database;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import androidx.room.Room;

import com.example.learnjava.models.ModelTask;

public class UserDatabaseService {

//    private static final String DB_NAME = "PROGRESS_DB";
//    private static UserDatabase instance;
//
//    public UserDatabaseService(Context context){
//        if(instance == null) {
//            //TODO applicationcontext???
//            Log.i("M_USER_DATABASE", "Instanziate the database");
//            instance = Room.databaseBuilder(context, UserDatabase.class, DB_NAME)
//                    .fallbackToDestructiveMigration()
//                    .build();
//        }
//    }
//
//    public ModelUserProgress getModelUserProgress(String userID){
//        return new GetUsersAsyncTask().e
//    }
//
//
//    private class GetUserAsyncTask extends AsyncTask<Void, Void, ModelUserProgress>{
//
//        @Override
//        protected Void doInBackground(Void... url){
//            return instance.getUserDao().getModelUserProgress(userID)
//        }
//    }
}
