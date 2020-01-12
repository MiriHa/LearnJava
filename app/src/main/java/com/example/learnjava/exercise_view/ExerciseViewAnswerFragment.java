package com.example.learnjava.exercise_view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnjava.Controller;
import com.example.learnjava.ExerciseCommunication;
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
            if (userInput.isEmpty()) {
                Toast.makeText(getContext(), "Pleas enter an answer", Toast.LENGTH_SHORT).show();
            } else {
                String userAnswer = userInput.replaceAll("\\s+","");
                Log.i("M_EXERCISE_VIEW_ANSWER", "check answer: " + userInput + " solution: " + currentTask.getSolutionString());
                progressController.makeaLog(Calendar.getInstance().getTime(), "EXERCISE_ANSWER_FRAGMENT", "number: " + currentTask.getTaskNumber() + " userInput: " + userInput);
                //TODO ingore case? when code answer not?  -> in code view fragment?
                if (currentTask.getSolutionString().equalsIgnoreCase(userAnswer)) {
                    mListener.sendAnswerFromExerciseView(true);
                    Log.i("M_EXERCISE_VIEW_ANSWER", " send answer: true");
                } else {
                    Log.i("M ANSWER", " was wrong");
                    mListener.sendAnswerFromExerciseView(false);
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
                myTextView.setPadding(10, 6, 10, 6);
                //TODO find a better way or see if its in packages
                //  myTextView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                myTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                textHolder.addView(myTextView);
//                }
            }
        }
    }

    private void receiveCurrentTask() {
        this.currentTask = mListener.sendCurrentTask();
    }


    public void reset(){
        editText.setText(" ");
    }


}

