package com.example.learnjava.resumption_cues;

//import android.support.v4.app.DialogFragment;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.learnjava.R;

public class WordCueFragment extends DialogFragment {

    private TextView cueText;
    private Button button;


    public WordCueFragment(){}

    public static WordCueFragment newIntance(String word){
        WordCueFragment frag = new WordCueFragment();
        Bundle args = new Bundle();
        args.putString("word", word);
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

        String word = getArguments().getString("word","Cue Word");
        cueText.setText(word);
    }
}
