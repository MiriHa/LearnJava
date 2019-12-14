package com.example.learnjava.exercise_view;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnjava.Controller;
import com.example.learnjava.ExerciseCommunication;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * This is the View to give an text answer.
 * ViewType: 1
 */

public class ExerciseViewAnswerFragment extends Fragment {

    private ExerciseCommunication mListener;


    private ModelTask currentTask;
    private Controller progressController;

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
        progressController = (Controller) getActivity().getApplicationContext();

        //get the currentTask
        receiveCurrentTask();

        exerciseViewHolder = view.findViewById(R.id.contentHolderAnswer);
        TextView exerciseText = view.findViewById(R.id.exerciseTextAnswer);
        exerciseText.setText(currentTask.getTaskText());
        editText = view.findViewById(R.id.editTextAnswer);
        nextButton = view.findViewById(R.id.nextButtonExerciseAnswer);

        if(progressController.checkExercise(currentTask)) {
            Log.i("MExerciseVIEW", "checkExericse and skip");
            nextButton.setText("Skip");
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("M BUTTONCLICKED", " in AnswerView");
                checkAnswers();
                //Hide the Keyboard
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
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
        if (progressController.checkExercise(currentTask) && userInput.isEmpty()) {
            Log.i("MExerciseVIEW", "checkExericse and skip");
            mListener.justOpenNext();
            //TODO listnefor textinput, when input change skip to check
        } else {
            if (userInput.isEmpty()) {
                Toast.makeText(getContext(), "Pleas enter an answer", Toast.LENGTH_SHORT).show();
            } else {
                String userAnswer = userInput.replaceAll("\\s+","");
                Log.i("M CheckAnswers", " anser: " + userInput + " solution: " + currentTask.getSolutionString());
                //TODO ingore case? when code answer not?  -> in code view fragment?
                if (currentTask.getSolutionString().equalsIgnoreCase(userAnswer)) {
                    mListener.sendAnswerFromExerciseView(true);
                    Log.i("MSENDANSWERFROMEXERCISE", " answer: true");
                } else {
                    Log.i("M ANSWER", " was wrong");
                    mListener.sendAnswerFromExerciseView(false);
                    Log.i("MSENDANSWERFROMEXERCISE", " answer: false");
                }
            }
        }
    }

    public void setExerciseCommunication(ExerciseCommunication callback) {
        Log.d("M SETEXERCISECOMM", " setMlistenere");
        this.mListener = callback;
    }


    private void receiveCurrentTask() {
        this.currentTask = mListener.sendCurrentTask();
    }


    public void reset(){
        editText.setText(" ");
    }


}

