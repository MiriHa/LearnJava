package com.example.learnjava.resumption_cues;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.learnjava.Controller;
import com.example.learnjava.R;

import java.util.Calendar;

/**
 * This ResumtionCueFragment shows your histroy of previous lessons
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
        View view = inflater.inflate(R.layout.fragment_history, container, false);
        progressController = (Controller) getActivity().getApplicationContext();

        Button gotit = view.findViewById(R.id.historyButton);
        historyHolder = view.findViewById(R.id.historyHolder);
        background = view.findViewById(R.id.historyBackground);

        getDialog().setCanceledOnTouchOutside(false);

        int section = getArguments().getInt("section", 1);
        setHistory(section);
        setBackground(section);

        progressController.makeaLog(Calendar.getInstance().getTime(), "History_CUE", "in section "+ section);
        gotit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        return view;
    }


    private void setHistory(int section) {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //TODO maybe not show the current section only the from before
        for (int i = 1; i <= section; i++) {

            TextView textView = new TextView(getContext());
            textView.setLayoutParams(mParams);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView.setGravity(View.TEXT_ALIGNMENT_CENTER);

            switch (i) {
                case 1:
                    textView.setText(getString(R.string.Lesson1));
                    break;
                case 2:
                    textView.setText(getString(R.string.Lesson2));
                    break;
                case 3:
                    textView.setText(getString(R.string.Lesson3));
                    break;
                case 4:
                    textView.setText(getString(R.string.Lesson4));
                    break;
                case 5:
                    textView.setText(getString(R.string.Lesson5));
                    break;
                case 6:
                    textView.setText(getString(R.string.Lesson6));
                    break;
                case 7:
                    textView.setText(getString(R.string.Lesson7));
                    break;
                case 8:
                    textView.setText(getString(R.string.Lesson8));
                    break;
                case 9:
                    textView.setText(getString(R.string.Lesson9));
                    break;
            }

            historyHolder.addView(textView);

            if(!(i==section)) {
                ImageView imageView = new ImageView(getContext());
                imageView.setLayoutParams(mParams);
                imageView.setImageResource(R.drawable.ic_arrow_drop_down_black);
                historyHolder.addView(imageView);
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


}
