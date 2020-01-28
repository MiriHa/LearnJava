package com.example.learnjava.view_exercises;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.text.InputFilter;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnjava.controller.Controller;
import com.example.learnjava.controller.ExerciseCommunication;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import static android.content.Context.INPUT_METHOD_SERVICE;

/**
 * This is the exercise View to give a fill in the Blanks Answer.
 * ViewType: 3
 */

public class ExerciseViewFillBlanksFragment extends Fragment {

    private ExerciseCommunication mListener;

    private ModelTask currentTask;
    private Controller progressController;

    private String[] userSolutionArray;
    private String[] solutionArray;
    private ArrayList<String> tags = new ArrayList<>();

    private LinearLayout blankHolder;
    private Button hintButton;

    private int counterCheck = 0;

    private Date entered;


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
        entered = Calendar.getInstance().getTime();
        progressController = (Controller) getActivity().getApplicationContext();

        //get the currentTask
        receiveCurrentTask();



        //get the solution
        userSolutionArray = new String[currentTask.getSolutionStringArray().length];
        solutionArray = currentTask.getSolutionStringArray();


        //Set the exercise task teext
        TextView exerciseName = view.findViewById(R.id.exerciseNameBlanks);
        exerciseName.setText(currentTask.getTaskName());

        TextView exerciseTaskText = view.findViewById(R.id.exerciseBlanksText);
        exerciseTaskText.setText(currentTask.getTaskText());


        blankHolder = view.findViewById(R.id.exerciseBlanksAnswerHolder);
        Button nextButton = view.findViewById(R.id.nextButtonExerciseAnswer);
        Button skipButton = view.findViewById(R.id.OnlyCheckButtonExerciseBlanks);

        if(progressController.checkTasks(getContext(),currentTask)) {
            Log.i("M_FILLBLANKS", "checkExericse and skip");
            skipButton.setVisibility(View.VISIBLE);
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("BUTTONCLICKED", " in AnswerView");
                checkAnswers();
                counterCheck += 1;
                checkHint();
                //Hide the keyboard
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


        hintButton = view.findViewById(R.id.hintButtonBlanks);
        checkHint();
        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showHint();
            }
        });

        //set the Layout with the Blanks needed
        setDynamicLayout();

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
        String[] solutions = currentTask.getSolutionStringArray();

        for(int i=0; i< tags.size(); i++){
            EditText editText = blankHolder.findViewWithTag(tags.get(i));
            editText.setText(solutions[i]);
        }
        progressController.makeaLog(getContext(),Calendar.getInstance().getTime(), "SHOW_SOLUTION","in exercise Answer");
        Log.i("M_EXERCISE_VIEW_ANSWER","showhint: "+currentTask.getSolutionString()+" counter: "+counterCheck);
    }


    /**
     * Check if the user answers were true.
     */
    private void checkAnswers() {
        Log.i("CheckAnswers", "solution");

        boolean wasEmpty = false;

            //get answers from edittexts and add them to the userSolutionArray
            for (int i = 0; i < tags.size(); i++) {
                EditText myEditText = blankHolder.findViewWithTag(tags.get(i));
                String solution = myEditText.getText().toString();
                if (solution.isEmpty()) {
                    wasEmpty = true;
                    break;
                } else {
                    userSolutionArray[i] = solution;
                }
            }
            //check if the answers are right and send it to the fragment
            if (wasEmpty) {
                Toast.makeText(getContext(), "Pleas enter all answers", Toast.LENGTH_SHORT).show();
            } else {
                Date ended;
                if (Arrays.equals(solutionArray, userSolutionArray)) {
                    ended = Calendar.getInstance().getTime();
                    String duration = progressController.calculateDuration(entered, ended);
                    progressController.makeaDurationLog(getContext(),Calendar.getInstance().getTime(), "EXERCISE_FILLBLANKS_FRAGMENT_RIGHT", "number: " + currentTask.getTaskNumber() + " section: "+currentTask.getSectionNumber()+" viewtype: "+currentTask.getExerciseViewType()+" userInput: " + Arrays.toString(userSolutionArray),duration);
                    mListener.sendAnswerFromExerciseView(true);
                    Log.i("SENDANSWERFROMEXERCISE", " answer: true");
                } else {
                    Log.i("ANSWER", " was wrong");
                    ended = Calendar.getInstance().getTime();
                    String duration = progressController.calculateDuration(entered, ended);
                    progressController.makeaDurationLog(getContext(), Calendar.getInstance().getTime(), "EXERCISE_FILLBLANKS_FRAGMENT_WRONG", "number: " + currentTask.getTaskNumber() + " section: "+currentTask.getSectionNumber()+" viewtype: "+currentTask.getExerciseViewType()+" userInput: " + Arrays.toString(userSolutionArray),duration);
                    mListener.sendAnswerFromExerciseView(false);
                    Log.i("SENDANSWERFROMEXERCISE", " answer: false");
                }
            }
        }


    public void setExerciseCommunication(ExerciseCommunication callback) {
        Log.d("SETEXERCISECOMM", " setMlistenere");
        this.mListener = callback;
    }


    private void receiveCurrentTask() {
        this.currentTask = mListener.sendCurrentTask();
    }


    /**
     * Set the Layout for the exercise:
     * Strings with @ seperatet, # marks where the blanks should go
     */
    private void setDynamicLayout() {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        String[] contentArray = currentTask.getContentStringArray();
        int counter = 0;

        for (int i = 0; i < contentArray.length; i++) {
            //one array element holds the Content of a row
            String[] textParts = contentArray[i].split("@");

            Log.i("M_FILLBLANKS","getContentArray, textParts: " + textParts.toString());
            LinearLayout rowHolder = new LinearLayout(getContext());
            rowHolder.setLayoutParams(mParams);
            rowHolder.setPadding(10,0,0,0);
            rowHolder.setOrientation(LinearLayout.HORIZONTAL);


            for (int j = 0; j < textParts.length; j++) {

                //a Space to indicate a editText needed, set a dag so we can find the solution
                if (textParts[j].equals("#")) {
                    Log.i("M_FILLBLANKS","textParts need EditText");
                    //Set A editText with a Task
                    EditText myEditText = new EditText(getContext());
                    myEditText.setLayoutParams(mParams);
                    Log.i("M_FILLBLANKS","counter "+counter + " answer: "+solutionArray[counter].length());
                    int maxLength = (solutionArray[counter].length())+1;
                    counter += 1;
                    //int maxLength = solutionArray.length;
                   // Log.i("FILLBLANKS","maxLength: " + maxLength);
                    myEditText.setHint("    ");
                    myEditText.setFilters(new InputFilter[] {new InputFilter.LengthFilter(maxLength)});
                    myEditText.setMaxLines(1);
                    myEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                    Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.inputmonocompressed_regular);
                    myEditText.setTypeface(typeface);
                    myEditText.setSingleLine(true);
                    String tag = "fillBlankAnswer" + i + j;
                    myEditText.setTag(tag);
                    tags.add(tag);
                    rowHolder.addView(myEditText);

                    //a text so a textview is needed
                } else {
                    Log.i("M_FILLBLANKS","textParts need TextView");
                    TextView myTextView = new TextView(getContext());
                    myTextView.setLayoutParams(mParams);
                    myTextView.setText(textParts[j]);
                    myTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                    Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.inputmonocompressed_regular);
                    myTextView.setTypeface(typeface);
                    //myTextView.setPadding(6, 6, 6, 6);
                    rowHolder.addView(myTextView);

                }
            }
            blankHolder.addView(rowHolder);
        }


    }


    /**
     * Reset the exxercise so that the user can try again
     */
    public void reset(){

        for(int i=0;i<tags.size();i++){
            EditText myeditText = blankHolder.findViewWithTag(tags.get(i));
            myeditText.setText("");
        }
    }
}

