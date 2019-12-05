package com.example.learnjava.lessons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.learnjava.Controller;
import com.example.learnjava.ExerciseView.ViewArrayAdapter;
import com.example.learnjava.MainActivity;
import com.example.learnjava.R;
import com.example.learnjava.ReadJson;
import com.example.learnjava.models.ModelTask;
import com.example.learnjava.models.ModelUserProgress;

import java.util.ArrayList;

public class LessonActivity extends AppCompatActivity{

    //Use a songelton?
    //ModelUserProgress userProgress;

    Controller progressController;
    private int sectionNumber;
    private boolean shouldAllowBack = false;

    ArrayList<ModelTask> taskContent;

    ModelTask currentTask;

    ReadJson readJson = new ReadJson();

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
            Log.i("Section opened", " "+ sectionNumber);
            progressController.updateCurrentSection(sectionNumber);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getSectionTitle());

        //get the Lesson content
        //TODO read this only once in the controller
        loadContent();

        //get the current task content
       // setCurrentTask();

        //open the first lesson Fragment
        openNewTask(0);

    }


    public void openNewTask(int taskType){

        switch (taskType) {

            case 0:
                //get the currentTask content
                setCurrentTask();
                //load a fragment
                LessonFragment firstlessonFragment = new LessonFragment();
                giveLessonFragmentContent(firstlessonFragment);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.FragmentHolder, firstlessonFragment)
                        .addToBackStack(null)
                        .commit();
                Log.d("FirstLesson", " loaded progress: " + progressCurrentScreen);
                break;

            case 1:
                //update the currentScreen and the current task
                updateProgress();
                setCurrentTask();
                //open new lessonFragment and set its content
                LessonFragment lessonFragment = new LessonFragment();
                giveLessonFragmentContent(lessonFragment);
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FragmentHolder, lessonFragment)
                        .addToBackStack(null)
                        .commit();
                Log.d("Nextbuttenclicked", " lesson progress: " + progressCurrentScreen);
                break;

            case 2:
                //update the CurrentScreen and currentTask
                updateProgress();
                setCurrentTask();
                //Open a new Fragment and set its content
                ExerciseFragment exerciseFragment = new ExerciseFragment();
                giveExerciseFragmentContent(exerciseFragment);
                // exerciseFragment.setExerciseLayout();
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.FragmentHolder, exerciseFragment)
                        //TODO verbessere abckstack/Überlappen von fragments
                        .addToBackStack(null)
                        .commit();

                Log.d("Nextbuttenclicked", " exercise progress: " + progressCurrentScreen);
                break;
        }
    }

    public void updateProgressLastTask(){

        //add the finished section to the PorgressCOntrolller
        progressController.updateFinishedSection(sectionNumber);
        //use a singelton?
        //userProgress.updateUserProgressFinishedSections(sectionNumber);
        Log.i("last task", " of section is reached");
        //TODO give feedback that section is finished?

        Intent intent = new Intent(LessonActivity.this, MainActivity.class);
        startActivity(intent);
        Log.d("ChangeActivity", " to activity: MainActivity");
    }


    public void giveLessonFragmentContent(LessonFragment fragment){
        Log.i("GIVE_CONTENT", "to lessonFragment");
        String name = currentTask.getTaskName();
        String  text = currentTask.getTaskText();
        int whatsNext = currentTask.getWhatsNext();
        fragment.setFragmentContent(name, text, whatsNext);

    }

    public void giveExerciseFragmentContent(ExerciseFragment fragment){
        Log.i("GIVE_CONTENT", "to exerciseFragment");
        String name = currentTask.getTaskName();
        String  text = currentTask.getTaskText();
        int whatsNext = currentTask.getWhatsNext();

        fragment.setFragmentContent(currentTask);

    }


    //TODO some method that reprots the progrss from the fragments -> need to add the current/finished lessons?
    //Maybe just report current screen and rightfully solved exercises

    public void updateProgress(){
        //add the lask task to finished taks list
        //Use a aingelton?
       // userProgress.addFinisehdTask(currentTask);
        progressController.addfinishedTask(currentTask);
//        int oldTasktype = currentTask.getType();
//        if(oldTasktype == 1){
//            progressController.addReadLesson(currentTask);
//        }
        Log.i("oldcurrentTask", " " + currentTask.getTaskName());
        progressCurrentScreen += 1;
        progressController.updateCurrentScreen(progressCurrentScreen);
    }

    public void setCurrentTask(){
        currentTask = taskContent.get(progressCurrentScreen);
        Log.i("UPDATE_CURRENTTASK", "in LessonActivity" + currentTask.getTaskName());
    }

    public void loadContent(){
        String sectionFile = "section"+sectionNumber;
       taskContent =  readJson.readTask(sectionFile, this);
       Log.i("loadContent", " in Lessonactivity"+sectionNumber);

    }

    public void exerciseSolved(){
        currentTask.isSolved();
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
