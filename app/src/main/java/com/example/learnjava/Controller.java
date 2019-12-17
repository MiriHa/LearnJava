package com.example.learnjava;

import android.content.Context;
import android.util.Log;

import com.example.learnjava.models.ModelTask;
import com.example.learnjava.room_database.Logging;
import com.example.learnjava.room_database.ModelUserProgress;
import com.example.learnjava.room_database.UserDatabase;

import java.util.ArrayList;
import java.util.Date;

public class Controller extends android.app.Application {

    //UserDatabase database = UserDatabase.getInstance(this);
    ModelUserProgress modelUserProgress;

    //TODO get das??
    UserDatabase database = UserDatabase.getInstance(this);

    ArrayList<ModelTask> taskContent;
    ReadJson readJson = new ReadJson();


    //call only once per user -> in popupwindow where user chooses Id
    public void initializeModelUserProgress(String userID, UserDatabase database){
        modelUserProgress = new ModelUserProgress(userID);

        database.getUserDao().insertModelUserProgress(modelUserProgress);
    }

    public void updateProgresstoDatabase(UserDatabase database){
        database.getUserDao().updateUserProgress(modelUserProgress);
    }

    public void makeaLog(String userOwnerId, Date time, String eventType, String details){
        Logging newLog = new Logging(userOwnerId, time, eventType, details);
        database.getLoggingDao().insertLog(newLog);

    }

    public  void deleteAllTabels(){
        database.getUserDao().deleteTable();
        database.getLoggingDao().deleteTable();
    }




    public void addFinishedTask(ModelTask task){
        modelUserProgress.addFinishedTask(task);
        Log.i("M_CONTROLLER", " addfnishedTask " + task.getTaskNumber());
    }

    public boolean checkTasks(ModelTask task){
        Log.i("M_CONTROLLER", "checkTasks" + modelUserProgress.checkTasks(task));
       return modelUserProgress.checkTasks(task);

    }




    public boolean checkUnlockedSections(Integer sectionNumber){
        Log.i("M_CONTROLLER","checkUnlockedSections "+sectionNumber);
       return modelUserProgress.checkProgressUnlockedSection(sectionNumber);
    }

    public void updateUnlockedSections(Integer sectionNumber){
        Log.i("M_CONTROLLER","updateUnlockedSections"+sectionNumber);
        modelUserProgress.updateUserProgressUnlockedSections(sectionNumber);
    }


    public void updateCurrentSection(int number){
        modelUserProgress.updateUserProgressCurrentSection(number);
        Log.i("M_CONTROLLER", " CurrentSectionNumber "+number);
    }

    public void updateCurrentScreen(int number){
        Log.i("M_CONTROLLER", " CurrentSreenNumber "+number);
        modelUserProgress.updateUserProgressCurrentScreen(number);
    }

    public void updateLatestTaskNumber(int number){
        Log.i("M_CONTROLLER","updateLatesTaskNumber "+number);
        modelUserProgress.updateLatestTaskNumber(number);
    }


    public void setLastLesson(ModelTask lastLesson){
        Log.i("M_CONTROLLER","setLastLesson: "+lastLesson.getTaskName() +" " + lastLesson.getTaskNumber());
        modelUserProgress.setLastLesson(lastLesson);
    }

    public ModelTask getLastLesson()
    {
        return modelUserProgress.getLastLesson();
    }

    public int getLatestTaskNumber(){
        return modelUserProgress.getLatestTaskNumber();
    }

    public void loadContent(int sectionNumber, Context context){
        String sectionFile = "section" + sectionNumber;
        taskContent =  readJson.readTask(sectionFile, context);
        Log.i("loadContent", " section" + sectionNumber);
        Log.i("M updateUserProgress", "loadContent");
    }

    public ArrayList<ModelTask> getTaskContent(){
        Log.i("M updateUserProgress", "getTaskContent");
        return taskContent;
    }

    public ArrayList<Integer> getSections(){
        return modelUserProgress.getUserProgressUnlockedSections();
    }

    public int getCurrentSection(){
        return modelUserProgress.getCurrentSection();
    }



}
