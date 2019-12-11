package com.example.learnjava.lessons;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.learnjava.Controller;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;

import static android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD;

public class LessonFragment extends Fragment {

   private TextView lessonName;
   private LinearLayout textHolder;
   private Button nextButton;

   private int whatsNext;

   private ModelTask currentTask;
   private Controller progressController;

    public LessonFragment() {
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
        View view = inflater.inflate(R.layout.fragment_lesson, container, false);
        progressController = (Controller) getContext().getApplicationContext();

        textHolder = view.findViewById(R.id.lessonTextHolder);

        lessonName = view.findViewById(R.id.lessonName);

        nextButton = view.findViewById(R.id.nextButtonLessonFrag);

        Log.i("M WHats Next", " " + whatsNext);
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("M Buttonclicked", " inLessonFragment. Next: "+whatsNext );
                if ((getActivity() != null)) {
                    progressController.setLastLesson(currentTask);
                    if( whatsNext == 2) {
                        ((LessonActivity) getActivity()).openNewTask(2);
                        Log.i("M Buttonclicked", " openExercise");
                    }
                    else if (whatsNext == 1) {
                        ((LessonActivity) getActivity()).openNewTask(1);
                        Log.i("M Buttonclicked", " openLesson");
                    }
                    else if (whatsNext == 3){
                        ((LessonActivity) getActivity()).updateProgressLastTask();
                        Log.i("M Buttonclicked", " lastLesson");
                    }
                    else {
                        Log.e("M Buttonclicked", " FalseWhatsNextType");
                    }
                }
            }
        });

        lessonName.setText(currentTask.getTaskName());
        setLessonText();
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
            fragmentInterface = (ExerciseCommunication) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "Error in retreiving data. must implement FragmentInterface");
        }
*/
    }

    public void setFragmentContentLesson(ModelTask currentTask){
        Log.i("M GIVE_CONTENT", "in LessonFragment");
        this.currentTask = currentTask;
        whatsNext = currentTask.getWhatsNext();
    }

    private void setLessonText(){
        String text = currentTask.getTaskText();
        //Split the string so that in can be passed to diffrent textviews
        String[] textParts = text.split("@");

        for(int i = 0; i < textParts.length; i++){
          // TextView textView = getTextView(i);
            String currentText = textParts[i];
            //TODO make some words bold?
           // String[] boldParts = currentText.split("§");

                LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                TextView myTextView = new TextView(getContext());
                myTextView.setLayoutParams(mParams);
                myTextView.setText(currentText);
                myTextView.setPadding(8,12,8,12);
                //TODO find a better way or see if its in packages
                //myTextView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                myTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                textHolder.addView(myTextView);

        }
    }


}
