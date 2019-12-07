package com.example.learnjava.ExerciseView;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnjava.ExerciseCommunication;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;


/**
 * This is the View to give an text answer.
 * ViewType: 1
 */

public class ExerciseViewAnswerFragment extends Fragment {

    private ExerciseCommunication mListener;


    ModelTask currentTask;

    private LinearLayout exerciseViewHolder;
    private EditText editText;
    private Button nextButton;


    public ExerciseViewAnswerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_view_answer, container, false);

        //get the currentTask
        receiveCurrentTask();

        exerciseViewHolder = view.findViewById(R.id.contentHolderAnswer);
        TextView exerciseText = view.findViewById(R.id.exerciseTextAnswer);
        editText = view.findViewById(R.id.editTextAnswer);
        nextButton = view.findViewById(R.id.nextButtonExerciseAnswer);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("BUTTONCLICKED", " in AnswerView");
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
        String userInput = editText.getText().toString();
        Log.i("CheckAnswers", " anser: " + userInput + " solution: " + currentTask.getSolutionString());
        if (currentTask.getSolutionString().equals(userInput)) {
            mListener.sendAnswerFromExerciseView(true);
            Log.i("SENDANSWERFROMEXERCISE", " answer: true");
            Toast.makeText(getContext(), "Answer was right.", Toast.LENGTH_SHORT).show();
        } else {
            Log.i("ANSWER", " was wrong");
            mListener.sendAnswerFromExerciseView(false);
            Log.i("SENDANSWERFROMEXERCISE", " answer: false");
            Toast.makeText(getContext(), "Answer was false", Toast.LENGTH_SHORT).show();
            //TODO give feedback that false answer??
        }
    }

    public void setExerciseCommunication(ExerciseCommunication callback) {
        Log.d("SETEXERCISECOMM", " setMlistenere");
        this.mListener = callback;
    }


    private void receiveCurrentTask() {
        this.currentTask = mListener.sendCurrentTask();
    }


}

