package com.example.learnjava.models;


//This model defines a Theory Block of a lesson
public class ModelLesson extends ModelTask {

    private String[] keywords;

    public ModelLesson(String lessonName, String lessonText, int lessonNumber, String[] keywords, String whatsNext){

        super(lessonName, lessonText,whatsNext,lessonNumber,1);
        this.keywords = keywords;

    }


    public String[] getKeywords(){
        return keywords;
    }

}
