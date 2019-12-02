package com.example.learnjava.lessons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.learnjava.Controller;
import com.example.learnjava.R;

public class Lesson1 extends AppCompatActivity{

    private int sectionNumber;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson1);

        final Controller progressController = (Controller) getApplicationContext();


        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b!=null)
        {
            sectionNumber = (int) b.get("LESSON_NUMBER");
            Log.d("Section opened", " "+ sectionNumber);
            progressController.updateFinishedSection(sectionNumber);
        }

       openLessonFragment();
    }


    public void openLessonFragment(){
        LessonFragment lessonFragment = new LessonFragment();
        lessonFragment.setFragmentContent("DataTypes and so " + sectionNumber, "blaaablablbalbla");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.FragmentHolder, lessonFragment)
                .addToBackStack(null)
                .commit();
        Log.d("FirstLessonfragOpened", " lesson");
    }

    public void onNextButtonClickedExercise(){
        ExerciseFragment exerciseFragment = new ExerciseFragment();
        //TOdo automate the content setting
        exerciseFragment.setFragmentContent("DataTypes exercise", "1+1=42", "2+2 = 69");
        getSupportFragmentManager()
                .beginTransaction()
                //.add(R.id.FragmentExerciseHolder, exerciseFragment)
                .replace(R.id.FragmentHolder, exerciseFragment)
        //TODO verbessere abckstack/Überlappen von fragments
                .addToBackStack(null)
                .commit();
        Log.d("Nextbuttenclicked", " exercise");

    }

    public void onNextButtonClickedLesson(){
        LessonFragment lessonFragment = new LessonFragment();
        //TOdo automate the content setting
        lessonFragment.setFragmentContent("DataTypes 2", "blaaablablbalblabluuuuuuuuuuuub");
        getSupportFragmentManager()
                .beginTransaction()
                //.add(R.id.FragmentHolder, lessonFragment)
                .replace(R.id.FragmentHolder, lessonFragment)
                .addToBackStack(null)
                .commit();
        Log.d("Nextbuttenclicked", " lesson");
    }


    //TODO some method that checks if its the last fragment of a section

    //TODO some method that reprots the progrss from the fragments -> need json reader



}
