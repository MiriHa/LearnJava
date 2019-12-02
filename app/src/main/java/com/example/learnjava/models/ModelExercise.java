package com.example.learnjava.models;


//this model defines an Exercise of an Lesson
public class ModelExercise {

    private String exerciseName;
    private String exerciseText;
    private int exercisenumber;

    private String[] solutionString;
    private int[] solutionInt;

    //  private boolean done;
    private String[] userinputString;
    private int[] userinputInt;

//    public ModelExercise(String exerciseText, int exercisenumber, String[] solution){
//        this.exerciseText = exerciseText;
//        this.exercisenumber = exercisenumber;
//        this.solutionString = solution;
//    }
//
//    public ModelExercise(String exerciseText, int exercisenumber, int[] solution){
//        this.exerciseText = exerciseText;
//        this.exercisenumber = exercisenumber;
//        this.solutionInt = solution;
//    }

    public ModelExercise(String exerciseName, String exerciseText, int exercisenumber, String[] solution, int[] solutionInt){
        this.exerciseName = exerciseName;
        this.exerciseText = exerciseText;
        this.exercisenumber = exercisenumber;
        this.solutionString = solution;
        this.solutionInt = solutionInt;
    }

    public void setUserinputString(String[] userinputString) {
        this.userinputString = userinputString;
    }

    public void setUserinputInt(int[] userinputInt) {
        this.userinputInt = userinputInt;
    }

    public String getExerciseText() {
        return exerciseText;
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
