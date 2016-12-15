package net.estebanrodriguez.apps.popularmovies.data_access;

import android.database.Cursor;

import net.estebanrodriguez.apps.popularmovies.database.DatabaseContract;
import net.estebanrodriguez.apps.popularmovies.model.MovieClip;
import net.estebanrodriguez.apps.popularmovies.model.MovieDetail;
import net.estebanrodriguez.apps.popularmovies.model.MovieReview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Spoooon on 11/2/2016.
 */


public class MovieDetailFactory {

    public static final String LOG_TAG = MovieDetailFactory.class.getSimpleName();
    public static final int MOVIE_CLIP = 0;
    public static final int MOVIE_REVIEW = 1;

    public static List<MovieDetail> buildMovieDetails(List<Map<String, String>> mapList, Integer detail_type) {
        List<MovieDetail> movieDetails = new ArrayList<>();

        switch (detail_type){

            case MOVIE_CLIP:{
                for (Map<String, String> map : mapList)
                    movieDetails.add((buildMovieClip(map)));
                break;
            }
            case MOVIE_REVIEW: {
                for (Map<String, String> map : mapList)
                    movieDetails.add(buildMovieReview(map));
            }
        }

        return movieDetails;
    }

    public static List<MovieDetail> buildMovieDetails(Cursor cursor){
        List<MovieDetail> movieDetails = new ArrayList<>();

        String[] colNames = cursor.getColumnNames();


        int detailType;
        List<Map<String, String>> detailsList = new ArrayList<>();

        cursor.moveToFirst();
        while(cursor.moveToNext()){
            Map<String, String> dataMap = new HashMap<>();
            for(String column: colNames){
                int index = cursor.getColumnIndex(column);

                switch (column){

                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_MOVIE_ID:{
                        dataMap.put(ConstantsVault.DETAIL_ID, cursor.getString(index));
                    }

                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_LANGUAGE_ISO3166:{
                        dataMap.put(ConstantsVault.LANGUAGE_CODE_3116_1, cursor.getString(index));
                    }

                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_LANGUAGE_ISO639:{
                        dataMap.put(ConstantsVault.LANGUAGE_CODE_639_1, cursor.getString(index));
                    }
                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_URL_KEY:{
                        dataMap.put(ConstantsVault.CLIP_KEY, cursor.getString(index));
                    }
                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_NAME:{
                        dataMap.put(ConstantsVault.CLIP_NAME, cursor.getString(index));
                    }


                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_CLIP_SIZE:{
                        dataMap.put(ConstantsVault.CLIP_SIZE, cursor.getString(index));
                    }

                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_SITE:{
                        Integer size = cursor.getInt(cursor.getInt(index));
                        dataMap.put(ConstantsVault.CLIP_SITE, size.toString());
                    }

                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_CLIP_TYPE:{
                        dataMap.put(ConstantsVault.CLIP_TYPE, cursor.getString(cursor.getColumnIndex(column)));
                        detailType = MOVIE_CLIP;
                    }





                }


            }

            detailsList.add(dataMap);

        }

        if(detailType == MOVIE_CLIP){

        }



        return movieDetails;
    }




    public static List<MovieClip> buildMovieClipList(List<Map<String, String>> mapList){
        List<MovieClip> movieClips = new ArrayList<>();
        for (Map<String, String> map : mapList){
            movieClips.add(buildMovieClip(map));
        }
       return movieClips;
    }

    public static List<MovieReview> buildMovieReviewList(List<Map<String, String>> mapList) {
        List<MovieReview> movieReviews = new ArrayList<>();
        for(Map<String, String> map : mapList ){
            movieReviews.add(buildMovieReview(map));
        }
        return movieReviews;
    }

    private static MovieReview buildMovieReview(Map<String, String> map) {
        MovieReview movieReview = new MovieReview();
        Set<String> keyset = map.keySet();

        for (String key : keyset) {
            if (map.get(key) != null) {
                switch (key) {

                    case ConstantsVault.DETAIL_ID:{
                        movieReview.setId(map.get(key));
                        break;
                    }
                    case ConstantsVault.DETAIL_AUTHOR:{
                        movieReview.setAuthor(map.get(key));
                        break;
                    }
                    case ConstantsVault.DETAIL_CONTENT:{
                        movieReview.setContent(map.get(key));
                        break;
                    }
                    case ConstantsVault.DETAIL_URL:{
                        movieReview.setUrl((map.get(key)));
                    }

                }
                }
                }

        return movieReview;
    }


    private static MovieClip buildMovieClip(Map<String, String> map) {
        MovieClip movieClip = new MovieClip();
        Set<String> keyset = map.keySet();

        for (String key : keyset) {
            if (map.get(key) != null) {
                switch (key) {

                    case ConstantsVault.DETAIL_ID: {
                        movieClip.setClipID(map.get(key));
                        break;
                    }
                    case ConstantsVault.LANGUAGE_CODE_3116_1: {
                        movieClip.setLanguagecodeiso3166(map.get(key));
                        break;
                    }
                    case ConstantsVault.LANGUAGE_CODE_639_1: {
                        movieClip.setLanguageCodeISO639(map.get(key));
                        break;
                    }
                    case ConstantsVault.CLIP_KEY: {
                        movieClip.setKey(map.get(key));
                        break;
                    }
                    case ConstantsVault.CLIP_NAME: {
                        movieClip.setName(map.get(key));
                        break;
                    }
                    case ConstantsVault.CLIP_SITE: {
                        movieClip.setSite(map.get(key));
                        break;
                    }
                    case ConstantsVault.CLIP_SIZE: {
                        movieClip.setSize(map.get(key));
                    }

                    case ConstantsVault.CLIP_TYPE: {
                        movieClip.setClipType(map.get(key));
                    }


                }

            }

        }
        return movieClip;
    }


}
