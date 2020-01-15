package com.example.learnjava.view_exercises;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnjava.controller.Controller;
import com.example.learnjava.controller.ExerciseCommunication;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * This is the ExerciseView to give a multiple choice answer.
 * ViewType: 2
 */

public class ExerciseViewChoiceFragment extends Fragment {

    private ExerciseCommunication mListener;


    private ModelTask currentTask;
    private Controller progressController;

    private String[] answerChoices;
    private int userAnswer = 0;

    private RadioGroup answerGroup;
    ArrayList<String> tags = new ArrayList<>();




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
        progressController = (Controller) getActivity().getApplicationContext();


        //get the currentTask
        receiveCurrentTask();

        TextView exerciseText = view.findViewById(R.id.exerciseTextChoice);
        exerciseText.setText(currentTask.getTaskText());

        answerChoices = currentTask.getSolutionStringArray();

        RadioButton answer1 = view.findViewById(R.id.answer1);
        answer1.setText(answerChoices[0]);
        RadioButton answer2 = view.findViewById(R.id.answer2);
        answer2.setText(answerChoices[1]);
        RadioButton answer3 = view.findViewById(R.id.answer3);
        answer3.setText(answerChoices[2]);
        RadioButton answer4 = view.findViewById(R.id.answer4);
        answer4.setText(answerChoices[3]);

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
        Button nextButton = view.findViewById(R.id.nextButtonExerciseChoice);
        final Button skipButton = view.findViewById(R.id.OnlyCheckButtonExerciseChoice);
        if(progressController.checkTasks(getContext(), currentTask)) {
            Log.i("M_Exercise_VIEW_CHOICE", "checkExericse and skip");
            skipButton.setVisibility(View.VISIBLE);
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("M_EXERCISE_VIEW_CHOICE", " in ChoiceView");
                checkAnswers();
            }
        });
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.justOpenNext();
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
            if (userAnswer == 0) {
                Toast.makeText(getContext(), "Please choose an answer", Toast.LENGTH_SHORT).show();
            } else {
                Log.i("M_EXERCISE_VIEW_CHOICE", " checkanswer: " + " solution: " + currentTask.getSolutionInt());
                if (currentTask.getSolutionInt() == userAnswer) {
                    progressController.makeaLog(Calendar.getInstance().getTime(), "EXERCISE_CHOICE_FRAGMENT_RIGHT", "number: " + currentTask.getTaskNumber() + " section: "+currentTask.getSectionNumber()+" viewtype: "+currentTask.getExerciseViewType()+" userInput: " + userAnswer);
                    mListener.sendAnswerFromExerciseView(true);
                    Log.i("M_EXERCISE_VIEW_CHOICE", " send answer: true");
                } else {
                    Log.i("M ANSWER", " was wrong");
                    progressController.makeaLog(Calendar.getInstance().getTime(), "EXERCISE_CHOICE_FRAGMENT_WRONG", "number: " + currentTask.getTaskNumber() + " section: "+currentTask.getSectionNumber()+" viewtype: "+currentTask.getExerciseViewType()+" userInput: " + userAnswer);
                    mListener.sendAnswerFromExerciseView(false);
                    Log.i("M_EXERCISE_VIEW_CHOICE", " sen answer: false");
                }
            }
        }


    public void setExerciseCommunication(ExerciseCommunication callback) {
        Log.d("M_EXERCISE_VIEW_CHOICE", " setMlistenere");
        this.mListener = callback;
    }


    private void receiveCurrentTask() {
        this.currentTask = mListener.sendCurrentTask();
    }


    //TODO for dynamiclly adding the buttons
    private void setLayout(){

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        for (int i=0; i<answerChoices.length;i++){

            RadioButton mButton = new RadioButton(getContext());
            mButton.setLayoutParams(mParams);
            mButton.setTag("answer"+i);
            //mButton.setId(R.id.Choice[i]);
            tags.add("answer"+i);
            mButton.setPadding(0,6,0,0);
            mButton.setTextSize(TypedValue.COMPLEX_UNIT_SP,18);

            answerGroup.addView(mButton);
        }

    }

    public void reset(){
        answerGroup.clearCheck();
    }

}


