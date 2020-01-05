package com.example.learnjava.models;

import com.google.firebase.database.IgnoreExtraProperties;

@IgnoreExtraProperties
public class ModelFinishedTask {

    private int sectionNumber;
    private int taskNumber;
    private String taskName;


    public ModelFinishedTask(){}

    public ModelFinishedTask(int sectionNumber, int taskNumber, String taskName){
        this.sectionNumber = sectionNumber;
        this.taskName = taskName;
        this.taskNumber = taskNumber;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public void setTaskNumber(int taskNumber) {
        this.taskNumber = taskNumber;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }
}
