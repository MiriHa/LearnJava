package com.example.learnjava.models;


//this model defines an Exercise of an Lesson
public class ModelExercise {

    private String exerciseName;
    private String exerciseText;
    private int exerciseNumber;
    private String whatsNext;

    private String[] solutionString;
    private int[] solutionInt;

    //  private boolean done;
    private String[] userinputString;
    private int[] userinputInt;

//    public ModelExercise(String exerciseText, int exerciseNumber, String[] solution){
//        this.exerciseText = exerciseText;
//        this.exerciseNumber = exerciseNumber;
//        this.solutionString = solution;
//    }
//
//    public ModelExercise(String exerciseText, int exerciseNumber, int[] solution){
//        this.exerciseText = exerciseText;
//        this.exerciseNumber = exerciseNumber;
//        this.solutionInt = solution;
//    }

    public ModelExercise(String exerciseName, String exerciseText, int exercisenumber, String[] solution, int[] solutionInt, String whatsNext){
        this.exerciseName = exerciseName;
        this.exerciseText = exerciseText;
        this.exerciseNumber = exercisenumber;
        this.solutionString = solution;
        this.solutionInt = solutionInt;
        this.whatsNext = whatsNext;
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

    public int getExerciseNumber() {
        return exerciseNumber;
    }

    public String[] getUserinputString() {
        return userinputString;
    }

    public int[] getUserinputInt() {
        return userinputInt;
    }

    public String getWhatsNext(){
        return whatsNext;
    }
}
