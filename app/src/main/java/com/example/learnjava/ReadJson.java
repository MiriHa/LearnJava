package com.example.learnjava;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


//This reads the Json files of the lessons
public class ReadJson {

   // @SuppressWarnings("unchecked")
    public static void main(String[] args) {
        //JSON parser object to parse read file
        JSONParser jsonParser = new JSONParser();

        /*
        try (FileReader reader = new FileReader("TutorialAndIntroduction.json"))
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
    }
}
