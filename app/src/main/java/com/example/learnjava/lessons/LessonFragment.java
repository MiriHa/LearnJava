package com.example.learnjava.lessons;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.learnjava.FragmentCommunication;
import com.example.learnjava.R;
import com.example.learnjava.lessons.Lesson1;

public class LessonFragment extends Fragment {

    FragmentCommunication fragmentInterface;

   private TextView lessonName;
   private TextView lessonText;
   private Button nextButton;

   private String lessonNameString;
   private String lessonTextString;

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
        nextButton = view.findViewById(R.id.nextButtonLessonFrag);

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO check the progress and make switch statement if exercise or lesson is needed
                if ((getActivity() != null)) {
                    ((Lesson1) getActivity()).onNextButtonClickedExercise();
                    Log.d("Buttonclicked", " inLessonFragment");
                }
            }
        });

        lessonName.setText(lessonNameString);
        lessonText.setText(lessonTextString);

        return view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }

    public void buttonClicked(){
        ((Lesson1)getActivity()).onNextButtonClickedExercise();
    }


    @Override
    public void onStart() {
        super.onStart();
      /*  try {
            fragmentInterface = (FragmentCommunication) getActivity();
        } catch (ClassCastException e) {
            throw new ClassCastException(getActivity().toString() + "Error in retreiving data. must implement FragmentInterface");
        }
*/
    }

    public void setFragmentContent(String name, String text){
        lessonNameString = name;
        lessonTextString = text;
    }
}
