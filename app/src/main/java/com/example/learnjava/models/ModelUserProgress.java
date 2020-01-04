package com.example.learnjava.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This model tracks the progress of a user and contains all user informations.
 * The App has seven sections.
 * Each Sections has various tasks either a theory lessons or Exercises.
 */

public class ModelUserProgress {

    private String userId;
    private String userName;
    private String email;

    //Keeps track over the current Section and the current Screen the user is on
    private int userProgressCurrentSection;
    private int userProgressCurrentScreen;

    //TODO open the last Screen so that the user can skip sscreens
    //save this and check the latest section? or save latest section seperate?
    //use latest section also to check the freigeschaltete sections instead of the array?
    private int latestTaskNumber;
    private int latestSectionNumber;

    //stores the last theorey lesson, so the the user can skip back

    private ModelTask lastLesson;


    //Keeps track over the unlocked sections
    //Not needed to save in firebase? -> latestSectionNumber is enough
    private ArrayList<Integer> userUnlockedSections = new ArrayList<>();

    //stores the solved exercises
    //
    //TODO Make this also Integer and give each task a unique number
    private List<ModelTask> finishedTasks = new ArrayList<>();
    private HashMap<String, ModelTask> finishedTaskList = new HashMap<>();

    private ArrayList<Float> finishedTasksNumbers = new ArrayList<>();



    public ModelUserProgress(String userId, String userName, String email){
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        userUnlockedSections.add(1);
        latestSectionNumber = 1;

    }

    public ModelUserProgress(String userId, String userName, String email, int latestTaskNumber, int latestSectionNumber, HashMap finishedTaskList){
        this.userId = userId;
        this.email = email;
        this.userName = userName;
        this.latestSectionNumber = latestSectionNumber;
        this.latestTaskNumber = latestTaskNumber;
        this.finishedTaskList = finishedTaskList;
    }


    public ModelUserProgress(){
    }


    //Update Methoden
    public void updateUserProgressUnlockedSections(Integer number){
        userUnlockedSections.add(number);
    }

    public void updateUserProgressCurrentSection(int number){
        this.userProgressCurrentSection = number;
    }

    public void updateUserProgressCurrentScreen(int userProgressCurrentScreen) {
        this.userProgressCurrentScreen = userProgressCurrentScreen;
    }

    public void updateLatestTaskNumber(int tasknumber){
        this.latestTaskNumber = tasknumber;
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
            finishedTasks.add(task);
        }
    }


    //check if task was already read/exercise was solved
    public boolean checkTasks(ModelTask aTask){
        return finishedTasks.contains(aTask);
    }

    //check if a section is already unlocked
    public boolean checkProgressUnlockedSection(Integer sectionNumber){
        return userUnlockedSections.contains(sectionNumber);
    }



    //Getter

    public String getUserId(){
        return userId;
    }

    public int getLatestTaskNumber(){
        return latestTaskNumber;
    }

    public int getUserProgressCurrentScreen() {
        return userProgressCurrentScreen;
    }

    public ArrayList<Integer> getUserProgressUnlockedSections(){
        return userUnlockedSections;
    }

    public ModelTask getLastLesson(){
        return lastLesson;
    }

    public int getLatestSectionNumber(){
        return latestSectionNumber;
    }

    public List<ModelTask> getFinishedTasks(){
        return finishedTasks;
    }

    public int getCurrentSection(){
        return userProgressCurrentSection;
    }

    public int getUserProgressCurrentSection() {
        return userProgressCurrentSection;
    }

    public void setUserProgressCurrentSection(int userProgressCurrentSection) {
        this.userProgressCurrentSection = userProgressCurrentSection;
    }

    public void setUserProgressCurrentScreen(int userProgressCurrentScreen) {
        this.userProgressCurrentScreen = userProgressCurrentScreen;
    }

    public void setLatestTaskNumber(int latestTaskNumber) {
        this.latestTaskNumber = latestTaskNumber;
    }

    public ArrayList<Integer> getUserUnlockedSections() {
        return userUnlockedSections;
    }

    public void setUserUnlockedSections(ArrayList<Integer> userUnlockedSections) {
        this.userUnlockedSections = userUnlockedSections;
    }

    public void setFinishedTasks(List<ModelTask> finishedTasks) {
        this.finishedTasks = finishedTasks;
    }

    public void setLatestSectionNumber(int sectionNumber){
        this.latestSectionNumber = sectionNumber;
    }

    public ArrayList<Float> getFinishedTasksNumbers() {
        return finishedTasksNumbers;
    }

    public void setFinishedTasksNumbers(ArrayList<Float> finishedTasksNumbers) {
        this.finishedTasksNumbers = finishedTasksNumbers;
    }
}
