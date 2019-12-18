package com.example.learnjava.room_database;

import android.util.Log;

import com.example.learnjava.models.ModelTask;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TypeConverter {


    /**
     * Convert a ArrayList<Integer>
     *
     */
    @androidx.room.TypeConverter
    public static ArrayList<Integer> stringToArrayList (String value) {
        Type listType = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        return new Gson().fromJson(value, listType);
    }

    @androidx.room.TypeConverter
    public static String arrayListToString(ArrayList<Integer> list) {

        Gson gson = new Gson();
        String json = gson.toJson(list);
        return json;
    }


    /**
     * Convert a List of TaskModels
     */
    @androidx.room.TypeConverter
    public static List<ModelTask> stringToModelTaskList(String data) {

        Gson gson = new Gson();
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<ModelTask>>() {
        }.getType();
        return gson.fromJson(data, listType);
    }

    @androidx.room.TypeConverter
    public static String modelTaskListToString(List<ModelTask> modelTasks) {
        Gson gson = new Gson();
        return gson.toJson(modelTasks);
    }


    @androidx.room.TypeConverter
    public static ModelTask stringToModelTask(String data){
        Gson gson = new Gson();
        Type taskType = new TypeToken<ModelTask>(){}.getType();

        return gson.fromJson(data, taskType);
    }


    @androidx.room.TypeConverter
    public static String modelTaskToString(ModelTask task){
        Gson gson = new Gson();
        return gson.toJson(task);
    }

    /**
     * Convert date to String
     */

    @androidx.room.TypeConverter
    public static Date StringtoDate(String value) {

        SimpleDateFormat formatter = new SimpleDateFormat("dd.mm.yyyy hh:mm:ss", Locale.GERMANY);

        Date date = Calendar.getInstance().getTime();

        try {
            date = formatter.parse(value);
            Log.i("M_TYPE_CONVERTER","date parse" + date);
        }catch (Exception e){
            e.printStackTrace();
        }
        return date;
    }

    @androidx.room.TypeConverter
    public static String dateToString(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd.mm.yyyy hh:mm:ss", Locale.GERMANY);
        String strDate = formatter.format(date);

        return strDate;
    }



}
