package com.example.learnjava.models;


/**
 * This is the parentclass for ModelExercise and ModelLesson
 */

public abstract class ModelTask {

    private final String taskName;
    private final String taskText;
    private final int whatsNext;
    private final int taskNumber;

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

    public abstract boolean getIsSolved();

    public abstract int getExerciseViewType();

    public abstract void isSolved();

    public abstract String[]  getKeywords();


}
