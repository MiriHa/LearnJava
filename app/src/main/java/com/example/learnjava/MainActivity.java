package com.example.learnjava;

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

import com.example.learnjava.resumption_cues.WordCueFragment;
import com.example.learnjava.sections.LessonActivity;
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
    Dialog myDialog;

    //Button lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, lesson9;
    LinearLayout lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, lesson9, ScrollViewContainer;
    EditText userIdEdit;
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
        lesson9 = findViewById(R.id.lesson9);
        scrollView = findViewById(R.id.MainScrollView);
        ScrollViewContainer = findViewById(R.id.MainscrollViewContainer);


        context = getApplicationContext();
        myProgressController = (Controller) getApplicationContext();
        myProgressController.fetchModelUserProgress();

        //check if the app is opend for the first time
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        currentuserID = auth.getCurrentUser().getUid();

        //myProgressController.fetchModelUserProgress();
        Log.i("M_MAIN_ACTIVITY", "set Refrences and Controller");
        myProgressController.makeaLog(Calendar.getInstance().getTime(), "ENTERED_MAIN_ACTIVITY", "set Refrences and Content");

        //TODO löschen wenn nicht mehr am test?
        //myProgressController.updateLatestSection(this,5);
        checkIfSolved(lesson1, 1);
        checkIfSolved(lesson2, 2);
        checkIfSolved(lesson3, 3);
        checkIfSolved(lesson4, 4);
        checkIfSolved(lesson5, 5);
        checkIfSolved(lesson6, 6);
        checkIfSolved(lesson7, 7);
        checkIfSolved(lesson8, 8);
        checkIfSolved(lesson9, 9);

        scrollToView(scrollView);

    }

    private void scrollToLatestSection(){
        LinearLayout latestLayout = latestLayoutID();
        Log.i("M_MAIN_ACTIVITY","scrollToLatestSection "+latestLayout.toString());
//        View targetView = findViewById(R.id.DESIRED_VIEW_ID);
//        targetView.getParent().requestChildFocus(targetView,targetView);
            Rect textRect = new Rect(); //coordinates to scroll to
            latestLayout.getHitRect(textRect); //fills textRect with coordinates of TextView relative to its parent (LinearLayout)
            scrollView.requestChildRectangleOnScreen(latestLayout, textRect, false); //ScrollView will make sure, the given textRect is visible

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
            case 9:
                return findViewById(R.id.lesson9);
        }
        return findViewById(R.id.lesson6);
    }

    /**
     * Used to scroll to the given view.
     *
     */
    private void scrollToView(final ScrollView scrollViewParent) {
        View view = latestLayoutID();
        // Get deepChild Offset
        Point childOffset = new Point();
        getDeepChildOffset(scrollViewParent, view.getParent(), view, childOffset);
        // Scroll to child.
        scrollViewParent.smoothScrollTo(0, childOffset.y);
    }

    /**
     * Used to get deep child offset.
     * <p/>
     * 1. We need to scroll to child in scrollview, but the child may not the direct child to scrollview.
     * 2. So to get correct child position to scroll, we need to iterate through all of its parent views till the main parent.
     *
     * @param mainParent        Main Top parent.
     * @param parent            Parent.
     * @param child             Child.
     * @param accumulatedOffset Accumulated Offset.
     */
    private void getDeepChildOffset(final ViewGroup mainParent, final ViewParent parent, final View child, final Point accumulatedOffset) {
        ViewGroup parentGroup = (ViewGroup) parent;
        accumulatedOffset.x += child.getLeft();
        accumulatedOffset.y += child.getTop();
        if (parentGroup.equals(mainParent)) {
            return;
        }
        getDeepChildOffset(mainParent, parentGroup.getParent(), parentGroup, accumulatedOffset);
    }

    /**
     * Check id the Section is unlocked, if yes set Clicklistener, if no grey background
     *
     * @param section which section Layout should be checked
     * @param number  the number of the section
     */
    public void checkIfSolved(LinearLayout section, Integer number) {
        // Date currentTime = Calendar.getInstance().getTime();
       // myProgressController.fetchModelUserProgress();

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


    public void showCueWord(String text, int section) {
        FragmentManager fm = getSupportFragmentManager();
        WordCueFragment wordCueFragment = WordCueFragment.newIntance(text,section);
        wordCueFragment.show(fm, "fragment_word_cue");
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


//    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
//        @Override
//        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//            //auth = FirebaseAuth.getInstance();
//            if (firebaseUser == null) {
//                Intent intent = new Intent(MainActivity.this, LogInActivity.class);
//                startActivity(intent);
//            }
//        }
//    };

    @Override
    protected void onStart() {
        super.onStart();
//        if(!isUserFirstTime) {
////            auth.addAuthStateListener(authStateListener);
////            Log.i("M_MAIN_ACTIVITY","on start  add AuthListener");
//        }
    }

    @Override
    protected void onStop() {
        super.onStop();
//        if(!isUserFirstTime) {
//            auth.removeAuthStateListener(authStateListener);
//            Log.i("M_MAIN_ACTIVITY","on stop remove AuthListener");
//        }
    }


}




