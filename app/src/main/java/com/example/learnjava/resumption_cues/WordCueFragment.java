package com.example.learnjava.resumption_cues;

//import android.support.v4.app.DialogFragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;

import com.example.learnjava.R;

public class WordCueFragment extends DialogFragment {

    private TextView cueText;
    ConstraintLayout background;
    private Button button;
    LinearLayout holder;


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
        return inflater.inflate(R.layout.fragment_word_cue, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        cueText = view.findViewById(R.id.wordCueText);
        background = view.findViewById(R.id.wordCueBackground);
        button = view.findViewById(R.id.wordCueButton);

        getDialog().setCanceledOnTouchOutside(false);

        //String word = getArguments().getString("word", "Cue Word");
        //TODO make it synammicly
        cueText.setText("A LESSON");
        int section = getArguments().getInt("section", 1);
        setBackground(section);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
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
