package com.lmu.learnjava.view_cues;

//import android.support.v4.app.DialogFragment;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.lmu.learnjava.controller.Controller;
import com.lmu.learnjava.R;
import com.lmu.learnjava.controller.SharedPrefrencesManager;

import java.util.Calendar;
import java.util.Date;

/**
 * This is the resumption Cue lastLesson: number 1
 */
public class WordCueFragment extends DialogFragment {

    private ConstraintLayout background;

    private Date entered;


    public WordCueFragment() {
    }


    public static WordCueFragment newIntance(int section) {
        WordCueFragment frag = new WordCueFragment();
        Bundle args = new Bundle();
        args.putInt("section", section);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPrefrencesManager.saveSharedSetting(getContext(), "CUE_OPEN","true");
        View view = inflater.inflate(R.layout.fragment_word_cue, container);
        entered = Calendar.getInstance().getTime();
        Controller progressController = (Controller) getActivity().getApplicationContext();

        TextView cueText = view.findViewById(R.id.wordCueText);
        background = view.findViewById(R.id.wordCueBackground);
        Button checkButton = view.findViewById(R.id.wordCueButton);


        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);

        int lessonNumber = SharedPrefrencesManager.readCurrentScreen(getContext());
        if (!(lessonNumber == 0))
            lessonNumber -= 1;

        String lastTaskName = progressController.getTaskContent().get(lessonNumber).getTaskName();
        Log.i("M_WORD_CUE","lessonNumber: "+lessonNumber+" lastTaskName "+lastTaskName);

        cueText.setText(lastTaskName);

        int section = getArguments().getInt("section", 1);
        setBackground(section);

        checkButton.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                checkButton.setEnabled(true);
                checkButton.setBackground(getResources().getDrawable(R.drawable.neutral_button));

            }
        },1500);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Date dismissed = Calendar.getInstance().getTime();
                String duration = progressController.calculateDuration(entered, dismissed);
                progressController.makeaDurationLog(getContext(),dismissed, "CUE_CLOSED","Word Cue dismissed",duration);
                  dismiss();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void setBackground(int section) {
        switch (section) {
            case 1:
                background.setBackgroundColor(getResources().getColor(R.color.section1_color));
               // holder.setBackgroundResource(R.drawable.alert_right);
                Log.i("M_WORD_CUE", "setBackgroundcolor 1");
                break;
            case 2:
                background.setBackgroundColor(getResources().getColor(R.color.section2_color));
                Log.i("M_WORD_CUE", "setBackgroundcolor 2");
                break;
            case 3:
                background.setBackgroundColor(getResources().getColor(R.color.section3_color));
                Log.i("M_WORD_CUE", "setBackgroundcolor 3");
                break;
            case 4:
                background.setBackgroundColor(getResources().getColor(R.color.section4_color));
                Log.i("M_WORD_CUE", "setBackgroundcolor 4");
                break;
            case 5:
                background.setBackgroundColor(getResources().getColor(R.color.section5_color));
                Log.i("M_WORD_CUE", "setBackgroundcolor 5");
                break;
            case 6:
                background.setBackgroundColor(getResources().getColor(R.color.section6_color));
                break;
            case 7:
                background.setBackgroundColor(getResources().getColor(R.color.section7_color));
                break;
            case 8:
                background.setBackgroundColor(getResources().getColor(R.color.section8_color));
                break;

        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPrefrencesManager.saveSharedSetting(getContext(), "CUE_OPEN","false");
        Log.i("M_CUE_OPEN_WORD","onDestroyView: false");
    }

}
