package com.example.learnjava.lessons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toolbar;

import com.example.learnjava.Controller;
import com.example.learnjava.MainActivity;
import com.example.learnjava.R;
import com.example.learnjava.ReadJson;
import com.example.learnjava.models.ModelExercise;
import com.example.learnjava.models.ModelLesson;

import java.util.ArrayList;

public class Lesson1 extends AppCompatActivity{

    Controller progressController;
    private int sectionNumber;
    private boolean shouldAllowBack = false;
    ArrayList<ModelLesson> lessonContent;
    ArrayList<ModelExercise> exerciseContent;

    ReadJson readJson = new ReadJson();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson1);

        progressController = (Controller) getApplicationContext();

        Intent intent = getIntent();
        Bundle b = intent.getExtras();

        if(b!=null)
        {
            sectionNumber = (int) b.get("LESSON_NUMBER");
            Log.d("Section opened", " "+ sectionNumber);
            progressController.updateFinishedSection(sectionNumber);
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Section" + sectionNumber);


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


    public void loadContent(){
        String sectionfile = "section"+sectionNumber;
       lessonContent =  readJson.readLessons(sectionfile, this);
       exerciseContent = readJson.readExercises(sectionfile, this);

    }

    @Override
    public void onBackPressed() {

        Log.i("BackButtonPressed", " in navigation");
        //TODO shouldallwback method?
        if (!shouldAllowBack) {
            super.onBackPressed();
            //don't allow it when first lesson is reached -> us then back button or ask if you want to exit the lesson
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //TODO display an dialog an notify the progressmodel to discard all progress?
                Intent intent = new Intent(Lesson1.this, MainActivity.class);
                startActivity(intent);
                Log.d("ChangeActivity", " to Mainactivity");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
