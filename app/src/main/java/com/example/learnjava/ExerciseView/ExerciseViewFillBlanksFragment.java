package com.example.learnjava.ExerciseView;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnjava.Controller;
import com.example.learnjava.ExerciseCommunication;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;

import java.util.ArrayList;
import java.util.Arrays;

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
        progressController = (Controller) getActivity().getApplicationContext();

        //get the currentTask
        receiveCurrentTask();

        userSolutionArray = new String[currentTask.getSolutionStringArray().length];
        solutionArray = currentTask.getSolutionStringArray();

        TextView exerciseNameText = view.findViewById(R.id.exerciseTextFillBlanks);
        exerciseNameText.setText(currentTask.getTaskName());
        TextView exerciseTaskText = view.findViewById(R.id.exerciseBlanksText);
        exerciseTaskText.setText(currentTask.getTaskText());


        blankHolder = view.findViewById(R.id.exerciseBlanksAnswerHolder);
        nextButton = view.findViewById(R.id.nextButtonExerciseAnswer);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("BUTTONCLICKED", " in AnswerView");
                checkAnswers();
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


    private void checkAnswers() {
        Log.i("CheckAnswers", "solution");

        boolean wasEmpty = false;

        if (progressController.checkExercise(currentTask)) {
            Log.i("MExerciseVIEW", "checkExericse and skip");
            mListener.justOpenNext();
        } else {
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
            //check if the answers are right
            if (wasEmpty) {
                Toast.makeText(getContext(), "Pleas enter all answers", Toast.LENGTH_SHORT).show();
            } else {
                if (Arrays.equals(solutionArray, userSolutionArray)) {
                    mListener.sendAnswerFromExerciseView(true);
                    Log.i("SENDANSWERFROMEXERCISE", " answer: true");
                } else {
                    Log.i("ANSWER", " was wrong");
                    mListener.sendAnswerFromExerciseView(false);
                    Log.i("SENDANSWERFROMEXERCISE", " answer: false");
                }
            }
//        } else {
//            //get answers from edittexts and add them to the userSolutionArray
//            for (int i = 0; i < solutionArray.length; i++) {
//                EditText myEditText = blankHolder.findViewWithTag("fillBlanksAnswer" + i);
//                String solution = myEditText.getText().toString();
//                if (solution.isEmpty()) {
//                    wasEmpty = true;
//                    break;
//                } else {
//                    userSolutionArray[i] = solution;
//                }
//            }
//            //check if the answers are right
//            if (wasEmpty) {
//                Toast.makeText(getContext(), "Pleas enter all answers", Toast.LENGTH_SHORT).show();
//            } else {
//                if (Arrays.equals(solutionArray, userSolutionArray)) {
//                    mListener.sendAnswerFromExerciseView(true);
//                    Log.i("SENDANSWERFROMEXERCISE", " answer: true");
//                } else {
//                    Log.i("ANSWER", " was wrong");
//                    mListener.sendAnswerFromExerciseView(false);
//                    Log.i("SENDANSWERFROMEXERCISE", " answer: false");
//                }
//            }
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
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams mParamsWeightLeight = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 0.5);
        LinearLayout.LayoutParams mParamsWeightHeavy = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 4.5);

        for (int i = 0; i < blanksNeeded; i++) {
            LinearLayout holder = new LinearLayout(getContext());
            holder.setLayoutParams(mParams);
            holder.setWeightSum(5);
            holder.setOrientation(LinearLayout.HORIZONTAL);

            TextView myTextView = new TextView(getContext());
            myTextView.setLayoutParams(mParamsWeightHeavy);
            myTextView.setText((i + 1) + ":");
            holder.addView(myTextView);

            EditText myEditText = new EditText(getContext());
            myEditText.setLayoutParams(mParamsWeightLeight);
            myEditText.setTag("fillBlanksAnswer" + i);
            myEditText.setHint("Write Answer " + (i + 1) + " here");
            holder.addView(myEditText);

            blankHolder.addView(holder);
        }


    }

    private void setDynamicLayout() {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams mParamsSmall = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 0.5);
        LinearLayout.LayoutParams mParamsWeightHeavy = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 4.5);

        String[] contentArray = currentTask.getContentStringArray();
        for (int i = 0; i < contentArray.length; i++) {
            //one array element holds the Content of a row
            String[] textParts = contentArray[i].split("@");

            Log.i("FILLBLANKS","getContentArray, textParts: " + textParts.toString());
            LinearLayout rowHolder = new LinearLayout(getContext());
            rowHolder.setLayoutParams(mParams);
            rowHolder.setPadding(10,0,0,0);
            rowHolder.setOrientation(LinearLayout.HORIZONTAL);


            for (int j = 0; j < textParts.length; j++) {

                //This is either a text or a Space to indicate a editText needed
                if (textParts[j].equals("#")) {
                    Log.i("FILLBLANKS","textParts need EditText");
                    //Set A editText with a Task
                    EditText myEditText = new EditText(getContext());
                    myEditText.setLayoutParams(mParams);
                    myEditText.setHint("____");
                    myEditText.setMaxLines(1);
                    myEditText.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                    //myEditText.setPadding(6, 6, 6, 6);
                    String tag = "fillBlankAnswer" + i + j;
                    myEditText.setTag(tag);
                    tags.add(tag);
                    rowHolder.addView(myEditText);

                } else {
                    Log.i("FILLBLANKS","textParts need TextView");
                    TextView myTextView = new TextView(getContext());
                    myTextView.setLayoutParams(mParams);
                    myTextView.setText(textParts[j]);
                    myTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16);
                    //myTextView.setPadding(6, 6, 6, 6);
                    rowHolder.addView(myTextView);

                }
            }
            blankHolder.addView(rowHolder);
        }


    }

}

