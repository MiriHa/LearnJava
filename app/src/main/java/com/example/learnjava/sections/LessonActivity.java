package com.example.learnjava.sections;

import androidx.annotation.DrawableRes;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

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

    LinearLayout progressHolder;


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

        progressHolder = findViewById(R.id.progressHolder);
        setProgressBar();

        //set the Toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getSectionTitle());

        //open the first lesson Fragment
        openNewTask(0);

    }


    public void openNewTask(int taskType){

        switch (taskType) {
            //open the first Lesson
            case 0:
                //get the currentTask content
                setCurrentTask();
                setProgressBackground();
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
                setProgressBackground();
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
                setProgressBackground();
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
                currentTaskNumber = lastLesson.getTaskNumber();
                setProgressBackground();
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
        progressController.updateLatesTaskNumber(currentTaskNumber);
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

    public void setProgressBar(){
        int tasksSize = taskContent.size();
        int[] taskNumber = new int[tasksSize];
        int[] taskTypes = new int[tasksSize];

        for(int i=0; i<tasksSize;i++){
            ModelTask task = taskContent.get(i);
            taskNumber[i] = task.getTaskNumber();
            taskTypes[i] = task.getType();
        }

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        //Make scrollVIew um Linaralyout falls viele Lessons?
        for(int j =0; j<tasksSize;j++){
            int counter = 1;
            if(taskTypes[j] == 1){
                TextView myTextView = new TextView(this);
                //TODO change the counter, counts exercises mit
                myTextView.setText(String.valueOf(j+1));
                myTextView.setLayoutParams(mParams);
                myTextView.setWidth(120);
                myTextView.setTag("PROGRESS_FIELD_"+taskNumber[j]);
                myTextView.setGravity(Gravity.CENTER);
                myTextView.setBackgroundResource(R.drawable.border);
                myTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

                myTextView.setOnClickListener(new ProgressLessonClickListener());
                progressHolder.addView(myTextView);


            }else if(taskTypes[j] == 2 || taskTypes[j] == 3){
                TextView myTextView = new TextView(this);
                myTextView.setText("?");
                myTextView.setWidth(120);
                myTextView.setTag("PROGRESS_FIELD_"+taskNumber[j]);
                myTextView.setGravity(Gravity.CENTER);
                myTextView.setLayoutParams(mParams);
                myTextView.setBackgroundResource(R.drawable.border);
                myTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

                myTextView.setOnClickListener(new ProgressExerciseClickListener());
                progressHolder.addView(myTextView);
            }

        }
    }

    public void setProgressBackground(){

        for(int i=0; i<=progressController.getLatestTaskNumber();i++){
            String tagOld = "PROGRESS_FIELD_"+i;
            Log.i("LESSONACTIVITY","latesTasknumber: "+progressController.getLatestTaskNumber()+" updateProgressBackground");
            TextView textViewOld = progressHolder.findViewWithTag(tagOld);
            textViewOld.setBackgroundResource(R.drawable.border);
        }

        String tag = "PROGRESS_FIELD_"+currentTaskNumber;
        TextView textView = progressHolder.findViewWithTag(tag);

        textView.setBackgroundResource(R.drawable.border_dark);
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


    public class ProgressExerciseClickListener implements View.OnClickListener{


        @Override
        public void onClick(View v) {
            Log.i("LESSONACTIVITY","on progressBar clicked Exercise");


        }
    }

    public class ProgressLessonClickListener implements View.OnClickListener{


        @Override
        public void onClick(View v) {
            Log.i("LESSONACTIVITY","on progressBar clicked lesson");
        }
    }


}
