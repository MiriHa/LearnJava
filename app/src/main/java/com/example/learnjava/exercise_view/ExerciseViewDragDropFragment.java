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
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.learnjava.Controller;
import com.example.learnjava.ExerciseCommunication;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * This is the View to give a drag and drop answer
 * ViewType: 4
 */

public class ExerciseViewDragDropFragment extends Fragment implements View.OnDragListener, View.OnLongClickListener {

    private ExerciseCommunication mListener;


    private ModelTask currentTask;
    private Controller progressController;
    private String[] answerArray;
    private ArrayList<String> dropTags = new ArrayList<>();
    private ArrayList<String> dragTags = new ArrayList<>();
    private Button nextButton;
    private LinearLayout contentHolder, answerHolder;

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

        TextView taskText = view.findViewById(R.id.exerciseTextDragDrop);
        taskText.setText(currentTask.getTaskText());

        setDynamicLayout();
//        setContentLayout();

        nextButton = view.findViewById(R.id.nextButtonExerciseDragDrop);
        if(progressController.checkTasks(currentTask)) {
            Log.i("M_Exercise_VIEW_DRAG", "checkExericse and skip");
            nextButton.setText(R.string.Skip);
        }
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("M_EXERCISE_VIEW_DRAG", "check button pressed");
                checkAnswers();
            }
        });


        final Button resetButton = view.findViewById(R.id.resetButton);
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
        List<String> answerArrayRandom = new ArrayList<>(answerArray.length);
        Collections.addAll(answerArrayRandom, answerArray);
        Collections.shuffle(answerArrayRandom);


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
                    TextView textView1 = new TextView((getContext()));
                    textView1.setLayoutParams(mParamsWrap);
                    textView1.setText("___________");
                    textView1.setPadding(4, 12, 4, 12);
                    String tag = "dropView" + i + j;
                    textView1.setTag(tag);
                    dropTags.add(tag);
                    textView1.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);

                    textView1.setOnDragListener(this);

                    Log.i("M_EXERCISE_VIEW_DROP", " content: " + j + " " + textParts[j]);
                    rowHolder.addView(textView1);
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
        if (answerArrayRandom.size() > 4) {

            //answer Array needs to be 4 or eight if more than four -> better layout in lines
            //TODO what if handy is smaller? only 3 words ins line?
            int linearLayouts = answerArrayRandom.size() / 4;
            for (int j = 0; j <= linearLayouts * 4; j += 4) {

                LinearLayout holder = new LinearLayout(getContext());
                holder.setLayoutParams(mParams);
                holder.setOrientation(LinearLayout.HORIZONTAL);

                for (int k = 0; k < 4; k++) {

                    if ((j + k) < answerArrayRandom.size()) {
                        Log.i("M_EXERCISE_VIEW_DRAG", "addAnswerDragVIews more than four " + (j + k));
                        TextView myTextview = new TextView(getContext());
                        myTextview.setLayoutParams(mParamsWeight);
                        myTextview.setText(answerArrayRandom.get(j + k));

                        String tag = "DRAGVIEW" + (j + k);
                        myTextview.setTag(tag);
                        dragTags.add(tag);

                        myTextview.setOnLongClickListener(this);

                        myTextview.setPadding(10, 30, 30, 10);
                        myTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
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
                Log.i("M_EXERCISE_VIEW_DRAG", "addAnswerDragVIew less dan four " + i);
                TextView myTextview = new TextView(getContext());
                myTextview.setLayoutParams(mParamsWeight);
                myTextview.setText(answerArrayRandom.get(i));
                String tag = "DRAGVIEW" + i;
                myTextview.setTag(tag);
                dragTags.add(tag);

                myTextview.setOnLongClickListener(this);

                myTextview.setPadding(15, 10, 15, 10);
                myTextview.setTextSize(TypedValue.COMPLEX_UNIT_SP, 23);
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

        if (progressController.checkTasks(currentTask)) {
            Log.i("M_EXERCISE_VIEW_DRAG", "checkExericse and skip");
            mListener.justOpenNext();
        }else {
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
            //progressController.makeaLog(Calendar.getInstance().getTime(), "EXERCISE_DRAG_DROP_FRAGMENT", "check answer number: " + currentTask.getTaskNumber() + " userInput: " + userSolution);
            //TODO check when no Answer is inputted
            if (Arrays.equals(solutionString, userSolution)) {
                mListener.sendAnswerFromExerciseView(true);
                Log.i("M_EXERCISE_VIEW_DRAG", " send answer: true");
            } else {
                Log.i("ANSWER", " was wrong");
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


    public void reset() {
        Log.i("M_DRAGDROP","reset");
        for(int i=0; i<dragTags.size();i++) {

            TextView text = currentView.findViewWithTag(dragTags.get(i));
            text.setVisibility(TextView.VISIBLE);
        }

        for(int i=0; i<dropTags.size();i++) {

            TextView text = currentView.findViewWithTag(dropTags.get(i));
            text.setTag(dropTags.get(i));
            text.setTypeface(Typeface.DEFAULT);
            text.setText("___________");
            text.setOnDragListener(this);
        }
    }


    @Override
    public boolean onLongClick(View v) {

//            ClipData.Item item = new ClipData.Item((CharSequence) v.getTag());
//            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
//
//            ClipData data = new ClipData(v.getTag().toString(), mimeTypes, item);

        Log.i("M_DRAGDROP", "Actiondown touch detected");
        ClipData data = ClipData.newPlainText("", "");
        View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(v);
        v.startDrag(data, shadowBuilder, v, 0);
        v.setVisibility(View.INVISIBLE);
       return true;
    }

    @Override
    public boolean onDrag(View v, DragEvent event) {
        int action = event.getAction();
        View view = (View) event.getLocalState();
        //view dragged item is being dropped on
        TextView dropTarget = (TextView) v;
        //view being dragged and dropped
        TextView dropped = (TextView) view;

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
               // Log.i("M_DRAGDROP", "EXITETDT: was correct"+wasCorrect);
                if(!wasCorrect) {
                    dropped.setVisibility(View.VISIBLE);
                    Log.i("M_DRAGDROP", "EXITET: Dropped set visible " + wasCorrect);
                }else{
                    dropped.setVisibility(View.INVISIBLE);
                    Log.i("M_DRAGDROP", "EXITET: Dropped set INvisible " + wasCorrect);
                    setWasCorrect(false);
                }
                break;
            case DragEvent.ACTION_DROP:

                Log.i("M_DRAGDROP", "ACTION DROP");
                if(dropTarget == null){
                    dropped.setVisibility(View.VISIBLE);
                    Log.i("M_DRAGDROP","dropTarget null");
                }

                //Check if dropptarget is a blank to drop in
                if (String.valueOf(dropTarget.getText().toString().charAt(0)).equals("_")) {

                    setWasCorrect(true);
                    Log.i("M_DRAGDROP", "ACTION DROP IF "+wasCorrect);
//                    if (dropTarget.getText().toString().charAt(0) == dropped.getText().toString().charAt(0)) {
                    //stop displaying the view where it was before it was dragged
                    // view.setVisibility(View.INVISIBLE);
                    dropped.setVisibility(View.INVISIBLE);
                    //update the text in the target view to reflect the data being dropped
                    dropTarget.setText(dropped.getText().toString());
                    //make it bold to highlight the fact that an item has been dropped
                    dropTarget.setTypeface(Typeface.DEFAULT_BOLD);
                    //if an item has already been dropped here, there will be a tag
                    Object tagDrop = dropTarget.getTag();
                    Object tagDrag = dropped.getTag();


                    //if there is already an item here, set it back visible in its original place

                    Log.i("M_DRAGDROP", " droptTarget: " + dropTarget.getTag() + " dropped: " + dropped.getTag());
                    //TODO hANDLE when text is already set
                    //TODO handle when drop ended not over dropVIew, make that drops can be changed
                    //TODO check why drag is invisble when noch draged in coorrect drop -> also check if its dropped anywhere else

                    //view.setVisibility(View.INVISIBLE);

//                    if (!String.valueOf(dropTarget.getText().toString().charAt(1)).equals("_")) {
//                        //the tag is the view id already dropped here
//                        //String existingID = (String) tag;
//                        //set the original view visible again
//                        //view.findViewWithTag(existingID).setVisibility(View.VISIBLE);
//                        dropped.setVisibility(View.VISIBLE);
//                        Log.i("M_DRAGDROP", "if set dropped visible");
//                    } else {
                        //set the tag in the target view being dropped on - to the Tag of the view being dropped
                        //TODO is this needed? answer fetching with old Tag would be better
                        //dropTarget.setTag(dropped.getTag());
                        view.setVisibility(View.INVISIBLE);
                        //remove setOnDragListener by setting OnDragListener to null, so that no further drag & dropping on this TextView can be done
                        dropTarget.setOnDragListener(null);
                        Log.i("M_DRAGDROP", "else invisible draglistener null");
                        return true;

                }

                else{
                    Log.i("M_DRAGDROP", "was not dropped correctly "+wasCorrect);
                    dropped.setVisibility(View.VISIBLE);
                    setWasCorrect(false);
                }
                break;

            case DragEvent.ACTION_DRAG_ENDED:
                Log.i("M_DRAGDROP", "Drag ended "+wasCorrect);
                if(!wasCorrect) {
                    dropped.setVisibility(View.VISIBLE);
                    Log.i("M_DRAGDROP", "ENDED: Dropped set visible " +wasCorrect);
                    break;
                }else{
                    dropped.setVisibility(View.INVISIBLE);
                    Log.i("M_DRAGDROP", "ENDED: Dropped set Invisible "+wasCorrect);
                    setWasCorrect(false);
                    break;
                }

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





