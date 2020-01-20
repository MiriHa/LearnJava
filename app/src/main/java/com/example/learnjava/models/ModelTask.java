package com.example.learnjava.models;


/**
 * This is the parent class for ModelExercise and ModelLesson  and defines a Task
 */

public abstract class ModelTask {

    private String taskName;
    private String taskText;
    private int whatsNext;
    private int taskNumber;
    private int sectionNumber;

    //1 for lessone 2 for exercise
    private int type;
    private String tag;


    public ModelTask(String name, String text, int next, int number, int section, int type){
        this.taskName = name;
        this.taskText = text;
        this.whatsNext = next;
        this.taskNumber = number;
        this.type = type;
        this.sectionNumber = section;
    }

    public ModelTask(){}


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

    public int getSectionNumber(){
        return sectionNumber;
    }

    public abstract int getExerciseViewType();

    public abstract String[]  getKeywords();

    public abstract String[] getSolutionStringArray();

    public abstract int[] getSolutionIntArray();

    public abstract String getSolutionString();

    public abstract int getSolutionInt();

    public abstract String[] getContentStringArray();

    public void setType(int type) {
        this.type = type;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }
}
