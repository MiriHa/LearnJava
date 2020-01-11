
package com.example.learnjava.resumption_cues;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Dimension;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.learnjava.Controller;
import com.example.learnjava.R;
import com.example.learnjava.SharedPrefrencesManager;
import com.example.learnjava.models.ModelTask;

import net.alhazmy13.wordcloud.ColorTemplate;
import net.alhazmy13.wordcloud.WordCloud;
import net.alhazmy13.wordcloud.WordCloudView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;


/**
 * This is the Resumption cue Word cloud: number 2
 */
public class WordCloudFragment extends DialogFragment {


    ArrayList<String> keywords = new ArrayList<>();
    private Controller progressController;

    private ConstraintLayout background;


    public static WordCloudFragment newInstance(int section) {
        WordCloudFragment fragment = new WordCloudFragment();
        Bundle args = new Bundle();
        args.putInt("section", section);
//        args.putString(ARG_PARAM2, param2);
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
        View view = inflater.inflate(R.layout.fragment_word_cloud, container, false);

        //set importent data
        progressController = (Controller) getActivity().getApplicationContext();
        getDialog().setCanceledOnTouchOutside(false);

        background = view.findViewById(R.id.wordCloudCueBackground);

        int section = getArguments().getInt("section", 1);
        setBackground(section);

        //make the wordcloud

        getKeywords(section);

        List<WordCloud> list = new ArrayList<>();
        Random random = new Random();
        for (String s : keywords) {
            list.add(new WordCloud(s, random.nextInt(keywords.size())));
        }
        WordCloudView wordCloud = view.findViewById(R.id.WordcloudView);
        wordCloud.setDataSet(list);
        //wordCloud.setSize(200, 400);
        wordCloud.setColors(ColorTemplate.MATERIAL_COLORS);
        wordCloud.setScale(50, 15);
//        wordCloud.setColors(new int[] {Color.BLUE, Color.GRAY, Color.GREEN, Color.CYAN });
        wordCloud.notifyDataSetChanged();


        //set the ButtonFunction
        Button checkButton = view.findViewById(R.id.WordCloudButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;

    }

    private void getKeywords(int section){
        ArrayList<ModelTask> lessons = progressController.getOnlyLessons();

        if(section == SharedPrefrencesManager.readLatestSectionNumber(getContext())){
            int currenTaskNumber = SharedPrefrencesManager.readCurrentScreen(getContext());

            for(int i =0; i<=currenTaskNumber; i++){

                ModelTask currentLesson = lessons.get(i);
                if(currentLesson.getTaskNumber() <= currenTaskNumber) {
                    Collections.addAll(keywords, lessons.get(i).getKeywords());
                }
            }
        }else {
            for (int i = 0; i < lessons.size(); i++) {
                Collections.addAll(keywords, lessons.get(i).getKeywords());
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
