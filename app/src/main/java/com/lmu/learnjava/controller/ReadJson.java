package com.lmu.learnjava.controller;

import android.content.Context;
import android.util.Log;

import com.lmu.learnjava.models.ModelExercise;
import com.lmu.learnjava.models.ModelLesson;
import com.lmu.learnjava.models.ModelQuestion;
import com.lmu.learnjava.models.ModelTask;

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
                int section_number_value = taskObject.getInt("sectionNumber");
                String text_value = taskObject.getString("taskText");
                int next_value = taskObject.getInt("taskWhatsNext");
                int type_value = taskObject.getInt("taskType");

                //read according to the tasktype. 1 for Lesson, 2  for exercise
                if (type_value == 2) {

                    int viewType_value = taskObject.getInt("exerciseViewType");

                        //Type: Answer String
                        if(viewType_value == 1){
                            JSONArray solutionStringArray = taskObject.getJSONArray("exerciseSolutionStringArray");
                            String[] solutionStringArray_value = toStringArray(solutionStringArray);

                            //Add values in ArrayList
                            ModelTask newExercise = new ModelExercise(name_value,text_value,number_value,section_number_value, solutionStringArray_value, null, next_value, viewType_value,0, null, null);
                            taskList.add(newExercise);
                        }
                        //Type: Answer Choice -> int
                        else if(viewType_value == 2){
                            JSONArray answerChoicesStringArray = taskObject.getJSONArray("exerciseSolutionStringArray");
                            String[] answerChoicesArray_value = toStringArray(answerChoicesStringArray);

                            int solutionInt_value = taskObject.getInt("exerciseSolutionInt");

                            //Add values in ArrayList,
                            ModelTask newExercise = new ModelExercise(name_value,text_value,number_value, section_number_value, answerChoicesArray_value, null, next_value, viewType_value, solutionInt_value,"", null);
                            taskList.add(newExercise);
                        }
                        //Type: Answer FillBlanks -> String[]
                        else if(viewType_value == 3){
                            JSONArray solutionStringArray = taskObject.getJSONArray("exerciseSolutionStringArray");
                            String[] solutionStringArray_value = toStringArray(solutionStringArray);

                            JSONArray contentStringArray = taskObject.getJSONArray("exerciseContentStringArray");
                            String[] contentStringArray_value = toStringArray(contentStringArray);

                            //Add values in ArrayList
                            ModelTask newExercise = new ModelExercise(name_value,text_value,number_value,section_number_value,solutionStringArray_value, null, next_value, viewType_value,0,"", contentStringArray_value);
                            taskList.add(newExercise);
                        }
                        //Type: Drag and Drop -> string[]
                        else if(viewType_value == 4){
                            JSONArray solutionStringArray = taskObject.getJSONArray("exerciseSolutionStringArray");
                            String[] solutionStringArray_value = toStringArray(solutionStringArray);

                            JSONArray contentStringArray = taskObject.getJSONArray("exerciseContentStringArray");
                            String[] contentStringArray_value = toStringArray(contentStringArray);

                            JSONArray solutionIntArray = taskObject.getJSONArray("exerciseSolutionIntArray");
                            int[] solutionIntArray_value = toIntArray(solutionIntArray);


                            //Add values in ArrayList
                            ModelTask newExercise = new ModelExercise(name_value,text_value,number_value,section_number_value,solutionStringArray_value, solutionIntArray_value, next_value, viewType_value,0,"", contentStringArray_value);
                            taskList.add(newExercise);

                        }
                        //Type: Order -> int[]
                        else if(viewType_value == 5){
                            JSONArray solutionStringArray = taskObject.getJSONArray("exerciseSolutionStringArray");
                            String[] solutionStringArray_value = toStringArray(solutionStringArray);
                            JSONArray contentStringArray = taskObject.getJSONArray("exerciseContentStringArray");
                            String[] contentStringArray_value = toStringArray(contentStringArray);

                            //Add values in ArrayList
                            ModelTask newExercise = new ModelExercise(name_value,text_value,number_value,section_number_value,solutionStringArray_value, null, next_value, viewType_value,0,"", contentStringArray_value);
                            taskList.add(newExercise);
                        }
                        //Type: Code -> String[]
                        else if(viewType_value == 6){
//                            JSONArray solutionStringArray = taskObject.getJSONArray("exerciseSolutionStringArray");
//                            String[] solutionStringArray_value = toStringArray(solutionStringArray);
                            String solutionString_value = taskObject.getString("exerciseSolutionString");

                            JSONArray contentStringArray = taskObject.getJSONArray("exerciseContentStringArray");
                            String[] contentStringArray_value = toStringArray(contentStringArray);

                            //Add values in ArrayList
                            ModelTask newExercise = new ModelExercise(name_value,text_value,number_value,section_number_value,null, null, next_value, viewType_value,0,solutionString_value, contentStringArray_value);
                            taskList.add(newExercise);

                        }
                        else {
                            Log.i("WrongVIewTypeValue", " must be 1 - 6");
                        }
                }

                else if( type_value == 1){
                    JSONArray keyWordsArray = taskObject.getJSONArray("lessonKeyWords");
                    String[] keyWords_value = toStringArray(keyWordsArray);

                    Log.i("READJSON", " " +name_value+text_value+number_value+keyWords_value);

                    //Add values in ArrayList
                    ModelTask newLesson = new ModelLesson(name_value, text_value, number_value, section_number_value, keyWords_value, next_value);
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

    /**
     * Read the Questions for the cue storead in a json file
     * @param activity in what context
     * @param sectionWhat section1 or section2 etc for reading the file needed
     * @return get all questions for a section
     */
    public ArrayList<ModelQuestion> readQuestions(Context activity, String sectionWhat){

        ArrayList<ModelQuestion> questionList = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(activity, "questionsCue"));
            JSONArray questionArray = obj.getJSONArray(sectionWhat);

            for(int i =0; i<questionArray.length();i++){

                JSONObject taskObject = questionArray.getJSONObject(i);

                //Read Json-values
                int questionNumber_value = taskObject.getInt("questionNumber");
                int answerInt_value = taskObject.getInt("questionAnswerInt");
                String question_value = taskObject.getString("question");

                JSONArray solutionStringArray = taskObject.getJSONArray("questionAnswerArray");
                String[] questionAnswerArray_value = toStringArray(solutionStringArray);

                ModelQuestion question = new ModelQuestion(questionNumber_value, answerInt_value, question_value, questionAnswerArray_value);
                questionList.add(question);

            }


        } catch (JSONException e) {
        e.printStackTrace();
    }

        return questionList;

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



