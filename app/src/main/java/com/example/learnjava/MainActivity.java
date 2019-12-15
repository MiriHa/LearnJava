package com.example.learnjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.learnjava.sections.LessonActivity;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    Controller myProgressController;
//    SharedPreferences mPrefs = getPreferences(MODE_PRIVATE);
    Context context;

    LinearLayout lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, lesson9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        lesson1 = findViewById(R.id.Lesson1);
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


    public void checkIfSolved(LinearLayout lesson, Integer number){

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


    @Override
    public void onClick(View v) {

        //start the activity according to the clicked lesson
        switch (v.getId()) {

            case R.id.Lesson1:
                startActivity(LessonActivity.class, 1);
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
