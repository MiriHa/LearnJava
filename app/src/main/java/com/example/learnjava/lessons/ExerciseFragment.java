package com.example.learnjava.lessons;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.learnjava.R;


public class ExerciseFragment extends Fragment {

    private String exerciseNameString;
    private String exerciseTextString;
    private String exerciseExampleString = "aha this is a testString";
    private int whatsNext;

    //TODO maye nee a atring array für zusätzliche aufgaben oder teilaufgaben

    private LinearLayout exerciseContainer;

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
        TextView exerciseName = view.findViewById(R.id.exerciseName);
        TextView exerciseText = view.findViewById(R.id.exerciseText);
        //TextView exerciseExample = view.findViewById(R.id.exerciseExample);
        exerciseContainer = view.findViewById(R.id.ExerciseContainerLayout);

        Button checkButton = view.findViewById(R.id.nextButtonExerciseFrag);


        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //TODO check the answers an report the progress when right, when wrong load dialog feedback
                    if ((getActivity() != null)) {
                        if( whatsNext == 2) {
                            ((LessonActivity) getActivity()).openNewExercise();
                            Log.i("Buttonclicked", " openExerciseFragment");
                        }
                        else if (whatsNext == 1) {
                            ((LessonActivity) getActivity()).openNewLesson();
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
        });

        exerciseName.setText(exerciseNameString);
        exerciseText.setText(exerciseTextString);

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

    public void setFragmentContent(String name, String text, int next){
        exerciseNameString = name;
        exerciseTextString = text;
        //exerciseExampleString = example;
        whatsNext = next;
        Log.i("GIVE_CONTENT", "in exerciseFragment");
    }

    public void setExerciseLayout(){

        //TODO get the current exercisenumber and make maaany switch statements?
//        Rel mRlayout = (RelativeLayout) findViewById(R.id.mRlayout);
        LinearLayout.LayoutParams mRparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        EditText myEditText = new EditText(getContext());
        myEditText.setLayoutParams(mRparams);
        exerciseContainer.addView(myEditText);


//        for(String title: listOfTitles){
//            TextView field = new TextView(this);
//            field.setText(title);
//            container.addView(field);
//        }
    }

}
