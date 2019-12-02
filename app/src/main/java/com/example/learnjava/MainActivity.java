package com.example.learnjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.learnjava.lessons.Lesson1;
import com.example.learnjava.lessons.Lesson2;
import com.example.learnjava.lessons.Lesson3;
import com.example.learnjava.lessons.Lesson4;
import com.example.learnjava.lessons.Lesson5;
import com.example.learnjava.lessons.Lesson6;
import com.example.learnjava.lessons.Lesson7;

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
                startActivity(Lesson1.class, 1);
                break;

            case R.id.lesson2:
                //TODO check if user has acess, if its looked
                startActivity(Lesson1.class, 2);
                break;

            case R.id.lesson3:
                //TODO check if user has acess, if its looked
                startActivity(Lesson1.class, 3);
                break;

            case R.id.lesson4:
                //TODO check if user has acess, if its looked
                startActivity(Lesson1.class,4);
                break;

            case R.id.lesson5:
                //TODO check if user has acess, if its looked
                startActivity(Lesson1.class,5);
                break;

            case R.id.lesson6:
                //TODO check if user has acess, if its looked
                startActivity(Lesson1.class, 6);
                break;

            case R.id.lesson7:
                //TODO check if user has acess, if its looked
                startActivity(Lesson1.class,7);
                break;
        }
    }

    public void startActivity(Class<?> otherActivityClass, int lessonNumber) {
        Intent intent = new Intent(MainActivity.this, otherActivityClass);
        intent.putExtra("LESSON_NUMBER", lessonNumber);
        startActivity(intent);
        Log.d("New LessonActivity", " activity: " + otherActivityClass);
    }

}
