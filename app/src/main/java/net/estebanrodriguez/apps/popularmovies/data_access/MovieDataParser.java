package net.estebanrodriguez.apps.popularmovies.data_access;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Spoooon on 10/9/2016.
 */

public class MovieDataParser {

//Parses original json string data into a list of movie data maps
    public static List<Map<String, String>> parseJsonMovieDataString(String jsonData){
        List<Map<String, String>> movieDataList = new ArrayList<>();

        try {

            JSONObject data = new JSONObject(jsonData);
            JSONArray jsonArray = data.getJSONArray(KeyVault.ARRAY_KEY);

                for(int i = 0; i < jsonArray.length(); i++){
                   movieDataList.add(parseJsonMovieDataObject(jsonArray.getJSONObject(i)));
                }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return null;
    }

//Parses a json movie data object into a movie data map
    public static Map<String, String> parseJsonMovieDataObject(JSONObject jsonObject){
        Map<String, String> movieDataObjectMap = new HashMap<>();

        try {

            Set<String> keySet = movieDataObjectMap.keySet();
            for(String key: keySet){
                movieDataObjectMap.put(key, jsonObject.getString(key));
            }

            return movieDataObjectMap;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }




}
