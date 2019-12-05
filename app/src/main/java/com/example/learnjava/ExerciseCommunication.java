package com.example.learnjava;

public interface ExerciseCommunication {

    void sendAnswerFromAnswerView(String answer);

    void sendAnswerFromChoiceView(int answer);

    void sendAnswerFromFillBlanksView(String[] answewrs);

    void sendAnswerFormOrderLinesVIew(int[] ansers);

   // void sendAnswersFromDragDropView();
}
