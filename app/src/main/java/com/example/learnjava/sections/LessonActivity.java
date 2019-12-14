package com.example.learnjava.sections;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.learnjava.Controller;
import com.example.learnjava.MainActivity;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;

import java.util.ArrayList;

public class LessonActivity extends AppCompatActivity {

    //Use a songelton?
    //ModelUserProgress userProgress;

    Controller progressController;
    private int sectionNumber;
    private boolean shouldAllowBack = false;

    ArrayList<ModelTask> taskContent;

    ModelTask currentTask;
    int currentTaskNumber;

    int currentSection;


    //TODO use the one from the progressMOdel instead of here?
    int progressCurrentScreen = 0;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        //get the progresscontroller
        progressController = (Controller) getApplicationContext();


        //use a singelton
        //userProgress = ModelUserProgress.getInstance();

        //get the recent sectionnumber to identifiy the section
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if(b!=null)
            sectionNumber = (int) b.get("LESSON_NUMBER");
            Log.i("M Section opened", " "+ sectionNumber);
            progressController.updateCurrentSection(sectionNumber);

        progressController.loadContent(sectionNumber, this);
        taskContent = progressController.getTaskContent();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getSectionTitle());


        //get the current task content
       // setCurrentTask();

        //open the first lesson Fragment
        openNewTask(0);

    }


    public void openNewTask(int taskType){

        switch (taskType) {
            //open the first Lesson
            case 0:
                //get the currentTask content
                setCurrentTask();
                Log.i("M CheckProgress", " currentProgreessScreen: " + progressCurrentScreen +" currentNmber: " +currentTask.getTaskNumber());
                //load a fragment
                LessonFragment firstlessonFragment = new LessonFragment();
                firstlessonFragment.setFragmentContentLesson(currentTask);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.FragmentHolder, firstlessonFragment)
                        .addToBackStack(null)
                        .commit();
                Log.d(" M FirstLesson", " loaded progress: " + progressCurrentScreen);
                break;

            //open the next Lesson
            case 1:
                checkProgress();
                setCurrentTask();
                //open new lessonFragment and set its content
                LessonFragment lessonFragment = new LessonFragment();
                lessonFragment.setFragmentContentLesson(currentTask);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FragmentHolder, lessonFragment)
                        .addToBackStack(null)
                        .commit();
                Log.d(" M loadLesson", " progress: " + progressCurrentScreen);
                break;

            //open the next Exercise
            case 2:
                //update the CurrentScreen and currentTask
                checkProgress();
                setCurrentTask();
                //Open a new Fragment and set its content
                ExerciseFragment exerciseFragment = new ExerciseFragment();
                exerciseFragment.setFragmentContentExercise(currentTask);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FragmentHolder, exerciseFragment)
                        //TODO verbessere abckstack/Überlappen von fragments
                        .addToBackStack(null)
                        .commit();

                Log.i(" M LoadExercise", " progress: " + progressCurrentScreen);
                break;

            //open the last Lesson
            case 3:
                ModelTask lastLesson = progressController.getLastLesson();
                checkProgress();
                currentTask = lastLesson;
                //load a fragment
                LessonFragment lastLessonFragment = new LessonFragment();
                lastLessonFragment.setFragmentContentLesson(currentTask);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.FragmentHolder, lastLessonFragment)
                        .addToBackStack(null)
                        .commit();
                Log.i(" M LastLesson", " loaded progress: " + progressCurrentScreen);
                break;

        }
    }

    public void updateProgressLastTask(){

        //add the finished section to the PorgressCOntrolller
        progressController.updateUnlockedSections((Integer) sectionNumber + 1);
        Log.i("MUPDATEUNLOCKSECTIONS", " section added " + progressController.getSections().toString());
        //use a singelton?
        //userProgress.updateUserProgressFinishedSections(sectionNumber);
        Log.i("M last task", " of section is reached");

        Intent intent = new Intent(LessonActivity.this, MainActivity.class);
        startActivity(intent);
        Log.d("M ChangeActivity", " to activity: MainActivity");
    }



    public void updateProgress(){
        Log.i("M oldcurrentTask", " " + currentTask.getTaskName());
        progressCurrentScreen += 1;
        progressController.updateCurrentScreen(progressCurrentScreen);
    }

    private void checkProgress(){
        Log.i("M CheckProgress", " currentProgreessScreen: " + progressCurrentScreen +" currentNmber: " +currentTask.getTaskNumber());
        if(progressCurrentScreen == currentTask.getTaskNumber()) {
            //progressController.addFinishedTask(currentTask);
            progressCurrentScreen = currentTask.getTaskNumber() + 1;
            progressController.updateCurrentScreen(progressCurrentScreen);
            Log.i("M CheckProgress if", " currentProgreessScreen: " + progressCurrentScreen +" currentNmber: " +currentTask.getTaskNumber());
        }
        else{
            progressCurrentScreen = currentTask.getTaskNumber() + 1;
            progressController.updateCurrentScreen(progressCurrentScreen);
            Log.i("M CheckProgress else", " currentProgreessScreen: " + progressCurrentScreen +" currentNmber: " +currentTask.getTaskNumber());
        }
    }

    public void setCurrentTask(){
        currentTask = taskContent.get(progressCurrentScreen);
        currentTaskNumber = currentTask.getTaskNumber();
        Log.i("M UPDATE_CURRENTTASK", "in LessonActivity" + currentTask.getTaskName() + "currentTaskNumber: " + currentTask.getTaskNumber());
    }

    public String getSectionTitle(){

        String title = "Section";

        switch (sectionNumber){
            case 1:
                title = getResources().getString(R.string.Lesson1);
                break;
            case 2:
                title = getResources().getString(R.string.Lesson2);
                break;
            case 3:
                title = getResources().getString(R.string.Lesson3);
                break;
            case 4:
                title = getResources().getString(R.string.Lesson4);
                break;
            case 5:
                title = getResources().getString(R.string.Lesson5);
                break;
            case 6:
                title = getResources().getString(R.string.Lesson6);
                break;
            case 7:
                title = getResources().getString(R.string.Lesson7);
                break;
        }
        return title;
    }

    @Override
    public void onBackPressed() {

        Log.i("M BackButtonPressed", " in navigation");
//        if (!shouldAllowBack) {
//            //super.onBackPressed();
//            //don't allow it when first lesson is reached -> us then back button or ask if you want to exit the lesson
//        } else {
//            super.onBackPressed();
//        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //TODO display an dialog an notify the progressmodel to discard all progress?
                Intent intent = new Intent(LessonActivity.this, MainActivity.class);
                startActivity(intent);
                Log.d("M ChangeActivity", " to Mainactivity");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void setSectionCOlor() {
        switch (progressController.getCurrentSection()) {

            case 1:
                getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.lightGreen1));
                break;
            case 2:
                getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.lightGreen2));
                break;
            case 3:
                getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.lightGreen3));
                break;
            case 4:
                getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.Green1));
                break;
            case 5:
                getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.Green2));
                break;
            case 6:
                getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.Blue1));
                break;
            case 7:
                getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.Blue2));
                break;
            case 8:
                getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.Blue3));
                break;
            case 9:
                getWindow().getDecorView().setBackgroundColor(getResources().getColor(R.color.Blue4));
                break;


        }
    }


}
