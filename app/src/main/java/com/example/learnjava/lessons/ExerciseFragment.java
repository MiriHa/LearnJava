package com.example.learnjava.lessons;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.learnjava.ExerciseCommunication;
import com.example.learnjava.ExerciseView.ExerciseViewAnswerFragment;
import com.example.learnjava.ExerciseView.ExerciseViewChoiceFragment;
import com.example.learnjava.ExerciseView.ExerciseViewCodeFragment;
import com.example.learnjava.ExerciseView.ExerciseViewDragDropFragment;
import com.example.learnjava.ExerciseView.ExerciseViewFillBlanksFragment;
import com.example.learnjava.ExerciseView.ExerciseViewOrderFragment;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;
import com.example.learnjava.models.ModelUserProgress;


public class ExerciseFragment extends Fragment implements ExerciseCommunication {

    private ExerciseCommunication mListener;

    private ModelUserProgress progressController;

    private TextView exerciseName;
    private int whatsNext;
    private int viewType;
    private ModelTask currentTask;


    //TODO maye nee a atring array für zusätzliche aufgaben oder teilaufgaben

   // private Button checkButton;

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
        exerciseName = view.findViewById(R.id.exerciseName);
        exerciseName.setText(currentTask.getTaskName());
        setViewContent();


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

    private void setFragmentContent(ModelTask currentTask){
        this.currentTask = currentTask;
        whatsNext = currentTask.getWhatsNext();
        viewType = currentTask.getExerciseViewType();
        Log.i("GIVE_CONTENT", "in exerciseFragment");
    }

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



    public void setExerciseLayout(){

        //TODO get the current exercisenumber and make maaany switch statements?
//        Rel mRlayout = (RelativeLayout) findViewById(R.id.mRlayout);
        LinearLayout.LayoutParams mRparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        EditText myEditText = new EditText(getContext());
        myEditText.setLayoutParams(mRparams);


//        for(String title: listOfTitles){
//            TextView field = new TextView(this);
//            field.setText(title);
//            container.addView(field);
//        }
    }

    private void openNextTask(){
        try {
            //TODO check the answers an report the progress when right, when wrong load dialog feedback
            // set get activity isSolved when correct or check is solved so you can skip the exercise to the next

            if ((getActivity() != null)) {
                //notify the exerciseViewFragment that nextButton is clicked
                if( whatsNext == 2) {
                    ((LessonActivity) getActivity()).openNewTask(2);
                    Log.i("Buttonclicked", " openExerciseFragment");
                }
                else if (whatsNext == 1) {
                    ((LessonActivity) getActivity()).openNewTask(1);
                    Log.i("Buttonclicked", " opnenLesson");
                }
                else if (whatsNext == 3){
                    ((LessonActivity) getActivity()).updateProgressLastTask();
                    Log.i("Buttonclicked", " lastExercise");
                }
                else {
                    Log.e("Buttonclicked", " FalseWhatsNextType");
                }
            }
        }
        catch (Exception e){
            Log.e("Not clickable", "error");
        }
    }

    @Override
    public ModelTask sendCurrentTask() {
        return currentTask;
    }

    @Override
    public void sendAnswerFromExerciseView(boolean answerChecked) {
        if (answerChecked){
            openNextTask();
            progressController.addFinishedExercise(currentTask);
            //TODO give Feedback
            Log.i("ANSWER", " was right");
        }
        else{
            //TODO give feedback
            Log.i("ANSWER", " was wrong");
        }
    }


//    @Override
//    public ModelTask sendCurrentTask() {
//        return currentTask;
//    }
//
//    @Override
//    public void sendAnswerFromExerciseView(boolean answerChecked) {
//        if (answerChecked){
//            openNextTask();
//        }
//        else {
//            Log.i("ANSWER", " was wrong");
//            //TODO give Feedback that answer is false??
//        }
//    }
}
