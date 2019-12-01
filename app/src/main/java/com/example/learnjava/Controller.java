package com.example.learnjava;

import android.app.Application;

import com.example.learnjava.models.ModelExercise;
import com.example.learnjava.models.ModelLesson;
import com.example.learnjava.models.ModelUserProgress;

public class Controller extends Application {

    ModelUserProgress modelUserProgress;

    //TODO may this instead of user model?
    // ArrayList<ModelExercise> exerciseList = new ArrayList<>();


    public ModelUserProgress getUserProgresModel() {
        return modelUserProgress;
    }


    //do I need these?
    public void addFinishedExercise(ModelExercise exercise){
        modelUserProgress.addFinishedExercise(exercise);
    }

    public void addReadLesson(ModelLesson lesson){
        modelUserProgress.addReadLesson(lesson);
    }

    public void updateFinishedSection(int number){
        modelUserProgress.updateUserProgressSections(number);
    }
}
