package com.example.learnjava.view_sections;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.learnjava.controller.Controller;
import com.example.learnjava.R;
import com.example.learnjava.controller.SharedPrefrencesManager;
import com.example.learnjava.view_cues.WordCueFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Controller myProgressController;

    FirebaseAuth auth;
    DatabaseReference ref;

    String currentuserID;

    Context context;

    //Button lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, lesson9;
    LinearLayout lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, ScrollViewContainer;
    ScrollView scrollView;

    public static final String PREF_USER_FIRST_TIME = "user_first_time";
    boolean isUserFirstTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("M_MAIN_ACTIVITY", " on create");

        FrameLayout lessonLayout = findViewById(R.id.FragmentHolder);
        //findLinearLayouts
        lesson1 = findViewById(R.id.lesson1);
        lesson2 = findViewById(R.id.lesson2);
        lesson3 = findViewById(R.id.lesson3);
        lesson4 = findViewById(R.id.lesson4);
        lesson5 = findViewById(R.id.lesson5);
        lesson6 = findViewById(R.id.lesson6);
        lesson7 = findViewById(R.id.lesson7);
        lesson8 = findViewById(R.id.lesson8);
        scrollView = findViewById(R.id.MainScrollView);
        ScrollViewContainer = findViewById(R.id.MainscrollViewContainer);


        context = getApplicationContext();
        myProgressController = (Controller) getApplicationContext();

        myProgressController.setFirebase();

        //check if the app is opend for the first time
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        currentuserID = auth.getCurrentUser().getUid();

        //myProgressController.setFirebase();
        Log.i("M_MAIN_ACTIVITY", "set Refrences and Controller");
        myProgressController.makeaLog(Calendar.getInstance().getTime(), "ENTERED_MAIN_ACTIVITY", "set Refrences and Content");

        checkIfSolved(lesson1, 1);
        checkIfSolved(lesson2, 2);
        checkIfSolved(lesson3, 3);
        checkIfSolved(lesson4, 4);
        checkIfSolved(lesson5, 5);
        checkIfSolved(lesson6, 6);
        checkIfSolved(lesson7, 7);
        checkIfSolved(lesson8, 8);

        scrollToView();

    }

    private LinearLayout latestLayoutID(){
        switch (SharedPrefrencesManager.readLatestSectionNumber(this)){
            case 1:
                return findViewById(R.id.lesson1);
            case 2:
                return findViewById(R.id.lesson2);
            case 3:
               return findViewById(R.id.lesson3);
            case 4:
                return findViewById(R.id.lesson4);
            case 5:
                return findViewById(R.id.lesson5);
            case 6:
                return findViewById(R.id.lesson6);
            case 7:
                return findViewById(R.id.lesson7);
            case 8:
                return findViewById(R.id.lesson8);
        }
        return findViewById(R.id.lesson6);
    }

    /**
     * Used to scroll to the given view.
     *
     */
    private void scrollToView() {
//
        scrollView.post(new Runnable() {
            @Override
            public void run() {
                int latestSection = SharedPrefrencesManager.readLatestSectionNumber(MainActivity.this);
                if(latestSection > 2) {
                    latestSection -=1;
                    LinearLayout currentTaskView;
                    Log.i("M_MAIN_ACTIVITY", "scrolltoview: " + SharedPrefrencesManager.readLatestSectionNumber(MainActivity.this));
                    switch (latestSection) {
                        case 1:
                            currentTaskView = findViewById(R.id.lesson1);
                            break;
                        case 2:
                            currentTaskView = findViewById(R.id.lesson2);
                            break;
                        case 3:
                            currentTaskView = findViewById(R.id.lesson3);
                            break;
                        case 4:
                            currentTaskView = findViewById(R.id.lesson4);
                            break;
                        case 5:
                            currentTaskView = findViewById(R.id.lesson5);
                            break;
                        case 6:
                            currentTaskView = findViewById(R.id.lesson6);
                            break;
                        case 7:
                            currentTaskView = findViewById(R.id.lesson7);
                            break;
                        case 8:
                            currentTaskView = findViewById(R.id.lesson8);
                            break;
                        default:
                            currentTaskView = findViewById(R.id.lesson1);
                    }
                    int scrollTo = currentTaskView.getTop();
                    scrollView.smoothScrollTo(0, scrollTo);
                }
            }
        });
    }


    /**
     * Check id the Section is unlocked, if yes set Clicklistener, if no grey background
     *
     * @param section which section Layout should be checked
     * @param number  the number of the section
     */
    public void checkIfSolved(LinearLayout section, Integer number) {
        // Date currentTime = Calendar.getInstance().getTime();
       // myProgressController.setFirebase();

        Log.i("M_MAIN_ACTIVITY", "checkIfSOlved " + String.valueOf(myProgressController.getLatestSectionNumber(this)) + " " + number);
        if (number == 1) {
            //TODO already in Modeoluser inizialize
            // myProgressController.updateUnlockedSections(1);
            section.setOnClickListener(this);
        } else if (number <= myProgressController.getLatestSectionNumber(this)) {
            section.setOnClickListener(this);
        } else {
            section.setBackgroundColor(getResources().getColor(R.color.grey));
        }

    }


    /**
     * Handle the CLickEvents for the LinearLayouts of the Sections
     */
    @Override
    public void onClick(View v) {

        //start the activity according to the clicked lesson
        switch (v.getId()) {

            case R.id.lesson1:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 1");
                startActivity(LessonActivity.class, 1);
                break;

            case R.id.lesson2:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 2");
                startActivity(LessonActivity.class, 2);
                break;

            case R.id.lesson3:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 3");
                startActivity(LessonActivity.class, 3);
                break;

            case R.id.lesson4:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 4");
                startActivity(LessonActivity.class, 4);
                break;

            case R.id.lesson5:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 5");
                startActivity(LessonActivity.class, 5);
                break;

            case R.id.lesson6:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 6");
                startActivity(LessonActivity.class, 6);
                break;

            case R.id.lesson7:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 7");
                startActivity(LessonActivity.class, 7);
                break;
            case R.id.lesson8:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 8");
                startActivity(LessonActivity.class, 8);
                break;
            case R.id.lesson9:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 9");
                startActivity(LessonActivity.class, 9);
                break;
        }
    }


    /**
     * open the right lessonActivity
     *
     * @param otherActivityClass which activity is needed
     * @param lessonNumber       which lessonNumber is needed
     */
    public void startActivity(Class<?> otherActivityClass, int lessonNumber) {
        //saveState();
        Intent intent = new Intent(MainActivity.this, otherActivityClass);
        intent.putExtra("LESSON_NUMBER", lessonNumber);
        intent.putExtra("FIRST_LESSON", true);
        startActivity(intent);
        Log.i("M_MAIN_ACTIVITY", " change to activity: " + otherActivityClass);
    }



    /**
     * Go to the Phone HomeScreen if Back is pressed
     */
    @Override
    public void onBackPressed() {
        Log.i("M_MAIN_ACTIVITY", "backbutton pressed");
        //Got to MainScreen when backputton is pressed
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);

        //finsish()
    }



    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        SharedPrefrencesManager.setTrigger(this, true, 1);
        Log.i("M_TRIGGER_CUES","MainActivity: onRestart, set Cue Trigger true");
    }


}




