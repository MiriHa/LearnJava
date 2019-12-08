package com.example.learnjava;

import android.app.Application;
import android.util.Log;

import com.example.learnjava.models.ModelExercise;
import com.example.learnjava.models.ModelLesson;
import com.example.learnjava.models.ModelTask;
import com.example.learnjava.models.ModelUserProgress;

public class Controller extends Application {

    ModelUserProgress modelUserProgress = new ModelUserProgress();


    public void addFinishedExercise(ModelTask exercise){
        modelUserProgress.addFinishedExercise(exercise);
        Log.i("M updateUserProgress", " addFinishedExercise");
    }

    public void addReadLesson(ModelTask lesson){
        modelUserProgress.addReadLesson(lesson);
        Log.i("M updateUserProgress", " addReadLesson");
    }

    public void addfinishedTask(ModelTask task){
        modelUserProgress.addFinisehdTask(task);
        Log.i("M updateUserProgress", " addfnishedTask");
    }


    public void updateFinishedSection(Integer number){
       modelUserProgress.updateUserProgressFinishedSections(number);
        Log.i("M updateUserProgress", " finishedSectionNumber "+number);
    }

    public void updateCurrentSection(int number){
        modelUserProgress.updateUserProgressCurrentSection(number);
        Log.i("M updateUserProgress", " CurrentSectionNumber "+number);
    }

    public void updateCurrentScreen(int number){
        Log.i("M updateUserProgress", " CurrentSreenNumber "+number);
        modelUserProgress.updateUserProgressCurrentScreen(number);
    }

    public void setLastLesson(ModelTask lastLesson){
        modelUserProgress.setLastLesson(lastLesson);
    }

    public ModelTask getLastLesson(){
        return modelUserProgress.getLastLesson();
    }


}
