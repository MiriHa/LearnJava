package com.example.learnjava;

import android.content.Context;
import android.util.Log;

import com.example.learnjava.models.ModelTask;
import com.example.learnjava.models.ModelUserProgress;

import java.util.ArrayList;

public class Controller extends android.app.Application {

    ModelUserProgress modelUserProgress = new ModelUserProgress();

    ArrayList<ModelTask> taskContent;
    ReadJson readJson = new ReadJson();


    //Make sure to only use this once
    public void setModelUserProgress(ModelUserProgress progress){
        this.modelUserProgress = progress;
    }

    public void addFinishedTask(ModelTask task){
        modelUserProgress.addFinishedTask(task);
        Log.i("M_CONTROLLER", " addfnishedTask " + task.getTaskNumber());
    }

    public boolean checkTasks(ModelTask task){
        Log.i("M_CONTROLLER", "checkTasks" + modelUserProgress.checkTasks(task));
       return modelUserProgress.checkTasks(task);

    }



    public boolean checkUnlockedSections(Integer sectionNumber){
        Log.i("M_CONTROLLER","checkUnlockedSections "+sectionNumber);
       return modelUserProgress.checkProgressUnlockedSection(sectionNumber);
    }

    public void updateUnlockedSections(Integer sectionNumber){
        Log.i("M_CONTROLLER","updateUnlockedSections"+sectionNumber);
        modelUserProgress.updateUserProgressUnlockedSections(sectionNumber);
    }


    public void updateCurrentSection(int number){
        modelUserProgress.updateUserProgressCurrentSection(number);
        Log.i("M_CONTROLLER", " CurrentSectionNumber "+number);
    }

    public void updateCurrentScreen(int number){
        Log.i("M_CONTROLLER", " CurrentSreenNumber "+number);
        modelUserProgress.updateUserProgressCurrentScreen(number);
    }

    public void updateLatestTaskNumber(int number){
        Log.i("M_CONTROLLER","updateLatesTaskNumber "+number);
        modelUserProgress.updateLatestTaskNumber(number);
    }


    public void setLastLesson(ModelTask lastLesson){
        Log.i("M_CONTROLLER","setLastLesson: "+lastLesson.getTaskName() +" " + lastLesson.getTaskNumber());
        modelUserProgress.setLastLesson(lastLesson);
    }

    public ModelTask getLastLesson()
    {
        return modelUserProgress.getLastLesson();
    }

    public int getLatestTaskNumber(){
        return modelUserProgress.getLatestTaskNumber();
    }

    public void loadContent(int sectionNumber, Context context){
        String sectionFile = "section" + sectionNumber;
        taskContent =  readJson.readTask(sectionFile, context);
        Log.i("loadContent", " section" + sectionNumber);
        Log.i("M updateUserProgress", "loadContent");
//        ArrayList<ModelTask> taskContent = new ArrayList<>();
//        //TODO load all at once?
//        for(int i=0; i<= sectionCount; i++ ){
//           taskContent =  readJson.readTask("section"+i, this);
//            Log.i("M updateUserProgress", "loadContent section: " +i);
//        }
//        return taskContent;
    }

    public ArrayList<ModelTask> getTaskContent(){
        Log.i("M updateUserProgress", "getTaskContent");
        return taskContent;
    }

    public ModelUserProgress getModelUserProgress(){
        return modelUserProgress;
    }

    public ArrayList<Integer> getSections(){
        return modelUserProgress.getUserProgressUnlockedSections();
    }

    public int getCurrentSection(){
        return modelUserProgress.getCurrentSection();
    }



}
