package com.example.learnjava.Models;

import java.util.ArrayList;

//This model tracks the progress of a user
public class UserProgressModel {

    //alls int oder array? array of lessons/arraylist?
    private int userProgressLessons;
    private int userProgressExercises;
    private  ArrayList<ExerciseModel> solvedExercises = new ArrayList<>();

    public UserProgressModel(int userProgressLessons, int userProgressExercises){
        this.userProgressExercises = userProgressExercises;
        this.userProgressLessons = userProgressLessons;
    }

    public int getUserProgressLessons() {
        return userProgressLessons;
    }

    public int getUserProgressExercises() {
        return userProgressExercises;
    }

    public void updateUserProgressExercise(){
        this. userProgressExercises += 1;
    }

    public void updateUserProgressLesson(){
        this.userProgressLessons += 1;
    }

    public boolean checkExercises(ExerciseModel aExercise) {
        return solvedExercises.contains(aExercise);
    }

    public int getExerciseNumber(){
        return solvedExercises.size();
    }

    //TODO maybe set UserProgressExercise with size directly an set it
}
