package com.example.learnjava;

import com.example.learnjava.models.ModelTask;

public interface ExerciseCommunication {

    ModelTask sendCurrentTask();

    void sendAnswerFromExerciseView(boolean answerChecked);

    void justOpenNext();




}
