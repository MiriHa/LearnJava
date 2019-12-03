package com.example.learnjava;

import android.content.Context;
import android.util.Log;

import com.example.learnjava.models.ModelExercise;
import com.example.learnjava.models.ModelLesson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;


//This reads the Json files of the lessons
public class ReadJson {


    public static void main(String[] args) {

    }

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


    public ArrayList<ModelLesson> readLessons(String sectionName, Context activity) {

        ArrayList<ModelLesson> lessonList = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(activity, sectionName));
            JSONArray lessonArray = obj.getJSONArray("lessons");

            //ArrayList<ModelLesson> lessonList = new ArrayList<>();

            for (int i = 0; i < lessonArray.length(); i++) {

                JSONObject lessonObject = lessonArray.getJSONObject(i);

                //Read Jsonvalues
                String name_value = lessonObject.getString("lessonName");
                String text_value = lessonObject.getString("lessonText");
                String next_value = lessonObject.getString("lessonWhatsNext");
                int number_value = lessonObject.getInt("lessonNumber");
                JSONArray keyWordsArray = lessonObject.getJSONArray("lessonKeyWords");
                String[] keyWords_value = toStringArray(keyWordsArray);

                Log.i("READJSON", " " +name_value+text_value+number_value+keyWords_value);

                //Add values in ArrayList
                ModelLesson newLesson = new ModelLesson(name_value, text_value, number_value, keyWords_value, next_value);
                lessonList.add(newLesson);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return lessonList;

    }

    public ArrayList<ModelExercise> readExercises(String sectionName, Context activity) {

        ArrayList<ModelExercise> exercisesList = new ArrayList<>();

        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset(activity, sectionName));
            JSONArray exerciseArray = obj.getJSONArray("exercises");

            for (int i = 0; i < exerciseArray.length(); i++) {

                JSONObject lessonObject = exerciseArray.getJSONObject(i);

                //Read Jsonvalues
                String name_value = lessonObject.getString("exerciseName");
                String text_value = lessonObject.getString("exerciseText");
                String next_value = lessonObject.getString("exerciseWhatsNext");
                int number_value = lessonObject.getInt("exerciseNumber");
                JSONArray solutionArrayString = lessonObject.getJSONArray("exerciseSolutionString");
                String[] solutionString_value = toStringArray(solutionArrayString);
                JSONArray solutionArrayInt = lessonObject.getJSONArray("exerciseSolutionInt");
                int[] solutionInt_value = toIntArray(solutionArrayInt);


                Log.i("READJSON", " " +name_value+text_value+number_value+solutionArrayInt+solutionArrayString);

                //Add values in ArrayList
                ModelExercise newExercise = new ModelExercise(name_value, text_value, number_value, solutionString_value, solutionInt_value, next_value);
                exercisesList.add(newExercise);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return exercisesList;

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


        //JSON parser object to parse read file
//        JSONParser jsonParser = new JSONParser();
//
//
//    try{
//
//        Object obj = jsonParser.parse(new FileReader("section1.json"));
//
//        JSONObject jsonObject = (JSONObject) obj;
//
//        JSONArray lessonList = (JSONArray) jsonObject.get("lessons");
//        int size = lessonList.size();
//
//        for (int i =0; i<size; i++){
//            JSONObject lessonObject = lessonList.
//
//            String name = lessonObject.getS
//        }
//        Iterator<String> iterator = lessonList.iterator();
//        while (iterator.hasNext()){
//
//        }
//
//
//
//    }catch (Exception e){
//        e.printStackTrace();
//    }
















        /*
        try (FileReader reader = new FileReader("section1.json"))
        {
            //Read JSON file
            Object obj = jsonParser.parse(reader);

            JSONArray employeeList = (JSONArray) obj;
            System.out.println(employeeList);

            //Iterate over employee array
            employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

         */

        /*

    private static void parseEmployeeObject(JSONObject employee)
    {
        //Get employee object within list
        JSONObject employeeObject = (JSONObject) employee.get("employee");

        //Get employee first name
        String firstName = (String) employeeObject.get("firstName");
        System.out.println(firstName);

        //Get employee last name
        String lastName = (String) employeeObject.get("lastName");
        System.out.println(lastName);

        //Get employee website name
        String website = (String) employeeObject.get("website");
        System.out.println(website);
    }
    */

        /*

        public static void main(String[] args) throws FileNotFoundException, JSONException {
            String jsonData = "";
            BufferedReader br = null;
            try {
                String line;
                br = new BufferedReader(new FileReader("/Users/<username>/Documents/Crunchify_JSON.txt"));
                while ((line = br.readLine()) != null) {
                    jsonData += line + "\n";
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null)
                        br.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            // System.out.println("File Content: \n" + jsonData);
            JSONObject obj = new JSONObject(jsonData);
            System.out.println("blogURL: " + obj.getString("blogURL"));
            System.out.println("twitter: " + obj.getString("twitter"));
            System.out.println("social: " + obj.getJSONObject("social"));
        }
        */


