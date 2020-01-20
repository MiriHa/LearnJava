package com.example.learnjava.models;


/**
 * This model defines a Question for the question cue
 */
public class ModelQuestion {

    private int number;
    private int answerInt;
    private String question;
    private String[] answers;

    public ModelQuestion(){

    }

    public ModelQuestion(int number, int answerInt, String question, String[] answers){
        this.number = number;
        this.answerInt = answerInt;
        this.question = question;
        this.answers = answers;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getAnswerInt() {
        return answerInt;
    }

    public void setAnswerInt(int answerInt) {
        this.answerInt = answerInt;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getAnswers() {
        return answers;
    }

    public void setAnswers(String[] answers) {
        this.answers = answers;
    }


}
