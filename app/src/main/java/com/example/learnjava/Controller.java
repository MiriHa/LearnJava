package com.example.learnjava;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;

import com.example.learnjava.models.ModelLog;
import com.example.learnjava.models.ModelTask;
import com.example.learnjava.models.ModelUserProgress;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;
public class Controller extends android.app.Application {


    //Make this class a singelton?
//    Controller progressController = new Controller();

    //    UserDatabase database = UserDatabase.getInstance(this);
    FirebaseAuth auth;
    DatabaseReference ref;

    ModelUserProgress modelUserProgress;

    ArrayList<ModelTask> taskContent;
    ReadJson readJson = new ReadJson();


    /**
     * Methods to acces the Database
     *
     *
     */
    //call only once per user -> in popupwindow where user chooses Id
    //TODO give database as variable
    public void initializeModelUser(ModelUserProgress modelUserProgress) {
    auth = FirebaseAuth.getInstance();
    ref = FirebaseDatabase.getInstance().getReference().child("users");
    Log.i("M_CONTROLLER","InstanzializeModelUserProgress: " + modelUserProgress.getUserId());
    this.modelUserProgress = modelUserProgress;
    }

    public void fetchModelUserProgress() {
        Log.i("M_CONTROLLER","fetchModelUserProgress ");
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference().child("users");

            FirebaseUser firebaseUser = auth.getCurrentUser();
            DatabaseReference currentReference = ref.child("users").child(firebaseUser.getUid());
            currentReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue() != null){
                       modelUserProgress = dataSnapshot.getValue(ModelUserProgress.class);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
    }

    public void updateProgresstoDatabase() {
        Log.i("M_CONTROLLER","UpdateModelUserProgress");

    }

    public void makeaLog(Date time, String eventType, String details) {
        Log.i("M_CONTROLLER","Make a Log "+eventType);
//        ModelLog newLog = new ModelLog(modelUserProgress.getUserId(), time, eventType, details);
//        database.getLoggingDao().insertLog(newLog);

    }


    /**
     * Methods to Update or check on the ModelUserProgress
     */

    public void addFinishedTask(ModelTask task) {
        modelUserProgress.addFinishedTask(task);
//        updateProgresstoDatabase(database);
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
//        updateProgresstoDatabase(database);
    }

    public void updateLatestSection(int sectionNumber){
        Log.i("M_CONTROLLER", "updateLatestSections" + sectionNumber);
        modelUserProgress.setLatestSectionNumber(sectionNumber);
//        updateProgresstoDatabase(database);
    }


    public void updateCurrentSection(int number) {
        modelUserProgress.updateUserProgressCurrentSection(number);
//        updateProgresstoDatabase(database);
        Log.i("M_CONTROLLER", " CurrentSectionNumber " + number);
    }

    public void updateCurrentScreen(int number) {
        Log.i("M_CONTROLLER", " CurrentSreenNumber " + number);
        modelUserProgress.updateUserProgressCurrentScreen(number);
//        updateProgresstoDatabase(database);
    }

    public void updateLatestTaskNumber(int number) {
        Log.i("M_CONTROLLER", "updateLatesTaskNumber " + number);
        modelUserProgress.updateLatestTaskNumber(number);
//        updateProgresstoDatabase(database);
    }


    /**
     * Getter and Setter and also Loading the the task content
     */


    public void setLastLesson(ModelTask lastLesson) {
        Log.i("M_CONTROLLER", "setLastLesson: " + lastLesson.getTaskName() + " " + lastLesson.getTaskNumber());
        modelUserProgress.setLastLesson(lastLesson);
//        updateProgresstoDatabase(database);
    }

    public void setLatestSectionNumber(int sectionNumber){
        modelUserProgress.setLatestSectionNumber(sectionNumber);
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

    public int getLatestSectionNumber(){
        return modelUserProgress.getLatestSectionNumber();
    }


}
