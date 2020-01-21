package com.example.learnjava.view_cues;

import android.content.SharedPreferences;
import android.media.midi.MidiOutputPort;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnjava.controller.Controller;
import com.example.learnjava.R;
import com.example.learnjava.controller.SharedPrefrencesManager;
import com.example.learnjava.models.ModelQuestion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

/**
 * This is the resumption cue Question: number 4
 */
public class QuestionsFragment extends DialogFragment {


    private Controller progressController;

    private ConstraintLayout background;
    private TextView bigText, rightAnswer;
    private Button checkButton;
    private RadioButton answer1, answer2, answer3;
    private RadioGroup answerGroup;

    private ModelQuestion currentQuestion;
    private String[] answers;
    private String sectionString;
    private int userAnswer = 0;
    private boolean answerChecked = false;

    public static QuestionsFragment newInstance(int sectionWhat) {
        QuestionsFragment fragment = new QuestionsFragment();
        Bundle args = new Bundle();
        args.putInt("SectionWhat", sectionWhat);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        SharedPrefrencesManager.saveSharedSetting(getContext(), "CUE_OPEN","true");
        View view = inflater.inflate(R.layout.fragment_questions, container, false);
        progressController = (Controller) getActivity().getApplicationContext();

        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);

        //get the currentSection and set the Background and de SectionString
        int sectionWhat = getArguments().getInt("SectionWhat", 1);
        background = view.findViewById(R.id.questionBackground);
        setBackground(sectionWhat);

        Log.i("M_QUESTION_CUE","sectionwhat: "+sectionString);
        //fetch the questions and choose a random one
        ArrayList<ModelQuestion> sectionQuestions = progressController.getQuestions(getActivity(), sectionString);

        Random random = new Random();
        int randomInt;
        //maybe add to random nummer +
        int latestTaskNumber = SharedPrefrencesManager.readLatestSectionNumber(getContext());
        if(sectionWhat == latestTaskNumber){
            int currentQuestionSize = 0;
            for (int i=0;i<sectionQuestions.size();i++){
                ModelQuestion currentQuestion = sectionQuestions.get(i);
               if(currentQuestion.getNumber() <= latestTaskNumber)
                   currentQuestionSize += 1 ;
            }
            randomInt = random.nextInt(currentQuestionSize);
        }else {
            randomInt = random.nextInt(sectionQuestions.size());
        }
        //int randomInt = random.;
        Log.i("M_QUESTION_CUE"," randomInt "+randomInt);
        currentQuestion = sectionQuestions.get(randomInt);

        //TODO only questions to latest task when in latest section??

        bigText = view.findViewById(R.id.questionBigText);
        rightAnswer = view.findViewById(R.id.questionRightAnswer);

        TextView questionText = view.findViewById(R.id.questionCueText);
        questionText.setText(currentQuestion.getQuestion());

        answers = currentQuestion.getAnswers();

        answer1 = view.findViewById(R.id.questionAnswer1);
        answer1.setText(answers[0]);
        answer2 = view.findViewById(R.id.questionAnswer2);
        answer2.setText(answers[1]);
        answer3 = view.findViewById(R.id.questionAnswer3);
        answer3.setText(answers[2]);

        answerGroup = view.findViewById(R.id.questionAnswerGroup);
        answerGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.questionAnswer1:
                        userAnswer = 1;
                        Log.i("M_QUESTION_CUE","answer 1 choosen: "+userAnswer);
                        break;
                    case R.id.questionAnswer2:
                        userAnswer = 2;
                        Log.i("M_QUESTION_CUE","answer 2 choosen: "+userAnswer);
                        break;
                    case R.id.questionAnswer3:
                        userAnswer = 3;
                        Log.i("M_QUESTION_CUE","answer 3 choosen: "+userAnswer);
                        break;
                }
            }
        });

       checkButton = view.findViewById(R.id.questionCueButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!answerChecked)
                    checkAnswers();
                else
                    dismiss();
            }
        });

        return view;
    }


    private void checkAnswers() {

        if (userAnswer == 0) {
            Toast.makeText(getContext(), "Please choose an answer", Toast.LENGTH_SHORT).show();
        } else {
            int answerInt = currentQuestion.getAnswerInt();
            Log.i("M_QUESTION_CUE", "userAnswerInt: " + userAnswer + " solutionINt: " + answerInt);
            if (answerInt == userAnswer) {
                setFeedback(answerInt);
                progressController.makeaLog(getContext(), Calendar.getInstance().getTime(), "QUESTION_CUE", "number: " + currentQuestion.getNumber() + " answer: right");
                bigText.setText(R.string.Question_right);
                Log.i("M_QUESTION_CUE", "answer: true, dismiss");
            } else {
                setFeedback(answerInt);
                progressController.makeaLog(getContext(), Calendar.getInstance().getTime(), "QUESTION_CUE", "number: " + currentQuestion.getNumber() + " answer: wrong");
                bigText.setText(R.string.Question_wrong);
                checkButton.setBackgroundResource(R.drawable.button_altert_red);
                Log.i("M_QUESTION_CUE", "answer: false, show right answer");
            }
        }
    }

    private void setFeedback(int answerInt){
        answerChecked = true;
        checkButton.setText(R.string.Cue_Button);

        //Set the ColorFeedback
        switch (answerInt){
            case 1:
                answer1.setTextColor(getResources().getColor(R.color.Greenbright));
                answer2.setTextColor(getResources().getColor(R.color.Red));
                answer3.setTextColor(getResources().getColor(R.color.Red));
                answer1.setChecked(true);

                break;
            case 2:
                answer2.setTextColor(getResources().getColor(R.color.Greenbright));
                answer1.setTextColor(getResources().getColor(R.color.Red));
                answer3.setTextColor(getResources().getColor(R.color.Red));
                answer2.setChecked(true);
                break;
            case 3:
                answer3.setTextColor(getResources().getColor(R.color.Greenbright));
                answer2.setTextColor(getResources().getColor(R.color.Red));
                answer1.setTextColor(getResources().getColor(R.color.Red));
                answer3.setChecked(true);
                break;
        }
        //make it unclickable
        answer1.setClickable(false);
        answer2.setClickable(false);
        answer3.setClickable(false);
    }


    private void setBackground(int sectionWhat) {
        switch (sectionWhat) {
            case 1:
                background.setBackgroundColor(getResources().getColor(R.color.section1_color));
                sectionString = "section1";
                // holder.setBackgroundResource(R.drawable.alert_right);
                Log.i("M_WORD_CUE", "setBackgroundcolor 1");
                break;
            case 2:
                background.setBackgroundColor(getResources().getColor(R.color.section2_color));
                sectionString = "section2";
                Log.i("M_WORD_CUE", "setBackgroundcolor 2");
                break;
            case 3:
                background.setBackgroundColor(getResources().getColor(R.color.section3_color));
                sectionString = "section3";
                Log.i("M_WORD_CUE", "setBackgroundcolor 3");
                break;
            case 4:
                background.setBackgroundColor(getResources().getColor(R.color.section4_color));
                sectionString = "section4";
                Log.i("M_WORD_CUE", "setBackgroundcolor 4");
                break;
            case 5:
                background.setBackgroundColor(getResources().getColor(R.color.section5_color));
                sectionString = "section5";
                Log.i("M_WORD_CUE", "setBackgroundcolor 5");
                break;
            case 6:
                background.setBackgroundColor(getResources().getColor(R.color.section6_color));
                sectionString = "section6";
                break;
            case 7:
                background.setBackgroundColor(getResources().getColor(R.color.section7_color));
                sectionString = "section7";
                break;
            case 8:
                background.setBackgroundColor(getResources().getColor(R.color.section8_color));
                sectionString = "section8";
                break;
            case 9:
                background.setBackgroundColor(getResources().getColor(R.color.section9_color));
                sectionString = "section9";
                break;
        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPrefrencesManager.saveSharedSetting(getContext(), "CUE_OPEN","false");
        Log.i("M_CUE_OPEN_QUES","onDestroyView: false");
    }
}
