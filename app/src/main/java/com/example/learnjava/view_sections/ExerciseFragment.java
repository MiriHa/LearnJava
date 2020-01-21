package com.example.learnjava.view_sections;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.learnjava.controller.Controller;
import com.example.learnjava.controller.ExerciseCommunication;
import com.example.learnjava.view_exercises.ExerciseViewAnswerFragment;
import com.example.learnjava.view_exercises.ExerciseViewChoiceFragment;
import com.example.learnjava.view_exercises.ExerciseViewCodeFragment;
import com.example.learnjava.view_exercises.ExerciseViewDragDropFragment;
import com.example.learnjava.view_exercises.ExerciseViewFillBlanksFragment;
import com.example.learnjava.view_exercises.ExerciseViewOrderFragment;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;

import java.util.Calendar;


public class ExerciseFragment extends Fragment implements ExerciseCommunication{

    private ExerciseCommunication mListener;

    private Controller progressController;

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
        if (getActivity() != null)
            progressController = (Controller) getActivity().getApplicationContext();
        Log.i("M_EXERCISE_FRAGMENT", " checkContorller: progressController Sections " + progressController.getLatestSectionNumber(getContext()));

        progressController.makeaLog(getContext(),Calendar.getInstance().getTime(), "ENTERED_A_EXERCISE", "number: " + currentTask.getTaskNumber() + " type: " + currentTask.getExerciseViewType());


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
    private void setViewContent() {
        switch (viewType) {
            case 1:
                ExerciseViewAnswerFragment answerFragment = new ExerciseViewAnswerFragment();
                answerFragment.setExerciseCommunication(mListener);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ExerciseViewHolder, answerFragment, "EXERCISE_VIEW_ANSWER")
                        .addToBackStack("EXERCISE_VIEW_ANSWER")
                        .commit();
                Log.i("M_EXERCISE_FRAGMENT", "Loadview: 1 Answer");
                break;
            case 2:
                ExerciseViewChoiceFragment choiceFragment = new ExerciseViewChoiceFragment();
                choiceFragment.setExerciseCommunication(mListener);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ExerciseViewHolder, choiceFragment, "EXERCISE_VIEW_CHOICE")
                        .addToBackStack("EXERCISE_VIEW_CHOICE")
                        .commit();
                Log.i("M_EXERCISE_FRAGMENT", "loadview: 2 Choice");
                break;
            case 3:
                ExerciseViewFillBlanksFragment fillBlanksFragment = new ExerciseViewFillBlanksFragment();
                fillBlanksFragment.setExerciseCommunication(mListener);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ExerciseViewHolder, fillBlanksFragment, "EXERCISE_VIEW_FILL_BLANKS")
                        .addToBackStack("EXERCISE_VIEW_FILL_BLANKS")
                        .commit();
                Log.i("M_EXERCISE_FRAGMENT", "loadView: 3 FillBlanks");
                break;
            case 4:
                ExerciseViewDragDropFragment dragDropFragment = new ExerciseViewDragDropFragment();
                dragDropFragment.setExerciseCommunication(mListener);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ExerciseViewHolder, dragDropFragment, "EXERCISE_VIEW_DRAG_DROP")
                        .addToBackStack("EXERCISE_VIEW_DRAG_DROP")
                        .commit();
                Log.i("M_EXERCISE_FRAGMENT", "loadView: 4 DragAnsDrop");
                break;
            case 5:
                ExerciseViewOrderFragment orderFragment = new ExerciseViewOrderFragment();
                orderFragment.setExerciseCommunication(mListener);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ExerciseViewHolder, orderFragment, "EXERCISE_VIEW_ORDER")
                        .addToBackStack("EXERCISE_VIEW_ORDER")
                        .commit();
                Log.i("M_EXERCISE_FRAGMENT", "loadView: 5 Order");
                break;
            case 6:
                ExerciseViewCodeFragment codeFragment = new ExerciseViewCodeFragment();
                codeFragment.setExerciseCommunication(mListener);
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ExerciseViewHolder, codeFragment, "EXERCISE_VIEW_CODE")
                        .addToBackStack("EXERCISE_VIEW_CODE")
                        .commit();
                Log.i("M_EXERCISE_FRAGMENT", "laodview: 6 Code");
                break;


        }
    }

    //used in Exercise Activity to set the currentTask
    void setFragmentContentExercise(ModelTask currentTask) {
        Log.i("M_EXERCISE_FRAGMENT", "setFragmentContent");
        this.currentTask = currentTask;
        whatsNext = currentTask.getWhatsNext();
        viewType = currentTask.getExerciseViewType();
    }

    //open the next task according to the getNext value from the currentTask
    private void openNextTask() {
        try {
            if ((getActivity() != null)) {
                //notify the exerciseViewFragment that nextButton is clicked
                if (whatsNext == 2) {
                    ((LessonActivity) getActivity()).openNewTask(2);
                    Log.i("M_EXERCISE_FRAGMENT", "ButtonClicked: openExerciseFragment");
                } else if (whatsNext == 1) {
                    ((LessonActivity) getActivity()).openNewTask(1);
                    Log.i("M_EXERCISE_FRAGMENT ", "ButtonCLicked: opnenLesson");
                } else if (whatsNext == 3) {
                    ((LessonActivity) getActivity()).updateProgressLastTask();
                    Log.i("M_EXERCISE_FRAGMENT", "BUttonCLicked: lastExercise");
                } else {
                    Log.i("M_EXERCISE_FRAGMENT", "ButtonClicked: FalseWhatsNextType");
                }
            }
        } catch (Exception e) {
            Log.e("M_EXERCISE_FRAGMENT", "Not clickable_error");
        }
    }

    //Communication with the ExerciseViewFragment
    @Override
    public ModelTask sendCurrentTask() {
        return currentTask;
    }


    @Override
    public void sendAnswerFromExerciseView(boolean answerChecked) {
        if (answerChecked) {
            showFeedbackDialogRight();
            Log.i("M_EXERCISE_FRAGMENT", "answer was right");
            progressController.makeaLog(getContext(),Calendar.getInstance().getTime(), "EXERCISE_ANSWER_RIGHT", "number: " + currentTask.getTaskNumber() + "section: "+currentTask.getSectionNumber()+" taskType: " + currentTask.getExerciseViewType());
        } else {
            showFeedbackDialogWrong();
            Log.i("M_EXERCISE_FRAGMENT", "answer was wrong");
            progressController.makeaLog(getContext(),Calendar.getInstance().getTime(), "EXERCISE_ANSWER_WRONG", "number: " + currentTask.getTaskNumber() + "section: "+currentTask.getSectionNumber()+" taskType: " + currentTask.getExerciseViewType());

        }
    }

    @Override
    public void justOpenNext() {
        Log.i("M_EXERCISEFRAGMENT","justopen next");
        openNextTask();
    }

    private void showFeedbackDialogRight() {

        Log.i("M_EXERCISE_FRAGMENT","showFeedBackDialogRight");
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_feedback_dialog_right, viewGroup, false);
        Button nextButton = dialogView.findViewById(R.id.FeedbackRightButton);

        if (currentTask.getWhatsNext() == 3) {
            nextButton.setText("Finish Section");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);

        final AlertDialog rightFeedbackDialog = builder.create();
        rightFeedbackDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        rightFeedbackDialog.setCanceledOnTouchOutside(false);
        rightFeedbackDialog.setCancelable(false);
        rightFeedbackDialog.show();

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openNextTask();
                rightFeedbackDialog.dismiss();

            }
        });

    }

    private void showFeedbackDialogWrong() {
        Log.i("M_EXERCISE_FRAGMENT","showFeedBackDialogWrong");
        View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.exercise_feedback_dialog_wrong, viewGroup, false);
        Button tryagainButton = dialogView.findViewById(R.id.FeedbackWrongTryAgainButton);
        Button previousButton = dialogView.findViewById(R.id.FeedbackWrongPreviousButton);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setView(dialogView);
        final AlertDialog wrongfeedbackDialog = builder.create();
        wrongfeedbackDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        wrongfeedbackDialog.setCanceledOnTouchOutside(false);
        wrongfeedbackDialog.setCancelable(false);
        wrongfeedbackDialog.show();

        tryagainButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO reset the layout
                reset();
                wrongfeedbackDialog.dismiss();
                progressController.makeaLog(getContext(),Calendar.getInstance().getTime(), "EXERCISE_ANSWER_WRONG_TRY_AGAIN", "number: " + currentTask.getTaskNumber() + "section: "+currentTask.getSectionNumber()+" taskType: " + currentTask.getExerciseViewType());


            }
        });
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((LessonActivity) getActivity()).openNewTask(3);
                wrongfeedbackDialog.dismiss();
                progressController.makeaLog(getContext(),Calendar.getInstance().getTime(), "EXERCISE_ANSWER_WRONG_SEE_LESSON", "number: " + currentTask.getTaskNumber() +"section: "+currentTask.getSectionNumber()+ " taskType: " + currentTask.getExerciseViewType());
            }
        });

    }

    private void reset() {
        Log.i("M_EXERCISE_FRAGMENT","Reset");
        int viewType = currentTask.getExerciseViewType();

        switch (viewType) {
            case 1:
                final ExerciseViewAnswerFragment fragment = (ExerciseViewAnswerFragment) getChildFragmentManager().findFragmentByTag("EXERCISE_VIEW_ANSWER");
                fragment.reset();
                break;
            case 2:
                final ExerciseViewChoiceFragment fragment1 = (ExerciseViewChoiceFragment) getChildFragmentManager().findFragmentByTag("EXERCISE_VIEW_CHOICE");
                fragment1.reset();
                break;
            case 3:
                final ExerciseViewFillBlanksFragment fragment2 = (ExerciseViewFillBlanksFragment) getChildFragmentManager().findFragmentByTag("EXERCISE_VIEW_FILL_BLANKS");
                fragment2.reset();
                break;
            case 4:
                final ExerciseViewDragDropFragment fragment3 = (ExerciseViewDragDropFragment) getChildFragmentManager().findFragmentByTag("EXERCISE_VIEW_DRAG_DROP");
                fragment3.reset();
                break;
            case 5:
                final ExerciseViewOrderFragment fragment4 = (ExerciseViewOrderFragment) getChildFragmentManager().findFragmentByTag("EXERCISE_VIEW_ORDER");
                fragment4.reset();
                break;
            case 6:
                final ExerciseViewCodeFragment fragment5 = (ExerciseViewCodeFragment) getChildFragmentManager().findFragmentByTag("EXERCISE_VIEW_CODE");
                fragment5.reset();
                break;

        }
    }


    private void setSectionColor() {
        switch ((int) progressController.getCurrentSection(getContext())) {

            case 1:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(), (R.color.section1_color)));
                break;
            case 2:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(), (R.color.section2_color)));
                break;
            case 3:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.section3_color));
                break;
            case 4:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(), (R.color.section4_color)));
                break;
            case 5:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(), (R.color.section5_color)));
                break;
            case 6:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.section6_color));
                break;
            case 7:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(), (R.color.section7_color)));
                break;
            case 8:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(), (R.color.section8_color)));
                break;


        }
    }

}
