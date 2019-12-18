package com.example.learnjava;

import android.content.Context;
import android.icu.lang.UScript;
import android.util.Log;

import com.example.learnjava.models.ModelTask;
import com.example.learnjava.room_database.DaoUserProgress;
import com.example.learnjava.room_database.Logging;
import com.example.learnjava.room_database.ModelUserProgress;
import com.example.learnjava.room_database.UserDatabase;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Controller extends android.app.Application {


    //Make this class a singelton?
//    Controller progressController = new Controller();

    //    UserDatabase database = UserDatabase.getInstance(this);
    ModelUserProgress modelUserProgress;

    ArrayList<ModelTask> taskContent;
    ReadJson readJson = new ReadJson();


    /**
     * Methods to acces the Database
     *
     * @param userID: The Id the user choose, saved in Shared prefrences
     */
    //call only once per user -> in popupwindow where user chooses Id
    //TODO give database as variable
    public void initializeModelUserProgress(String userID, UserDatabase database) {

        Log.i("M_CONTROLLER","InstanzializeModelUserProgress: " + userID);
                modelUserProgress = new ModelUserProgress(userID);
                database.getUserDao().insertModelUserProgress(modelUserProgress);
    }

    public void fetchModelUserProgress(final String userID, UserDatabase database) {
        Log.i("M_CONTROLLER","fetchModelUserProgress: " + userID);
                modelUserProgress = database.getUserDao().getModelUserProgress(userID);
    }

    public void updateProgresstoDatabase(final UserDatabase database) {
        Log.i("M_CONTROLLER","UpdateModelUserProgress");
        database.getUserDao().updateUserProgress(modelUserProgress);

    }

    public void makeaLog(Date time, String eventType, String details, UserDatabase database) {
        Log.i("M_CONTROLLER","Make a Log "+eventType);
        Logging newLog = new Logging(modelUserProgress.getUserId(), time, eventType, details);
        database.getLoggingDao().insertLog(newLog);

    }

    public void deleteAllTabels(UserDatabase database) {
        Log.i("M_CONTROLLER","Delte all tabels");
        database.getUserDao().deleteTable();
        database.getLoggingDao().deleteTable();
    }


    /**
     * Methods to Update or check on the ModelUserProgress
     */

    public void addFinishedTask(ModelTask task) {
        modelUserProgress.addFinishedTask(task);
        Log.i("M_CONTROLLER", " addfnishedTask " + task.getTaskNumber());
    }

    public boolean checkTasks(ModelTask task) {
        Log.i("M_CONTROLLER", "checkTasks" + modelUserProgress.checkTasks(task));
        return modelUserProgress.checkTasks(task);

    }

    public boolean checkUnlockedSections(Integer sectionNumber) {
        Log.i("M_CONTROLLER", "checkUnlockedSections " + sectionNumber);
        return modelUserProgress.checkProgressUnlockedSection(sectionNumber);
    }

    public void updateUnlockedSections(Integer sectionNumber) {
        Log.i("M_CONTROLLER", "updateUnlockedSections" + sectionNumber);
        modelUserProgress.updateUserProgressUnlockedSections(sectionNumber);
    }


    public void updateCurrentSection(int number) {
        modelUserProgress.updateUserProgressCurrentSection(number);
        Log.i("M_CONTROLLER", " CurrentSectionNumber " + number);
    }

    public void updateCurrentScreen(int number) {
        Log.i("M_CONTROLLER", " CurrentSreenNumber " + number);
        modelUserProgress.updateUserProgressCurrentScreen(number);
    }

    public void updateLatestTaskNumber(int number) {
        Log.i("M_CONTROLLER", "updateLatesTaskNumber " + number);
        modelUserProgress.updateLatestTaskNumber(number);
    }


    /**
     * Getter and Setter and also Loading the the task content
     */


    public void setLastLesson(ModelTask lastLesson) {
        Log.i("M_CONTROLLER", "setLastLesson: " + lastLesson.getTaskName() + " " + lastLesson.getTaskNumber());
        modelUserProgress.setLastLesson(lastLesson);
    }

    public void setModelUserProgress(ModelUserProgress userProgress){
        this.modelUserProgress = userProgress;
    }

    public void loadContent(int sectionNumber, Context context) {
        String sectionFile = "section" + sectionNumber;
        taskContent = readJson.readTask(sectionFile, context);
        Log.i("loadContent", " section" + sectionNumber);
        Log.i("M updateUserProgress", "loadContent");
    }

    public ArrayList<ModelTask> getTaskContent() {
        Log.i("M updateUserProgress", "getTaskContent");
        return taskContent;
    }

    public ArrayList<Integer> getSections() {
        return modelUserProgress.getUserProgressUnlockedSections();
    }

    public int getCurrentSection() {
        return modelUserProgress.getCurrentSection();
    }

    public ModelTask getLastLesson() {
        return modelUserProgress.getLastLesson();
    }

    public int getLatestTaskNumber() {
        return modelUserProgress.getLatestTaskNumber();
    }


}
