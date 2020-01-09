package com.example.learnjava;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.learnjava.models.ModelFinishedTask;
import com.example.learnjava.models.ModelLog;
import com.example.learnjava.models.ModelTask;
import com.example.learnjava.models.ModelUserProgress;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class Controller extends android.app.Application {


    FirebaseDatabase database;
    FirebaseAuth auth;
    DatabaseReference ref;
    String userId;

    //ModelUserProgress modelUserProgress;

    ArrayList<ModelTask> taskContent;
    ReadJson readJson = new ReadJson();

    DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.GERMAN);

    /**
     * Methods to save and read Progress
     *
     *
     */

    public void initializeModelUser(Context con, String username, String email) {
        database = FirebaseDatabase.getInstance();
        //database.setPersistenceEnabled(true);
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        userId = auth.getCurrentUser().getUid();
        List<ModelFinishedTask> tasks = new ArrayList<>();

        SharedPrefrencesManager.saveUserName(con, username);
        SharedPrefrencesManager.saveEMail(con, email);
        SharedPrefrencesManager.saveLatestSectionNumber(con, 1);
        SharedPrefrencesManager.savefinishedTasks(con, tasks);

        Log.i("M_CONTROLLER","InstanzializeModelUserProgress: " + userId);
//    this.modelUserProgress = modelUserProgress;



    }

    public void fetchModelUserProgress() {
        Log.i("M_CONTROLLER","fetchModelUserProgress1 ");
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        userId = auth.getCurrentUser().getUid();

//        modelUserProgress = new ModelUserProgress(userId);
//
//            FirebaseUser firebaseUser = auth.getCurrentUser();
//            DatabaseReference currentReference = ref.child("users").child(firebaseUser.getUid());
//            currentReference.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    if(dataSnapshot.getValue() != null){
//                       modelUserProgress = dataSnapshot.getValue(ModelUserProgress.class);
//                        //ref.child("users").child(userId).setValue(modelUserProgress);
//                        Log.i("M_CONTROLLER","fetchModelUserProgress2 :" + modelUserProgress.toString());
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    Log.i("M_CONTROLLER","fetchModelUserProgress cancelled");
//
//                }
//            });
    }

    /**
     * Methods to check on the ModelUserProgress
     */

    public boolean checkTasks(Context con, ModelTask aTask) {
        //fetchModelUserProgress();
        ModelFinishedTask finishedTask = new ModelFinishedTask(aTask.getSectionNumber(),aTask.getTaskNumber(), aTask.getTaskName());
        List<ModelFinishedTask> tasks = SharedPrefrencesManager.readfinishedTasks(con);
        Log.i("M_CONTROLLER", "checkTasks " + printtasks(tasks) + " " + tasks.contains(finishedTask));
//        return modelUserProgress.checkTasks(finishedTask);
        return tasks.contains(finishedTask);

    }

    public String[] printtasks(List<ModelFinishedTask> tasks){
        String[] taskslist = new String[tasks.size()];

        for(int i =0; i<tasks.size();i++){
            taskslist[i] = tasks.get(i).getTaskName();
        }
        return taskslist;
    }


    /**
     * Methods to update different values
     *
     */

    public void addFinishedTask(Context con, ModelTask task) {
        ModelFinishedTask finishedTask = new ModelFinishedTask(task.getSectionNumber(), task.getTaskNumber(), task.getTaskName());

        //modelUserProgress.addFinishedTask(finishedTask);
        SharedPrefrencesManager.addfinishedTask(con, finishedTask);
        //ref.child("users").child(userId).child("finishedTasksList").child("Section: " + task.getSectionNumber() + " Task: " + task.getTaskNumber()).setValue(finishedTask);
        //updateProgresstoDatabase();
        Log.i("M_CONTROLLER", " addfnishedTask " + task.getTaskNumber());
    }

    public void updateLatestSection(Context con, int sectionNumber){
        Log.i("M_CONTROLLER", "updateLatestSections " + sectionNumber);
        //modelUserProgress.setLatestSectionNumber((long) sectionNumber);
        SharedPrefrencesManager.saveLatestSectionNumber(con, sectionNumber);
        ref.child("users").child(userId).child("latestSectionNumber").setValue((long) sectionNumber);

    }


    public void updateCurrentSection(Context con, int number) {
        //modelUserProgress.updateUserProgressCurrentSection(number);
        SharedPrefrencesManager.saveCurrentSection(con,number );
        ref.child("users").child(userId).child("userProgressCurrentSection").setValue((long) number);
       // updateProgresstoDatabase();
        Log.i("M_CONTROLLER", "update CurrentSectionNumber " + number);
    }

    public void updateCurrentScreen(Context con, int number) {
        Log.i("M_CONTROLLER", " update CurrentSreenNumber " + number);
        //modelUserProgress.updateUserProgressCurrentScreen(number);
        SharedPrefrencesManager.saveCurrentScreen(con, number);
        ref.child("users").child(userId).child("userProgressCurrentScreen").setValue((long) number);

    }

    public void updateLatestTaskNumber(Context con, int number) {
        Log.i("M_CONTROLLER", "updateLatesTaskNumber " + number);
        //modelUserProgress.updateLatestTaskNumber(number);
        SharedPrefrencesManager.savelatestTaskNumber(con, number);
        ref.child("users").child(userId).child("latestTaskNumber").setValue((long) number);

    }


    /**
     * Getter and Setter and also Loading the the task content
     */


    public void setLastLesson(Context con, int tasknumber) {
        Log.i("M_CONTROLLER", "setLastLesson: " + tasknumber);
       // modelUserProgress.setLastLessonNumber(tasknumber);
        SharedPrefrencesManager.saveLastLesson(con, tasknumber);
        ref.child("users").child(userId).child("lastLessonNumber").setValue((long) tasknumber);

    }


    public int getCurrentSection(Context con) {
        Log.i("M_CONTROLLER", "getCurrentSection");
        //return modelUserProgress.getCurrentSection();
        return SharedPrefrencesManager.readCurrentSection(con);
    }

    public int getLastLessonNumber(Context con){
        Log.i("M_CONTROLLER", "getLastLessonNumber");
       // return (int) modelUserProgress.getLastLessonNumber();
        return SharedPrefrencesManager.readLastLesson(con);
    }

    public int getLatestTaskNumber(Context con) {
        Log.i("M_CONTROLLER", "getLatestTaskNumber");
//        return modelUserProgress.getLatestTaskNumber();
        return SharedPrefrencesManager.readLatestTaskNumber(con);
    }

    public int getLatestSectionNumber(Context con){
        Log.i("M_CONTROLLER", "getLatestSectionNumber");
//        return modelUserProgress.getLatestSectionNumber();
        return SharedPrefrencesManager.readLatestSectionNumber(con);
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


    public void makeaLog(Date time, String eventType, String details){
        String strDate = dateFormat.format(time);
        String randomID = UUID.randomUUID().toString();
        String logID = "Log " + randomID;
        ModelLog modelLog = new ModelLog(logID, strDate, eventType, details);
        //modelUserProgress.addLog(modelLog);
        ref.child("users").child(userId).child("loggingList").child(eventType +" "+ randomID).setValue(modelLog);

    }


}
