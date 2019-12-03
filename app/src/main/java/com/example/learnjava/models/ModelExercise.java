package com.example.learnjava.models;


//this model defines an Exercise of an Lesson
public class ModelExercise extends ModelTask{

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

    public ModelExercise(String exerciseName, String exerciseText, int exercisenumber, String[] solution, int[] solutionInt, int whatsNext){

        super(exerciseName, exerciseText, whatsNext, exercisenumber, 2);
        this.solutionString = solution;
        this.solutionInt = solutionInt;

    }

    public void setUserinputString(String[] userinputString) {
        this.userinputString = userinputString;
    }


    public void setUserinputInt(int[] userinputInt) {
        this.userinputInt = userinputInt;
    }

    public String[] getUserinputString() {
        return userinputString;
    }

    public int[] getUserinputInt() {
        return userinputInt;
    }

}
