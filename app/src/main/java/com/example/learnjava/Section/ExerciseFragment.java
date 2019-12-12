package com.example.learnjava.Section;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.learnjava.Controller;
import com.example.learnjava.ExerciseCommunication;
import com.example.learnjava.ExerciseView.ExerciseViewAnswerFragment;
import com.example.learnjava.ExerciseView.ExerciseViewChoiceFragment;
import com.example.learnjava.ExerciseView.ExerciseViewCodeFragment;
import com.example.learnjava.ExerciseView.ExerciseViewDragDropFragment;
import com.example.learnjava.ExerciseView.ExerciseViewFillBlanksFragment;
import com.example.learnjava.ExerciseView.ExerciseViewOrderFragment;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;


public class ExerciseFragment extends Fragment implements ExerciseCommunication {

    private ExerciseCommunication mListener;

    private Controller progressController;

    private TextView exerciseName;
    private ViewGroup viewGroup;
    private ConstraintLayout background;
    private int whatsNext;
    private int viewType;
    private ModelTask currentTask;


    public ExerciseFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        if(getActivity() != null)
            progressController = (Controller) getActivity().getApplicationContext();
        Log.i("CheckCOntroller", " progressController Sections " + progressController.getSections().toString());

        exerciseName = view.findViewById(R.id.exerciseName);
        exerciseName.setText(currentTask.getTaskName());

        background = view.findViewById(R.id.ExerciseHolder);

        viewGroup = view.findViewById(android.R.id.content);
        setViewContent();
        setSectionColor();


        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        }

    @Override
    public void onDetach() {
        super.onDetach();

    }



    //Set the right ExerciseView
    private void setViewContent(){
        switch (viewType){
            case 1:
                ExerciseViewAnswerFragment answerFragment = new ExerciseViewAnswerFragment();
                answerFragment.setExerciseCommunication(mListener);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ExerciseViewHolder, answerFragment)
                        .addToBackStack(null)
                        .commit();
                Log.i("LOADVIEW", " 1 Answer");
                break;
            case 2:
                ExerciseViewChoiceFragment choiceFragment = new ExerciseViewChoiceFragment();
                choiceFragment.setExerciseCommunication(mListener);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ExerciseViewHolder, choiceFragment)
                        .addToBackStack(null)
                        .commit();
                Log.i("LOADVIEW", " 2 Choice");
                break;
            case 3:
                ExerciseViewFillBlanksFragment fillBlanksFragment = new ExerciseViewFillBlanksFragment();
                fillBlanksFragment.setExerciseCommunication(mListener);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ExerciseViewHolder, fillBlanksFragment)
                        .addToBackStack(null)
                        .commit();
                Log.i("LOADVIEW", " 3 FillBlanks");
                break;
            case 4:
                ExerciseViewDragDropFragment dragDropFragment = new ExerciseViewDragDropFragment();
                dragDropFragment.setExerciseCommunication(mListener);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ExerciseViewHolder, dragDropFragment)
                        .addToBackStack(null)
                        .commit();
                Log.i("LOADVIEW", " 4 DragAnsDrop");
                break;
            case 5:
                ExerciseViewOrderFragment orderFragment = new ExerciseViewOrderFragment();
                orderFragment.setExerciseCommunication(mListener);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ExerciseViewHolder, orderFragment)
                        .addToBackStack(null)
                        .commit();
                Log.i("LOADVIEW", " 5 Order");
                break;
            case 6:
                ExerciseViewCodeFragment codeFragment = new ExerciseViewCodeFragment();
                codeFragment.setExerciseCommunication(mListener);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ExerciseViewHolder, codeFragment)
                        .addToBackStack(null)
                        .commit();
                Log.i("LOADVIEW", " 6 Code");
                break;


        }
    }

    //used in Exercise Activity to set the currentTask
    public void setFragmentContentExercise(ModelTask currentTask) {
        Log.i("M GIVE_CONTENT", "in ExerciseFragment");
        this.currentTask = currentTask;
        whatsNext = currentTask.getWhatsNext();
        viewType = currentTask.getExerciseViewType();
    }

    //open the next task according to the getNext value from the currentTask
    private void openNextTask(){
        try {
            if ((getActivity() != null)) {
                //notify the exerciseViewFragment that nextButton is clicked
                if( whatsNext == 2) {
                    ((LessonActivity) getActivity()).openNewTask(2);
                    Log.i("M Buttonclicked", " openExerciseFragment");
                }
                else if (whatsNext == 1) {
                    ((LessonActivity) getActivity()).openNewTask(1);
                    Log.i("M Buttonclicked", " opnenLesson");
                }
                else if (whatsNext == 3){
                    ((LessonActivity) getActivity()).updateProgressLastTask();
                    Log.i("M Buttonclicked", " lastExercise");
                }
                else {
                    Log.i("M Buttonclicked", " FalseWhatsNextType");
                }
            }
        }
        catch (Exception e){
            Log.e("M Not clickable", "error");
        }
    }

    //Communication with the ExerciseViewFragment
    @Override
    public ModelTask sendCurrentTask() {
        return currentTask;
    }

    @Override
    public void sendAnswerFromExerciseView(boolean answerChecked) {
        if (answerChecked){
            progressController.addFinishedTask(currentTask);
            showFeedbackDialogRight();
            Log.i("M ANSWER", " was right");
        }
        else{
            showFeedbackDialogWrong();
            Log.i("M ANSWER", " was wrong");
        }
    }

    @Override
    public void justOpenNext() {
        openNextTask();
    }

    private void showFeedbackDialogRight(){
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_feedback_dialog_right, viewGroup, false);
        Button nextButton = dialogView.findViewById(R.id.FeedbackRightButton);

        if(currentTask.getWhatsNext() == 3){
            nextButton.setText("Finish Section");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        final AlertDialog rightFeedbackDialog = builder.create();
        rightFeedbackDialog.show();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNextTask();
                rightFeedbackDialog.dismiss();

            }
        });

    }

    private void showFeedbackDialogWrong(){
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_feedback_dialog_wrong, viewGroup, false);
        Button tryagainButton = dialogView.findViewById(R.id.FeedbackWrongTryAgainButton);
        Button previousButton = dialogView.findViewById(R.id.FeedbackWrongPreviousButton);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        //  AlertDialog builder = new AlertDialog.Builder(getContext()).create();
        builder.setView(dialogView);
        final AlertDialog feedbackDialog = builder.create();
      //  feedbackDialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.transparent)));
        feedbackDialog.show();

        tryagainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LessonActivity) getActivity()).openNewTask(4);
                feedbackDialog.dismiss();

            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LessonActivity) getActivity()).openNewTask(3);
                feedbackDialog.dismiss();
            }
        });

    }

    private void setSectionColor() {
        switch (progressController.getCurrentSection()) {

            case 1:
                background.setBackgroundColor(getResources().getColor(R.color.lightGreen1));
                break;
            case 2:
                background.setBackgroundColor(getResources().getColor(R.color.lightGreen2));
                break;
            case 3:
                background.setBackgroundColor(getResources().getColor(R.color.lightGreen3));
                break;
            case 4:
                background.setBackgroundColor(getResources().getColor(R.color.Green1));
                break;
            case 5:
                background.setBackgroundColor(getResources().getColor(R.color.Green2));
                break;
            case 6:
                background.setBackgroundColor(getResources().getColor(R.color.Blue1));
                break;
            case 7:
                background.setBackgroundColor(getResources().getColor(R.color.Blue2));
                break;
            case 8:
                background.setBackgroundColor(getResources().getColor(R.color.Blue3));
                break;
            case 9:
                background.setBackgroundColor(getResources().getColor(R.color.Blue4));
                break;


        }
    }

}
