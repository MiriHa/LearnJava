package com.example.learnjava;

import com.example.learnjava.models.ModelTask;

public interface ExerciseVIewCommunication {

    //TODO maybe use this also to communitycate between fragment and activity?

    ModelTask sendCurrentTask();

    void sendAnswerFromExerciseView(boolean anserChecked);

}
