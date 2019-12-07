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

import java.util.Arrays;

/**
 * This is the exercise View to give a fill in the Blanks Answer.
 * ViewType: 3
 */

public class ExerciseViewFillBlanksFragment extends Fragment {

    private ExerciseCommunication mListener;


    ModelTask currentTask;

    String[] userSolutionArray;

    private Button nextButton;
    private LinearLayout blankHolder;


    public ExerciseViewFillBlanksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise_view_fill_blanks, container, false);

        //get the currentTask
        receiveCurrentTask();
        setLayout();
        userSolutionArray = new String[currentTask.getSolutionStringArray().length];

        TextView exerciseText = view.findViewById(R.id.exerciseTextAnswer);
        blankHolder = view.findViewById(R.id.exerciseBlanksAnswerHolder);
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
        Log.i("CheckAnswers", "solution");
        String[] solutionArray = currentTask.getSolutionStringArray();

        //get answers from edittexts and add them to the userSolutionArray
        for(int i = 0; i <= solutionArray.length; i++){
            EditText myEditText = (EditText) blankHolder.findViewWithTag("fillBlanksAnswer" + i);
            String solution = myEditText.getText().toString();
            userSolutionArray[i] = solution;
        }

        //check if the answers are right
        if(Arrays.equals(solutionArray, userSolutionArray)){
            mListener.sendAnswerFromExerciseView(true);
            Log.i("SENDANSWERFROMEXERCISE", " answer: true");
            Toast.makeText(getContext(), "Answer was right.", Toast.LENGTH_SHORT).show();
        }
        else {
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

    private void setLayout() {
        int blanksNeeded = currentTask.getSolutionStringArray().length;
        LinearLayout.LayoutParams mRparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i = 0; i <= blanksNeeded; i++) {
            TextView myTextView = new TextView(getContext());
            myTextView.setLayoutParams(mRparams);
            myTextView.setText("1:");
            EditText myEditText = new EditText(getContext());
            myEditText.setLayoutParams(mRparams);
            myEditText.setTag("fillBlanksAnswer" + i);

            blankHolder.addView(myTextView);
            blankHolder.addView(myEditText);
        }


    }

}

