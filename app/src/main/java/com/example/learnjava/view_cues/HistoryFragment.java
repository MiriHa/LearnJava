package com.example.learnjava.view_cues;

import android.graphics.Typeface;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.DialogFragment;

import android.os.Handler;
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
import com.example.learnjava.controller.SharedPrefrencesManager;
import com.example.learnjava.models.ModelTask;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * This Resumtion Cue histroy of previous lessons: number 3
 */
public class HistoryFragment extends DialogFragment {

    private Controller progressController;
    private LinearLayout historyHolder;
    private ConstraintLayout background;

    public static HistoryFragment newInstance(int currentSection) {
        HistoryFragment fragment = new HistoryFragment();
        Bundle args = new Bundle();
        args.putInt("section", currentSection);
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        progressController = (Controller) getActivity().getApplicationContext();

        Button checkButton = view.findViewById(R.id.historyButton);
        historyHolder = view.findViewById(R.id.historyHolder);
        background = view.findViewById(R.id.historyBackground);

        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);

        checkButton.setEnabled(false);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                // This method will be executed once the timer is over
                checkButton.setEnabled(true);
                checkButton.setBackground(getResources().getDrawable(R.drawable.neutral_button));

            }
        },3000);

        int section = getArguments().getInt("section", 1);
        setHistory();
        setBackground(section);

        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }


    private void setHistory() {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        if (SharedPrefrencesManager.readCurrentSection(getContext()) == SharedPrefrencesManager.readLatestSectionNumber(getContext())) {
            //Give overview over the last lessons
            int currenTaskNumber = SharedPrefrencesManager.readCurrentScreen(getContext());
            //TODO maybe not show the current section only the from before
            ArrayList<ModelTask> lessons = progressController.getTaskContent();

            for (int i = 0; i <= currenTaskNumber; i++) {

                ModelTask currentTask = lessons.get(i);

                if(currentTask.getTaskNumber() < currenTaskNumber && currentTask.getType() == 1) {
                    TextView textView = new TextView(getContext());
                    textView.setLayoutParams(mParams);
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                    Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.trixiesans);
                    textView.setTypeface(typeface);

                    textView.setText(currentTask.getTaskName());


                    historyHolder.addView(textView);

                    if (!(i == currenTaskNumber)) {
                        ImageView imageView = new ImageView(getContext());
                        imageView.setLayoutParams(mParams);
                        imageView.setImageResource(R.drawable.ic_arrow_drop_down_black);
                        historyHolder.addView(imageView);
                    }
                }

            }
        } else {
            //give overview over the whole Section
            //TODO statt tascontetn lesson Names
            ArrayList<ModelTask> onlyLessonTasks = progressController.getOnlyLessons();
            int lessons = onlyLessonTasks.size();
            for (int i = 0; i < lessons; i++) {

                TextView textView = new TextView(getContext());
                textView.setLayoutParams(mParams);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                textView.setGravity(View.TEXT_ALIGNMENT_CENTER);
                Typeface typeface = ResourcesCompat.getFont(getContext(), R.font.trixiesans);
                textView.setTypeface(typeface);

                textView.setText(onlyLessonTasks.get(i).getTaskName());

                historyHolder.addView(textView);

                if (!(i == lessons-1)) {
                    ImageView imageView = new ImageView(getContext());
                    imageView.setLayoutParams(mParams);
                    imageView.setImageResource(R.drawable.ic_arrow_drop_down_black);
                    historyHolder.addView(imageView);
                }
            }

        }
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
            case 9:
                background.setBackgroundColor(getResources().getColor(R.color.section9_color));
                break;
        }

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPrefrencesManager.saveSharedSetting(getContext(), "CUE_OPEN","false");
        Log.i("M_CUE_OPEN_HISTORY","onDestroyView: false");
    }





}
