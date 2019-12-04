package com.example.learnjava.lessons;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.learnjava.ExerciseView.ViewArrayAdapter;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;


public class ExerciseFragment extends Fragment {

    TextView exerciseName;
    private int whatsNext;
    private ModelTask currentTask;

    private RecyclerView recyclerView;

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

        ViewArrayAdapter viewArrayAdapter = new ViewArrayAdapter(currentTask);
        recyclerView = view.findViewById(R.id.exerciseView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(viewArrayAdapter);
        Button checkButton = view.findViewById(R.id.nextButtonExerciseFrag);


        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    //TODO check the answers an report the progress when right, when wrong load dialog feedback
                    // set get activity isSolved when correct or check is solved so you can skip the exercise to the next
                    if ((getActivity() != null)) {
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
        });

        exerciseName.setText(currentTask.getTaskName());

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
        Log.i("GIVE_CONTENT", "in exerciseFragment");
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

}
