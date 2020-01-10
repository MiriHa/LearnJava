package com.example.learnjava;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class SharedPrefrencesManager {

    private static final String PREFERENCES_FILE = "ONBOARDING_SETTINGS";
    private static final String PROGRESS_FILE = "USER_PROGRESS_SAVE";

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        Log.i("M_UTILS","readSharedSetting user for the first time");
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        Log.i("M_UTILS","saveSharedSetting user for the first time");
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static void deleteSharedSetting(Context ctx, String settingName) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
    }


//    public static void saveModelToProgress(Context ctx, ModelUserProgress progress){
//        Log.i("M_UTILS","save progress");
//        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPref.edit();
//
//        Gson gson = new Gson();
//        String json = gson.toJson(progress);
//        editor.putString("MY_PROGRESS", json);
//        editor.apply();
//    }
//
//    public static ModelUserProgress readProgress(Context ctx){
//        Log.i("M_UTILS","read progress");
//        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
//
//        Gson gson = new Gson();
//        String json = sharedPref.getString("MY_PROGRESS", "");
//
//        return gson.fromJson(json, ModelUserProgress.class);
//    }

    public static void deleteProgress(Context ctx, String settingName) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
    }




    public static void saveUserName(Context ctx, String userName){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("USER_NAME", userName);
        editor.apply();
        Log.i("M_SHARED_PREFERENCES","saveUserName" +userName);

    }
    public static String readUserName(Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        Log.i("M_SHARED_PREFERENCES","readUserName");
        return sharedPref.getString("USER_NAME", "");
    }

    public static void saveEMail(Context ctx, String email){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putString("EMAIL", email);
        editor.apply();
        Log.i("M_SHARED_PREFERENCES","saveEmail" + email);

    }
    public static String readEmail(Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        Log.i("M_SHARED_PREFERENCES","readEmail");
        return sharedPref.getString("EMAIL", "");
    }

    public static void saveCurrentSection(Context ctx, int userProgressCurrentSection){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt("CURRENT_SECTION", userProgressCurrentSection);
        editor.apply();
        Log.i("M_SHARED_PREFERENCES","saveCurrentSection" + userProgressCurrentSection);

    }
    public static int readCurrentSection(Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        Log.i("M_SHARED_PREFERENCES","readCUrrentSection");
        return sharedPref.getInt("CURRENT_SECTION", 0);
    }

    public static void saveCurrentScreen(Context ctx, int userProgressCurrentScreen){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt("CURRENT_SCREEN", userProgressCurrentScreen);
        editor.apply();
        Log.i("M_SHARED_PREFERENCES","saveCurrentScreen" +userProgressCurrentScreen);

    }

    public static int readCurrentScreen(Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        Log.i("M_SHARED_PREFERENCES","readCUrrentScreen");
        return sharedPref.getInt("CURRENT_SCREEN", 0);
    }

    public static void savelatestTaskNumber(Context ctx, int latestTaskNumbern){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt("LATEST_TASK_NUMBER", latestTaskNumbern);
        editor.apply();
        Log.i("M_SHARED_PREFERENCES","savelatestTaskNumber" + latestTaskNumbern);

    }

    public static int readLatestTaskNumber(Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        Log.i("M_SHARED_PREFERENCES","readLatestTaskNumber");
        return sharedPref.getInt("LATEST_TASK_NUMBER", 0);
    }

    public static void saveLatestSectionNumber(Context ctx, int latestSectionNumber){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt("LATEST_SECTION_NUMBER", latestSectionNumber);
        editor.apply();
        Log.i("M_SHARED_PREFERENCES","saveLatestSection" + latestSectionNumber);
    }

    public static int readLatestSectionNumber(Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        Log.i("M_SHARED_PREFERENCES","readLatestSectionNumber");
        return sharedPref.getInt("LATEST_SECTION_NUMBER", 0);
    }

    public static void saveLastLesson(Context ctx, int lastLessonNumber){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putInt("LAST_LESSON_NUMBER", lastLessonNumber);
        editor.apply();
        Log.i("M_SHARED_PREFERENCES","saveLASTLesson" + lastLessonNumber);

    }

    public static int readLastLesson(Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        Log.i("M_SHARED_PREFERENCES","readLastLesson");
        return sharedPref.getInt("LAST_LESSON_NUMBER", 0);
    }





}
