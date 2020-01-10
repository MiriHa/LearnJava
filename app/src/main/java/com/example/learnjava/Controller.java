package com.example.learnjava;

import android.content.Context;
import android.util.Log;

import com.example.learnjava.models.ModelLog;
import com.example.learnjava.models.ModelQuestion;
import com.example.learnjava.models.ModelTask;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
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
      //  List<ModelFinishedTask> tasks = new ArrayList<>();

        SharedPrefrencesManager.saveUserName(con, username);
        SharedPrefrencesManager.saveEMail(con, email);
        SharedPrefrencesManager.saveLatestSectionNumber(con, 1);

        Log.i("M_CONTROLLER","InstanzializeModelUserProgress: " + userId);


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

//        ModelFinishedTask finishedTask = new ModelFinishedTask(aTask.getSectionNumber(),aTask.getTaskNumber(), aTask.getTaskName());
//        List<ModelFinishedTask> tasks = SharedPrefrencesManager.readfinishedTasks(con);
//        Log.i("M_CONTROLLER", "checkTasks " + printtasks(tasks) + " " + tasks.contains(finishedTask));
////        return modelUserProgress.checkTasks(finishedTask);
//        return tasks.contains(finishedTask);
        int latestSection = SharedPrefrencesManager.readLatestSectionNumber(con);
        int latestTask = SharedPrefrencesManager.readLatestTaskNumber(con);

        Log.i("M_CONTROLLER","checkTasks " + "task: " + aTask.getTaskNumber() + " section: " +aTask.getSectionNumber());

        if(aTask.getSectionNumber() == latestSection && aTask.getTaskNumber() <= latestTask){
            return true;
        }
        else return aTask.getSectionNumber() < latestSection;
//        boolean check = aTask.getTaskNumber() <= latestTask && aTask.getSectionNumber() <= latestSection;
    }



    /**
     * Methods to update different values
     *
     */

    public void addFinishedTask(Context con, ModelTask task) {
       // ModelFinishedTask finishedTask = new ModelFinishedTask(task.getSectionNumber(), task.getTaskNumber(), task.getTaskName());

        //modelUserProgress.addFinishedTask(finishedTask);
       // SharedPrefrencesManager.addfinishedTask(con, finishedTask);
        //ref.child("users").child(userId).child("finishedTasksList").child("Section: " + task.getSectionNumber() + " Task: " + task.getTaskNumber()).setValue(finishedTask);
        //updateProgresstoDatabase();
        Log.i("M_CONTROLLER", " addfnishedTask nothing happeing here " + task.getTaskNumber());
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

    public void updateLatestTaskNumber(Context con, int number, int currentSection) {
        Log.i("M_CONTROLLER", "updateLatesTaskNumber " + number);
        //modelUserProgress.updateLatestTaskNumber(number);
        if(currentSection == SharedPrefrencesManager.readLatestSectionNumber(this)){
            SharedPrefrencesManager.savelatestTaskNumber(con, number);
            ref.child("users").child(userId).child("latestTaskNumber").setValue((long) number);
        }

    }

    public void updateLatestSection(Context con, int sectionNumber){
        Log.i("M_CONTROLLER", "updateLatestSections " + sectionNumber);
        //modelUserProgress.setLatestSectionNumber((long) sectionNumber);
        if(SharedPrefrencesManager.readLatestSectionNumber(con) == sectionNumber) {
            SharedPrefrencesManager.saveLatestSectionNumber(con, sectionNumber + 1);
            ref.child("users").child(userId).child("latestSectionNumber").setValue((long) sectionNumber);
        }

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
        int section = SharedPrefrencesManager.readLastLesson(con);
        Log.i("M_CONTROLLER", "getLastLessonNumber " +section);
       // return (int) modelUserProgress.getLastLessonNumber();
        return section;
    }

    public int getLatestTaskNumber(Context con) {
        int task = SharedPrefrencesManager.readLatestTaskNumber(con);
        Log.i("M_CONTROLLER", "getLatestTaskNumber "+task);
//        return modelUserProgress.getLatestTaskNumber();
        return task;
    }

    public int getLatestSectionNumber(Context con){
        int section = SharedPrefrencesManager.readLatestSectionNumber(con);
        Log.i("M_CONTROLLER", "getLatestSectionNumber " +section);
//        return modelUserProgress.getLatestSectionNumber();
        return section;
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

    public ArrayList<ModelQuestion> getQuestions(Context con, String sectionWhat){

        return readJson.readQuestions(con, sectionWhat);
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
