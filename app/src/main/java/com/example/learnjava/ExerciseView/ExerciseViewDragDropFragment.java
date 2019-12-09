package com.example.learnjava.ExerciseView;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.net.LinkAddress;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Layout;
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

import com.example.learnjava.ExerciseCommunication;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * This is the View to give a drag and drop answer
 * ViewType: 4
 */

public class ExerciseViewDragDropFragment extends Fragment implements View.OnDragListener, View.OnLongClickListener {

    private ExerciseCommunication mListener;


    ModelTask currentTask;

    private Button nextButton;
    private LinearLayout contentHolder, answerHolder;

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
        View view = inflater.inflate(R.layout.fragment_exercise_view_drag_drop, container, false);

        //get the currentTask
        receiveCurrentTask();

        contentHolder = view.findViewById(R.id.contentHolderDragDrop);
        answerHolder = view.findViewById(R.id.answerHolderDragDrop);

        setContentLayout();

        nextButton = view.findViewById(R.id.nextButtonExerciseDragDrop);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("BUTTONCLICKED", " in DragDropView");
                checkAnswers();
            }
        });


        return view;
    }

    private void setContentLayout() {
        //TODO vllt überarbeiten etwas kompliziert so?
        //Arrag length needs to bedivisble through 3 -> per row textview dropView textview
        //ansosten zweidimensionales array? verschachteltes array?
        String[] contentArray = currentTask.getContentStringArray();
        String[] answerArray = currentTask.getSolutionStringArray();

        List<String> answerArrayRandom = new ArrayList<>(answerArray.length);
        Collections.addAll(answerArrayRandom, answerArray);
        Collections.shuffle(answerArrayRandom);


        Log.i("MDRAGDROP", "answerArray: " + answerArrayRandom.toString());

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams mParamsWeight = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 1.0);
        LinearLayout.LayoutParams mParamsWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //created the Lines in the ContentHolder
        int linearLayoutsNeeded = contentArray.length / 3;
        for (int j = 0; j < linearLayoutsNeeded * 3; j += 3) {

            LinearLayout holder = new LinearLayout(getContext());
            holder.setLayoutParams(mParams);
            holder.setOrientation(LinearLayout.HORIZONTAL);

            String[] currentLineContent = {contentArray[j], contentArray[j + 1], contentArray[j + 2]};
            Log.i("M DRAGDROP", " content: " + j + " " + currentLineContent.toString());

            //create the three Textview in each Line
            for (int i = 0; i < 3; i++) {

                if (currentLineContent[i].equals("")) {
                    TextView textView1 = new TextView((getContext()));
                    textView1.setLayoutParams(mParamsWrap);
                    textView1.setText("___________");
                    textView1.setPadding(10, 20, 10, 20);
                    textView1.setTag("dropView" + (i + j));
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

                    textView1.setOnDragListener(this);

                    Log.i("M DRAGDROP", " content: " + i + " " + currentLineContent[i]);
                    holder.addView(textView1);
                } else {
                    TextView textView1 = new TextView((getContext()));
                    textView1.setLayoutParams(mParamsWrap);
                    textView1.setText(currentLineContent[i]);
                    textView1.setPadding(20, 25, 20, 25);
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
                    Log.i("M DRAGDROP", " content: " + i + " " + currentLineContent[i]);
                    holder.addView(textView1);
                }

            }
            contentHolder.addView(holder);
        }

        //create the DragViews with the Solutions
        if (answerArrayRandom.size() > 4) {

            //answer Array needs to be 4 or eight if more than four
            int linearLayouts = answerArrayRandom.size() / 4;
            for (int j = 0; j <= linearLayouts * 4; j += 4) {

                LinearLayout holder = new LinearLayout(getContext());
                holder.setLayoutParams(mParams);
                holder.setOrientation(LinearLayout.HORIZONTAL);

                for (int k = 0; k < 4; k++) {

                    if ((j + k) < answerArrayRandom.size()) {
                        Log.i("MDRAGDROP", "addAnswerDragVIews more than four " + (j + k));
                        TextView myTextview = new TextView(getContext());
                        myTextview.setLayoutParams(mParamsWeight);
                        myTextview.setText(answerArrayRandom.get(j + k));
                        myTextview.setTag("DRAGVIEW" + (j + k));

                        myTextview.setOnLongClickListener(this);

                        myTextview.setPadding(10, 30, 30, 10);
                        myTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
                        holder.addView(myTextview);
                    } else {
                        break;
                    }
                }
                answerHolder.addView(holder);

            }
        } else {
            LinearLayout holder = new LinearLayout(getContext());
            holder.setLayoutParams(mParams);
            holder.setOrientation(LinearLayout.HORIZONTAL);
            holder.setWeightSum(answerArrayRandom.size());

            for (int i = 0; i < answerArrayRandom.size(); i++) {
                Log.i("MDRAGDROP", "addAnswerDragVIew less dan four " + i);
                TextView myTextview = new TextView(getContext());
                myTextview.setLayoutParams(mParamsWeight);
                myTextview.setText(answerArrayRandom.get(i));
                myTextview.setTag("DRAGVIEW" + i);

                myTextview.setOnLongClickListener(this);

                myTextview.setPadding(15, 10, 15, 10);
                myTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 35);
                holder.addView(myTextview);
            }
            answerHolder.addView(holder);
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
//        String userInput = editText.getText().toString();
//        Log.i("CheckAnswers", " anser: " + userInput + " solution: " + currentTask.getSolutionString());
//        if (currentTask.getSolutionString().equals(userInput)) {
//            mListener.sendAnswerFromExerciseView(true);
//            Log.i("SENDANSWERFROMEXERCISE", " answer: true");
//            Toast.makeText(getContext(), "Answer was right.", Toast.LENGTH_SHORT).show();
//        } else {
//            Log.i("ANSWER", " was wrong");
//            mListener.sendAnswerFromExerciseView(false);
//            Log.i("SENDANSWERFROMEXERCISE", " answer: false");
//            Toast.makeText(getContext(), "Answer was false", Toast.LENGTH_SHORT).show();
//            //TODO give feedback that false answer??
//        }
    }

    public void setExerciseCommunication(ExerciseCommunication callback) {
        Log.d("SETEXERCISECOMM", " setMlistenere");
        this.mListener = callback;
    }


    private void receiveCurrentTask() {
        this.currentTask = mListener.sendCurrentTask();
    }


    private final class MyTouchListener implements View.OnTouchListener {
        public boolean onTouch(View view, MotionEvent motionEvent) {
            view.performClick();
            if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                Log.i("DRAGDROP", "Actiondown touch detected");
                ClipData data = ClipData.newPlainText("", "");
                View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
                view.startDrag(data, shadowBuilder, view, 0);
                view.setVisibility(View.INVISIBLE);
                return true;
            } else {
                return false;
            }
        }
    }




        @Override
        public boolean onLongClick(View v) {

//            ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
//            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
//
//            ClipData data = new ClipData(v.getTag().toString(), mimeTypes, item);

            Log.i("DRAGDROP", "Actiondown touch detected");
            ClipData data = ClipData.newPlainText("", "");
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
            v.startDrag(data, shadowBuilder, v, 0);
            v.setVisibility(View.INVISIBLE);
            return true;
        }

        @Override
        public boolean onDrag(View v, DragEvent event) {
            int action = event.getAction();
            switch (action) {
                case DragEvent.ACTION_DRAG_STARTED:
                    if (event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN)) {
//                        v.setBackgroundColor(getResources().getColor(R.color.grey));
//                        v.invalidate();
                        return true;
                    }
                    return false;
                case DragEvent.ACTION_DRAG_ENTERED:
//                    v.setBackgroundColor(getResources().getColor(R.color.lightGreen2));
//                    v.invalidate();
                    return true;
                case DragEvent.ACTION_DRAG_EXITED:

                    break;
                case DragEvent.ACTION_DROP:

                    Log.i("DRAGDROP", "ACTION DROP");

                    View view = (View) event.getLocalState();
                    //view dragged item is being dropped on
                    TextView dropTarget = (TextView) v;
                    //view being dragged and dropped
                    TextView dropped = (TextView) view;
                    //checking whether first character of dropTarget equals first character of dropped
//                    if (dropTarget.getText().toString().charAt(0) == dropped.getText().toString().charAt(0)) {
                        //stop displaying the view where it was before it was dragged
                        view.setVisibility(View.INVISIBLE);
                        //update the text in the target view to reflect the data being dropped
                        dropTarget.setText(dropped.getText().toString());
                        //make it bold to highlight the fact that an item has been dropped
                        dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                        //if an item has already been dropped here, there will be a tag
                        Object tag = dropTarget.getTag();
                        //if there is already an item here, set it back visible in its original place
                    Log.i("DRAGDROP", " droptTarget: " + dropTarget.getTag() + " dropped: " + dropped.getTag());
//                        if (dropTarget.getText().toString().charAt(2) == dropped.getText().toString().charAt(2)) {
//                            //the tag is the view id already dropped here
//                            String existingID = (String) tag;
//                            //set the original view visible again
//                            view.findViewWithTag(existingID).setVisibility(View.VISIBLE);
//                        }
                        //set the tag in the target view being dropped on - to the Tag of the view being dropped
                       //TODO is this needed? answer fetching with old Tag would be better
                       // dropTarget.setTag(dropped.getTag());
                        //remove setOnDragListener by setting OnDragListener to null, so that no further drag & dropping on this TextView can be done
                        dropTarget.setOnDragListener(null);
                        return true;
//                    }

                case DragEvent.ACTION_DRAG_ENDED:
                    //v.setBackgroundDrawable(normalShape);
//                default:
//                    Log.e("DragDrop", "Unknown action type received by OnDragListener.");
//                    break;
            }
            return false;
        }
    }





