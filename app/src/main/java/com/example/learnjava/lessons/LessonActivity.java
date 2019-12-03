package com.example.learnjava.lessons;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.learnjava.Controller;
import com.example.learnjava.MainActivity;
import com.example.learnjava.R;
import com.example.learnjava.ReadJson;
import com.example.learnjava.models.ModelExercise;
import com.example.learnjava.models.ModelLesson;

import java.util.ArrayList;

public class LessonActivity extends AppCompatActivity{

    Controller progressController;
    private int sectionNumber;
    private boolean shouldAllowBack = false;

    ArrayList<ModelLesson> lessonContent;
    ArrayList<ModelExercise> exerciseContent;

    ModelLesson currentLesson;
    ModelExercise currentExercise;

    ReadJson readJson = new ReadJson();

    int progressLessons = 0;
    int progressExercises = 0;
    int progressCurrentScreen = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        //get the progresscontroller
        progressController = (Controller) getApplicationContext();

        //get the recent sectionnumber to identifiy the section
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null)
            sectionNumber = (int) b.get("LESSON_NUMBER");
            Log.i("Section opened", " "+ sectionNumber);
            progressController.updateCurrentSection(sectionNumber);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Section" + sectionNumber);

        //get the Lesson content
        loadContent();

        //open the first lesson Fragment
        loadFirstLesson();
    }


    public void openNewExercise(){
        progressExercises += 1;
        progressCurrentScreen += 1;
        ExerciseFragmentChoice exerciseFragmentChoice = new ExerciseFragmentChoice();
        //TOdo automate the content setting
        exerciseFragmentChoice.setFragmentContent("DataTypes exercise", "1+1=42", "2+2 = 69");
        exerciseFragmentChoice.setExerciseLayout();
        getSupportFragmentManager()
                .beginTransaction()
                //.add(R.id.FragmentExerciseHolder, exerciseFragmentChoice)
                .replace(R.id.FragmentHolder, exerciseFragmentChoice)
        //TODO verbessere abckstack/Überlappen von fragments
                .addToBackStack(null)
                .commit();

        Log.d("Nextbuttenclicked", " exercise");
        updateProgress();
    }

    public void openNewLesson(){
        progressLessons += 1;
        progressCurrentScreen +=1;
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
        updateProgress();
    }

    public void loadFirstLesson(){
        LessonFragment lessonFragment = new LessonFragment();
        //TOdo automate the content setting
        lessonFragment.setFragmentContent("DataTypes 2", "THis is the first lessonfragment");
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.FragmentHolder, lessonFragment)
                .addToBackStack(null)
                .commit();
        Log.d("FirstLesson", " loaded");
    }

    public void findCurrentContent(){
        if(progressCurrentScreen == 0)
             currentLesson = lessonContent.get(0);


    }

    public String getNext(){

        return currentLesson.getWhatsNext();
    }


    public boolean checkIfSectionFinished(){

        int lastLesson = lessonContent.size();
        int lastExercise = exerciseContent.size();

        if(progressExercises == lastExercise && progressLessons == lastLesson)
            return true;
        else
            return false;

    }

    //TODO some method that reprots the progrss from the fragments -> need to add the current/finished lessons?
    //Maybe just report current screen and rightfully solved exercises

    public void updateProgress(){
        progressController.updateCurrentScreen(progressCurrentScreen);

        if(checkIfSectionFinished())
            progressController.updateFinishedSection(sectionNumber);
    }

    //TODO some method that gets the next lessonnumber and it`s content


    public void loadContent(){
        String sectionFile = "section"+sectionNumber;
       lessonContent =  readJson.readLessons(sectionFile, this);
       exerciseContent = readJson.readExercises(sectionFile, this);
       Log.i("loadContent", " in Lessonactivity"+sectionNumber);

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
                Intent intent = new Intent(LessonActivity.this, MainActivity.class);
                startActivity(intent);
                Log.d("ChangeActivity", " to Mainactivity");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


}
