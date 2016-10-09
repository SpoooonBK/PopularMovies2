package net.estebanrodriguez.apps.popularmovies.data_access;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

            movieDataObjectMap.put(KeyVault.POSTER_PATH, jsonObject.getString(KeyVault.POSTER_PATH));
            movieDataObjectMap.put(KeyVault.ADULT, jsonObject.getString(KeyVault.ADULT));
            movieDataObjectMap.put(KeyVault.OVERVIEW, jsonObject.getString(KeyVault.OVERVIEW));
            movieDataObjectMap.put(KeyVault.RELEASE_DATE, jsonObject.getString(KeyVault.RELEASE_DATE));
            movieDataObjectMap.put(KeyVault.GENRE_IDS, jsonObject.getString(KeyVault.GENRE_IDS));
            movieDataObjectMap.put(KeyVault.ID, jsonObject.getString(KeyVault.ID));
            movieDataObjectMap.put(KeyVault.ORIGINAL_TITLE, jsonObject.getString(KeyVault.ORIGINAL_TITLE));
            movieDataObjectMap.put(KeyVault.ORIGINAL_LANGUAGE, jsonObject.getString(KeyVault.ORIGINAL_LANGUAGE));
            movieDataObjectMap.put(KeyVault.TITLE, jsonObject.getString(KeyVault.TITLE));
            movieDataObjectMap.put(KeyVault.BACKDROP_PATH, jsonObject.getString(KeyVault.BACKDROP_PATH));
            movieDataObjectMap.put(KeyVault.POPULARITY, jsonObject.getString(KeyVault.POPULARITY));
            movieDataObjectMap.put(KeyVault.VOTE_COUNT, jsonObject.getString(KeyVault.VOTE_COUNT));
            movieDataObjectMap.put(KeyVault.VIDEO, jsonObject.getString(KeyVault.VIDEO));
            movieDataObjectMap.put(KeyVault.VOTE_AVERAGE, jsonObject.getString(KeyVault.VOTE_AVERAGE));

            return movieDataObjectMap;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }




}
