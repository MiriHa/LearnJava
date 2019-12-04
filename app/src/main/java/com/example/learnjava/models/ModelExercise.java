package com.example.learnjava.models;


/**
 * This model defines an Exercise of an Lesson
 * The exerciseViewType represents the needed Layout for tha exercise:
 * 1: Answer a qestions
 * 2: Multiple Choice
 * 3: Fill in the Blanks
 * 4: Drag and drop
 * 5: order the Lines
 * 6: Code
 */

public class ModelExercise extends ModelTask{

    private final int exerciseViewType;
    private boolean isSolved = false;
    private final String[] solutionString;
    private final int[] solutionInt;

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

    public ModelExercise(String exerciseName, String exerciseText, int exercisenumber, String[] solution, int[] solutionInt, int whatsNext, int viewType){

        super(exerciseName, exerciseText, whatsNext, exercisenumber, 2);
        this.solutionString = solution;
        this.solutionInt = solutionInt;
        this.exerciseViewType = viewType;

    }


    //SETTER

    public void setUserinputString(String[] userinputString) {
        this.userinputString = userinputString;
    }

    public void isSolved(){
        isSolved = true;
    }

    public void setUserinputInt(int[] userinputInt) {
        this.userinputInt = userinputInt;
    }

    //GETTER

    public boolean getIsSolved(){
        return isSolved;
    }

    public int getExerciseViewType(){
        return exerciseViewType;
    }

    public String[] getUserinputString() {
        return userinputString;
    }

    public int[] getUserinputInt() {
        return userinputInt;
    }

    public String[] getKeywords(){
        return null;
    }

}
