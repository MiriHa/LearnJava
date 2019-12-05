package com.example.learnjava.ExerciseView;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learnjava.ExerciseCommunication;
import com.example.learnjava.R;


public class ExerciseViewAnswer extends Fragment {

    private ExerciseCommunication mListener;

    public ExerciseViewAnswer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_exercise_view_answer, container, false);
    }

    public void sendAnswers(String answer){
        mListener.sendAnswerFromAnswerView(answer);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof ExerciseCommunication) {
            mListener = (ExerciseCommunication) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement ExerciseCommunication");
        }

//        try {
//            mListener = (ExerciseCommunication) context;
//        } catch (ClassCastException e) {
//            throw new ClassCastException(context.toString()
//                    + " must implement ExerciseCommunication");
//        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

}
