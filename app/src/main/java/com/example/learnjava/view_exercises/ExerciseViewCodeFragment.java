package com.example.learnjava.view_exercises;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnjava.controller.Controller;
import com.example.learnjava.controller.ExerciseCommunication;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;

import java.util.Calendar;
import java.util.Date;
import java.util.Objects;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * This is the View to give an code answer.
 * ViewType: 6
 */

public class ExerciseViewCodeFragment extends Fragment {


        private ExerciseCommunication mListener;


        private ModelTask currentTask;
        private Controller progressController;

        private EditText answerEditText;
        private Button hintButton;

        int counterCheck = 0;

        private Date entered;

        public ExerciseViewCodeFragment() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_exercise_view_code, container, false);
            entered = Calendar.getInstance().getTime();
            progressController = (Controller) getActivity().getApplicationContext();

            //get the currentTask
            receiveCurrentTask();

            TextView exerciseName = view.findViewById(R.id.exerciseNameCode);
            exerciseName.setText(currentTask.getTaskName());

            TextView exerciseText = view.findViewById(R.id.exerciseTextCode);
            exerciseText.setText(currentTask.getTaskText());
            answerEditText = view.findViewById(R.id.CodeTextInput);
            answerEditText.setText(currentTask.getContentStringArray()[0]);

            Button nextButton = view.findViewById(R.id.nextButtonExerciseCode);
            Button skipButton = view.findViewById(R.id.OnlyCheckButtonExerciseCode);

            if(progressController.checkTasks(getContext(),currentTask)) {
                Log.i("M_Exercise_VIEW_CODE", "checkExericse and skip");
                //nextButton.setText(R.string.Skip_Text);
                skipButton.setVisibility(View.VISIBLE);
            }
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("M_EXERCISE_VIEW_CODE", "Nextbuttonclicked");
                    checkAnswers();
                    counterCheck += 1;
                    checkHint();
                    //Hide the Keyboard
                    InputMethodManager inputMethodManager = (InputMethodManager) Objects.requireNonNull(getActivity()).getSystemService(INPUT_METHOD_SERVICE);
                    assert inputMethodManager != null;
                    inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
                }
            });
            skipButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.justOpenNext();
                }
            });

            hintButton = view.findViewById(R.id.hintButtonCode);
            checkHint();
            hintButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showHint();
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


    private void checkHint(){
        if (counterCheck >= 5){
            hintButton.setEnabled(true);
            hintButton.setClickable(true);
            hintButton.setBackground(getResources().getDrawable(R.drawable.hint_button));
        }
    }

    private void showHint(){
        progressController.makeaLog(getContext(),Calendar.getInstance().getTime(), "SHOW_SOLUTION","in exercise Answer");
        answerEditText.setText(currentTask.getSolutionString());
        Log.i("M_EXERCISE_VIEW_ANSWER","showhint: "+currentTask.getSolutionString()+" counter: "+counterCheck);
    }


        private void checkAnswers() {
            String userInput = answerEditText.getText().toString();
                if (userInput.isEmpty()) {
                    Toast.makeText(getContext(), "Pleas enter an answer", Toast.LENGTH_SHORT).show();
                } else {
                    String userAnswer = userInput.replaceAll("\\s+","");
                    userAnswer = userAnswer.replace("\n", "").replace("\r", "");

                    String answer = currentTask.getSolutionString().replaceAll("\\s+","");
                    answer = answer.replaceAll("\n", "").replaceAll("\r","");

                    Log.i("M_EXERCISE_VIEW_CODE", "check answer: " + userInput + userAnswer+" solution: " + currentTask.getSolutionString());

                    Date ended;
                       if (answer.equalsIgnoreCase(userAnswer)) {
                           ended = Calendar.getInstance().getTime();
                           String duration = progressController.calculateDuration(entered, ended);
                        progressController.makeaDurationLog(getContext(), Calendar.getInstance().getTime(), "EXERCISE_CODE_FRAGMENT_RIGHT", "number: " + currentTask.getTaskNumber() + " section: "+currentTask.getSectionNumber()+" viewtype: "+currentTask.getExerciseViewType()+" userInput: " + userInput, duration);
                        mListener.sendAnswerFromExerciseView(true);
                        Log.i("M_EXERCISE_VIEW_CODE", " send answer: true");
                    } else {
                        Log.i("M ANSWER", " was wrong");
                           ended = Calendar.getInstance().getTime();
                           //TODO maybe set entered new? only when try again clicked? -> setter
                           String duration = progressController.calculateDuration(entered, ended);
                           progressController.makeaDurationLog(getContext(),Calendar.getInstance().getTime(), "EXERCISE_CODE_FRAGMENT_WRONG", "number: " + currentTask.getTaskNumber() + " section: "+currentTask.getSectionNumber()+" viewtype: "+currentTask.getExerciseViewType()+" userInput: " + userInput, duration);
                        mListener.sendAnswerFromExerciseView(false);
                        Log.i("M_EXERCISE_VIEW_CODE", "send answer: false");
                    }
                }
            }
//        }

        public void setExerciseCommunication(ExerciseCommunication callback) {
            Log.d("M_EXERCISE_VIEW_CODE", " setMlistenere");
            this.mListener = callback;
        }


        private void receiveCurrentTask() {
            this.currentTask = mListener.sendCurrentTask();
        }


        public void reset(){
           // answerEditText.setText(currentTask.getContentStringArray()[0]);
            //do nothing so that the user can check his errors
        }


}

