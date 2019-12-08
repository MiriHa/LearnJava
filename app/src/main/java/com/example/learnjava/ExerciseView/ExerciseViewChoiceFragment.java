package com.example.learnjava.ExerciseView;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnjava.ExerciseCommunication;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;

/**
 * This is the ExerciseView to give a multiple choice answer.
 * ViewType: 2
 */

public class ExerciseViewChoiceFragment extends Fragment {

    private ExerciseCommunication mListener;


    private ModelTask currentTask;
    private int userAnswer;

    private Button nextButton;
    private RadioGroup answerGroup;


    public ExerciseViewChoiceFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_view_choice, container, false);

        //get the currentTask
        receiveCurrentTask();

        TextView exerciseText = view.findViewById(R.id.exerciseTextChoice);
        answerGroup = view.findViewById(R.id.answerGroup);

        answerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.answer1:
                        userAnswer = 1;
                        break;
                    case R.id.answer2:
                        userAnswer = 2;
                        break;
                    case R.id.answer3:
                        userAnswer = 3;
                        break;
                    case R.id.answer4:
                        userAnswer = 4;
                        break;
                }
            }
        });
        nextButton = view.findViewById(R.id.nextButtonExerciseChoice);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("M BUTTONCLICKED", " in ChoiceView");
                checkAnswers();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
        try {
            mListener = (ExerciseCommunication) getParentFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ExerciseCommunication");
        }

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    private void checkAnswers() {
        Log.i("M CheckAnswers", " anser: " + " solution: " + currentTask.getSolutionInt());
        if (currentTask.getSolutionInt() == userAnswer) {
            mListener.sendAnswerFromExerciseView(true);
            Toast.makeText(getContext(), "Answer was right", Toast.LENGTH_SHORT).show();
            Log.i("MSENDANSWERFROMEXERCISE", " answer: true");
        } else {
            Log.i("M ANSWER", " was wrong");
            mListener.sendAnswerFromExerciseView(false);
            Log.i("MSENDANSWERFROMEXERCISE", " answer: false");
            Toast.makeText(getContext(), "Answer was false", Toast.LENGTH_SHORT).show();
            //TODO give feedback that false answer??
        }
    }

    public void setExerciseCommunication(ExerciseCommunication callback) {
        Log.d("M SETEXERCISECOMM", " setMlistenere");
        this.mListener = callback;
    }


    private void receiveCurrentTask() {
        this.currentTask = mListener.sendCurrentTask();
    }


}


