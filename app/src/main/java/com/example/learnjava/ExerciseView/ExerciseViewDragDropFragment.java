package com.example.learnjava.ExerciseView;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.DragEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.learnjava.ExerciseCommunication;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;


/**
 * This is the View to give a drag and drop answer
 * ViewType: 4
 */

public class ExerciseViewDragDropFragment extends Fragment implements View.OnLongClickListener, View.OnDragListener {

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

        //Arrag length needs to bedivisble through 3 -> per row textview dropView textview
        //ansosten zweidimensionales array? verschachteltes array?
        String[] contentArray = currentTask.getContentStringArray();

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams mParamsWeight = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, (float) 1.0);


        for (int i = 0; i < contentArray.length; i++) {

            LinearLayout holder = new LinearLayout(getContext());
            holder.setLayoutParams(mParams);
            holder.setOrientation(LinearLayout.HORIZONTAL);

            if(contentArray[i].equals("")){
                TextView textView1 = new TextView((getContext()));
                textView1.setLayoutParams(mParams);
                textView1.setText(contentArray[i]);
                textView1.setTag("dropView" + i);
                Log.i("M DRAGDROP", " content: " + i + " " + contentArray[i] );
                holder.addView(textView1);
            }else {
                TextView textView1 = new TextView((getContext()));
                textView1.setLayoutParams(mParams);
                textView1.setText(contentArray[i]);
                Log.i("M DRAGDROP", " content: " + i + " " + contentArray[i] );
                holder.addView(textView1);
            }

            if ((i+1) % 3 == 0) {
                Log.i("M DRAGDROP modulo", " content: " + i + " " + contentArray[i] );
                contentHolder.addView(holder);
            }
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


    @Override
    public boolean onDrag(View v, DragEvent event) {
        return false;
    }

    @Override
    public boolean onLongClick(View v) {
        return false;
    }
}

