package com.example.learnjava.sections;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnjava.Controller;
import com.example.learnjava.MainActivity;
import com.example.learnjava.R;
import com.example.learnjava.models.ModelTask;
import com.example.learnjava.room_database.UserDatabase;

import java.util.ArrayList;
import java.util.Calendar;

public class LessonActivity extends AppCompatActivity {

    //Use a songelton?
    //ModelUserProgress userProgress;

    Controller progressController;
    UserDatabase database;

    private int sectionNumber;
    private boolean shouldAllowBack = false;

    ArrayList<ModelTask> taskContent;

    ModelTask currentTask;
    int currentTaskNumber;

    int currentSection;
    Context context;

    LinearLayout progressHolder;


    //TODO use the one from the progressMOdel instead of here?
    int progressCurrentScreen = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        //get the progresscontroller
        progressController = (Controller) getApplicationContext();
        context = this;
        database = UserDatabase.getInstance(this);


        //use a singelton
        //userProgress = ModelUserProgress.getInstance();

        //get the recent sectionnumber to identifiy the section
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        if (b != null)
            sectionNumber = (int) b.get("LESSON_NUMBER");
        Log.i("M_LESSON_ACTIVITY", " section opend: " + sectionNumber);
        progressController.updateCurrentSection(sectionNumber, database);

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


    public void openNewTask(int taskType) {

        FragmentManager manager = getSupportFragmentManager();
        Log.i("M_LESSON_ACTIVITY", " backstack:" + manager.getFragments().toString());
        switch (taskType) {
            //open the first Lesson
            case 0:
                //get the currentTask content
                setCurrentTask();
                setProgressBackground();
                Log.i("M_LESSON_ACTIVITY", "opennewtask: currentProgreessScreen: " + progressCurrentScreen + " currentNmber: " + currentTask.getTaskNumber());
                progressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_NEW_TASK", "First Lesson of a Section", database);
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

            //open the next Lesson
            case 1:
                checkProgress();
                setCurrentTask();
                setProgressBackground();
                String tagLES = "FRAGMENT_LESSON_" + currentTask.getTaskNumber();
//                Fragment lessonFragment = manager.findFragmentByTag(tagLES);

                boolean fragmentPoppedLES = manager.popBackStackImmediate(tagLES, 0);

                if (!fragmentPoppedLES && manager.findFragmentByTag(tagLES) == null) { //fragment not in back stack, create it.
//                    FragmentTransaction ft = manager.beginTransaction();
//                    ft.replace(R.id.FragmentHolder, lastLessonFragment, tagLast);
//                    ft.addToBackStack(tagLast);
//                    ft.commit();

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
                progressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_NEW_TASK", "open the next lesson: " + currentTask.getTaskNumber(), database);
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
                progressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_NEW_TASK", "open the next exercise: " + currentTask.getTaskNumber(), database);
                break;

            //open the last Lesson
            case 3:
                ModelTask lastLesson = progressController.getLastLesson();
                Log.i("M_LESSON_ACTIVTIY", "last Lesson: " + lastLesson.getTaskName() + " whats next: " + lastLesson.getWhatsNext());
                checkProgress();
                currentTask = lastLesson;
                currentTaskNumber = lastLesson.getTaskNumber();
                setProgressBackground();
                //load a fragment
                String tagLast = "FRAGMENT_LESSON_" + currentTaskNumber;
                Log.i("M_LESSON_ACTIVITY", " backstack:" + manager.getFragments().toString());
                //manager.popBackStack(tagLast, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                //                LessonFragment lastLessonFragment = new LessonFragment();
                //                lastLessonFragment.setFragmentContentLesson(currentTask);
                //                getSupportFragmentManager()
                //                        .beginTransaction()
                //                        .add(R.id.FragmentHolder, lastLessonFragment)
                //                        .addToBackStack(null)
                //                        .commit();
                Log.i(" M_LESSON_ACTIVITY", "checkProgress 3: loaded progress: " + progressCurrentScreen);
                progressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_NEW_TASK", "open the last lesson: " + currentTask.getTaskNumber(), database);


//                    Fragment lastLessonFragment = manager.findFragmentByTag(tagLast);
                //                manager.popBackStack(tagLast, 0);

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


    public void updateProgressLastTask() {

        //add the finished section to the PorgressCOntrolller
        progressController.updateUnlockedSections((Integer) sectionNumber + 1, database);
        Log.i("M_LESSON_ACTIVITY", " updateprogress: section added " + progressController.getSections().toString());
        //use a singelton?
        //userProgress.updateUserProgressFinishedSections(sectionNumber);
        Log.i("M_LESSON_ACTIVITY", "last task of section is reached");
        Log.i("M_LESSON_ACTIVITY", " backstack:" + getSupportFragmentManager().getFragments().toString());
        Intent intent = new Intent(LessonActivity.this, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        Log.d("M_LESSON_ACTIVITY", "change to activity: MainActivity");
    }


    private void checkProgress() {
        Log.i("M CheckProgress", " currentProgreessScreen: " + progressCurrentScreen + " currentNmber: " + currentTask.getTaskNumber());
        if (progressCurrentScreen == currentTask.getTaskNumber()) {
            //TODO progressController.addFinishedTask(currentTask)
            //TODO why is it the same code? here
            progressCurrentScreen = currentTask.getTaskNumber() + 1;
            progressController.updateCurrentScreen(progressCurrentScreen, database);
            Log.i("M_LESSON_ACTIVITY", " checkprogress: currentProgreessScreen: " + progressCurrentScreen + " currentNmber: " + currentTask.getTaskNumber());
        } else {
            progressCurrentScreen = currentTask.getTaskNumber() + 1;
            progressController.updateCurrentScreen(progressCurrentScreen, database);
            Log.i("M_LESSON_ACTIVITY", " checkProgress else: currentProgreessScreen: " + progressCurrentScreen + " currentNmber: " + currentTask.getTaskNumber());
        }
    }

    public void setCurrentTask() {
        currentTask = taskContent.get(progressCurrentScreen);
        currentTaskNumber = currentTask.getTaskNumber();
        progressController.updateLatestTaskNumber(currentTaskNumber, database);
        Log.i("M_LESSON_ACTIVITY", "set current task" + currentTask.getTaskName() + "currentTaskNumber: " + currentTask.getTaskNumber());
    }

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
        }
        return title;
    }

    //set the Top PrgressBar with the current Lessons und Exercises
    public void setProgressBar() {
        Log.i("M_LESSON_ACTIVITY", "set progressbar");
        int tasksSize = taskContent.size();
        int[] taskNumber = new int[tasksSize];
        int[] taskTypes = new int[tasksSize];

        for (int i = 0; i < tasksSize; i++) {
            ModelTask task = taskContent.get(i);
            taskNumber[i] = task.getTaskNumber();
            taskTypes[i] = task.getType();
        }

        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        LinearLayout.LayoutParams mParamsWeight = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1.5f);
        progressHolder.setWeightSum(tasksSize+(tasksSize/2));

        for (int j = 0; j < tasksSize; j++) {
            int counter = 1;
            if (taskTypes[j] == 1) {
                TextView myTextView = new TextView(this);
                //TODO change the counter, counts exercises mit
                myTextView.setText(String.valueOf(j + 1));
                myTextView.setLayoutParams(mParamsWeight);
                String tag = String.valueOf(taskNumber[j]);
                myTextView.setClickable(true);
//                myTextView.setTag("PROGRESS_FIELD_" + taskNumber[j]);
                myTextView.setTag(tag);
                myTextView.setGravity(Gravity.CENTER);
                myTextView.setBackgroundResource(R.drawable.border);
                myTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

                myTextView.setOnClickListener(new ProgressLessonClickListener());
                progressHolder.addView(myTextView);


            } else if (taskTypes[j] == 2 || taskTypes[j] == 3) {
                TextView myTextView = new TextView(this);
                myTextView.setText("?");
                String tag = String.valueOf(taskNumber[j]);
                myTextView.setClickable(true);
//                myTextView.setTag("PROGRESS_FIELD_" + taskNumber[j]);
                myTextView.setTag(tag);
                myTextView.setGravity(Gravity.CENTER);
                myTextView.setLayoutParams(mParamsWeight);
                myTextView.setBackgroundResource(R.drawable.border);
                myTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);

                myTextView.setOnClickListener(new ProgressExerciseClickListener());
                progressHolder.addView(myTextView);
            }

        }
    }

    //Update the Background of the ProgressBar
    public void setProgressBackground() {

//        for (int i = 0; i <= progressController.getLatestTaskNumber(); i++) {
        for (int i = 0; i < taskContent.size(); i++) {
//            String tagOld = "PROGRESS_FIELD_" + i;
            String tagOld = String.valueOf(i);
            Log.i("M_LESSON_ACTIVITY", "latesTasknumber: " + progressController.getLatestTaskNumber() + " updateProgressBackground");
            TextView textViewOld = progressHolder.findViewWithTag(tagOld);
            textViewOld.setBackgroundResource(R.drawable.border);
        }

//        String tag = "PROGRESS_FIELD_" + currentTaskNumber;
        String tag = String.valueOf(currentTaskNumber);
        TextView textView = progressHolder.findViewWithTag(tag);

        textView.setBackgroundResource(R.drawable.border_dark);
    }

    @Override
    public void onBackPressed() {

        Log.i("M_BackButtonPressed", " in navigation");
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
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                Log.d("M_LESSON_ACTIVITY", " change to Mainactivity");
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    public class ProgressExerciseClickListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            TextView tv = (TextView) v;
            int number = Integer.valueOf(tv.getTag().toString());
            //currentTask = taskContent.get(number);
            if(progressController.checkTasks(taskContent.get(number)) || number == progressController.getLatestTaskNumber()) {
                openTaskProgress(2, number);
            } else {
                Toast.makeText(context, "Not unlocked yet", Toast.LENGTH_SHORT).show();
            }
            Log.i("M LESSON_ACTIVITY", "on progressBar clicked Exercise number: " + number);

        }
    }

    public class ProgressLessonClickListener implements View.OnClickListener {


        @Override
        public void onClick(View v) {
            TextView tv = (TextView) v;
            int number = Integer.valueOf(tv.getTag().toString());
            // currentTask = taskContent.get(number);
            if(progressController.checkTasks(taskContent.get(number)) || number == progressController.getLatestTaskNumber()) {
                openTaskProgress(1, number);
            } else {
            Toast.makeText(context, "Not unlocked yet", Toast.LENGTH_SHORT).show();
        }
            Log.i("M_LESSON_ACTIVITY", "on progressBar clicked lesson number: " + number);
        }
    }

    //TODO integirer das in openNewTask -> zweiter parameter? nur checkprogress weggelassen
    public void openTaskProgress(int tasktype, int number) {
        FragmentManager manager = getSupportFragmentManager();
        progressCurrentScreen = number;
        currentTask = taskContent.get(progressCurrentScreen);
        currentTaskNumber = currentTask.getTaskNumber();

        setProgressBackground();
            switch (tasktype) {

                case 1:
                    String tagLES = "FRAGMENT_LESSON_" + currentTask.getTaskNumber();

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
                    Log.d(" M_LESSON_ACTIVITY", "checkprogress 1: progress: " + progressCurrentScreen);
                    break;

                //open the next Exercise
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
                    Log.i(" M_LESSON_ACTIVITY", " checkprogress 2: progress: " + progressCurrentScreen);
                    break;

                //open the last Lesson
            }
    }



}
