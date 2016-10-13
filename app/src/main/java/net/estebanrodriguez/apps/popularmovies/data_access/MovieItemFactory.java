package net.estebanrodriguez.apps.popularmovies.data_access;

import android.util.Log;

import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Spoooon on 10/9/2016.
 */

public class MovieItemFactory {

    private static final String LOG_TAG = MovieItemFactory.class.getSimpleName();


/**
 * Method: buildMovieList(List<Map<String, String>> mapList
 * Builds an ArrayList of movies from maps of parsed Json data
  */

    public static List<MovieItem> buildMovieList(List<Map<String, String>> mapList){

        List<MovieItem> movieItems = new ArrayList<>();

        for(Map<String, String> map: mapList )
            movieItems.add(buildMovie(map));
        return movieItems;

    }


/** Method: MovieItem buildMovie(Map<String,String> dataMap)
 *  Uses a map of parsed json data to instantiate a single MovieItem
 */
    public static MovieItem buildMovie(Map<String,String> dataMap){
        //TODO Implement

        MovieItem movieItem = new MovieItem();
        Set<String> keyset = dataMap.keySet();

        for (String key: keyset){
            if(dataMap.get(key) != null){
                switch (key){

                    case ConstantsVault.POSTER_PATH:{
                        movieItem.setPosterPath(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.ADULT:{
                        movieItem.setAdult(Boolean.parseBoolean(dataMap.get(key)));
                        break;
                    }
                    case ConstantsVault.RELEASE_DATE:{
                        movieItem.setReleaseDate(Date.valueOf(dataMap.get(key)));
                        break;
                    }
                    case ConstantsVault.GENRE_IDS:{
                        //TODO implement
                        break;
                    }
                    case ConstantsVault.ID:{
                        movieItem.setID(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.ORIGINAL_TITLE:{
                        movieItem.setOriginalTitle(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.ORIGINAL_LANGUAGE:{
                        movieItem.setOriginalLanguage(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.TITLE:{
                        movieItem.setTitle(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.BACKDROP_PATH:{
                        movieItem.setBackdropPath(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.POPULARITY:{
                        movieItem.setPopularity(Double.parseDouble(dataMap.get(key)));
                        break;
                    }
                    case ConstantsVault.VOTE_COUNT:{
                        movieItem.setVoteCount(Double.parseDouble(dataMap.get(key)));
                        break;
                    }
                    case ConstantsVault.VIDEO:{
                        movieItem.setVideo(Boolean.parseBoolean(dataMap.get(key)));
                        break;
                    }
                    case ConstantsVault.VOTE_AVERAGE:{
                        movieItem.setVoteAverage(Double.parseDouble(dataMap.get(key)));
                        break;
                    }
                }


            }

        }
        Log.v(LOG_TAG, "Created movieItem: " + movieItem.toString());

        return movieItem;
    }


}
