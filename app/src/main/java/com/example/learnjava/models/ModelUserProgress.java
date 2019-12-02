package com.example.learnjava.models;

import android.util.Log;

import java.util.ArrayList;

/**
 * This model tracks the progress of a user and contains all user informations
 * The App has seven sections.
 * Each Sections has Theory lessons and Exercises.
 */

public class ModelUserProgress {

    //alls int oder array? array of lessons/arraylist?
    //are lessons really needed?
    private int userProgressLessons;
    private int userProgressExercises;
    private ArrayList<Integer> userProgressSections;
    private int userProgressCurrentSection;

//    private int userProgressCurrentLesson;
//    private int userProgressCurrentExercise;
    private int userProgressCurrentSreen;

    private  ArrayList<ModelExercise> solvedExercises = new ArrayList<>();
    private ArrayList<ModelLesson> readLessons = new ArrayList<>();

    public ModelUserProgress(int userProgressLessons, int userProgressExercises){
        this.userProgressExercises = userProgressExercises;
        this.userProgressLessons = userProgressLessons;
    }

    public int getUserProgressLessons() {
        //return readLessons.size();
        return userProgressLessons;
    }

    public int getUserProgressExercises() {
        //return solvedExercises.size();
        return userProgressExercises;
    }

    public ArrayList<Integer> getUserProgressSections(){
        return  userProgressSections;
    }

    public void updateUserProgressSections(int number){
        userProgressSections.add(number);
    }

    public void updateUserProgressExercise(){
        this. userProgressExercises += 1;
    }

    public void updateUserProgressLesson(){
        this.userProgressLessons += 1;
    }

    public boolean checkExercises(ModelExercise aExercise) {
        return solvedExercises.contains(aExercise);
    }

    public boolean checkLessons(ModelLesson aLesson){
        return readLessons.contains(aLesson);
    }

    public int getExerciseNumber(){
        return solvedExercises.size();
    }

    public void setUserProgressCurrentSection(int number){

        this.userProgressCurrentSection = number;
    }

    public int getUserProgressCurrentSection(){
        return userProgressCurrentSection;
    }


    public void addReadLesson(ModelLesson lesson){
        if(!checkLessons(lesson)){
            readLessons.add(lesson);
            updateUserProgressLesson();
        }

    }

    public void addFinishedExercise(ModelExercise exercise){
        //TODO only add when exercise was right
        if(!checkExercises(exercise)) {
            solvedExercises.add(exercise);
            updateUserProgressExercise();
        }

    }

    public int getUserProgressCurrentScreen() {
        return userProgressCurrentSreen;
    }

    public void setUserProgressCurrentSreen(int userProgressCurrentScreen) {
        this.userProgressCurrentSreen = userProgressCurrentScreen;
    }
}
