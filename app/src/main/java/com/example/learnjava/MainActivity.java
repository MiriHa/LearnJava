package com.example.learnjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.learnjava.room_database.Logging;
import com.example.learnjava.sections.LessonActivity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    Controller myProgressController;
//    SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
    Context context;

    String userID;

//    LinearLayout lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, lesson9;
      Button lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, lesson9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            userID = savedInstanceState.getString("userId");
        }
        else{
            showUserIdPOpUP();
        }
        setContentView(R.layout.activity_main_alternate);
        Log.i("M_MAIN_ACTIVITY", " on create");

        myProgressController = (Controller) getApplicationContext();
       // retriveSave();

        context = getApplicationContext();

        Log.i("M_MAIN_ACTIVITY", " backstack:" + getSupportFragmentManager().getFragments().toString());

        //TODO Only for testing purposes
        myProgressController.updateUnlockedSections(2);
        myProgressController.updateUnlockedSections(3);
       // myProgressController.updateUnlockedSections(4);

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
        lesson9 = findViewById(R.id.lesson9);


        Log.i("M_MAIN_ACTIVITY", " CheckCOntroller: progressController Sections " + myProgressController.getSections().toString());

        //SetOnClickListener for Layouts defined in onClick
        checkIfSolved(lesson1, 1);
        checkIfSolved(lesson2, 2);
        checkIfSolved(lesson3, 3);
        checkIfSolved(lesson4, 4);
        checkIfSolved(lesson5, 5);
        checkIfSolved(lesson6, 6);
        checkIfSolved(lesson7, 7);
        checkIfSolved(lesson8, 8);
        checkIfSolved(lesson9, 9);



    }


    public void checkIfSolved(Button lesson, Integer number){


        Date currentTime = Calendar.getInstance().getTime();

        Log.i("M_MAIN_ACTIVITY", "checkIfSOlved");
        if(number == 1){
            myProgressController.updateUnlockedSections(1);
            lesson.setOnClickListener(this);
        }
        else if(myProgressController.checkUnlockedSections(number)){
            lesson.setOnClickListener(this);
        }
        else{
            lesson.setBackgroundColor(getResources().getColor(R.color.grey));
        }

    }

    public void showUserIdPOpUP(){
        SharedPreferences sp = getSharedPreferences("FirstTimeFile", Context.MODE_PRIVATE);
//
//        SharedPreferences settings = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
//
//        // Writing data to SharedPreferences
//        SharedPreferences.Editor editor = settings.edit();
//        editor.putString("key", "some value");
//        editor.apply();
//
//        // Reading from SharedPreferences
//        String value = settings.getString("key", "");
//        Log.d(TAG, value);

/**
 * when the app is opened for the first time, no such variable
 * (appIsOpenedForTheFirstTime) exists. So, it becomes true.
 */
        boolean appIsOpenedForTheFirstTime = sp.getBoolean("IsAppOpenedForFirstTime",true);


//since it is true, it will be set to false after the execution of following block:
        if(appIsOpenedForTheFirstTime) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("IsAppOpenedForFirstTime", false);
            editor.apply();

            //PUT THE CODE FOR YOUR POPUP HERE
        }
    }


    @Override
    public void onClick(View v) {

        //start the activity according to the clicked lesson
        switch (v.getId()) {

            case R.id.Lesson1:
                startActivity(LessonActivity.class, 1);
                Logging log = new Logging(userID, Calendar.getInstance().getTime(), "OPEN_A_SECTION","Section 1");
                break;

            case R.id.lesson2:
                startActivity(LessonActivity.class, 2);
                break;

            case R.id.lesson3:
                startActivity(LessonActivity.class, 3);
                break;

            case R.id.lesson4:
                startActivity(LessonActivity.class,4);
                break;

            case R.id.lesson5:
                startActivity(LessonActivity.class,5);
                break;

            case R.id.lesson6:
                startActivity(LessonActivity.class, 6);
                break;

            case R.id.lesson7:
                startActivity(LessonActivity.class,7);
                break;
        }
    }

    public void startActivity(Class<?> otherActivityClass, int lessonNumber) {
        //saveState();
        Intent intent = new Intent(MainActivity.this, otherActivityClass);
        intent.putExtra("LESSON_NUMBER", lessonNumber);
        startActivity(intent);
        Log.i("M_MAIN_ACTIVITY", " change to activity: " + otherActivityClass);
    }


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
    protected void onSaveInstanceState (Bundle outState) {

        super.onSaveInstanceState(outState);
       // outState.putCharSequence(EDIT_TEXT_VALUE, mTextView.getText()); //<-- Saving operation, change the values to what ever you want.

        outState.putString("userID", "This is my message to be reloaded");
    }




//    void saveState() {
//        ModelUserProgress userProgress = myProgressController.getModelUserProgress();
//
//        SharedPreferences.Editor prefsEditor = mPrefs.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(userProgress);
//        prefsEditor.putString("userProgress", json);
//        prefsEditor.commit();
//    }
//
//    public void retriveSave(){
//        Gson gson = new Gson();
//        String json = mPrefs.getString("userProgress", "");
//        ModelUserProgress userProgress = gson.fromJson(json, ModelUserProgress.class);
//        myProgressController.setModelUserProgress(userProgress);
//    }




















}
