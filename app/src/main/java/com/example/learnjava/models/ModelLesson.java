package com.example.learnjava.models;


//This model defines a Theory Block of a lesson
public class ModelLesson extends ModelTask {

    private final String[] keywords;

    public ModelLesson(String lessonName, String lessonText, int lessonNumber, String[] keywords, int whatsNext){

        super(lessonName, lessonText,whatsNext,lessonNumber,1);
        this.keywords = keywords;

    }


    public String[] getKeywords(){
        return keywords;
    }



    //needed implemented but not used here
    @Override
    public String[] getSolutionStringArray() {
        return new String[0];
    }

    @Override
    public int[] getSolutionIntArray() {
        return new int[0];
    }

    @Override
    public String getSolutionString() {
        return null;
    }

    @Override
    public int getSolutionInt() {
        return 0;
    }

    @Override
    public String[] getContentStringArray() {
        return new String[0];
    }

    public boolean getIsSolved(){
        return false;
    }

    public int getExerciseViewType(){return 0;}

    public void isSolved(){}

}
