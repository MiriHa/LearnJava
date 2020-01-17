package com.example.learnjava.view_exercises;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnjava.controller.Controller;
import com.example.learnjava.controller.ExerciseCommunication;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;
import com.google.android.flexbox.FlexboxLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;


/**
 * This is the View to give a drag and drop answer
 * ViewType: 4
 */

public class ExerciseViewDragDropFragment extends Fragment implements View.OnDragListener, View.OnTouchListener {

    private ExerciseCommunication mListener;


    private ModelTask currentTask;
    private Controller progressController;
    private String[] answerArray;
    private ArrayList<String> dropTags = new ArrayList<>();
    private ArrayList<String> dragTags = new ArrayList<>();

    List<String> answerArrayRandom = new ArrayList<>();

    private LinearLayout contentHolder;
    private FlexboxLayout answerHolder;

    private View currentView;

    boolean wasCorrect;

    LinearLayout.LayoutParams mParamsWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


    public ExerciseViewDragDropFragment() {
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


        contentHolder = view.findViewById(R.id.contentHolderDragDrop);
        answerHolder = view.findViewById(R.id.answerHolderDragDrop);
        answerHolder.setOnDragListener(new View.OnDragListener() {
            @Override
            public boolean onDrag(View v, DragEvent event) {
                int action = event.getAction();

                switch (action) {
                    case DragEvent.ACTION_DROP:
                        Log.i("M_DRAGDROP", "Drag drop");

                        //dragView
                        TextView view = (TextView) event.getLocalState();
                        ViewGroup owner = (ViewGroup) view.getParent();
                        owner.removeView(view);

                        //Dropview
                        FlexboxLayout container = (FlexboxLayout) v;
                        container.addView(view);
                        view.setVisibility(View.VISIBLE);

                        break;

                    case DragEvent.ACTION_DRAG_ENDED:
                        Log.i("M_DRAGDROP", "Drag ended "+wasCorrect);
                        View view2 = (View) event.getLocalState();
                        view2.setVisibility(View.VISIBLE);
                        break;
                }
                return true;
            }
        });

        answerArray = currentTask.getSolutionStringArray();

        Collections.addAll(answerArrayRandom, answerArray);
        Collections.shuffle(answerArrayRandom);

        TextView taskText = view.findViewById(R.id.exerciseTextDragDrop);
        taskText.setText(currentTask.getTaskText());

        setDynamicLayout();
//        setContentLayout();

        Button nextButton = view.findViewById(R.id.nextButtonExerciseDragDrop);
        Button skipButton = view.findViewById(R.id.OnlyCheckButtonExerciseDragDrop);

        if(progressController.checkTasks(getContext(),currentTask)) {
            Log.i("M_Exercise_VIEW_DRAG", "checkExericse and skip");
            skipButton.setVisibility(View.VISIBLE);
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("M_EXERCISE_VIEW_DRAG", "check button pressed");
                checkAnswers();
            }
        });

        skipButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.justOpenNext();
            }
        });


        final Button resetButton = view.findViewById(R.id.resetButtonDragDrop);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("M_EXERCISE_VIEW_DRAG", "Reset Button pressed");
                reset();
            }
        });


        return view;
    }

    /**
     * Set the Layout dynamiclly according to the ccntent needed
     */
    private void setDynamicLayout() {
        String[] contentArray = currentTask.getContentStringArray();

        //Randomize the DropTargetViews answers


        Log.i("M_EXERCISE_VIEW_DRAG", "setLayout: answerArray: " + answerArrayRandom.toString());

//        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
//        LinearLayout.LayoutParams mParamsWeight = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 1.0);
//        LinearLayout.LayoutParams mParamsWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //created the Lines in the ContentHolder
        for (int i = 0; i < contentArray.length; i++) {
            //one array element holds the Content of a row
            String[] textParts = contentArray[i].split("@");

            Log.i("M_EXERCISE_VIEW_DRAG","getContentArray, textParts: " + textParts.toString() + "contentLength: " + contentArray.length);
            LinearLayout rowHolder = new LinearLayout(getContext());
            rowHolder.setLayoutParams(mParamsWrap);
            rowHolder.setOrientation(LinearLayout.HORIZONTAL);

            //TODO optimieren fals kein dropview nötig kein linear layout
            //create the textviews in each Line
            for (int j = 0; j < textParts.length; j++) {

                //create the blanks with Tag dropView i j
                if (textParts[j].equals("#")) {
                    LinearLayout linLay = new LinearLayout(getContext());
                    linLay.setLayoutParams(mParamsWrap);
                    linLay.setOrientation(LinearLayout.HORIZONTAL);
                    linLay.setPadding(4, 8, 4, 8);
                    linLay.setMinimumWidth(175);
                    linLay.setMinimumHeight(110);
                    linLay.setBackground(getResources().getDrawable(R.drawable.grey));
                    String tag = "dropView" + i + j;
                    linLay.setTag(tag);
                    dropTags.add(tag);

//
//                    TextView textView1 = new TextView((getContext()));
//                    textView1.setLayoutParams(mParamsWrap);
//                    textView1.setText("______");
//                    textView1.setTag("EMPTY_TEXT");
//                   // textView1.setPadding(4, 12, 4, 12);
//                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

                    linLay.setOnDragListener(this);

                    Log.i("M_EXERCISE_VIEW_DROP", " content: " + j + " " + textParts[j]);

//                    linLay.addView(textView1);
                    rowHolder.addView(linLay);
                }
                //create the Textviews with the content, no tags
                else {
                    TextView textView1 = new TextView((getContext()));
                    textView1.setLayoutParams(mParamsWrap);
                    textView1.setText(textParts[j]);
                    textView1.setPadding(4, 8, 4, 8);
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
                    Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.inputmonocompressed_regular);
                    textView1.setTypeface(typeface);
                    Log.i("M_EXERCISE_VIEW_DRAG", " content: " + j + " " + textParts[j]);
                    rowHolder.addView(textView1);
                }

            }
            contentHolder.addView(rowHolder);
        }

        //create the DragViews with the Solutions

        for(int i=0; i<answerArrayRandom.size();i++) {
            Log.i("M_EXERCISE_VIEW_DRAG", "addAnswerDragVIews more than four " + (i));
            TextView myTextview = new TextView(getContext());
            myTextview.setLayoutParams(mParamsWrap);
            myTextview.setText(answerArrayRandom.get(i));

            String tag = "DRAGVIEW" + (i);
            myTextview.setTag(tag);
            dragTags.add(tag);

            myTextview.setOnTouchListener(this);

            myTextview.setPadding(15, 3, 15, 3);
            myTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 17);
            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.inputmonocompressed_regular);
            myTextview.setTypeface(typeface);
            answerHolder.addView(myTextview);
        }

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

            boolean allAnswers = true;
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
//                TextView solView = currentView.findViewWithTag(dropTags.get(i));
                LinearLayout linView = currentView.findViewWithTag(dropTags.get(i));
                TextView solView = (TextView) linView.getChildAt(0);
                if(solView == null){
                    Toast.makeText(getContext(),"Please enter all Solutions!",Toast.LENGTH_SHORT).show();
                    allAnswers = false;
                    break;
                }else {
                    userSolution[i] = solView.getText().toString();
                    allAnswers = true;
                }
            }

           if(allAnswers) {
               if (Arrays.equals(solutionString, userSolution)) {
                   progressController.makeaLog(Calendar.getInstance().getTime(), "EXERCISE_DRAGDROP_FRAGMENT_RIGHT", "number: " + currentTask.getTaskNumber() + " section: " + currentTask.getSectionNumber() + " viewtype: " + currentTask.getExerciseViewType() + " userInput: " + Arrays.toString(userSolution));
                   mListener.sendAnswerFromExerciseView(true);
                   Log.i("M_EXERCISE_VIEW_DRAG", " send answer: true");
               } else {
                   Log.i("ANSWER", " was wrong");
                   progressController.makeaLog(Calendar.getInstance().getTime(), "EXERCISE_DRAGDROP_FRAGMENT_WRONG", "number: " + currentTask.getTaskNumber() + " section: " + currentTask.getSectionNumber() + " viewtype: " + currentTask.getExerciseViewType() + " userInput: " + Arrays.toString(userSolution));
                   mListener.sendAnswerFromExerciseView(false);
                   Log.i("M_EXERCISE_VIEW_DRAG", "send answer: false");
               }
           }
        }


    public void setExerciseCommunication(ExerciseCommunication callback) {
        Log.d("M_SETEXERCISECOMM", " setMlistenere");
        this.mListener = callback;
    }


    private void receiveCurrentTask() {
        this.currentTask = mListener.sendCurrentTask();
    }


    @SuppressLint("ClickableViewAccessibility")
    public void reset() {
        Log.i("M_DRAGDROP","reset");

        LinearLayout.LayoutParams mParamsWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        answerHolder.removeAllViews();

        for(int i=0; i<answerArray.length;i++) {
            Log.i("M_EXERCISE_VIEW_DRAG", "addAnswerDragVIews more than four " + (i));
            TextView myTextview = new TextView(getContext());
            myTextview.setLayoutParams(mParamsWrap);
            myTextview.setText(answerArrayRandom.get(i));

            String tag = "DRAGVIEW" + (i);
            myTextview.setTag(tag);
            dragTags.add(tag);

            myTextview.setOnTouchListener(this);

            myTextview.setPadding(12, 3, 12, 3);
            myTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.inputmonocompressed_regular);
            myTextview.setTypeface(typeface);
            answerHolder.addView(myTextview);
        }
            for (int m = 0; m < dropTags.size(); m++) {

                LinearLayout linlay = currentView.findViewWithTag(dropTags.get(m));
                linlay.removeAllViews();

            }
        }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

//            ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
//            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
//
//            ClipData data = new ClipData(v.getTag().toString(), mimeTypes, item);

        if(event.getAction() == MotionEvent.ACTION_DOWN){
        Log.i("M_DRAGDROP", "Actiondown touch detected");
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        v.startDrag(data, shadowBuilder, v, 0);
        v.setVisibility(View.INVISIBLE);
        return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();

        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                Log.i("M_DRAGDROP", "Drag stared");
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
                Log.i("M_DRAGDROP", "Drag entered");
                break;
            case DragEvent.ACTION_DRAG_EXITED:
                Log.i("M_DRAGDROP", "Drag exited");
                break;
            case DragEvent.ACTION_DROP:
                Log.i("M_DRAGDROP", "Drag drop");
                //dragView
                TextView view = (TextView) event.getLocalState();
                ViewGroup owner = (ViewGroup) view.getParent();
                owner.removeView(view);

                //Dropview
                LinearLayout container = (LinearLayout) v;
                container.addView(view);
                if(container.getChildCount()>1) {
                    TextView oldView = (TextView) container.getChildAt(0);
                    TextView oldView2 = (TextView) container.getChildAt(1);
                    Log.i("M_DRAGDROP", "childcount: " + container.getChildCount() + " child0: " + oldView.getText().toString() + " child1: " + oldView2.getText().toString());
                }
                if(container.getChildCount()>1){
                    TextView oldView = (TextView) container.getChildAt(0);
                    ViewGroup owenerOld = (ViewGroup) oldView.getParent();
                    owenerOld.removeView(oldView);
                    answerHolder.addView(oldView);
                }

                view.setVisibility(View.VISIBLE);
//                view.setTypeface(Typeface.DEFAULT_BOLD);
                break;

            case DragEvent.ACTION_DRAG_ENDED:
             Log.i("M_DRAGDROP", "Drag ended "+wasCorrect);
                View view2 = (View) event.getLocalState();
                view2.setVisibility(View.VISIBLE);
                break;
        }
        return true;
    }

    private void setWasCorrect(boolean wasCorrect){
        this.wasCorrect = wasCorrect;
        Log.i("M_DRAGDROP","setWasCorrect + "+wasCorrect);
    }

}





