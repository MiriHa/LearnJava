package com.example.learnjava.models;

import com.google.firebase.database.IgnoreExtraProperties;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * This model tracks the progress of a user and contains all user informations.
 * The App has seven sections.
 * Each Sections has various tasks either a theory lessons or Exercises.
 */

@IgnoreExtraProperties
public class ModelUserProgress {

    private String userId;
    private String userName;
    private String email;

    //Keeps track over the current Section and the current Screen the user is on
    private long userProgressCurrentSection;
    private long userProgressCurrentScreen;

    //TODO open the last Screen so that the user can skip sscreens
    //save this and check the latest section? or save latest section seperate?
    //use latest section also to check the freigeschaltete sections instead of the array?
    private long latestTaskNumber;
    private long latestSectionNumber;

    //stores the last theorey lesson, so the the user can skip back

    //TODO is note saved? also nicht benötig zum saven da nur temporär?
    private ModelTask lastLesson;

    //Keeps track over the unlocked sections
    //Not needed to save in firebase? -> latestSectionNumber is enough
   // private ArrayList<Integer> userUnlockedSections = new ArrayList<>();

    //stores the solved exercises
    //
    //TODO Make this also Integer and give each task a unique number
   // private List<ModelTask> finishedTasks = new ArrayList<>();
    private HashMap<String, ModelTask> finishedTasks = new HashMap<>();

   // private ArrayList<Float> finishedTasksNumbers = new ArrayList<>();
    private HashMap<String, ModelLog> loggingList = new HashMap<>();



    public ModelUserProgress(String userId, String userName, String email){
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        //userUnlockedSections.add(1);
        latestSectionNumber = 1;

    }

    public ModelUserProgress(String userId, String userName, String email, int latestTaskNumber, int latestSectionNumber, HashMap finishedTaskList){
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.latestSectionNumber = latestSectionNumber;
        this.latestTaskNumber = latestTaskNumber;
      //  this.finishedTaskList = finishedTaskList;
    }


    public ModelUserProgress(){
    }


    //Update Methoden
//    public void updateUserProgressUnlockedSections(Integer number){
//        userUnlockedSections.add(number);
//    }

    public void updateUserProgressCurrentSection(int number){
        this.userProgressCurrentSection = (long) number;
    }

    public void updateUserProgressCurrentScreen(int userProgressCurrentScreen) {
        this.userProgressCurrentScreen = userProgressCurrentScreen;
    }

    public void updateLatestTaskNumber(int tasknumber){
        this.latestTaskNumber = (long) tasknumber;
    }

    public void setLastLesson(ModelTask lastLesson){
        this.lastLesson = lastLesson;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    //add a finishedTask bzw exercise so that the user can skip it
    public void addFinishedTask(ModelTask task){
        if(!checkTasks(task)){
           // finishedTasks.add(task);
            finishedTasks.put(task.getTaskName(),task);
        }
    }


    //check if task was already read/exercise was solved
    public boolean checkTasks(ModelTask aTask){
        return finishedTasks.containsValue(aTask);
    }

    //check if a section is already unlocked
    public boolean checkProgressUnlockedSection(Integer sectionNumber){
        return (latestSectionNumber >= sectionNumber);
    }



    //Getter

    public String getUserId(){
        return userId;
    }

    public long getLatestTaskNumber(){
        return latestTaskNumber;
    }

    public long getUserProgressCurrentScreen() {
        return userProgressCurrentScreen;
    }

//    //public ArrayList<Integer> getUserProgressUnlockedSections(){
//        return userUnlockedSections;
//    }

    public ModelTask getLastLesson(){
        return lastLesson;
    }

    public long getLatestSectionNumber(){
        return latestSectionNumber;
    }

//    public List<ModelTask> getFinishedTasks(){
//        return finishedTasks;
//    }

    public long getCurrentSection(){
        return userProgressCurrentSection;
    }

    public long getUserProgressCurrentSection() {
        return userProgressCurrentSection;
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

//    public ArrayList<Integer> getUserUnlockedSections() {
//        return userUnlockedSections;
//    }
//
//    public void setUserUnlockedSections(ArrayList<Integer> userUnlockedSections) {
//        this.userUnlockedSections = userUnlockedSections;
//    }

//    public void setFinishedTasks(List<ModelTask> finishedTasks) {
//        this.finishedTasks = finishedTasks;
//    }

    public void setLatestSectionNumber(long sectionNumber){
        this.latestSectionNumber = sectionNumber;
    }

//    public ArrayList<Float> getFinishedTasksNumbers() {
//        return finishedTasksNumbers;
//    }
//
//    public void setFinishedTasksNumbers(ArrayList<Float> finishedTasksNumbers) {
//        this.finishedTasksNumbers = finishedTasksNumbers;
//    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public HashMap<String, ModelTask> getFinishedTasks() {
        return finishedTasks;
    }

    public void setFinishedTasks(HashMap<String, ModelTask> finishedTasks) {
        this.finishedTasks = finishedTasks;
    }

    /**
     * Edit the Logging List
     */

    public HashMap<String, ModelLog> getLoggingList() {
        return loggingList;
    }

    public void setLoggingList(HashMap<String, ModelLog> loggingList) {
        this.loggingList = loggingList;
    }

    public void addLog(String userOwnerName, Date time, String eventType, String details){
        ModelLog modelLog = new ModelLog(userOwnerName, time, eventType, details);
        loggingList.put(eventType,modelLog);
    }
}
