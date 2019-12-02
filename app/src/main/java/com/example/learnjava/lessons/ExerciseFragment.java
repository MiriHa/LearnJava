package com.example.learnjava.lessons;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.learnjava.R;


public class ExerciseFragment extends Fragment {

    private String exerciseNameString;
    private String exerciseTextString;
    private String exerciseExampleString;
    private TextView exerciseName;
    private TextView exerciseText;
    private TextView exerciseExample;

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
        Log.d("InflateLayout", "in exerciseFragment");
        exerciseName = view.findViewById(R.id.exerciseName);
        exerciseText = view.findViewById(R.id.exerciseText);
        exerciseExample = view.findViewById(R.id.exerciseExample);
        Button checkButton = view.findViewById(R.id.nextButtonExerciseFrag);


        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                        //TODO check the progress and make switch statement if exercise or lesson is needed
                    //TODO check the answers an report the progress when right, when wrong load dialog feedback
                        if((getActivity() != null)){
                            ((LessonActivity) getActivity()).openNewLesson();
                            Log.d("Buttonclicked", " inLessonFragment");
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

    public void setFragmentContent(String name, String text, String example){
        exerciseNameString = name;
        exerciseTextString = text;
        exerciseExampleString = example;
        Log.d("SetFragmentContent", " is set");
    }

}
