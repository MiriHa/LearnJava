package com.example.learnjava.lessons;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.example.learnjava.LessonFragment;
import com.example.learnjava.R;

public class Lesson1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson1);

       openLessonFragment();
    }


    public void openLessonFragment(){
        Fragment lessonFragment = new LessonFragment();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.lessonFragment, lessonFragment)
                .addToBackStack(null)
                .commit();
    }
}
