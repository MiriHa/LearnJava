package com.lmu.learnjava.view_exercises;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lmu.learnjava.controller.Controller;
import com.lmu.learnjava.controller.ExerciseCommunication;
import com.lmu.learnjava.R;
import com.lmu.learnjava.models.ModelTask;
import com.jmedeisis.draglinearlayout.DragLinearLayout;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


/**
 * This is the View order lines in correct order.
 * ViewType: 5
 */

public class ExerciseViewOrderFragment extends Fragment {

        private ExerciseCommunication mListener;


        private ModelTask currentTask;
        private Controller progressController;

        private DragLinearLayout contentHolder;

        private View currentView;

        private Date entered;

        public ExerciseViewOrderFragment() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @SuppressLint("ClickableViewAccessibility")
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.fragment_exercise_view_order, container, false);
            entered = Calendar.getInstance().getTime();
            currentView = view;
            progressController = (Controller) getActivity().getApplicationContext();

            //get the currentTask
            receiveCurrentTask();

            //Populate the Arrays

            TextView exerciseName = view.findViewById(R.id.exerciseNameOrder);
            exerciseName.setText(currentTask.getTaskName());

            TextView taskText = view.findViewById(R.id.exerciseTextOrder);
            contentHolder = view.findViewById(R.id.contentHolderOrderRow);

            //set the Textviews and the InstructionText
            taskText.setText(currentTask.getTaskText());
            setDynamicLayout();

            //Make the TextViews draggable

            makeDynmaivChilds();


            Button nextButton = view.findViewById(R.id.nextButtonExerciseOrder);
            Button skipButton = view.findViewById(R.id.OnlyCheckButtonExerciseOrder);

            if (progressController.checkTasks(getContext(),currentTask)) {
                Log.i("M_Exercise_VIEW_ORDER", "checkExercise and skip");
                skipButton.setVisibility(View.VISIBLE);
            }
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("M_EXERCISE_VIEW_ORDER", "check button pressed");
                    checkAnswers();
                }
            });
            skipButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mListener.justOpenNext();
                }
            });

            Button hintButton = view.findViewById(R.id.hintButtonOrder);
            hintButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });

            return view;
        }

        private void setDynamicLayout() {

            String[] contentArray = currentTask.getContentStringArray();


            Log.i("M_EXERCISE_VIEW_ORDER", "setLayout: answerArray: ");

            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams mParamsWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            //created the Lines in the ContentHolder
            for (int i = 0; i < contentArray.length; i++) {
                //one array element holds the Content of a row

                Log.i("M_EXERCISE_VIEW_ORDER", "getContentArray, textParts: " + "contentLength: " + contentArray.length);

                LinearLayout linearLayout = new LinearLayout(getContext());
                linearLayout.setLayoutParams(mParams);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                linearLayout.setPadding(8, 8, 4, 8);
                linearLayout.setTag(i);
                linearLayout.setBackground(getResources().getDrawable(R.drawable.grey_line));

                    TextView textView1 = new TextView((getContext()));
                    textView1.setLayoutParams(mParamsWrap);
                    textView1.setText(contentArray[i]);
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    Typeface typeface = ResourcesCompat.getFont(Objects.requireNonNull(getContext()), R.font.inputmonocompressed_regular);
                    textView1.setTypeface(typeface);
                    textView1.setTag("TEXT"+i);
                    Log.i("M_EXERCISE_VIEW_ORDER", " content: " + i + " " + contentArray[i]);

                    linearLayout.addView(textView1);
                    contentHolder.addView(linearLayout);
            }
        }


        // answers are in an int array saved. The sequence gives the right Order, each TextView has

        private void checkAnswers() {

                //fetch the SolutionInts
                String[] solutionTags = currentTask.getSolutionStringArray();

                //fetch the userInput
                String[] userSolution = new String[solutionTags.length];
                for (int i = 0; i < solutionTags.length; i++) {
                    LinearLayout linearLayout = currentView.findViewWithTag(i);
                    userSolution[i] = (String) linearLayout.getChildAt(0).getTag();
                }

            Log.i("M_ORDER","solutiontags: "+ Arrays.toString(solutionTags) +" usertags:"+ Arrays.toString(userSolution));
                Date ended;
                if (Arrays.equals(solutionTags, userSolution)) {
                    ended = Calendar.getInstance().getTime();
                    String duration = progressController.calculateDuration(entered, ended);
                    progressController.makeaDurationLog(getContext(),Calendar.getInstance().getTime(), "EXERCISE_ORDER_FRAGMENT_RIGHT", "number: " + currentTask.getTaskNumber() + " section: "+currentTask.getSectionNumber()+" viewtype: "+currentTask.getExerciseViewType()+" userInput: " + Arrays.toString(userSolution),duration);
                    mListener.sendAnswerFromExerciseView(true);
                    Log.i("M_EXERCISE_VIEW_ORDER", " send answer: true");
                } else {
                    Log.i("ANSWER", " was wrong");
                    ended = Calendar.getInstance().getTime();
                    String duration = progressController.calculateDuration(entered, ended);
                    progressController.makeaDurationLog(getContext(),Calendar.getInstance().getTime(), "EXERCISE_ORDER_FRAGMENT_WRONG", "number: " + currentTask.getTaskNumber() + " section: "+currentTask.getSectionNumber()+" viewtype: "+currentTask.getExerciseViewType()+" userInput: " + Arrays.toString(userSolution),duration);
                    mListener.sendAnswerFromExerciseView(false);
                    Log.i("M_EXERCISE_VIEW_ORDER", " send answer: false");
                }
            }



        private void receiveCurrentTask() {
            this.currentTask = mListener.sendCurrentTask();
        }

    public void setExerciseCommunication(ExerciseCommunication callback) {
        Log.d("M_EXERCISE_VIEW_CODE", " setMlistenere");
        this.mListener = callback;
    }

    @Override
    public void onAttach(@NonNull Context context) {
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

    private void makeDynmaivChilds(){
        for(int i = 0; i < contentHolder.getChildCount(); i++){
            View child = contentHolder.getChildAt(i);
            contentHolder.setViewDraggable(child, child);
        }

        contentHolder.setOnViewSwapListener(new DragLinearLayout.OnViewSwapListener() {
            @Override
            public void onSwap(View firstView, int firstPosition,
                               View secondView, int secondPosition) {
                // update data, etc..
                firstView.setTag(secondPosition);
                secondView.setTag(firstPosition);
            }
        });
    }

    public void reset() {
        contentHolder.removeAllViews();
        setDynamicLayout();
        makeDynmaivChilds();
        }
    }


