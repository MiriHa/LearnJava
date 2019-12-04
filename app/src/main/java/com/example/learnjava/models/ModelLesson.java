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

    public boolean getIsSolved(){
        return false;
    }

    public int getExerciseViewType(){return 0;}

    public void isSolved(){}

}
