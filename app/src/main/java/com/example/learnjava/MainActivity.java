package com.example.learnjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.learnjava.lessons.LessonActivity;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("Main activity loaded", " on create");

        final Controller myProgressController = (Controller) getApplicationContext();

        FrameLayout lessonLayout = findViewById(R.id.FragmentHolder);
        //findLinearLayouts
        LinearLayout lesson1 = findViewById(R.id.Lesson1);
        LinearLayout lesson2 = findViewById(R.id.lesson2);
        LinearLayout lesson3 = findViewById(R.id.lesson3);
        LinearLayout lesson4 = findViewById(R.id.lesson4);
        LinearLayout lesson5 = findViewById(R.id.lesson5);
        LinearLayout lesson6 = findViewById(R.id.lesson6);
        LinearLayout lesson7 = findViewById(R.id.lesson7);

        //SetOnClickListener for Layouts defined in onClick
        lesson1.setOnClickListener(this);
        lesson2.setOnClickListener(this);
        lesson3.setOnClickListener(this);
        lesson4.setOnClickListener(this);
        lesson5.setOnClickListener(this);
        lesson6.setOnClickListener(this);
        lesson7.setOnClickListener(this);


    }



    @Override
    public void onClick(View v) {

        //start the activity according to the clicked lesson
        switch (v.getId()) {

            case R.id.Lesson1:
                //TODO check if user has acess, if its looked
                startActivity(LessonActivity.class, 1);
                break;

            case R.id.lesson2:
                //TODO check if user has acess, if its looked
                startActivity(LessonActivity.class, 2);
                break;

            case R.id.lesson3:
                //TODO check if user has acess, if its looked
                startActivity(LessonActivity.class, 3);
                break;

            case R.id.lesson4:
                //TODO check if user has acess, if its looked
                startActivity(LessonActivity.class,4);
                break;

            case R.id.lesson5:
                //TODO check if user has acess, if its looked
                startActivity(LessonActivity.class,5);
                break;

            case R.id.lesson6:
                //TODO check if user has acess, if its looked
                startActivity(LessonActivity.class, 6);
                break;

            case R.id.lesson7:
                //TODO check if user has acess, if its looked
                startActivity(LessonActivity.class,7);
                break;
        }
    }

    public void startActivity(Class<?> otherActivityClass, int lessonNumber) {

        Intent intent = new Intent(MainActivity.this, otherActivityClass);
        intent.putExtra("LESSON_NUMBER", lessonNumber);
        startActivity(intent);
        Log.d("ChangeActivity", " to activity: " + otherActivityClass);
    }


    @Override
    public void onBackPressed() {
        Log.i("BackButtonPressed", " in Mainacitivity");
        //TODO when backbutten pressed close the application or do nothing
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

}
