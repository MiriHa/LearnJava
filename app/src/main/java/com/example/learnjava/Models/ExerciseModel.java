package com.example.learnjava.Models;


//this model defines an Exercise of an Lesson
public class ExerciseModel {

    private String exerciseName;
    private String exerciseTask;
    private int exercisenumber;
  //  private boolean done;
    private String[] userinputString;
    private int[] userinputInt;

    private String[] solution;
    private int[] solutionInt;

    public ExerciseModel(String exerciseTask, int exercisenumber){
        this.exerciseTask = exerciseTask;
        this.exercisenumber = exercisenumber;
    }

    public void setUserinputString(String[] userinputString) {
        this.userinputString = userinputString;
    }

    public void setUserinputInt(int[] userinputInt) {
        this.userinputInt = userinputInt;
    }

    public String getExerciseTask() {
        return exerciseTask;
    }

    public int getExercisenumber() {
        return exercisenumber;
    }

    public String[] getUserinputString() {
        return userinputString;
    }

    public int[] getUserinputInt() {
        return userinputInt;
    }
}
