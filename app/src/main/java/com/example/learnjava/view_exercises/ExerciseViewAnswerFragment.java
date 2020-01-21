package com.example.learnjava.view_exercises;

import android.content.Context;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnjava.controller.Controller;
import com.example.learnjava.controller.ExerciseCommunication;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;

import java.util.Calendar;

import static android.content.Context.INPUT_METHOD_SERVICE;


/**
 * This is the View to give an text answer.
 * ViewType: 1
 */

public class ExerciseViewAnswerFragment extends Fragment {

    private ExerciseCommunication mListener;


    private ModelTask currentTask;
    private Controller progressController;

    private LinearLayout textHolder;
    private EditText editText;
    private TextView exerciseText;
     private Button hintButton;

    private int counterCheck = 0;

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

        TextView exerciseName = view.findViewById(R.id.exerciseNameAnswer);
        exerciseName.setText(currentTask.getTaskName());

        textHolder = view.findViewById(R.id.answerTextHolder);
        //exerciseText = view.findViewById(R.id.exerciseTextAnswer);
        setText();

        editText = view.findViewById(R.id.editTextAnswer);
        Button nextButton = view.findViewById(R.id.nextButtonExerciseAnswer);
        final Button skipButton = view.findViewById(R.id.OnlyCheckButtonExerciseAnswer);

        if(progressController.checkTasks(getContext(), currentTask)) {
            Log.i("M_Exercise_VIEW_ANSWER", "checkExericse and skip");
            skipButton.setVisibility(View.VISIBLE);
        }

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                counterCheck += 1;
                checkHint();
                Log.i("M_EXERCISE_VIEW_ANSWER", "buttonclicked in AnswerView");
                checkAnswers();
                //Hide the Keyboard
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(),0);
            }
        });
        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.justOpenNext();
            }
        });

       hintButton = view.findViewById(R.id.hintButtonAnswer);
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
        hintButton.setEnabled(false);
    }




    private void checkHint(){
        if (counterCheck >= 5){
            hintButton.setEnabled(true);
            hintButton.setClickable(true);
            hintButton.setBackground(getResources().getDrawable(R.drawable.hint_button));
        }
    }

    private void showHint(){
        editText.setText(currentTask.getSolutionStringArray()[0]);
        progressController.makeaLog(getContext(),Calendar.getInstance().getTime(), "SHOW_SOLUTION","in exercise Answer");
        Log.i("M_EXERCISE_VIEW_ANSWER","showhint: "+currentTask.getSolutionString()+" counter: "+counterCheck);
    }

    private void checkAnswers() {
        String userInput = editText.getText().toString();
            if (userInput.isEmpty()) {
                Toast.makeText(getContext(), "Pleas enter an answer", Toast.LENGTH_SHORT).show();
            } else {
                String userAnswer = userInput.replaceAll("\\s+","");
                Log.i("M_EXERCISE_VIEW_ANSWER", "check answer: " + userInput + " solution: " + currentTask.getSolutionStringArray());

                if (checkArrays(currentTask.getSolutionStringArray(),userAnswer)) {
                    mListener.sendAnswerFromExerciseView(true);
                    progressController.makeaLog(getContext(), Calendar.getInstance().getTime(), "EXERCISE_ANSWER_FRAGMENT_RIGHT", "number: " + currentTask.getTaskNumber() + " section: "+currentTask.getSectionNumber()+" viewtype: "+currentTask.getExerciseViewType()+" userInput: " + userInput);
                    Log.i("M_EXERCISE_VIEW_ANSWER", " send answer: true");
                } else {
                    Log.i("M ANSWER", " was wrong");
                    mListener.sendAnswerFromExerciseView(false);
                    progressController.makeaLog(getContext(),Calendar.getInstance().getTime(), "EXERCISE_ANSWER_FRAGMENT_WRONG", "number: " + currentTask.getTaskNumber() + " section: "+currentTask.getSectionNumber()+" viewtype: "+currentTask.getExerciseViewType()+" userInput: " + userInput);
                    Log.i("M_EXERCISE_VIEW_ANSWER", "send answer: false");
                }
            }
        }


    public void setExerciseCommunication(ExerciseCommunication callback) {
        Log.d("M_EXERCISE_VIEW_ANSWER", " setMlistenere");
        this.mListener = callback;
    }

    private void setText() {
        String[] splitString = currentTask.getTaskText().split("@");

        for (String element : splitString) {

            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams mParamsFit = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            if (String.valueOf(element.charAt(0)).equals("_")) {

                String uri = "@drawable/" + element;  // where myresource (without the extension) is the file
                String name = getActivity().getPackageName();

                int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());

                Drawable res = getResources().getDrawable(imageResource);

                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(mParamsFit);
                imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                imageView.setClickable(false);
                imageView.setBackgroundColor(getResources().getColor(R.color.grey));
                imageView.setAdjustViewBounds(true);
                imageView.setImageDrawable(res);
                textHolder.addView(imageView);

            } else {

                TextView myTextView = new TextView(getContext());
                myTextView.setLayoutParams(mParams);
                myTextView.setText(Html.fromHtml(element));
                Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.trixiesans);
                myTextView.setTypeface(typeface);
                myTextView.setPadding(3, 6, 3, 6);
                //TODO find a better way or see if its in packages
                //  myTextView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                myTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                textHolder.addView(myTextView);
//                }
            }
        }
    }

    private boolean checkArrays(String[] arr, String targetValue) {
        for(String s: arr){
            if(s.equalsIgnoreCase(targetValue))
                return true;
        }
        return false;
    }

    private void receiveCurrentTask() {
        this.currentTask = mListener.sendCurrentTask();
    }


    public void reset(){
        editText.setText(" ");
    }


}

