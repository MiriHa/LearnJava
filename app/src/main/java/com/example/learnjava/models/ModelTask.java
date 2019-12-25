package com.example.learnjava.models;


/**
 * This is the parentclass for ModelExercise and ModelLesson
 */

public abstract class ModelTask {

    private final String taskName;
    private final String taskText;
    private final int whatsNext;
    private final int taskNumber;
    private final int sectionNumber;

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




}
