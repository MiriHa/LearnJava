package com.example.learnjava.models;

import android.util.Log;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This model tracks the progress of a user and contains all user informations.
 * The App has seven sections.
 * Each Sections has various tasks either a theory lessons or Exercises.
 */

//@IgnoreExtraProperties
public class ModelUserProgress {

    private String userId;
    private String userName;
    private String email;

    //Keeps track over the current Section and the current Screen the user is on
    private long userProgressCurrentSection;
    private long userProgressCurrentScreen;

    //latestTaskNumber: when in latest Section jump to latestTask
    private long latestTaskNumber;
    //latestSectionNumber: used for checking unlocked Sections
    private long latestSectionNumber = 1;

    //TODO is note saved? also nicht benötig zum saven da nur temporär? not needed?
//    @Exclude
//    private ModelTask lastLesson;

    //stores the last theorey lesson, so the the user can skip back
    private long lastLessonNumber;

    //stores the solved exercises
    //TODO Make this also Integer and give each task a unique number
    //private List<ModelFinishedTask> finishedTasksList = new ArrayList<>();
    private Map<String, ModelFinishedTask> finishedTasksList = new HashMap<>();


    //Stores the Logs
   // private ArrayList<Float> finishedTasksNumbers = new ArrayList<>();
   // private Map<String, ModelLog> loggingList = new HashMap<>();



    public ModelUserProgress(String userId, String userName, String email){
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        //userUnlockedSections.add(1);
        latestSectionNumber = 1;

    }

    public ModelUserProgress(String userId, String userName, String email, int latestTaskNumber, int latestSectionNumber){
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.latestSectionNumber = latestSectionNumber;
        this.latestTaskNumber = latestTaskNumber;

        //this.finishedTasksList = finishedTaskList;
    }

    public ModelUserProgress(String userId){
        this.userId = userName;
    }


    public ModelUserProgress(){
    }


    /**
     * Update and Set Methods
     *
     */

    public void updateUserProgressCurrentSection(int number){
        this.userProgressCurrentSection = (long) number;
    }

    public void updateUserProgressCurrentScreen(int userProgressCurrentScreen) {
        this.userProgressCurrentScreen = userProgressCurrentScreen;
    }

    public void updateLatestTaskNumber(int tasknumber){
        this.latestTaskNumber = (long) tasknumber;
    }

//    public void setLastLesson(ModelTask lastLesson){
//        this.lastLesson = lastLesson;
//    }

    public void setLastLessonNumber(int number){
        this.lastLessonNumber = (long) number;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    //add a finishedTask bzw exercise so that the user can skip it
    public void addFinishedTask(ModelFinishedTask task){
        if(!checkTasks(task)){
           // finishedTasksList.add(task);
            String id = task.getSectionNumber()+"."+task.getTaskNumber();
            finishedTasksList.put(id,task);
        }
    }


    /**
     * Methods to check on UserProgress
     */


    //check if task was already read/exercise was solved
    public boolean checkTasks(ModelFinishedTask aTask){
        Log.i("M_MODELUSERPROGRESS","checktasks "+aTask.getTaskNumber() + " " + finishedTasksList.containsValue(aTask));
        String id = aTask.getSectionNumber()+"."+aTask.getTaskNumber();
        return finishedTasksList.containsKey(id);
        //return finishedTasksList.containsValue(aTask);
    }

    //check if a section is already unlocked
    public boolean checkProgressUnlockedSection(Integer sectionNumber){
        return (latestSectionNumber >= sectionNumber);
    }


    /**
     * Getter
     *
     */

    public String getUserId(){
        return userId;
    }

    public long getLatestTaskNumber(){
        return latestTaskNumber;
    }

    public long getUserProgressCurrentScreen() {
        return userProgressCurrentScreen;
    }


//    public ModelTask getLastLesson(){
//        return lastLesson;
//    }

    public long getLastLessonNumber(){
        return lastLessonNumber;
    }

    public long getLatestSectionNumber(){
        return latestSectionNumber;
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

//    public List<ModelFinishedTask> getFinishedTasksList() {
//        return finishedTasksList;
//    }

    public Map<String, ModelFinishedTask> getFinishedTasksList() {
        return finishedTasksList;
    }

//    public List<ModelTask> getFinishedTasksList(){
//        return finishedTasksList;
//    }

    public long getCurrentSection(){
        return userProgressCurrentSection;
    }

    public long getUserProgressCurrentSection() {
        return userProgressCurrentSection;
    }


    /**
     * Setter
     */
    public void setLatestSectionNumber(long sectionNumber){
        this.latestSectionNumber = sectionNumber;
    }

    public void setUserProgressCurrentSection(long userProgressCurrentSection) {
        this.userProgressCurrentSection = userProgressCurrentSection;
    }

    public void setUserProgressCurrentScreen(long userProgressCurrentScreen) {
        this.userProgressCurrentScreen = userProgressCurrentScreen;
    }

    public void setLatestTaskNumber(long latestTaskNumber) {
        this.latestTaskNumber = latestTaskNumber;
    }


    public void setUserName(String userName) {
        this.userName = userName;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public void setFinishedTasksList(Map<String, ModelFinishedTask> finishedTasksList) {
        this.finishedTasksList = finishedTasksList;
    }

    /**
     * Edit the Logging List
     */

//    public Map<String, ModelLog> getLoggingList() {
//        return loggingList;
//    }
//
//    public void setLoggingList(HashMap<String, ModelLog> loggingList) {
//        this.loggingList = loggingList;
//    }
//
//    public void addLog(ModelLog log){
//        loggingList.put(log.getEventType(),log);
//    }
}
