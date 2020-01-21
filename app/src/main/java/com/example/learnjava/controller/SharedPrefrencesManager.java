package com.example.learnjava.controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.learnjava.models.ModelLog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class SharedPrefrencesManager {

    private static final String PREFERENCES_FILE = "ONBOARDING_SETTINGS";
    private static final String PROGRESS_FILE = "USER_PROGRESS_SAVE";
    private static final String CUES_FILE = "CUES_SAVE";
    private static final String LOG_FILE = "LOGS_SAVE";


    /**
     * Manage the User for the first time variable
     */
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


    /**
     * Manage the Resumption Cues
     */

    public static void setTrigger(Context ctx, boolean trigger, int why){
        SharedPreferences sharedPref = ctx.getSharedPreferences(CUES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        editor.putBoolean("TRIGGER", trigger);
        //0: trigger set false, 1: screen has gone dark, 2 app is restartet
        editor.putInt("WHY",why);
        editor.apply();
        Log.i("M_SHARED_PREFERENCES","setTrigger " + trigger);

    }

    public static boolean readTrigger(Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences(CUES_FILE, Context.MODE_PRIVATE);
        Log.i("M_SHARED_PREFERENCES","readUserName");
        return sharedPref.getBoolean("TRIGGER", false);
    }

    public static int readTriggerWhy(Context ctx){
        SharedPreferences sharedPref = ctx.getSharedPreferences(CUES_FILE, Context.MODE_PRIVATE);
        Log.i("M_SHARED_PREFERENCES","readUserName");
        return sharedPref.getInt("WHY", 1);
    }




    /**
     * Save and Read the User Progress
     */

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



    public static void saveLogs(Context ctx, ModelLog log){
        SharedPreferences sharedPref = ctx.getSharedPreferences(LOG_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Log.i("M_SHARED_PREFERENCES","saveLogs");
        ArrayList<ModelLog> logs;
        if(readLogs(ctx) != null) {
            logs = readLogs(ctx);
        }else{
            logs = new ArrayList<>();
        }

        logs.add(log);

        Gson gson = new Gson();
        String json = gson.toJson(logs);
        editor.putString(LOG_FILE, json);
        editor.apply();
    }


    public static ArrayList<ModelLog> readLogs(Context ctx) {
        SharedPreferences sharedPref = ctx.getSharedPreferences(LOG_FILE, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sharedPref.getString(LOG_FILE, null);
        Type type = new TypeToken<ArrayList<ModelLog>>() {}.getType();
        Log.i("M_SHARED_PREFERENCES","readLogs "+ gson.fromJson(json, type));
        return gson.fromJson(json, type);
    }

    public static void deleteLogs(Context ctx) {
        Log.i("M_SHARED_PREFERENCES","delete Logs");
        SharedPreferences sharedPref = ctx.getSharedPreferences(LOG_FILE, Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
    }

}
