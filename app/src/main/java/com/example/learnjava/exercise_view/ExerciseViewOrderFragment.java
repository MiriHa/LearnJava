package com.example.learnjava.exercise_view;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.learnjava.Controller;
import com.example.learnjava.ExerciseCommunication;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;
import com.example.learnjava.room_database.UserDatabase;
import com.jmedeisis.draglinearlayout.DragLinearLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


/**
 * This is the View order lines in correct order.
 * ViewType: 5
 */

public class ExerciseViewOrderFragment extends Fragment {

    //TODO use a listview and then ordering will be easer -> adapter needed see examples

        private ExerciseCommunication mListener;


        private ModelTask currentTask;
        private Controller progressController;
        private UserDatabase database;

        private String[] answerArray;
        private ArrayList<String> dropTags = new ArrayList<>();
        private ArrayList<String> dragTags = new ArrayList<>();
        private Button nextButton;
       // private LinearLayout contentHolder;
        private DragLinearLayout contentHolder;
        private View currentView;
        private TextView row1, row2, row4, row5, row3, row6;


        public ExerciseViewOrderFragment() {
            // Required empty public constructor
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View view = inflater.inflate(R.layout.fragment_exercise_view_drag_drop, container, false);
            currentView = view;
            progressController = (Controller) getActivity().getApplicationContext();


            //get the currentTask
            receiveCurrentTask();
            database = UserDatabase.getInstance(getActivity());


            contentHolder = view.findViewById(R.id.contentRowHolderOrder);
            answerArray = currentTask.getSolutionStringArray();
            row1 = view.findViewById(R.id.dragView1);
            row2 = view.findViewById(R.id.dragView2);
            row3 = view.findViewById(R.id.dragView3);
            row4 = view.findViewById(R.id.dragView4);
            row5 = view.findViewById(R.id.dragView5);
            row6 = view.findViewById(R.id.dragView6);


            TextView taskText = view.findViewById(R.id.exerciseTextDragDrop);
            taskText.setText(currentTask.getTaskText());

            setDynamicLayout();
//        setContentLayout();

            nextButton = view.findViewById(R.id.nextButtonExerciseDragDrop);
            if (progressController.checkTasks(currentTask)) {
                Log.i("M_Exercise_VIEW_ORDER", "checkExercise and skip");
                nextButton.setText(R.string.Skip);
            }
            nextButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("M_EXERCISE_VIEW_ORDER", "check button pressed");
                    checkAnswers();
                }
            });


            final Button resetButton = view.findViewById(R.id.resetButton);
            resetButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.i("M_EXERCISE_VIEW_ORDER", "Reset Button pressed");
                    reset();
                }
            });


            return view;
        }

        public void setExerciseCommunication(ExerciseCommunication callback) {
            Log.d("M_EXERCISE_VIEW_CODE", " setMlistenere");
            this.mListener = callback;
        }

        private void setDynamicLayout() {
            //Arrag length needs to bedivisble through 3 -> per row textview dropView textview
            String[] contentArray = currentTask.getContentStringArray();

            List<String> contentArrayRandom = new ArrayList<>(contentArray.length);
            Collections.addAll(contentArrayRandom, contentArray);
            Collections.shuffle(contentArrayRandom);



            Log.i("M_EXERCISE_VIEW_ORDER", "setLayout: answerArray: ");

            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams mParamsWeight = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 1.0);
            LinearLayout.LayoutParams mParamsWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            //created the Lines in the ContentHolder
            for (int i = 0; i < contentArrayRandom.size(); i++) {
                //one array element holds the Content of a row
                //TODO solit really neede? only 1 text
               // String[] textParts = contentArrayRandom.get(i).split("@");

                Log.i("M_EXERCISE_VIEW_ORDER", "getContentArray, textParts: " + "contentLength: " + contentArray.length);
//
//                    TextView textView1 = new TextView((getContext()));
//                    textView1.setLayoutParams(mParamsWrap);
//                    textView1.setText(contentArrayRandom.get(i));
//                    textView1.setPadding(8, 8, 4, 8);
//                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//                    textView1.setTag("DRAGVIEW" + i);
//                    dragTags.add("DRAGVIEW" + i);
//                    Log.i("M_EXERCISE_VIEW_ORDER", " content: " + i + " " + contentArrayRandom.get(i));
//                    contentHolder.addView(textView1);
                String tag = "dragView" + (i+1);
                if(contentArrayRandom.get(i) == null)
                    textView.setText("");
                else
                    textView.setText(contentArrayRandom.get(i));

            }

            //make all childs of DragLinearLayout draggable
            for(int m = 0; m<contentHolder.getChildCount(); m++){
                View child = contentHolder.getChildAt(m);
                contentHolder.setViewDraggable(child,child);
            }
            contentHolder.setOnViewSwapListener(new DragLinearLayout.OnViewSwapListener() {
                @Override
                public void onSwap(View firstView, int firstPosition, View secondView, int secondPosition) {

                    //TODO update data? show number am rand?

                 }
            });

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

        //right answers are in an int array saved. The seequence gives the right order and the numbers represent the Strings in
        //exerciseSolutionIntArray

        private void checkAnswers() {

            if (progressController.checkTasks(currentTask)) {
                Log.i("M_EXERCISE_VIEW_ORDER", "checkExericse and skip");
                mListener.justOpenNext();
            } else {
                //fetch the Solution and convert to StringArray
                int[] solutionInt = currentTask.getSolutionIntArray();
                String[] solutionString = new String[solutionInt.length];
                for (int i = 0; i < solutionInt.length; i++) {
                    int place = solutionInt[i];
                    solutionString[i] = answerArray[place];
                }

                //fetch the userInput
                String[] userSolution = new String[solutionInt.length];
                for (int i = 0; i < solutionInt.length; i++) {
                    TextView solView = currentView.findViewWithTag(dropTags.get(i));
                    userSolution[i] = solView.getText().toString();
                }
                progressController.makeaLog(Calendar.getInstance().getTime(), "EXERCISE_ORDER_FRAGMENT", "check answers number: " + currentTask.getTaskNumber() + " userInput: " + userSolution, database);
                //TODO check when no Answer is inputted
                if (Arrays.equals(solutionString, userSolution)) {
                    mListener.sendAnswerFromExerciseView(true);
                    Log.i("M_EXERCISE_VIEW_ORDER", " send answer: true");
                } else {
                    Log.i("ANSWER", " was wrong");
                    mListener.sendAnswerFromExerciseView(false);
                    Log.i("M_EXERCISE_VIEW_ORDER", " send answer: false");
                }
            }
        }


        private void receiveCurrentTask() {
            this.currentTask = mListener.sendCurrentTask();
        }


        public void reset() {
            //TODO reposition the lines in random order. -> set dynamic layout?
//            for (int i = 0; i < dragTags.size(); i++) {
//
//                TextView text = currentView.findViewWithTag(dragTags.get(i));
//                text.setVisibility(TextView.VISIBLE);
//            }
//
//            for (int i = 0; i < dropTags.size(); i++) {
//
//                TextView text = currentView.findViewWithTag(dropTags.get(i));
//                text.setTag(dropTags.get(i));
//                text.setTypeface(Typeface.DEFAULT);
//                text.setText("___________");
//                text.setOnDragListener(this);
//            }
//        }
        }
    }


