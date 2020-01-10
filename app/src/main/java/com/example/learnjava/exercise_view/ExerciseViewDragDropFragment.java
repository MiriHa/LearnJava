package com.example.learnjava.exercise_view;

import android.annotation.SuppressLint;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;

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

import com.example.learnjava.Controller;
import com.example.learnjava.ExerciseCommunication;
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

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams mParamsWeight = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 1.0);
        LinearLayout.LayoutParams mParamsWrap = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

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
                    String tag = "dropView" + i + j;
                    linLay.setTag(tag);
                    dropTags.add(tag);


                    TextView textView1 = new TextView((getContext()));
                    textView1.setLayoutParams(mParamsWrap);
                    textView1.setText("___________");
                   // textView1.setPadding(4, 12, 4, 12);
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

                    linLay.setOnDragListener(this);

                    Log.i("M_EXERCISE_VIEW_DROP", " content: " + j + " " + textParts[j]);

                    linLay.addView(textView1);
                    rowHolder.addView(linLay);
                }
                //create the Textviews with the content, no tags
                else {
                    TextView textView1 = new TextView((getContext()));
                    textView1.setLayoutParams(mParamsWrap);
                    textView1.setText(textParts[j]);
                    textView1.setPadding(4, 8, 4, 8);
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
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

            myTextview.setPadding(12, 3, 12, 3);
            myTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
            answerHolder.addView(myTextview);
        }

////        if (answerArrayRandom.size() > 4) {
//
//            //answer Array needs to be 4 or eight if more than four -> better layout in lines
//            //TODO what if handy is smaller? only 3 words ins line?
////            int linearLayouts = answerArrayRandom.size() / 4;
////            for (int j = 0; j <= linearLayouts * 4; j += 4) {
////
////                LinearLayout holder = new LinearLayout(getContext());
////                holder.setLayoutParams(mParams);
////                holder.setOrientation(LinearLayout.HORIZONTAL);
////
////                for (int k = 0; k < 4; k++) {
//
//                    if ((j + k) < answerArrayRandom.size()) {
//                        Log.i("M_EXERCISE_VIEW_DRAG", "addAnswerDragVIews more than four " + (j + k));
//                        TextView myTextview = new TextView(getContext());
//                        myTextview.setLayoutParams(mParamsWeight);
//                        myTextview.setText(answerArrayRandom.get(j + k));
//
//                        String tag = "DRAGVIEW" + (j + k);
//                        myTextview.setTag(tag);
//                        dragTags.add(tag);
//
//                        myTextview.setOnLongClickListener(this);
//
//                        myTextview.setPadding(10, 30, 30, 10);
//                        myTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
//                        holder.addView(myTextview);
//                    } else {
//                        break;
//                    }
//                }
//                answerHolder.addView(holder);
//
//            }
//        } else {
//            LinearLayout holder = new LinearLayout(getContext());
//            holder.setLayoutParams(mParams);
//            holder.setOrientation(LinearLayout.HORIZONTAL);
//            holder.setWeightSum(answerArrayRandom.size());
//
//            for (int i = 0; i < answerArrayRandom.size(); i++) {
//                Log.i("M_EXERCISE_VIEW_DRAG", "addAnswerDragVIew less dan four " + i);
//                TextView myTextview = new TextView(getContext());
//                myTextview.setLayoutParams(mParamsWeight);
//                myTextview.setText(answerArrayRandom.get(i));
//                String tag = "DRAGVIEW" + i;
//                myTextview.setTag(tag);
//                dragTags.add(tag);
//
//                myTextview.setOnLongClickListener(this);
//
//                myTextview.setPadding(15, 10, 15, 10);
//                myTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
//                holder.addView(myTextview);
//            }
//            answerHolder.addView(holder);
//        }
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
                userSolution[i] = solView.getText().toString();
            }
            progressController.makeaLog(Calendar.getInstance().getTime(), "EXERCISE_DRAG_DROP_FRAGMENT", "check answer number: " + currentTask.getTaskNumber() + " userInput: " + userSolution);

            if (Arrays.equals(solutionString, userSolution)) {
                mListener.sendAnswerFromExerciseView(true);
                Log.i("M_EXERCISE_VIEW_DRAG", " send answer: true");
            } else {
                Log.i("ANSWER", " was wrong");
                mListener.sendAnswerFromExerciseView(false);
                Log.i("M_EXERCISE_VIEW_DRAG", "send answer: false");
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
            answerHolder.addView(myTextview);
        }
            for (int m = 0; m < dropTags.size(); m++) {

                LinearLayout linlay = currentView.findViewWithTag(dropTags.get(m));

                TextView textView1 = new TextView((getContext()));
                textView1.setLayoutParams(mParamsWrap);
                textView1.setText("___________");
                // textView1.setPadding(4, 12, 4, 12);
                textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

                linlay.removeAllViews();
                linlay.addView(textView1);
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
//        View view = (View) event.getLocalState();
//        //view dragged item is being dropped on
//       // TextView dropTarget = (TextView) v;
//        LinearLayout dropTarget = (LinearLayout) v;
//        //view being dragged and dropped
//        TextView dropped = (TextView) view;

        switch (action) {
            case DragEvent.ACTION_DRAG_STARTED:
                //                        v.setBackgroundColor(getResources().getColor(R.color.grey));
                //                        v.invalidate();
                //return event.getClipDescription().hasMimeType(ClipDescription.MIMETYPE_TEXT_PLAIN);
                break;
            case DragEvent.ACTION_DRAG_ENTERED:
//                    v.setBackgroundColor(getResources().getColor(R.color.lightGreen2));
//                    v.invalidate();
                break;
            case DragEvent.ACTION_DRAG_EXITED:
               Log.i("M_DRAGDROP", "EXITETDT: was correct"+wasCorrect);
//                if(!wasCorrect) {
//                    dropped.setVisibility(View.VISIBLE);
//                    Log.i("M_DRAGDROP", "EXITET: Dropped set visible " + wasCorrect);
//                }else{
//                    dropped.setVisibility(View.INVISIBLE);
//                    Log.i("M_DRAGDROP", "EXITET: Dropped set INvisible " + wasCorrect);
//                    setWasCorrect(false);
//                }
                break;
            case DragEvent.ACTION_DROP:

//                Log.i("M_DRAGDROP", "ACTION DROP");
//                if(dropTarget == null){
//                    dropped.setVisibility(View.VISIBLE);
//                    Log.i("M_DRAGDROP","dropTarget null");
//                }
//
//                Object tagDropTraget = dropTarget.getTag();
//                Object tagDrag = dropped.getTag();
//
//                //Check if dropptarget is a blank to drop in
//                if (String.valueOf(dropTarget.getText().toString().charAt(0)).equals("_")) {
//
//                    setWasCorrect(true);
//                    Log.i("M_DRAGDROP", "ACTION DROP IF " + wasCorrect);
////
//                    //stop displaying the view where it was before it was dragged
//                    dropped.setVisibility(View.INVISIBLE);
//                    dropTarget.setText(dropped.getText().toString());
//                    dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
//
//                    dropTarget.setOnDragListener(null);
//
//                    //if there is already an item here, set it back visible in its original place
//
//                    Log.i("M_DRAGDROP", " droptTarget: " + dropTarget.getTag() + " dropped: " + dropped.getTag());
//                    //TODO hANDLE when text is already set
//                    //TODO handle when drop ended not over dropVIew, make that drops can be changed
//                    //TODO check why drag is invisble when noch draged in coorrect drop -> also check if its dropped anywhere else
//
////                    if(tag!=null)
////                    {
////                        //the tag is the view id already dropped here tag = tag from dropTarget
////                        int existingID = (Integer)tag;
////                        //set the original view visible again
////                        findViewById(existingID).setVisibility(View.VISIBLE);
////                    }
//
//
////                    if(!String.valueOf(dropTarget.getText().toString().charAt(0)).equals("_")){
////                        dropped.setVisibility(View.VISIBLE);
////                    }
//
//                    //set the tag in the target view being dropped on - to the ID of the view being dropped
//                    dropTarget.setTag(dropped.getId());
//
////                    if (!String.valueOf(dropTarget.getText().toString().charAt(1)).equals("_")) {
////                        //the tag is the view id already dropped here
////                        //String existingID = (String) tag;
////                        //set the original view visible again
////                        //view.findViewWithTag(existingID).setVisibility(View.VISIBLE);
////                        dropped.setVisibility(View.VISIBLE);
////                        Log.i("M_DRAGDROP", "if set dropped visible");
////                    } else {
//                        //set the tag in the target view being dropped on - to the Tag of the view being dropped
//                        //TODO is this needed? answer fetching with old Tag would be better
//                        //dropTarget.setTag(dropped.getTag());
//                        //view.setVisibility(View.INVISIBLE);
//                        //remove setOnDragListener by setting OnDragListener to null, so that no further drag & dropping on this TextView can be done
//                      //  dropTarget.setOnDragListener(null);
//                        Log.i("M_DRAGDROP", "else invisible draglistener null");
//                        return true;
//
//                }
//                else{
//                    Log.i("M_DRAGDROP", "was not dropped correctly "+wasCorrect);
//                    dropped.setVisibility(View.VISIBLE);
//                    setWasCorrect(false);
//                }

//                View view = (View) event.getLocalState();
//                ViewGroup owner = (ViewGroup) view.getParent();
//
//                dropTarget.removeAllViews();
//              //  LinearLayout container = (LinearLayout) v;
//                dropTarget.addView(view);
//                view.setVisibility(View.VISIBLE);


        //view dragged item is being dropped on
       // TextView dropTarget = (TextView) v;
//        LinearLayout dropTarget = (LinearLayout) v;
//        //view being dragged and dropped
//        TextView dropped = (TextView) view;

                TextView view = (TextView) event.getLocalState();
                ViewGroup owner = (ViewGroup) view.getParent();
                owner.removeView(view);

                LinearLayout container = (LinearLayout) v;
                container.removeViewAt(0);
                container.addView(view);
                view.setVisibility(View.VISIBLE);
                //remove drag listener
                //TODO maybe add later that it can be dragged elsewhere????
                view.setOnDragListener(null);
                view.setTypeface(Typeface.DEFAULT_BOLD);

                break;

            case DragEvent.ACTION_DRAG_ENDED:
             Log.i("M_DRAGDROP", "Drag ended "+wasCorrect);
                View view2 = (View) event.getLocalState();
                view2.setVisibility(View.VISIBLE);
                break;

//                if(!wasCorrect) {
//                    dropped.setVisibility(View.VISIBLE);
//                    Log.i("M_DRAGDROP", "ENDED: Dropped set visible " +wasCorrect);
//                    break;
//                }else{
//                    dropped.setVisibility(View.INVISIBLE);
//                    Log.i("M_DRAGDROP", "ENDED: Dropped set Invisible "+wasCorrect);
//                    setWasCorrect(false);
//                    break;
//                }

                //v.setBackgroundDrawable(normalShape);
//                default:
//                    Log.e("DragDrop", "Unknown action type received by OnDragListener.");
//                    break;
        }
        return true;
    }

    private void setWasCorrect(boolean wasCorrect){
        this.wasCorrect = wasCorrect;
        Log.i("M_DRAGDROP","setWasCorrect + "+wasCorrect);
    }

}





