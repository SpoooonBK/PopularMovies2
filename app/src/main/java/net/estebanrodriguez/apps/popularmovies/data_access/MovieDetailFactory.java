package net.estebanrodriguez.apps.popularmovies.data_access;

import android.database.Cursor;

import net.estebanrodriguez.apps.popularmovies.database.DatabaseContract;
import net.estebanrodriguez.apps.popularmovies.model.MovieClip;
import net.estebanrodriguez.apps.popularmovies.model.MovieReview;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Builds MovieDetail objects -reviews and clips
 */


public class MovieDetailFactory {

    /**
     * The constant LOG_TAG.
     */
    public static final String LOG_TAG = MovieDetailFactory.class.getSimpleName();
    /**
     * The constant MOVIE_CLIP.
     */
    public static final int MOVIE_CLIP = 0;
    /**
     * The constant MOVIE_REVIEW.
     */
    public static final int MOVIE_REVIEW = 1;


    /**
     * Build movie clip list list.
     *
     * @param cursor the cursor
     * @return the list
     */
    public static List<MovieClip> buildMovieClipList(Cursor cursor) {

        String[] colNames = cursor.getColumnNames();
        List<Map<String, String>> detailsList = new ArrayList<>();

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Map<String, String> dataMap = new HashMap<>();
            for (String column : colNames) {
                int index = cursor.getColumnIndex(column);

                switch (column) {

                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_MOVIE_ID: {
                        dataMap.put(ConstantsVault.DETAIL_ID, cursor.getString(index));
                        break;
                    }

                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_LANGUAGE_ISO3166: {
                        dataMap.put(ConstantsVault.LANGUAGE_CODE_3116_1, cursor.getString(index));
                        break;
                    }

                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_LANGUAGE_ISO639: {
                        dataMap.put(ConstantsVault.LANGUAGE_CODE_639_1, cursor.getString(index));
                        break;
                    }
                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_URL_KEY: {
                        dataMap.put(ConstantsVault.CLIP_KEY, cursor.getString(index));
                        break;
                    }
                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_NAME: {
                        dataMap.put(ConstantsVault.CLIP_NAME, cursor.getString(index));
                        break;
                    }


                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_CLIP_SIZE: {
                        dataMap.put(ConstantsVault.CLIP_SIZE, cursor.getString(index));
                        break;
                    }

                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_SITE: {
                        Integer size = cursor.getInt(cursor.getInt(index));
                        dataMap.put(ConstantsVault.CLIP_SITE, size.toString());
                        break;
                    }

                    case DatabaseContract.MovieClipEntries.COLUMN_NAME_CLIP_TYPE: {
                        dataMap.put(ConstantsVault.CLIP_TYPE, cursor.getString(cursor.getColumnIndex(column)));
                        break;
                    }
                }
            }

            detailsList.add(dataMap);

        }

        return buildMovieClipList(detailsList);

    }


    /**
     * Build movie clip list list.
     *
     * @param mapList the map list
     * @return the list
     */
    public static List<MovieClip> buildMovieClipList(List<Map<String, String>> mapList) {
        List<MovieClip> movieClips = new ArrayList<>();
        for (Map<String, String> map : mapList) {
            movieClips.add(buildMovieClip(map));
        }
        return movieClips;
    }


    /**
     * Build movie review list list.
     *
     * @param cursor the cursor
     * @return the list
     */
    public static List<MovieReview> buildMovieReviewList(Cursor cursor) {
        String[] colNames = cursor.getColumnNames();
        List<Map<String, String>> detailsList = new ArrayList<>();

        cursor.moveToFirst();
        while (cursor.moveToNext()) {
            Map<String, String> dataMap = new HashMap<>();
            for (String column : colNames) {
                int index = cursor.getColumnIndex(column);

                switch (column) {

                    case DatabaseContract.MovieReviewEntries.COLUMN_NAME_MOVIE_ID: {
                        dataMap.put(ConstantsVault.DETAIL_ID, cursor.getString(index));
                        break;
                    }

                    case DatabaseContract.MovieReviewEntries.COLUMN_NAME_AUTHOR: {
                        dataMap.put(ConstantsVault.DETAIL_AUTHOR, cursor.getString(index));
                        break;
                    }

                    case DatabaseContract.MovieReviewEntries.COLUMN_NAME_CONTENT: {
                        dataMap.put(ConstantsVault.DETAIL_CONTENT, cursor.getString(index));
                        break;
                    }
                    case DatabaseContract.MovieReviewEntries.COLUMN_NAME_REVIEW_URL: {
                        dataMap.put(ConstantsVault.DETAIL_URL, cursor.getString(index));
                        break;
                    }

                }
            }

            detailsList.add(dataMap);

        }

        return buildMovieReviewList(detailsList);
    }


    /**
     * Build movie review list list.
     *
     * @param mapList the map list
     * @return the list
     */
    public static List<MovieReview> buildMovieReviewList(List<Map<String, String>> mapList) {
        List<MovieReview> movieReviews = new ArrayList<>();
        for (Map<String, String> map : mapList) {
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

                    case ConstantsVault.DETAIL_ID: {
                        movieReview.setId(map.get(key));
                        break;
                    }
                    case ConstantsVault.DETAIL_AUTHOR: {
                        movieReview.setAuthor(map.get(key));
                        break;
                    }
                    case ConstantsVault.DETAIL_CONTENT: {
                        movieReview.setContent(map.get(key));
                        break;
                    }
                    case ConstantsVault.DETAIL_URL: {
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
