package com.lmu.learnjava.controller;

import com.lmu.learnjava.models.ModelTask;

public interface ExerciseCommunication {

    ModelTask sendCurrentTask();

    void sendAnswerFromExerciseView(boolean answerChecked);

    void justOpenNext();




}
