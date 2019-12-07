package com.example.learnjava;

import android.app.Application;
import android.util.Log;

import com.example.learnjava.models.ModelExercise;
import com.example.learnjava.models.ModelLesson;
import com.example.learnjava.models.ModelTask;
import com.example.learnjava.models.ModelUserProgress;

public class Controller extends Application {

    ModelUserProgress modelUserProgress = new ModelUserProgress();


    public void addFinishedExercise(ModelExercise exercise){
        modelUserProgress.addFinishedExercise(exercise);
        Log.i("updateUserProgress", " addFinishedExercise");
    }

    public void addReadLesson(ModelLesson lesson){
        modelUserProgress.addReadLesson(lesson);
        Log.i("updateUserProgress", " addReadLesson");
    }

    public void addfinishedTask(ModelTask task){
        modelUserProgress.addFinisehdTask(task);
        Log.i("updateUserProgress", " addfnishedTask");
    }


    public void updateFinishedSection(Integer number){
       modelUserProgress.updateUserProgressFinishedSections(number);
        Log.i("updateUserProgress", " finishedSectionNumber "+number);
    }

    public void updateCurrentSection(int number){
        modelUserProgress.updateUserProgressCurrentSection(number);
        Log.i("updateUserProgress", " CurrentSectionNumber "+number);
    }

    public void updateCurrentScreen(int number){
        Log.i("updateUserProgress", " CurrentSreenNumber "+number);
        modelUserProgress.updateUserProgressCurrentScreen(number);
    }


}
