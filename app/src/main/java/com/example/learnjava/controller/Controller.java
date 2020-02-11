package com.example.learnjava.controller;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;

import com.example.learnjava.models.ModelLog;
import com.example.learnjava.models.ModelQuestion;
import com.example.learnjava.models.ModelTask;
import com.example.learnjava.view_cues.HistoryFragment;
import com.example.learnjava.view_cues.QuestionsFragment;
import com.example.learnjava.view_cues.WordCloudFragment;
import com.example.learnjava.view_cues.WordCueFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
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
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

public class Controller extends android.app.Application {


    //Firebase
    FirebaseDatabase database;
    FirebaseAuth auth;
    DatabaseReference ref;
    String userId;

    //Content
    ArrayList<ModelTask> taskContent;
    ReadJson readJson = new ReadJson();

    DateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm:ss,SSS", Locale.GERMAN);

    /**
     * Methods to save and read Progress
     *
     */

    public void initializeModelUser(Context con, String username, String email) {
        database = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        userId = auth.getCurrentUser().getUid();

        SharedPrefrencesManager.saveUserName(con, username);
        SharedPrefrencesManager.saveEMail(con, email);
        SharedPrefrencesManager.saveLatestSectionNumber(con, 1);
        SharedPrefrencesManager.setTrigger(con, false, 0);

        Log.i("M_CONTROLLER","InstanzializeModelUserProgress: " + userId);
    }

    public void setFirebase() {
        Log.i("M_CONTROLLER","setFirebase");
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        userId = auth.getCurrentUser().getUid();
    }

    //Fetch the Progress form Firebase if the users has logged in
    public void fetchProgressFromFireBase(final Context con){
        Log.i("M_CONTROLLER","fetchProgressfromFirebase");
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        userId = auth.getCurrentUser().getUid();

        FirebaseUser firebaseUser = auth.getCurrentUser();
        DatabaseReference currentReference = ref.child("users").child(firebaseUser.getUid());
        currentReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getValue() != null){
                    Integer latestSectionNumber = dataSnapshot.child("latestSectionNumber").getValue(Integer.class);
                    Integer latestTaskNumber = dataSnapshot.child("latestTaskNumber").getValue(Integer.class);
                    SharedPrefrencesManager.saveLatestSectionNumber(con, latestSectionNumber);
                    SharedPrefrencesManager.savelatestTaskNumber(con, latestTaskNumber);
                    Log.i("M_CONTROLLER","fetchProgressFromFireBase: section :"+ latestSectionNumber+ " latestTAsk: "+latestTaskNumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.i("M_CONTROLLER","fetchProgressFromFireBase cancelled");

            }
        });

    }


    /**
     * Check  if a task was already completed
     * @param con context for shared preferences
     * @param aTask wich task should be checked
     * @return if the task was already completed or not
     */
    public boolean checkTasks(Context con, ModelTask aTask) {

        int latestSection = SharedPrefrencesManager.readLatestSectionNumber(con);
        int latestTask = SharedPrefrencesManager.readLatestTaskNumber(con);

        Log.i("M_CONTROLLER","checkTasks " + "task: " + aTask.getTaskNumber() + " latest task: "+latestTask+" section: " +aTask.getSectionNumber() + " latestSection "+latestSection);

        if(aTask.getSectionNumber() == latestSection && aTask.getTaskNumber() < latestTask){
            return true;
        }
        else return aTask.getSectionNumber() < latestSection;
    }



    /**
     * Methods to update different values
     *
     */

    public void updateCurrentSection(Context con, int number) {
        SharedPrefrencesManager.saveCurrentSection(con,number );
        ref.child("users").child(userId).child("userProgressCurrentSection").setValue((long) number);
        Log.i("M_CONTROLLER", "update CurrentSectionNumber " + number);
    }

    public void updateCurrentScreen(Context con, int number) {
        Log.i("M_CONTROLLER", " update CurrentSreenNumber " + number);
        SharedPrefrencesManager.saveCurrentScreen(con, number);
        ref.child("users").child(userId).child("userProgressCurrentScreen").setValue((long) number);

    }

    public void updateLatestTaskNumber(Context con, int number, int currentSection) {
        Log.i("M_CONTROLLER", "updateLatesTaskNumber not in latestSection");
        if(currentSection == SharedPrefrencesManager.readLatestSectionNumber(this)){
            Log.i("M_CONTROLLER", "updateLatesTaskNumber " + number+" currentSection: "+currentSection+" latestSection: "+SharedPrefrencesManager.readLatestSectionNumber(this));
            SharedPrefrencesManager.savelatestTaskNumber(con, number);
            ref.child("users").child(userId).child("latestTaskNumber").setValue((long) number);
        }

    }

    public void updateLatestSection(Context con, int sectionNumber){
        Log.i("M_CONTROLLER", "updateLatestSections " + sectionNumber);
        if(SharedPrefrencesManager.readLatestSectionNumber(con) == sectionNumber) {
            SharedPrefrencesManager.saveLatestSectionNumber(con, sectionNumber + 1);
            ref.child("users").child(userId).child("latestSectionNumber").setValue((long) sectionNumber);
        }

    }


    /**
     * Getter and Setter
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
     * load the content
     * @param sectionNumber which section content is needed
     * @param context get the current context
     */
    public void loadContent(int sectionNumber, Context context) {
        String sectionFile = "section" + sectionNumber;
        taskContent = readJson.readTask(sectionFile, context);
        Log.i("loadContent", " section" + sectionNumber);
    }


    /**
     * get the taskcontent or only the lessonst
     */
    public ArrayList<ModelTask> getTaskContent() {
        Log.i("M updateUserProgress", "getTaskContent");
        return taskContent;
    }

    public ArrayList<ModelTask> getOnlyLessons(){
        ArrayList<ModelTask> lessonNames = new ArrayList<>();
        for(int i=0; i<taskContent.size(); i++){
            if(taskContent.get(i).getType()==1){
                lessonNames.add(taskContent.get(i));
            }
        }
        return lessonNames;
    }

    /**
     * get the Questions for the question cue
     * @param con context for shared preferences
     * @param sectionWhat for which section
     * @return all questions for the section
     */
    public ArrayList<ModelQuestion> getQuestions(Context con, String sectionWhat){
        return readJson.readQuestions(con, sectionWhat);

    }

    /**
     * open the right resumption Cue
     * @param section which is the currentSection
     * which cue is needed: 1 WORD-CUE, 2: WORD-CLOUD, 3: HISTORY, 4: QUESTIONS
     */
    public void showCue(Context con, int section, FragmentManager fm) {
        if(SharedPrefrencesManager.readTrigger(con) && !(Boolean.valueOf(SharedPrefrencesManager.readSharedSetting(con, "CUE_OPEN","false")))) {

            //0: false, 1 screen was dark, 2 app has restarted
            int why = SharedPrefrencesManager.readTriggerWhy(con);
            // determite which cue should be shown:
            int whichCue = 1;
//            if(why == 1) {
//                if (section <= 4)
//                    whichCue = 1;
//                else
//                    whichCue = 4;
//            }
//            else if(why == 2){
//                if (section <= 4)
//                    whichCue = 2;
//                else
//                    whichCue = 3;
//            }else {
//                whichCue = 0;
//            }
            Random random = new Random();
            switch (section){
                case 1:
                case 3:
                case 5:
                case 7:
                    whichCue = (random.nextInt(3 - 1) + 1);
                    break;
                case 2:
                case 4:
                case 6:
                case 8:
                    whichCue = (random.nextInt(5 - 3 ) + 3);
                    break;
            }

            Log.i("M_TRIGGER_CUES", "which cue: " +whichCue + " why: "+why);

            SharedPrefrencesManager.setTrigger(con, false, 0);
            Log.i("M_TRIGGER_CUES", "set Trigger in showCue: false");

            switch (whichCue) {
                case 0:
                    Log.i("M_TRIGGER_CUES","Trigger WHy was 0");
                    break;
                case 1:
                    WordCueFragment wordCueFragment = WordCueFragment.newIntance(section);
                    wordCueFragment.setCancelable(false);
                    // wordCueFragment.getDialog().setCanceledOnTouchOutside(false);
                    wordCueFragment.show(fm, "fragment_word_cue");
                    makeaLog(con, Calendar.getInstance().getTime(), "OPENED_A_CUE", "WordCue, why: "+why);
                    Log.i("M_TRIGGER_CUES", "show Word Cue " + section);
                    break;
                case 2:
                    WordCloudFragment wordCloudFragment = WordCloudFragment.newInstance(section);
                    wordCloudFragment.setCancelable(false);
                    // wordCloudFragment.getDialog().setCanceledOnTouchOutside(false);
                    wordCloudFragment.show(fm, "fragment_cloud_cue");
                    makeaLog(con, Calendar.getInstance().getTime(), "OPENED_A_CUE", "CloudCue, why: "+why);
                    Log.i("M_TRIGGER_CUES", "show Word Cloud Cue " + " " + section);
                    break;
                case 3:
                    HistoryFragment historyFragment = HistoryFragment.newInstance(section);
                    historyFragment.setCancelable(false);
                    // historyFragment.getDialog().setCanceledOnTouchOutside(false);
                    historyFragment.show(fm, "fragment_history_cue");
                    makeaLog(con, Calendar.getInstance().getTime(), "OPENED_A_CUE", "HistoryCue, why: "+why);
                    Log.i("M_TRIGGER_CUES", "show History Cue " + " " + section);
                    break;
                case 4:
                    QuestionsFragment questionsFragment = QuestionsFragment.newInstance(section);
                    questionsFragment.setCancelable(false);
                    questionsFragment.show(fm, "fragment_question_cue");
                    makeaLog(con, Calendar.getInstance().getTime(), "OPENED_A_CUE", "questionCue, why: "+why);
                    Log.i("M_TRIGGER_CUES", "show Question Cue " + " " + section);
                    break;

            }
        }else{
            Log.i("M_TRIGGER_CUES", "show no Cue");
        }
    }


    /**
     * Make a Logs and save it in firebase
     *
     */
    public void makeaLog(Context con, Date time, String eventType, String details){
        String strDate = dateFormat.format(time);
        ModelLog modelLog = new ModelLog( strDate, eventType, details);
        SharedPrefrencesManager.saveLogs(con, modelLog);
        ref.child("users").child(userId).child("loggingList").child(strDate + " " + eventType).setValue(modelLog);

    }

    public void makeaDurationLog(Context con, Date time, String eventType, String details, String duration){
        String strDate = dateFormat.format(time);
        ModelLog modelLog = new ModelLog( strDate, eventType, details, duration);
        SharedPrefrencesManager.saveLogs(con, modelLog);
        ref.child("users").child(userId).child("loggingList").child(strDate + " " + eventType).setValue(modelLog);
    }

    public String calculateDuration(Date dateStart, Date dateEnd){
        long diff = dateEnd.getTime() - dateStart.getTime();

        long seconds = diff / 1000;
        long minutes = seconds /60;

        String duration = minutes+":"+(seconds-(60*minutes))+":"+(diff-(1000*seconds));

//        long diffSeconds = diff / 1000 % 60;
//        long diffMinutes = diff / (1000*60) % 60;
//        long diffHours = diff / (60*60*100) % 24;
//
//        String dateDuration = diffHours+":"+diffMinutes+":"+diffSeconds;
//        long minutes = TimeUnit.MILLISECONDS.toMinutes(milliseconds);
//        // long seconds = (milliseconds / 1000);
//        long seconds = TimeUnit.MILLISECONDS.toSeconds(milliseconds);

        Log.i("M_DURATION","Duration: "+diff+ " string: "+ duration);


        return duration;

    }

    public void pushLogs(Context con){
        ArrayList<ModelLog> logs = SharedPrefrencesManager.readLogs(con);
        Log.i("M_CONTROLLER","pushLogs");
        for(ModelLog log : logs) {
            Log.i("M_CONTROLLER","pushlog:"+log.getEventType());
            ref.child("users").child(userId).child("loggingList").child(log.getTime() + " " + log.getEventType()).setValue(log).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        SharedPrefrencesManager.deleteLogs(con);
                    } else {
                        Log.i("M_CONTROLLER","pushlogs not succesfull "+ task.getException().getMessage());
                    }
                }
            });
        }
    }


}