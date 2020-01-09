package com.example.learnjava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.example.learnjava.registration.LogInActivity;
import com.example.learnjava.registration.SignUpActivity;
import com.example.learnjava.registration.Utils;
import com.example.learnjava.resumption_cues.WordCueFragment;
import com.example.learnjava.sections.LessonActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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
    LinearLayout lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, lesson9;
    EditText userIdEdit;

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


        context = getApplicationContext();
        myProgressController = (Controller) getApplicationContext();

        //check if the app is opend for the first time
        auth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference();
        currentuserID = auth.getCurrentUser().getUid();
        Log.i("M_MAIN_ACTIVITY","on start  add AuthListener");

        myProgressController.fetchModelUserProgress();
        Log.i("M_MAIN_ACTIVITY","set Refrences and Controller");
        myProgressController.makeaLog(Calendar.getInstance().getTime(), "ENTERED_MAIN_ACTIVITY", "set Refrences and Content");

        myProgressController.updateLatestSection(3);
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

    public void setTheContent(){
        myProgressController.updateLatestSection(3);
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


    public void checkIfSolved(LinearLayout lesson, Integer number) {
       // Date currentTime = Calendar.getInstance().getTime();

        Log.i("M_MAIN_ACTIVITY", "checkIfSOlved");
        if (number == 1) {
            //TODO already in Modeoluser inizialize
            // myProgressController.updateUnlockedSections(1);
            lesson.setOnClickListener(this);
        } else if (number <= myProgressController.getLatestSectionNumber()) {
            lesson.setOnClickListener(this);
        } else {
            lesson.setBackgroundColor(getResources().getColor(R.color.grey));
        }

    }


    public void showCueWord(String text){
        FragmentManager fm = getSupportFragmentManager();
        WordCueFragment wordCueFragment = WordCueFragment.newIntance(text);
        wordCueFragment.show(fm, "fragment_word_cue");
    }



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

//    @Override
//    protected void onSaveInstanceState(Bundle outState) {
//
//        super.onSaveInstanceState(outState);
//        // outState.putCharSequence(EDIT_TEXT_VALUE, mTextView.getText()); //<-- Saving operation, change the values to what ever you want.
//
//        outState.putString("userID", "This is my message to be reloaded");
//    }


//    private void showLoginPopUp(final View view) {
//        //Create a View object yourself through inflater
//        LayoutInflater inflater = (LayoutInflater) view.getContext().getSystemService(view.getContext().LAYOUT_INFLATER_SERVICE);
//        View popupView = inflater.inflate(R.layout.pop_up_user_name, null);
//
//        //Specify the length and width through constants
//        int width = LinearLayout.LayoutParams.MATCH_PARENT;
//        int height = LinearLayout.LayoutParams.MATCH_PARENT;
//
//        //Make Inactive Items Outside Of PopupWindow
//        boolean focusable = true;
//
//        //Create a window with our parameters
//        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);
//
//        //Set the location of the window on the screen
//        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
//
//        //Initialize the elements of our window, install the handler
//
//        EditText editText = popupView.findViewById(R.id.editTextPopUp);
//
//        Button buttonEdit = popupView.findViewById(R.id.PopUpCheckButton);
//        buttonEdit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //As an example, display the message
//                Toast.makeText(view.getContext(), "Wow, popup action button", Toast.LENGTH_SHORT).show();
//
//            }
//        });
//
//
//        //Handler for clicking on the inactive zone of the window
//
//        popupView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//                //Close the window when clicked
//                //popupWindow.dismiss();
//                Log.i("M_POPUP", "outside of PopupWindows clicked");
//                return true;
//            }
//        });
//    }



//    @Override
//    protected void onSaveInstanceState (Bundle outState) {
//
//        super.onSaveInstanceState(outState);
//        // outState.putCharSequence(EDIT_TEXT_VALUE, mTextView.getText()); //<-- Saving operation, change the values to what ever you want.
//
//        outState.putString("userID", "This is my message to be reloaded");
//    }


}




