package net.estebanrodriguez.apps.popularmovies.data_access;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by Spoooon on 10/9/2016.
 */
public class MovieDataParser {

    /**
     * The constant LOG_TAG.
     */
    public static final String LOG_TAG = MovieDataParser.class.getSimpleName();

    /**
     * Parse json movie data string list.
     *
     * @param jsonData the json data
     * @return the list
     */
//Parses original json string data into a list of movie data maps
    public static List<Map<String, String>> parseJsonMovieDataString(String jsonData) {


        List<Map<String, String>> movieDataList = new ArrayList<>();

        try {

            JSONObject data = new JSONObject(jsonData);
            JSONArray jsonArray = data.getJSONArray(ConstantsVault.JSON_ARRAY_KEY);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject movieData = (JSONObject) jsonArray.get(i);
                movieDataList.add(parseJsonMovieDataObject(movieData));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return movieDataList;
    }

    /**
     * Parse json movie data object map.
     *
     * @param jsonObject the json object
     * @return the map
     */
//Parses a json movie data object into a movie data map
    public static Map<String, String> parseJsonMovieDataObject(JSONObject jsonObject) {
        Map<String, String> movieDataObjectMap = new HashMap<>();

        Iterator<String> iterator = jsonObject.keys();

        try {

            while (iterator.hasNext()) {
                String key = iterator.next();
                movieDataObjectMap.put(key, jsonObject.getString(key));
            }

            return movieDataObjectMap;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

}
