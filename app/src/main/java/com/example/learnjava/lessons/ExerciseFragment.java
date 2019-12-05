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

import com.example.learnjava.ExerciseView.ExerciseViewAnswerFragment;
import com.example.learnjava.ExerciseView.ExerciseViewChoiceFragment;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;


public class ExerciseFragment extends Fragment {

    private ExerciseCommunication mListener;

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

    public void setFragmentContent(ModelTask currentTask){
        this.currentTask = currentTask;
        whatsNext = currentTask.getWhatsNext();
        viewType = currentTask.getExerciseViewType();
        Log.i("GIVE_CONTENT", "in exerciseFragment");
    }

    public void setViewContent(){
        switch (viewType){
            case 1:
                ExerciseViewAnswerFragment answerFragment = new ExerciseViewAnswerFragment();
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ExerciseViewHolder, answerFragment)
                        .addToBackStack(null)
                        .commit();
                Log.i("LOADVIEW", " 1 Answer");
                break;
            case 2:
                ExerciseViewChoiceFragment choiceFragment = new ExerciseViewChoiceFragment();
                getChildFragmentManager()
                        .beginTransaction()
                        .replace(R.id.ExerciseViewHolder, choiceFragment)
                        .addToBackStack(null)
                        .commit();
                Log.i("LOADVIEW", " 2 Choice");
                break;

                //TODO the other views

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

    public void openNextTask(){
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

    public interface ExerciseCommunication{
        ModelTask sendCurrentTask();

        void sendAnswerFromExerciseViev(boolean answerChecked);
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
