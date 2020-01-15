package com.example.learnjava.view_sections;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Html;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.learnjava.controller.Controller;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;

import java.util.Calendar;

public class LessonFragment extends Fragment {

   private TextView lessonName;
   private LinearLayout textHolder;
   private ConstraintLayout background;
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
        background = view.findViewById(R.id.LessonHolder);

        lessonName = view.findViewById(R.id.lessonName);

        nextButton = view.findViewById(R.id.nextButtonLessonFrag);

        Log.i("M_LESSON_FRAGMENT", " whatsNext: " + whatsNext + " currenTasknumber: " + currentTask.getTaskNumber());
        progressController.makeaLog(Calendar.getInstance().getTime(), "ENTERED_A_LESSON", "number: " + currentTask.getTaskNumber() + "section: "+currentTask.getSectionNumber());

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("M_LESSON_FRAGMENT", "Button clicked, Next: "+whatsNext );
                if ((getActivity() != null)) {
                    //save this as the Las lesson to come back to
                    progressController.setLastLesson(getContext(),currentTask.getTaskNumber());
                    //Add lesson to finished tasks
                    progressController.addFinishedTask(getContext(),currentTask);
                    //open a exerciseFragment
                    if( whatsNext == 2) {
                        ((LessonActivity) getActivity()).openNewTask(2);
                        Log.i("M_LESSON_FRAGMENT", " openExercise");
                    }
                    //open a lessonFragment
                    else if (whatsNext == 1) {
                        ((LessonActivity) getActivity()).openNewTask(1);
                        Log.i("M_LESSON_FRAGMENT", " openLesson");
                    }
                    //finish the section an go to the main Screen
                    else if (whatsNext == 3){
                        ((LessonActivity) getActivity()).updateProgressLastTask();
                        Log.i("M_LESSON_FRAGMENT", " lastLesson");
                    }
                    else {
                        Log.e("M_LESSON_FRAGMENT", " FalseWhatsNextType");
                    }
                }
            }
        });

        lessonName.setText(currentTask.getTaskName());
        setLessonText();
        setSectionColor();

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
        Log.i("M_LESSON_FRAGMENt", "setFragmentCOntent");
        this.currentTask = currentTask;
        whatsNext = currentTask.getWhatsNext();
    }

    private void setLessonText(){
        String text = currentTask.getTaskText();
        //Split the string so that in can be passed to diffrent textviews
        String[] textParts = text.split("@");

//        for(int i = 0; i < textParts.length; i++){
        for(String element : textParts){

//            String[] pic = element.split("#");
//            for(String pice : pic) {
            LinearLayout.LayoutParams mParamsFit = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

            if(String.valueOf(element.charAt(0)).equals("_")){

                    String uri = "@drawable/"+element;  // where myresource (without the extension) is the file
                    String name = getActivity().getPackageName();

                    int imageResource = getResources().getIdentifier(uri, null, getActivity().getPackageName());

                    Drawable res = getResources().getDrawable(imageResource);

                    ImageView imageView = new ImageView(getContext());
                    imageView.setLayoutParams(mParamsFit);
                    imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
                    imageView.setClickable(false);
                    imageView.setBackgroundColor(getResources().getColor(R.color.grey));
                    imageView.setAdjustViewBounds(true);
                    imageView.setImageDrawable(res);
                    textHolder.addView(imageView);

                } else {

                    TextView myTextView = new TextView(getContext());
                    myTextView.setLayoutParams(mParams);
                    myTextView.setText(Html.fromHtml(element));
                    myTextView.setPadding(10, 6, 10, 6);
                    //TODO find a better way or see if its in packages
                    //  myTextView.setJustificationMode(Layout.JUSTIFICATION_MODE_INTER_WORD);
                    myTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

                    textHolder.addView(myTextView);
//                }
            }

        }
    }

    private void setSectionColor() {
        switch ((int) progressController.getCurrentSection(getContext())) {

            case 1:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(),(R.color.section1_color)));
                break;
            case 2:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(),(R.color.section2_color)));
                break;
            case 3:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.section3_color));
                break;
            case 4:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(),(R.color.section4_color)));
                break;
            case 5:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(),(R.color.section5_color)));
                break;
            case 6:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.section6_color));
                break;
            case 7:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(),(R.color.section7_color)));
                break;
            case 8:
                background.setBackgroundColor(ContextCompat.getColor(getActivity(),(R.color.section8_color)));
                break;
//            case 9:
//                background.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.section9_color));
//                break;


        }
    }


}
