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
import androidx.fragment.app.DialogFragment;

import com.example.learnjava.R;

public class WordCueFragment extends DialogFragment {

    private TextView cueText;
    LinearLayout background;
    private Button button;


    public WordCueFragment(){}

    public static WordCueFragment newIntance(String word, int section){
        WordCueFragment frag = new WordCueFragment();
        Bundle args = new Bundle();
        args.putString("word", word);
        args.putInt("section",section);
        frag.setArguments(args);
        return frag;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_word_cue, container);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        cueText = view.findViewById(R.id.wordCueText);
        background = view.findViewById(R.id.wordCueBackground);
        button = view.findViewById(R.id.wordCueButton);

        String word = getArguments().getString("word","Cue Word");
        cueText.setText(word);
        int section = getArguments().getInt("section",1);

        switch (section){
            case 1:
                background.setBackgroundColor(getResources().getColor(R.color.section1_color));
                Log.i("M_WORD_CUE","setBackgroundcolor");
                break;
            case 2:
                background.setBackgroundColor(getResources().getColor(R.color.section2_color));
                Log.i("M_WORD_CUE","setBackgroundcolor");
                break;
            case 3:
                background.setBackgroundColor(getResources().getColor(R.color.section3_color));
                Log.i("M_WORD_CUE","setBackgroundcolor");
                break;
            case 4:
                background.setBackgroundColor(getResources().getColor(R.color.section4_color));
                Log.i("M_WORD_CUE","setBackgroundcolor");
                break;
            case 5:
                background.setBackgroundColor(getResources().getColor(R.color.section5_color));
                Log.i("M_WORD_CUE","setBackgroundcolor");
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }


}
