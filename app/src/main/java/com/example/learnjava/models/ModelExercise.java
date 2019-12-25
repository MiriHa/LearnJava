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

    //Solutions
    private final String[] solutionStringArray;
    private final int[] solutionIntArray;
    private final String solutionString;
    private final int solutionInt;


    //  private boolean done;
    private String[] userinputString;
    private int[] userinputInt;

    private String[] contentString;

    public ModelExercise(String exerciseName, String exerciseText, int exercisenumber, int sectionNumber, String[] solutionStringArray, int[] solutionIntArray, int whatsNext, int viewType, int solutionInt, String solutionString,  String[] contentString){

        super(exerciseName, exerciseText, whatsNext, exercisenumber, sectionNumber, 2);
        this.solutionStringArray = solutionStringArray;
        this.solutionIntArray = solutionIntArray;
        this.exerciseViewType = viewType;
        this.solutionInt = solutionInt;
        this.solutionString = solutionString;
        this.contentString = contentString;

    }


    //SETTER

    public void setUserinputString(String[] userinputString) {
        this.userinputString = userinputString;
    }


    public void setUserinputInt(int[] userinputInt) {
        this.userinputInt = userinputInt;
    }

    //GETTER

    public int getExerciseViewType(){
        return exerciseViewType;
    }

    public String[] getUserinputString() {
        return userinputString;
    }

    public int[] getUserinputInt() {
        return userinputInt;
    }

    public String[] getSolutionStringArray() {
        return solutionStringArray;
    }

    public int[] getSolutionIntArray() {
        return solutionIntArray;
    }

    @Override
    public String getSolutionString() {
        return solutionString;
    }

    @Override
    public int getSolutionInt() {
        return solutionInt;
    }

    public String[] getKeywords(){
        return null;
    }

    public String[] getContentStringArray(){
        return contentString;
    }



}
