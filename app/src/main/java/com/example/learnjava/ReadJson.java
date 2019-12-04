package com.example.learnjava;

import android.content.Context;
import android.util.Log;

import com.example.learnjava.models.ModelExercise;
import com.example.learnjava.models.ModelLesson;
import com.example.learnjava.models.ModelTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


//This reads the Json files of the lessons
public class ReadJson {


    private String loadJSONFromAsset(Context activity, String sectionName) {
        String json = null;
        try {
            InputStream is = activity.getAssets().open(sectionName + ".json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }



    public ArrayList<ModelTask> readTask(String sectionName, Context activity) {

        ArrayList<ModelTask> taskList = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(activity, sectionName));
            JSONArray taskArray = obj.getJSONArray("tasks");

            for (int i = 0; i < taskArray.length(); i++) {

                JSONObject taskObject = taskArray.getJSONObject(i);

                //Read Json-values
                String name_value = taskObject.getString("taskName");
                int number_value = taskObject.getInt("taskNumber");
                String text_value = taskObject.getString("taskText");
                int next_value = taskObject.getInt("taskWhatsNext");
                int type_value = taskObject.getInt("taskType");

                //read according to the tasktype. 1 for Lesson, 2  for exercise
                if (type_value == 2) {

                    int viewType_value = taskObject.getInt("exerciseViewType");
                    if(viewType_value == 1){
                        //TODO ad values
                    }

                    JSONArray solutionArrayString = taskObject.getJSONArray("exerciseSolutionString");
                    String[] solutionString_value = toStringArray(solutionArrayString);
                    JSONArray solutionArrayInt = taskObject.getJSONArray("exerciseSolutionInt");
                    int[] solutionInt_value = toIntArray(solutionArrayInt);

                    Log.i("READJSON", " " + name_value + text_value + number_value + solutionArrayInt + solutionArrayString);

                    //Add values in ArrayList
                    ModelTask newExercise = new ModelExercise(name_value, text_value, number_value, solutionString_value, solutionInt_value, next_value, viewType_value);
                    taskList.add(newExercise);
                }
                else if( type_value == 1){
                    JSONArray keyWordsArray = taskObject.getJSONArray("lessonKeyWords");
                    String[] keyWords_value = toStringArray(keyWordsArray);

                    Log.i("READJSON", " " +name_value+text_value+number_value+keyWords_value);

                    //Add values in ArrayList
                    ModelTask newLesson = new ModelLesson(name_value, text_value, number_value, keyWords_value, next_value);
                    taskList.add(newLesson);
                }
                else{
                    Log.e("MISSING TASK TYPE", " in readJson");
                }

            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return taskList;

    }

    private static String[] toStringArray(JSONArray array) {
        if (array == null)
            return null;

        String[] arr = new String[array.length()];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = array.optString(i);
        }
        return arr;
    }

    private static  int[] toIntArray(JSONArray array){
        if(array == null)
            return null;

        int[] arr = new int[array.length()];
        for (int i = 0; i < arr.length; i++){
            arr[i] = array.optInt(i);
        }
        return arr;
    }


}



