package com.example.learnjava.models;

import android.view.Display;

import java.util.ArrayList;

/**
 * This model tracks the progress of a user and contains all user informations.
 * The App has seven sections.
 * Each Sections has various tasks either a theory lessons or Exercises.
 */

public class ModelUserProgress {

    //USe a singelton?

    //Keeps track over the unlocked sections
    private ArrayList<Integer> userUnlockedSections = new ArrayList<>();

    //Keeps track over the current Section and the current Screen the user is on
    private int userProgressCurrentSection;
    private int userProgressCurrentScreen;

    //stores the solved exercises
    //
    private ArrayList<ModelTask> finishedTasks = new ArrayList<>();

    //TODO open the last Screen so that the user can skip sscreens
    private int latetestTaskNumber;

    //stores the last theorey lesson, so the the user can skip back
    private ModelTask lastLesson;


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
        this.latetestTaskNumber = tasknumber;
    }

    public void setLastLesson(ModelTask lastLesson){
        this.lastLesson = lastLesson;
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

    public int getLatetestTaskNumber(){
        return latetestTaskNumber;
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

    public ArrayList<ModelTask> getFinishedTasks(){
        return finishedTasks;
    }

    public int getCurrentSection(){
        return userProgressCurrentSection;
    }

}
