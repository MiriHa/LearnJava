package com.example.learnjava.room_database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.learnjava.models.ModelTask;
import com.example.learnjava.room_database.TypeConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * This model tracks the progress of a user and contains all user informations.
 * The App has seven sections.
 * Each Sections has various tasks either a theory lessons or Exercises.
 */

@Entity (indices = {@Index("user_ID")})
public class ModelUserProgress {

    //TODO use a int instead?
    @PrimaryKey @NonNull
    @ColumnInfo(name = "user_ID")
    private String userId = " ";

    //Keeps track over the current Section and the current Screen the user is on
    @ColumnInfo(name = "current_Section")
    private int userProgressCurrentSection;

    @ColumnInfo(name = "current_Screen")
    private int userProgressCurrentScreen;

    //TODO open the last Screen so that the user can skip sscreens
    //save this and check the latest section? or save latest section seperate?
    //use latest section also to check the freigeschaltete sections instead of the array?
    @ColumnInfo(name = "latest_taskNumber")
    private int latestTaskNumber;

    @ColumnInfo (name = "latest_sectionNumber")
    private int latestSectionNumber;

    //stores the last theorey lesson, so the the user can skip back
    @ColumnInfo(name = "last_Lesson")
    @TypeConverters({TypeConverter.class})
    private ModelTask lastLesson;


    //Keeps track over the unlocked sections
    @TypeConverters({TypeConverter.class})
    private ArrayList<Integer> userUnlockedSections = new ArrayList<>();

    //stores the solved exercises
    //
    //TODO Make this also Integer and give each task a unique number

    @TypeConverters({TypeConverter.class})
    private List<ModelTask> finishedTasks = new ArrayList<>();

    @Ignore
    private ArrayList<Float> finishedTasksNumbers = new ArrayList<>();



    public ModelUserProgress(String userId){
        this.userId = userId;
        userUnlockedSections.add(1);
    }

    @Ignore
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
