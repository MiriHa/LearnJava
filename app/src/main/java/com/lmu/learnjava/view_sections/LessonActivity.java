package com.lmu.learnjava.view_sections;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.FragmentManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lmu.learnjava.controller.Controller;
import com.lmu.learnjava.R;
import com.lmu.learnjava.controller.SharedPrefrencesManager;
import com.lmu.learnjava.models.ModelTask;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class LessonActivity extends AppCompatActivity {

    Controller progressController;

    private int sectionNumber;

    ArrayList<ModelTask> taskContent;

    ModelTask currentTask;
    int currentTaskNumber;

    Context context;

    LinearLayout progressHolder;
    HorizontalScrollView progressScroll;


    int progressCurrentScreen = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        progressHolder = findViewById(R.id.progressHolder);

        //get the progress controller
        progressController = (Controller) getApplicationContext();
        context = this;


        //get the recent section number to identify the section
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null)
            sectionNumber = (int) b.get("LESSON_NUMBER");
        Log.i("M_LESSON_ACTIVITY", " section opend: " + sectionNumber);

        progressController.updateCurrentSection(this, sectionNumber);

        progressController.loadContent(sectionNumber, this);
        taskContent = progressController.getTaskContent();

        progressScroll = findViewById(R.id.progressScroll);
        setProgressBar();

        //set the Toolbar
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getSectionTitle());

        //open the first lesson Fragment
        openNewTask(0);



    }


    /**
     * Open the next TaskFragment
     * @param taskType O: first Lesson, 1: a lesson, 2: a exercise, 3: last seen lesson
     */
    public void openNewTask(int taskType) {

        FragmentManager manager = getSupportFragmentManager();
        Log.i("M_LESSON_ACTIVITY", " backstack:" + manager.getFragments().toString());
        switch (taskType) {
            //open the first Lesson
            case 0:
                //get the currentTask content
                if (progressController.getLatestSectionNumber(this) == sectionNumber) {
                    Log.i("M_LESSONACTIVITY","open latestTask in latest section");
                    openLatestTask();

                }else {
                    setCurrentTask();
                    setProgressBackground();
                    Log.i("M_LESSON_ACTIVITY", "opennewtask: currentProgreessScreen: " + progressCurrentScreen + " currentNmber: " + currentTask.getTaskNumber());
                    //load a fragment
                    LessonFragment firstlessonFragment = new LessonFragment();
                    firstlessonFragment.setFragmentContentLesson(currentTask);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.FragmentHolder, firstlessonFragment, "FRAGMENT_LESSON_0")
                            .addToBackStack("FRAGMENT_LESSON_0")
                            .commit();
                    Log.d(" M_LESSON_ACTIVITY", " checkProgress 0: loaded progress: " + progressCurrentScreen);
                }
                break;

            //open the next Lesson
            case 1:
                checkProgress();
                setCurrentTask();
                setProgressBackground();
                String tagLES = "FRAGMENT_LESSON_" + currentTask.getTaskNumber();
                boolean fragmentPoppedLES = manager.popBackStackImmediate(tagLES, 0);

                if (!fragmentPoppedLES && manager.findFragmentByTag(tagLES) == null) { //fragment not in back stack, create it.
//
                    //open new lessonFragment and set its content
                    LessonFragment lessonFragment = new LessonFragment();
                    lessonFragment.setFragmentContentLesson(currentTask);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.FragmentHolder, lessonFragment, tagLES)
                            .addToBackStack(tagLES)
                            .commit();
                }
                Log.d(" M_LESSON_ACTIVITY", "checkprogress 1: progress: " + progressCurrentScreen);
                break;

            //open the next Exercise
            case 2:
                //update the CurrentScreen and currentTask
                checkProgress();
                setCurrentTask();
                setProgressBackground();
                String tagEX = "FRAGMENT_EXERCISE_" + currentTask.getTaskNumber();
                boolean fragmentPoppedEX = manager.popBackStackImmediate(tagEX, 0);

                if (!fragmentPoppedEX && manager.findFragmentByTag(tagEX) == null) { //fragment not in back stack, create it.
                    //Open a new Fragment and set its content
                    ExerciseFragment exerciseFragment = new ExerciseFragment();
                    exerciseFragment.setFragmentContentExercise(currentTask);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.FragmentHolder, exerciseFragment, tagEX)
                            .addToBackStack(tagEX)
                            .commit();
                }
                Log.i(" M_LESSON_ACTIVITY", " checkprogress 2: progress: " + progressCurrentScreen);
                break;

            //open the last Lesson
            case 3:
                int lastLessonNumber = progressController.getLastLessonNumber(this);
                ModelTask lastLesson = taskContent.get(lastLessonNumber);
               // ModelTask lastLesson = progressController.getLastLesson();
                Log.i("M_LESSON_ACTIVTIY", "last Lesson: " + lastLesson.getTaskName() + " whats next: " + lastLesson.getWhatsNext());
                checkProgress();
                currentTask = lastLesson;
                currentTaskNumber = (int) lastLesson.getTaskNumber();
                setProgressBackground();
                //load a fragment
                String tagLast = "FRAGMENT_LESSON_" + currentTaskNumber;
                Log.i("M_LESSON_ACTIVITY", " backstack:" + manager.getFragments().toString());
                Log.i(" M_LESSON_ACTIVITY", "checkProgress 3: loaded progress: " + progressCurrentScreen);
                boolean fragmentPoppedLAST = manager.popBackStackImmediate(tagLast, 0);

                if (!fragmentPoppedLAST && manager.findFragmentByTag(tagLast) == null) { //fragment not in back stack, create it.
                    LessonFragment lastLessonFragment = new LessonFragment();
                    lastLessonFragment.setFragmentContentLesson(currentTask);
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.FragmentHolder, lastLessonFragment, tagLast)
                            .addToBackStack(tagLast)
                            .commit();
                }
                break;
        }
    }


    /**
     * This method opens the latest Task when the user is in the Latest Section
     */
    public void openLatestTask(){
        currentTaskNumber = progressController.getLatestTaskNumber(this);
        currentTask = taskContent.get(currentTaskNumber);
        scrollToProgress();
        switch (currentTask.getType()){
            case 1:
                setProgressBackground();
                Log.i("M_LESSON_ACTIVITY", "opennewtask: currentProgreessScreen: " + progressCurrentScreen + " currentNmber: " + currentTask.getTaskNumber());
                //load a fragment
                LessonFragment firstlessonFragment = new LessonFragment();
                firstlessonFragment.setFragmentContentLesson(currentTask);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.FragmentHolder, firstlessonFragment, "FRAGMENT_LESSON_0")
                        .addToBackStack("FRAGMENT_LESSON_0")
                        .commit();
                Log.d(" M_LESSON_ACTIVITY", " checkProgress 0: loaded progress: " + progressCurrentScreen);
                break;
            case 2:
                setProgressBackground();
                Log.i("M_LESSON_ACTIVITY", "opennewtask: currentProgreessScreen: " + progressCurrentScreen + " currentNmber: " + currentTask.getTaskNumber());
                  //load a fragment
                ExerciseFragment exerciseFragment = new ExerciseFragment();
                exerciseFragment.setFragmentContentExercise(currentTask);
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.FragmentHolder, exerciseFragment, "FRAGMENT_Exercise_0")
                        .addToBackStack("FRAGMENT_LESSON_0")
                        .commit();
                Log.d(" M_LESSON_ACTIVITY", " checkProgress 0: loaded latestTask " + progressCurrentScreen);
                break;
        }

    }



    /**
     * Update the Progress when the end of a Section is reached, unlock next Section and go to MainActivity
     */
    public void updateProgressLastTask() {

        //add the finished section to the PorgressCOntrolller when needed
        progressController.updateLatestTaskNumber(this, 0, sectionNumber);
        progressController.updateLatestSection(this,sectionNumber);
        Log.i("M_LESSON_ACTIVITY", "updatet LatestSection");

        Log.i("M_LESSON_ACTIVITY", "last task of section is reached");
        //GO Back to the MainActivity
        Intent intent = new Intent(LessonActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Log.d("M_LESSON_ACTIVITY", "change to activity: MainActivity");
    }


    /**
     * Check if the current Progress matches the TaskNumbers
     */
    private void checkProgress() {
        Log.i("M_CheckProgress", " currentProgreessScreen: " + progressCurrentScreen + " currentNmber: " + currentTask.getTaskNumber());
            progressCurrentScreen =  currentTask.getTaskNumber() + 1;
            //When in the latest Section update the latest TaskNumber;
            progressController.updateLatestTaskNumber(this, progressCurrentScreen, sectionNumber);
            progressController.updateCurrentScreen(this,progressCurrentScreen);
            Log.i("M_LESSON_ACTIVITY", " checkprogress: currentProgreessScreen: " + progressCurrentScreen + " currentNmber: " + currentTask.getTaskNumber());
    }

    /**
     * fetch the currentTask from the ContentArray according to the currentTaskNumber
     */
    public void setCurrentTask() {
            currentTask = taskContent.get(progressCurrentScreen);
            currentTaskNumber = currentTask.getTaskNumber();
            progressController.updateLatestTaskNumber(this, currentTaskNumber, sectionNumber);
            scrollToProgress();
            Log.i("M_LESSON_ACTIVITY","set recent task in latest Section " + currentTask.getTaskName() + " " + currentTask.getTaskNumber());

    }

    private void scrollToProgress(){
        progressScroll.post(new Runnable() {
            @Override
            public void run() {
                TextView currentTaskView;
                if(currentTaskNumber > 4) {
                    currentTaskView = (TextView) progressHolder.getChildAt(currentTaskNumber - 4);
                    int scrollTo = currentTaskView.getLeft();

                    progressScroll.smoothScrollTo(scrollTo, 0);
                }

            }
        });
    }

    /**
     * get SectionTitle
     * @return Section title as String
     */
    public String getSectionTitle() {

        String title = "Section";

        switch (sectionNumber) {
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
            case 8:
                title = getResources().getString(R.string.Lesson8);
                break;
        }
        return title;
    }


    /**
     * set the Top ProgressBar dynamiclly with the current Lessons und Exercises
     */
    public void setProgressBar() {
        Log.i("M_LESSON_ACTIVITY", "set progressbar");
        int tasksSize = taskContent.size();
        int[] taskNumber = new int[tasksSize];
        int[] taskTypes = new int[tasksSize];

        for (int i = 0; i < tasksSize; i++) {
            ModelTask task = taskContent.get(i);
            taskNumber[i] =  task.getTaskNumber();
            taskTypes[i] = task.getType();
        }

        LinearLayout.LayoutParams mParamsWeight = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.5f);
        progressHolder.setWeightSum(tasksSize+(tasksSize/2));

        for (int j = 0; j < tasksSize; j++) {

            if (taskTypes[j] == 1) {
                TextView myTextView = new TextView(this);
                myTextView.setText(String.valueOf(j + 1));
                myTextView.setLayoutParams(mParamsWeight);
                String tag = String.valueOf(taskNumber[j]);
                myTextView.setClickable(true);
                myTextView.setTag(tag);
                myTextView.setGravity(Gravity.CENTER);
                myTextView.setBackgroundResource(R.drawable.border);
                Typeface typeface = ResourcesCompat.getFont(this, R.font.trixiesans);
                myTextView.setTypeface(typeface);
                myTextView.setBackgroundResource(R.drawable.border);
                myTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

                myTextView.setOnClickListener(new ProgressLessonClickListener());
                progressHolder.addView(myTextView);


            } else if (taskTypes[j] == 2 || taskTypes[j] == 3) {
                TextView myTextView = new TextView(this);
                myTextView.setText("?");
                String tag = String.valueOf(taskNumber[j]);
                myTextView.setClickable(true);
                myTextView.setTag(tag);
                myTextView.setGravity(Gravity.CENTER);
                myTextView.setLayoutParams(mParamsWeight);
                myTextView.setBackgroundResource(R.drawable.border);
                Typeface typeface = ResourcesCompat.getFont(this, R.font.trixiesans);
                myTextView.setTypeface(typeface);
                myTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

                myTextView.setOnClickListener(new ProgressExerciseClickListener());
                progressHolder.addView(myTextView);
            }

        }
    }
    /**
     * Update the Background of the ProgressBar for the current TaskNumber
     **/
    public void setProgressBackground() {

        for (int i = 0; i < taskContent.size(); i++) {
            String tagOld = String.valueOf(i);
            Log.i("M_LESSON_ACTIVITY", "setProgressBackground");
            TextView textViewOld = progressHolder.findViewWithTag(tagOld);
            textViewOld.setBackgroundResource(R.drawable.border);
        }
        String tag = String.valueOf(currentTaskNumber);
        TextView textView = progressHolder.findViewWithTag(tag);

        textView.setBackgroundResource(R.drawable.border_dark);
    }


    /**
     * DO nothing on Backpressed, easier Fragment Backstack management
     */
    @Override
    public void onBackPressed() {
        Log.i("M_BackButtonPressed", " in navigation");
    }

    /**
     * Back pressed in the Toolbar, navigating to the MainActivity
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Intent intent = new Intent(LessonActivity.this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            Log.d("M_LESSON_ACTIVITY", " change to Mainactivity");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * ClickListener for the ProgressBar: Exercises clicked
     */
    public class ProgressExerciseClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            TextView tv = (TextView) v;
            int number = Integer.valueOf(tv.getTag().toString());
            if(checkTasksforProgressBar(taskContent.get(number))) {
                openTaskProgress(2, number);
            } else {
                Toast.makeText(context, "Not unlocked yet", Toast.LENGTH_SHORT).show();
            }
            Log.i("M LESSON_ACTIVITY", "on progressBar clicked Exercise number: " + number);

        }
    }

    /**
     * ClickListener for the ProgressBar: Lessons clicked
     */
    public class ProgressLessonClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            TextView tv = (TextView) v;
            int number = Integer.valueOf(tv.getTag().toString());
            if(checkTasksforProgressBar(taskContent.get(number))) {
                openTaskProgress(1, number);

            } else {
            Toast.makeText(context, "Not unlocked yet", Toast.LENGTH_SHORT).show();
        }
            Log.i("M_LESSON_ACTIVITY", "on progressBar clicked lesson number: " + number);
        }
    }

    private boolean checkTasksforProgressBar(ModelTask aTask){
        int latestSection = SharedPrefrencesManager.readLatestSectionNumber(context);
        int latestTask = SharedPrefrencesManager.readLatestTaskNumber(context);

        if(aTask.getSectionNumber() == latestSection && aTask.getTaskNumber() <= latestTask){
            return true;
        }
        else return aTask.getSectionNumber() < latestSection;
    }


    /**
     * open the clicked Task in the ProgressBar
     * @param tasktype 1: lesson, 2: Exercise
     * @param number which task to open
     */
    public void openTaskProgress(int tasktype, int number) {
        FragmentManager manager = getSupportFragmentManager();
        progressCurrentScreen = number;
        currentTask = taskContent.get(progressCurrentScreen);
        currentTaskNumber = currentTask.getTaskNumber();

        progressController.updateCurrentScreen(this, progressCurrentScreen);

        setProgressBackground();
            switch (tasktype) {

                //open a lesson
                case 1:
                    String tagLES = "FRAGMENT_LESSON_" + currentTask.getTaskNumber();
                    progressController.setLastLesson(LessonActivity.this, currentTask.getTaskNumber());
                    boolean fragmentPoppedLES = manager.popBackStackImmediate(tagLES, 0);

                    if (!fragmentPoppedLES && manager.findFragmentByTag(tagLES) == null) { //fragment not in back stack, create it.
                        //open new lessonFragment and set its content
                        LessonFragment lessonFragment = new LessonFragment();
                        lessonFragment.setFragmentContentLesson(currentTask);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.FragmentHolder, lessonFragment, tagLES)
                                .addToBackStack(tagLES)
                                .commit();
                    }
                    progressController.makeaLog(this, Calendar.getInstance().getTime(), "OPEN_AN_OLD_TASK", "Lesson via the progressbar: " + currentTask.getTaskNumber()+"section: "+currentTask.getSectionNumber());
                    Log.d(" M_LESSON_ACTIVITY", "checkprogress 1: progress: " + progressCurrentScreen);
                    break;

                //open a Exercise
                case 2:
                    //update the CurrentScreen and currentTask
                    String tagEX = "FRAGMENT_EXERCISE_" + currentTask.getTaskNumber();
                    boolean fragmentPoppedEX = manager.popBackStackImmediate(tagEX, 0);

                    if (!fragmentPoppedEX && manager.findFragmentByTag(tagEX) == null) { //fragment not in back stack, create it.
                        //Open a new Fragment and set its content
                        ExerciseFragment exerciseFragment = new ExerciseFragment();
                        exerciseFragment.setFragmentContentExercise(currentTask);
                        getSupportFragmentManager()
                                .beginTransaction()
                                .replace(R.id.FragmentHolder, exerciseFragment, tagEX)
                                .addToBackStack(tagEX)
                                .commit();
                    }
                     progressController.makeaLog(this, Calendar.getInstance().getTime(), "OPEN_AN_OLD_TASK", "Exercise via the progressbar: " + currentTask.getTaskNumber()+"section: "+currentTask.getSectionNumber());
                    Log.i(" M_LESSON_ACTIVITY", " checkprogress 2: progress: " + progressCurrentScreen);
                    break;
            }
    }



    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPrefrencesManager.setTrigger(this, true, 1);
        Log.i("M_TRIGGER_CUES","LessonActivity: onRestart, set Cue Trigger true");
    }

    @Override
    protected void onStart() {
        super.onStart();
        progressController.showCue(this, sectionNumber, getSupportFragmentManager());
        Log.i("M_TRIGGER_CUES","LessonActivity: onStart, showCue");
    }


}
