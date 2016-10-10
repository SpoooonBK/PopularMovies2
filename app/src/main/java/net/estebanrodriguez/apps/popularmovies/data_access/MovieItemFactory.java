package net.estebanrodriguez.apps.popularmovies.data_access;

import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.MissingFormatArgumentException;
import java.util.Set;

/**
 * Created by Spoooon on 10/9/2016.
 */

public class MovieItemFactory {


    public static List<MovieItem> buildMovieList(List<Map<String, String>> mapList){

        List<MovieItem> movieItems = new ArrayList<>();

        for(Map<String, String> map: mapList )
            movieItems.add(buildMovie(map));
        return null;

    }

    public static MovieItem buildMovie(Map<String,String> dataMap){
        //TODO Implement

        MovieItem movieItem = new MovieItem();
        Set<String> keyset = dataMap.keySet();

        for (String key: keyset){
            if(dataMap.get(key) != null){
                switch (key){

                    case KeyVault.POSTER_PATH:{
                        movieItem.setPosterPath(dataMap.get(key));
                    }
                    case KeyVault.ADULT:{
                        movieItem.setAdult(Boolean.parseBoolean(dataMap.get(key)));
                    }
                    case KeyVault.RELEASE_DATE:{
                        movieItem.setReleaseDate(Date.valueOf(dataMap.get(key)));
                    }
                    case KeyVault.GENRE_IDS:{
                        //TODO implement
                    }
                    case KeyVault.ID:{
                        movieItem.setID(dataMap.get(key));
                    }
                    case KeyVault.ORIGINAL_TITLE:{
                        movieItem.setOriginalTitle(dataMap.get(key));
                    }
                    case KeyVault.ORIGINAL_LANGUAGE:{
                        movieItem.setOriginalLanguage(dataMap.get(key));
                    }
                    case KeyVault.TITLE:{
                        movieItem.setTitle(dataMap.get(key));
                    }
                    case KeyVault.BACKDROP_PATH:{
                        movieItem.setBackdropPath(dataMap.get(key));
                    }
                    case KeyVault.POPULARITY:{
                        movieItem.setPopularity(Double.parseDouble(dataMap.get(key)));
                    }
                    case KeyVault.VOTE_COUNT:{
                        movieItem.setVoteCount(Double.parseDouble(dataMap.get(key)));
                    }
                    case KeyVault.VIDEO:{
                        movieItem.setVideo(Boolean.parseBoolean(dataMap.get(key)));
                    }
                    case KeyVault.VOTE_AVERAGE:{
                        movieItem.setVoteAverage(Double.parseDouble(dataMap.get(key)));
                    }
                }


            }

        }


        if(dataMap.get(KeyVault.POSTER_PATH) != null){


        }
        if(dataMap.get(KeyVault.ADULT) != null){
            movieItem.setAdult(Boolean.parseBoolean(dataMap.get(KeyVault.ADULT)));
        }
        if (dataMap.get(KeyVault.OVERVIEW) != null){
            movieItem.setOverview(dataMap.get(KeyVault.OVERVIEW));
        }








        return null;
    }


}
