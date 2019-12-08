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
    //private static final ModelUserProgress userProgress = new ModelUserProgress();

    //Keeps track over the finished sections to unlock new ones
    private ArrayList<Integer> userProgressSections = new ArrayList<>();

    //Keeps track over the current Section and the current Screen the user is on
    private int userProgressCurrentSection;
    private int userProgressCurrentScreen;

    //stores the solved exercises maybe that the user needed solve it twice and can skip over solved exercises
    // not needed only to track the progress and open lesson an entsprechender stelle
    //nicht möglich da modeltask list und currenttask modelTask ist
    private  ArrayList<ModelTask> solvedExercises = new ArrayList<>();
    private ArrayList<ModelTask> readLessons = new ArrayList<>();

    //IS this better? acess over task number
    //TODO tasknumber muss platz in arraylist übereinstimmen?
    private ArrayList<ModelTask> finishedTasks = new ArrayList<>();

    //TODO open the laste Screen so that the user can skip sscreens
    private int latetestTaskNumber;

    private ModelTask lastLesson;


    public ModelUserProgress(){

    }

    //use a singelton?
//    public static ModelUserProgress getInstance() {
//        return userProgress;
//    }


    public void updateUserProgressFinishedSections(Integer number){
        userProgressSections.add(number);
    }

    public void updateUserProgressCurrentSection(int number){
        this.userProgressCurrentSection = number;
    }

    public void updateUserProgressCurrentScreen(int userProgressCurrentScreen) {
        this.userProgressCurrentScreen = userProgressCurrentScreen;
    }

    public void setLastLesson(ModelTask lastLesson){
        this.lastLesson = lastLesson;
    }

    //check if exercise was already solved
    public boolean checkExercises(ModelTask aExercise) {
        return solvedExercises.contains(aExercise);
    }

    //check if lesson was already read
    public boolean checkLessons(ModelTask aLesson){
        return readLessons.contains(aLesson);
    }

    public boolean checkTasks(ModelTask aTask){
        return finishedTasks.contains(aTask);
    }



    //TODO add the lessons!!!

    public void addReadLesson(ModelTask lesson){
        if(!checkLessons(lesson)){
            readLessons.add(lesson);
        }
    }

    public void addFinishedExercise(ModelTask exercise){
        //TODO only add when exercise was right
        if(!checkExercises(exercise)) {
            solvedExercises.add(exercise);
        }
    }

    public void addFinisehdTask(ModelTask task){
        if(!checkTasks(task)){
            finishedTasks.add(task);
        }
    }


    //Getter

    public int getUserProgressCurrentScreen() {
        return userProgressCurrentScreen;
    }

    public ArrayList<Integer> getUserProgressSections(){
        return  userProgressSections;
    }

    public int getUserProgressCurrentSection(){
        return userProgressCurrentSection;
    }

    public int getExerciseNumber(){
        return solvedExercises.size();
    }

    public ModelTask getLastLesson(){
        return lastLesson;
    }

}
