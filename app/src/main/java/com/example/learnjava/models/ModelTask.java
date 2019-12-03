package com.example.learnjava.models;

public class ModelTask {

    private String taskName;
    private String taskText;
    private int whatsNext;
    private int taskNumber;

    //1 for lessone 2 for exercise
    private int type;


    public ModelTask(String name, String text, int next, int number, int type){
        this.taskName = name;
        this.taskText = text;
        this.whatsNext = next;
        this.taskNumber = number;
        this.type = type;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskText() {
        return taskText;
    }

    public int getWhatsNext() {
        return whatsNext;
    }

    public int getTaskNumber() {
        return taskNumber;
    }

    public int getType(){
        return type;
    }
}
