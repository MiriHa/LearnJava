package com.example.learnjava.view_registration;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.learnjava.models.ModelUserProgress;
import com.google.gson.Gson;

public class Utils {

    private static final String PREFERENCES_FILE = "ONBOARDING_SETTINGS";
    private static final String PROGRESS_FILE = "USER_PROGRESS_SAVE";

    public static String readSharedSetting(Context ctx, String settingName, String defaultValue) {
        Log.i("M_UTILS","read sharedsetting");
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        return sharedPref.getString(settingName, defaultValue);
    }

    public static void saveSharedSetting(Context ctx, String settingName, String settingValue) {
        Log.i("M_UTILS","save shared setting");
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(settingName, settingValue);
        editor.apply();
    }

    public static void deleteSharedSetting(Context ctx, String settingName) {
        Log.i("M_UTILS","delete setting");
        SharedPreferences sharedPref = ctx.getSharedPreferences(PREFERENCES_FILE, Context.MODE_PRIVATE);
        sharedPref.edit().clear().apply();
    }



    public static void saveModelToProgress(Context ctx, ModelUserProgress progress){
        Log.i("M_UTILS","save progress");
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();

        Gson gson = new Gson();
        String json = gson.toJson(progress);
        editor.putString("MyObject", json);
        editor.apply();
    }

    public static ModelUserProgress readProgress(Context ctx){
        Log.i("M_UTILS","read progress");
        SharedPreferences sharedPref = ctx.getSharedPreferences(PROGRESS_FILE, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        String json = sharedPref.getString("MyObject", "");

        return gson.fromJson(json, ModelUserProgress.class);
    }
}

