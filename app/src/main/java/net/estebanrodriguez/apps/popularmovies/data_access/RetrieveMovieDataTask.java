package net.estebanrodriguez.apps.popularmovies.data_access;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import net.estebanrodriguez.apps.popularmovies.BuildConfig;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


/**
 * Created by Spoooon on 10/9/2016.
 */

public class RetrieveMovieDataTask extends AsyncTask<Void, Void, String> {

    URL mURL;
    private final String LOG_TAG = RetrieveMovieDataTask.class.getSimpleName();

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        //Build URL
        final String BASE_URL = "http://api.themoviedb.org/3/movie/popular?";
        final String API_PARAM = "api_key";

        Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                .appendQueryParameter(API_PARAM, BuildConfig.THE_MOVIE_DB_API_KEY)
                .build();

        try {
            mURL = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }


    }

    @Override
    protected String doInBackground(Void... params) {

        String movieData = null;
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            urlConnection = (HttpURLConnection) mURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null){
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));



            String line;
            while ((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0){
                return null;
            }

            movieData = buffer.toString();
            Log.d(LOG_TAG, movieData);

        } catch (IOException e) {
            e.printStackTrace();
        }
         finally {
            if(urlConnection != null) {
                urlConnection.disconnect();
            }
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return movieData;
    }

}
