
package com.example.learnjava.resumption_cues;

import android.content.Context;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Dimension;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.learnjava.R;
import com.kennycason.kumo.CollisionMode;
import com.kennycason.kumo.WordCloud;
import com.kennycason.kumo.WordFrequency;
import com.kennycason.kumo.bg.RectangleBackground;
import com.kennycason.kumo.font.scale.LinearFontScalar;
import com.kennycason.kumo.nlp.FrequencyAnalyzer;
import com.kennycason.kumo.palette.ColorPalette;

import net.alhazmy13.wordcloud.ColorTemplate;
import net.alhazmy13.wordcloud.WordCloudView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class WordCloudFragment extends DialogFragment {

    List<WordCloud> list ;

    public static WordCloudFragment newInstance(String param1, String param2) {
        WordCloudFragment fragment = new WordCloudFragment();
        Bundle args = new Bundle();
     //   args.putString(ARG_PARAM1, param1);
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

//        WordCloudView wordCloud = view.findViewById(R.id.WordcloudView);

//        Random random = new Random();
//        for (String s : words){
//            list.add(new WordCloud(s,random.nextInt(50)));
//        }

//        String text = "aasd asdasd asdasd";
//
//        String[] data = text.split(" ");
//        list = new ArrayList<>();
//        Random random = new Random();
//        for (String s : data) {
//            list.add(new WordCloud(s,random.nextInt(50)));
//
//            WordCloudView wordCloud = view.findViewById(R.id.WordcloudView);
//            wordCloud.setDataSet(list);
//            wordCloud.setSize(200,200);
//            wordCloud.setColors(ColorTemplate.MATERIAL_COLORS);
//            wordCloud.notifyDataSetChanged();
//        final FrequencyAnalyzer frequencyAnalyzer = new FrequencyAnalyzer();
//        final List<WordFrequency> wordFrequencies = frequencyAnalyzer.load("text/my_text_file.txt");
//        final Dimension dimension = new Dimension(600, 600);
//        final WordCloud wordCloud = new WordCloud(dimension, CollisionMode.RECTANGLE);
//        wordCloud.setPadding(0);
//        wordCloud.setBackground(new RectangleBackground(dimension));
//        wordCloud.setColorPalette(new ColorPalette(Color.RED, Color.GREEN, Color.YELLOW, Color.BLUE));
//        wordCloud.setFontScalar(new LinearFontScalar(10, 40));
//        wordCloud.build(wordFrequencies);
//        wordCloud.writeToFile("kumo-core/output/wordcloud_rectangle.png");

        return view;


    }



}
