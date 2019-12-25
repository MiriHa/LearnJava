package com.example.learnjava;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.learnjava.room_database.Logging;
import com.example.learnjava.room_database.UserDatabase;
import com.example.learnjava.sections.LessonActivity;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Controller myProgressController;

    UserDatabase database;
    String userID = "M";

    Context context;
    Dialog myDialog;

    //Button lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, lesson9;
    LinearLayout lesson1, lesson2, lesson3, lesson4, lesson5, lesson6, lesson7, lesson8, lesson9;
    EditText userIdEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i("M_MAIN_ACTIVITY", " on create");

        context = getApplicationContext();
        database = UserDatabase.getInstance(this);
        myProgressController = (Controller) getApplicationContext();

        //TODO for testing purposes

        myProgressController.deleteAllTabels(database);

        //CHeck and retrive USerID or open UserID PopUpWindow
        myDialog = new Dialog(this);
        showUserIdPOpUP();

        Log.i("M_MAIN_ACTIVITY", " backstack:" + getSupportFragmentManager().getFragments().toString());

        //TODO Only for testing purposes
//        myProgressController.updateUnlockedSections(2);
//        myProgressController.updateUnlockedSections(3);
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


        //Log.i("M_MAIN_ACTIVITY", " CheckCOntroller: progressController Sections " + myProgressController.getSections().toString());

        //SetOnClickListener for Layouts defined in onClick
//        checkIfSolved(lesson1, 1);
//        checkIfSolved(lesson2, 2);
//        checkIfSolved(lesson3, 3);
//        checkIfSolved(lesson4, 4);
//        checkIfSolved(lesson5, 5);
//        checkIfSolved(lesson6, 6);
//        checkIfSolved(lesson7, 7);
//        checkIfSolved(lesson8, 8);
//        checkIfSolved(lesson9, 9);


    }

    public void setTheCOntent(){
//        myProgressController.updateUnlockedSections(2, database);
//        myProgressController.updateUnlockedSections(3, database);
//
        myProgressController.updateLatestSection(3, database);


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

    public void showUserIdPOpUP() {

        Log.i("M_MAIN_ACTIVITY","showUserIdPopUp");
        SharedPreferences sp = getSharedPreferences("FirstTimeFile", Context.MODE_PRIVATE);


        //Whenn the App is opened for the first time the appIsOPendFOrTHeFirstTime doesn't exist -> becomes true
        boolean appIsOpenedForTheFirstTime = sp.getBoolean("IsAppOpenedForFirstTime", true);

        // boolean appIsOpenedForTheFirstTime = true;

        //since it is true, it will be set to false after the execution of following block:
        //set the variavle to false and open the PopUp to get the USerID
        if (appIsOpenedForTheFirstTime) {
            SharedPreferences.Editor editor = sp.edit();
            editor.putBoolean("IsAppOpenedForFirstTime", false);
            editor.apply();
            Log.i("M_MAIN_ACTIVITY","openPopUP, app open for the first Time");
            //Open the PopUP to set the UserID
            openPopUp();
        }else {
            //get the UserID from shared Prefrences and fetch the saved ModelUSerProgress from the Database
            SharedPreferences preferences = context.getSharedPreferences("USER_ID", Context.MODE_PRIVATE);
            userID = preferences.getString("userID", "Blub");
            myProgressController.fetchModelUserProgress(userID, database);

            setTheCOntent();

            Log.i("M_MAIN_ACTIVITY","userId Retrived: " + userID);
        }
    }

    private void openPopUp() {
        myDialog.setContentView(R.layout.pop_up_user_name);
        userIdEdit = (EditText) myDialog.findViewById(R.id.editTextPopUp);

        Button checkButton = (Button) myDialog.findViewById(R.id.PopUpCheckButton);
        checkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.i("M_MAIN_ACTIVITY","Check Button in User Id PopUP");
                userID = userIdEdit.getText().toString();
                //save the userID to shared Prefrences
                SharedPreferences prefs =  context.getSharedPreferences("USER_ID", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("userID", userID);
                editor.apply();

                //instanziate a new UserProgressModel
                myProgressController.initializeModelUserProgress(userID, database);
                setTheCOntent();
                Log.i("M_MAIN_ACTIVITY","userId SAVED: " + userID);
                myDialog.dismiss();
            }
        });
        //myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        myDialog.show();
        Log.i("M_MAIN_ACTIVITY","open PopUP");
        myDialog.setCanceledOnTouchOutside(false);
    }


    @Override
    public void onClick(View v) {

        //start the activity according to the clicked lesson
        switch (v.getId()) {

            case R.id.lesson1:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 1", database);
                startActivity(LessonActivity.class, 1);
                break;

            case R.id.lesson2:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 2", database);
                startActivity(LessonActivity.class, 2);
                break;

            case R.id.lesson3:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 3", database);
                startActivity(LessonActivity.class, 3);
                break;

            case R.id.lesson4:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 4", database);
                startActivity(LessonActivity.class, 4);
                break;

            case R.id.lesson5:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 5", database);
                startActivity(LessonActivity.class, 5);
                break;

            case R.id.lesson6:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 6", database);
                startActivity(LessonActivity.class, 6);
                break;

            case R.id.lesson7:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 7", database);
                startActivity(LessonActivity.class, 7);
                break;
            case R.id.lesson8:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 8", database);
                startActivity(LessonActivity.class, 8);
                break;
            case R.id.lesson9:
                myProgressController.makeaLog(Calendar.getInstance().getTime(), "OPEN_A_SECTION", "Section 9", database);
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




