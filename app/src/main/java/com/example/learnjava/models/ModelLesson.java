package com.example.learnjava.models;


//This model defines a Theory Block of a lesson
public class ModelLesson {


    private String lessonName;
    private String lessonText;
    private int lessonNumber;
    private String[] keywords;

    public ModelLesson(String lessonName, String lessonText, int lessonNumber, String[] keywords){
        this.lessonName = lessonName;
        this.lessonText = lessonText;
        this.lessonNumber = lessonNumber;
        this.keywords = keywords;
    }

    public String getLessonName() {
        return lessonName;
    }

    public String getLessonText() {
        return lessonText;
    }

    public int getLessonNumber() {
        return lessonNumber;
    }

    public String[] getKeywords(){
        return keywords;
    }
}
