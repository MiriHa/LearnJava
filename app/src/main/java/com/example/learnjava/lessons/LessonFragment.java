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
import com.example.learnjava.models.ModelTask;

public class LessonFragment extends Fragment {

   private TextView lessonName;
   private TextView lessonText;
   private TextView lessonExample;
   private Button nextButton;

   private int whatsNext;

   private ModelTask currentTask;

    public LessonFragment() {
        // Required empty public constructor
    }

    //TODO check the progress of a user an read the right json data und set this data

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);

        lessonName = view.findViewById(R.id.lessonName);
        lessonText = view.findViewById(R.id.lessonText);
        lessonExample = view.findViewById(R.id.lessonExample);
        nextButton = view.findViewById(R.id.nextButtonLessonFrag);

        Log.i("WHats Next", " " + whatsNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Buttonclicked", " inLessonFragment. Next: "+whatsNext );
                if ((getActivity() != null)) {
                    if( whatsNext == 2) {
                        ((LessonActivity) getActivity()).openNewTask(2);
                        Log.i("Buttonclicked", " openExercise");
                    }
                    else if (whatsNext == 1) {
                        ((LessonActivity) getActivity()).openNewTask(1);
                        Log.i("Buttonclicked", " openLesson");
                    }
                    else if (whatsNext == 3){
                        ((LessonActivity) getActivity()).updateProgressLastTask();
                        Log.i("Buttonclicked", " lastLesson");
                    }
                    else {
                        Log.e("Buttonclicked", " FalseWhatsNextType");
                    }
                }
            }
        });

        lessonName.setText(currentTask.getTaskName());
        lessonText.setText(currentTask.getTaskText());
       // lessonExample.setText("Maybe we need in json more text section for examples to better format them");

        return view;

    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }


    @Override
    public void onStart() {
        super.onStart();
      /*  try {
            fragmentInterface = (ExerciseVIewCommunication) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "Error in retreiving data. must implement FragmentInterface");
        }
*/
    }

    public void setFragmentContent(ModelTask currentTask){
        Log.i("GIVE_CONTENT", "in LessonFragment");
        this.currentTask = currentTask;
        whatsNext = currentTask.getWhatsNext();
    }
}
