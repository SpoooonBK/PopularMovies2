package net.estebanrodriguez.apps.popularmovies.factories;

import android.database.Cursor;

import net.estebanrodriguez.apps.popularmovies.external_data_access.ConstantsVault;
import net.estebanrodriguez.apps.popularmovies.local_database.DatabaseContract;
import net.estebanrodriguez.apps.popularmovies.local_database.FavoriteManager;
import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Builds MovieItems
 */
public class MovieItemFactory {

    private static final String LOG_TAG = MovieItemFactory.class.getSimpleName();


    /**
     * Method: buildMovieList(List<Map<String, String>> mapList)
     * Builds an ArrayList of movies from maps of parsed Json data
     *
     * @param mapList the map list
     * @return List<MovieItem> list
     */
    public static List<MovieItem> buildMovieList(List<Map<String, String>> mapList) {

        List<MovieItem> movieItems = new ArrayList<>();

        for (Map<String, String> map : mapList)
            movieItems.add(buildMovie(map));
        return movieItems;

    }

    /**
     * Method: Builds movie list from cursor data
     *
     * @param cursor the cursor
     * @return List<MovieItems> list
     */
    public static List<MovieItem> buildMovieList(Cursor cursor) {
        List<MovieItem> movieItems = new ArrayList<>();

        Map<String, String> dataMap = new HashMap<>();
        String[] colNames = cursor.getColumnNames();



        while (cursor.moveToNext()) {
            for (String column : colNames) {


                switch (column) {

                    case DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_POSTER_PATH: {
                        dataMap.put(ConstantsVault.POSTER_PATH, cursor.getString(cursor.getColumnIndex(column)));
                        break;
                    }
                    case DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_ADULT: {
                        dataMap.put(ConstantsVault.ADULT, cursor.getString(cursor.getColumnIndex(column)));
                        break;
                    }
                    case DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_RELEASE_DATE: {
                        dataMap.put(ConstantsVault.RELEASE_DATE, cursor.getString(cursor.getColumnIndex(column)));
                        break;
                    }
                    case DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_MOVIE_ID: {
                        Integer id = cursor.getInt(cursor.getColumnIndex(column));
                        dataMap.put(ConstantsVault.ID, id.toString());
                        break;
                    }
                    case DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_ORGINAL_TITLE: {
                        dataMap.put(ConstantsVault.ORIGINAL_TITLE, cursor.getString(cursor.getColumnIndex(column)));
                        break;
                    }
                    case DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_ORIGINAL_LANGUAGE:{
                        dataMap.put(ConstantsVault.ORIGINAL_LANGUAGE, cursor.getString(cursor.getColumnIndex(column)));
                        break;
                    }
                    case DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_TITLE: {
                        dataMap.put(ConstantsVault.TITLE, cursor.getString(cursor.getColumnIndex(column)));
                        break;
                    }
                    case DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_BACKDROP_PATH: {
                        dataMap.put(ConstantsVault.BACKDROP_PATH, cursor.getString(cursor.getColumnIndex(column)));
                        break;
                    }
                    case DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_POPULARITY: {
                        Double popularity = cursor.getDouble(cursor.getColumnIndex(column));
                        dataMap.put(ConstantsVault.POPULARITY, popularity.toString());
                        break;
                    }
                    case DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_VOTE_COUNT: {
                        Double voteCount = cursor.getDouble(cursor.getColumnIndex(column));
                        dataMap.put(ConstantsVault.VOTE_COUNT, voteCount.toString());
                        break;
                    }
                    case DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_VIDEO: {
                        dataMap.put(ConstantsVault.VIDEO, cursor.getString(cursor.getColumnIndex(column)));
                        break;
                    }
                    case DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_VOTE_AVERAGE: {
                        Double voteAverage = cursor.getDouble(cursor.getColumnIndex(column));
                        dataMap.put(ConstantsVault.ORIGINAL_TITLE, voteAverage.toString());
                        break;
                    }
                    case DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_OVERVIEW: {
                        dataMap.put(ConstantsVault.OVERVIEW, cursor.getString(cursor.getColumnIndex(column)));
                        break;
                    }
                    case DatabaseContract.BasicMovieDetailEntries.COLUMN_NAME_FAVORITED: {
                        dataMap.put(ConstantsVault.FAVORITED, cursor.getString(cursor.getColumnIndex(column)));
                        break;
                    }


                }
            }
            movieItems.add(buildMovie(dataMap));
        }
        if(cursor != null && !cursor.isClosed()){
            cursor.close();
        }

        return movieItems;
    }


    /**
     * Method: MovieItem buildMovie(Map<String,String> dataMap)
     * Uses a map of parsed json data to instantiate a single MovieItem
     *
     * @param dataMap the data map
     * @return MovieItem movie item
     */
    public static MovieItem buildMovie(Map<String, String> dataMap) {


        MovieItem movieItem = new MovieItem();
        Set<String> keyset = dataMap.keySet();

        for (String key : keyset) {
            if (dataMap.get(key) != null) {
                switch (key) {

                    case ConstantsVault.POSTER_PATH: {
                        movieItem.setPosterPath(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.ADULT: {
                        try{
                            int value = Integer.parseInt(dataMap.get(key));
                            if(value == 1){
                                movieItem.setAdult(true);
                            }else if(value == 0){
                                movieItem.setAdult(false);
                            }
                        }catch (Exception e){
                            movieItem.setAdult(Boolean.parseBoolean(dataMap.get(key)));
                        }
                        break;
                    }
                    case ConstantsVault.RELEASE_DATE: {
                        movieItem.setReleaseDate((dataMap.get(key)));
                        break;
                    }
                    case ConstantsVault.GENRE_IDS: {
                        //TODO implement
                        break;
                    }
                    case ConstantsVault.ID: {
                        movieItem.setID(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.ORIGINAL_TITLE: {
                        movieItem.setOriginalTitle(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.ORIGINAL_LANGUAGE: {
                        movieItem.setOriginalLanguage(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.TITLE: {
                        movieItem.setTitle(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.BACKDROP_PATH: {
                        movieItem.setBackdropPath(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.POPULARITY: {
                        movieItem.setPopularity(Double.parseDouble(dataMap.get(key)));
                        break;
                    }
                    case ConstantsVault.VOTE_COUNT: {
                        movieItem.setVoteCount(Double.parseDouble(dataMap.get(key)));
                        break;
                    }
                    case ConstantsVault.VIDEO: {
                        try{
                            int value = Integer.parseInt(dataMap.get(key));
                            if(value == 1){
                                movieItem.setVideo(true);
                            }else if(value == 0){
                                movieItem.setVideo(false);
                            }
                        }catch (Exception e){
                            movieItem.setVideo(Boolean.parseBoolean(dataMap.get(key)));
                        }
                        break;
                    }
                    case ConstantsVault.VOTE_AVERAGE: {
                        movieItem.setVoteAverage(Double.parseDouble(dataMap.get(key)));
                        break;
                    }
                    case ConstantsVault.OVERVIEW: {
                        movieItem.setOverview(dataMap.get(key));
                        break;
                    }


                }



            }

        }

        boolean isFavorite = FavoriteManager.getInstance().isFavorited(movieItem.getID());
        movieItem.setFavorited(isFavorite);



        return movieItem;
    }


}
