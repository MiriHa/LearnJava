package com.example.learnjava;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity  implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                startActivity(Lesson1.class);
                break;

            case R.id.lesson2:
                startActivity(Lesson2.class);
                break;

            case R.id.lesson3:
                startActivity(Lesson3.class);
                break;

            case R.id.lesson4:
                startActivity(Lesson4.class);
                break;

            case R.id.lesson5:
                startActivity(Lesson5.class);
                break;

            case R.id.lesson6:
                startActivity(Lesson6.class);
                break;

            case R.id.lesson7:
                startActivity(Lesson7.class);
                break;
        }
    }

    public void startActivity(Class<?> otherActivityClass) {
        Intent intent = new Intent(MainActivity.this, otherActivityClass);
        startActivity(intent);
    }

}
