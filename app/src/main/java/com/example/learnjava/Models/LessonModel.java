package com.example.learnjava.Models;

public class LessonModel {

    private String lessonName;
    private String lessonText;
    private int lessonNumber;
    private String[] keywords;

    public LessonModel(String lessonName, String lessonText, int lessonNumber, String[] keywords){
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
}
