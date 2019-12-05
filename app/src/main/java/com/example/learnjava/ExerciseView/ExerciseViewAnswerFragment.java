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

import com.example.learnjava.R;
import com.example.learnjava.lessons.ExerciseFragment;
import com.example.learnjava.lessons.ExerciseFragment.ExerciseCommunication;
import com.example.learnjava.models.ModelTask;


public class ExerciseViewAnswerFragment extends Fragment implements ExerciseCommunication {

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

        currentTask = mListener.sendCurrentTask();

        exerciseViewHolder = view.findViewById(R.id.contentHolderAnswer);
        TextView exerciseText = view.findViewById(R.id.exerciseTextAnswer);
        editText = view.findViewById(R.id.editTextAnswer);
        nextButton = view.findViewById(R.id.nextButtonExerciseAnswer);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswers();
            }
        });


        return view;
    }

    @Override
    public void onAttach(Context context) {
//        if (context instanceof ExerciseVIewCommunication) {
//            mListener = (ExerciseVIewCommunication) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement ExerciseVIewCommunication");
//        }

        try {
            mListener = (ExerciseCommunication) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement ExerciseVIewCommunication");
        }

        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }



    private void checkAnswers(){
        String userInput = editText.getText().toString();
        if(currentTask.getSolutionString().equals(userInput)){
          //  mListener.sendAnswerFromExerciseView(true);
        }
        else {
            Log.i("ANSWER", " was wrong");
            //TODO give feedback that false answer??
        }
    }

    public void setExerciseCommunication(ExerciseFragment.ExerciseCommunication callback) {
        this.mListener = callback;
    }

    @Override
    public ModelTask sendCurrentTask() {
        return null;
    }

    @Override
    public void sendAnswerFromExerciseViev(boolean answerChecked) {

    }





    //Needed when more and one questions is
//    private void setLayout(int howmany) {
//
//        LinearLayout.LayoutParams mRparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//
//        for (int i = 0; i <= howmany; i++) {
//            TextView myTextView = new TextView(getContext());
//            myTextView.setLayoutParams(mRparams);
//            EditText myEditText = new EditText(getContext());
//            myEditText.setLayoutParams(mRparams);
//
//            exerciseViewHolder.addView(myTextView);
//            exerciseViewHolder.addView(myEditText);
//        }

    }

