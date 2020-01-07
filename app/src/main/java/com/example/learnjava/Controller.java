package com.example.learnjava;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.learnjava.models.ModelFinishedTask;
import com.example.learnjava.models.ModelLog;
import com.example.learnjava.models.ModelTask;
import com.example.learnjava.models.ModelUserProgress;
import com.example.learnjava.registration.Utils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;

public class Controller extends android.app.Application {



    FirebaseAuth auth;
    DatabaseReference ref;
    String userId;

    ModelUserProgress modelUserProgress;

    ArrayList<ModelTask> taskContent;
    ReadJson readJson = new ReadJson();

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.GERMAN);

    /**
     * Methods to acces the Database
     *
     *
     */
    //call only once per user -> in popupwindow where user chooses Id
    //TODO give database as variable
    public void initializeModelUser(Context ctx, ModelUserProgress modelUserProgress) {
    auth = FirebaseAuth.getInstance();
    ref = FirebaseDatabase.getInstance().getReference();
    userId = auth.getCurrentUser().getUid();

    Utils.saveModelToProgress(ctx, modelUserProgress);
    Log.i("M_CONTROLLER","InstanzializeModelUserProgress: " + modelUserProgress.getUserId());
    this.modelUserProgress = modelUserProgress;
    }

    public void fetchModelUserProgress(Context ctx) {
        Log.i("M_CONTROLLER","fetchModelUserProgress1 ");
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        userId = auth.getCurrentUser().getUid();

       // modelUserProgress = new ModelUserProgress(userId);
        modelUserProgress = Utils.readProgress(ctx);

            FirebaseUser firebaseUser = auth.getCurrentUser();
            DatabaseReference currentReference = ref.child("users").child(firebaseUser.getUid());
            currentReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.getValue() != null){
                       modelUserProgress = dataSnapshot.getValue(ModelUserProgress.class);
                        //ref.child("users").child(userId).setValue(modelUserProgress);
                        Log.i("M_CONTROLLER","fetchModelUserProgress2 :" + modelUserProgress.toString());
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.i("M_CONTROLLER","fetchModelUserProgress cancelled");

                }
            });
    }

    public void updateProgresstoDatabase() {
        Log.i("M_CONTROLLER","UpdateModelUserProgress");
        String userId = auth.getCurrentUser().getUid();
        ref.child("users").child(userId).setValue(modelUserProgress);    }

        public void saveProgresstoPrefrences(Context ctx){
            Utils.saveModelToProgress(ctx,modelUserProgress);
        }

    /**
     * Methods to check on the ModelUserProgress
     */

    public boolean checkTasks(ModelTask aTask) {
        //fetchModelUserProgress();
        ModelFinishedTask finishedTask = new ModelFinishedTask(aTask.getSectionNumber(),aTask.getTaskNumber(), aTask.getTaskName());
        Log.i("M_CONTROLLER", "checkTasks");
        return modelUserProgress.checkTasks(finishedTask);

    }
    public boolean checkUnlockedSections(Integer sectionNumber) {
        Log.i("M_CONTROLLER", "checkUnlockedSections " + sectionNumber);
        return modelUserProgress.checkProgressUnlockedSection(sectionNumber);
    }


    /**
     * Methods to update different values
     *
     */

    public void addFinishedTask(Context ctx, ModelTask task) {
        ModelFinishedTask finishedTask = new ModelFinishedTask(task.getSectionNumber(), task.getTaskNumber(), task.getTaskName());
        modelUserProgress.addFinishedTask(finishedTask);
        //ref.child("users").child(userId).child("finishedTasks").child(task.getTaskName()).setValue(task);
        Utils.saveModelToProgress(ctx, modelUserProgress);
        ref.child("users").child(userId).child("finishedTasksList").child(task.getTaskName() + " " + task.getTaskNumber()).setValue(finishedTask);
        //updateProgresstoDatabase();
        Log.i("M_CONTROLLER", " addfnishedTask " + task.getTaskNumber());
    }

    public void updateLatestSection(Context ctx, int sectionNumber){
        Log.i("M_CONTROLLER", "updateLatestSections " + sectionNumber);
        modelUserProgress.setLatestSectionNumber((long) sectionNumber);
        Utils.saveModelToProgress(ctx, modelUserProgress);
        ref.child("users").child(userId).child("latestSectionNumber").setValue((long) sectionNumber);
       // updateProgresstoDatabase();
    }


    public void updateCurrentSection(Context ctx, int number) {
        modelUserProgress.updateUserProgressCurrentSection(number);
        Utils.saveModelToProgress(ctx, modelUserProgress);
        ref.child("users").child(userId).child("userProgressCurrentSection").setValue((long) number);
       // updateProgresstoDatabase();
        Log.i("M_CONTROLLER", " CurrentSectionNumber " + number);
    }

    public void updateCurrentScreen(Context ctx, int number) {
        Log.i("M_CONTROLLER", " CurrentSreenNumber " + number);
        modelUserProgress.updateUserProgressCurrentScreen(number);
        Utils.saveModelToProgress(ctx, modelUserProgress);
        ref.child("users").child(userId).child("userProgressCurrentScreen").setValue((long) number);
       // updateProgresstoDatabase();
    }

    public void updateLatestTaskNumber(Context ctx, int number) {
        Log.i("M_CONTROLLER", "updateLatesTaskNumber " + number);
        modelUserProgress.updateLatestTaskNumber(number);
        Utils.saveModelToProgress(ctx, modelUserProgress);
        ref.child("users").child(userId).child("latestTaskNumber").setValue((long) number);
       // updateProgresstoDatabase();
    }


    /**
     * Getter and Setter and also Loading the the task content
     */


    public void setLastLesson(Context ctx, int tasknumber) {
        Log.i("M_CONTROLLER", "setLastLesson: " + tasknumber);
       // modelUserProgress.setLastLesson(lastLesson);
        modelUserProgress.setLastLessonNumber(tasknumber);
        Utils.saveModelToProgress(ctx, modelUserProgress);
        ref.child("users").child(userId).child("lastLessonNumber").setValue((long) tasknumber);
       //updateProgresstoDatabase();
    }

    public void setModelUserProgress(ModelUserProgress userProgress){
        Log.i("M_CONTROLLER", "setModelUserProgress");
        this.modelUserProgress = userProgress;
    }


    public long getCurrentSection(Context ctx) {
        Log.i("M_CONTROLLER", "getCurrentSection");
      //  fetchModelUserProgress();
        modelUserProgress = Utils.readProgress(ctx);
        return modelUserProgress.getCurrentSection();
    }

    public int getLastLessonNumber(Context ctx){
        Log.i("M_CONTROLLER", "getLastLessonNumber");
       // fetchModelUserProgress();
        modelUserProgress = Utils.readProgress(ctx);
        return (int) modelUserProgress.getLastLessonNumber();
    }

    public long getLatestTaskNumber(Context ctx) {
        Log.i("M_CONTROLLER", "getLatestTaskNumber");
       // fetchModelUserProgress();
        modelUserProgress = Utils.readProgress(ctx);
        return modelUserProgress.getLatestTaskNumber();
    }

    public long getLatestSectionNumber(Context ctx){
        Log.i("M_CONTROLLER", "getLatestSectionNumber");
        //fetchModelUserProgress();
        modelUserProgress = Utils.readProgress(ctx);
        return modelUserProgress.getLatestSectionNumber();
    }


    /**
     * lead the content
     * @param sectionNumber which section content is needed
     * @param context get the current context
     */
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


    /**
     * Make Logs
     *
     */


//    public Map<String, ModelLog> getLogList(){
//        return modelUserProgress.getLoggingList();
//    }

    public void makeaLog(Date time, String eventType, String details){
        String strDate = dateFormat.format(time);
        String randomID = UUID.randomUUID().toString();
        String logID = "Log " + randomID;
        ModelLog modelLog = new ModelLog(logID, strDate, eventType, details);
        //modelUserProgress.addLog(modelLog);
        ref.child("users").child(userId).child("loggingList").child(eventType +" "+ randomID).setValue(modelLog);

    }


}
