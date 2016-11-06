package net.estebanrodriguez.apps.popularmovies.data_access;

import android.util.Log;

import net.estebanrodriguez.apps.popularmovies.model.MovieClip;
import net.estebanrodriguez.apps.popularmovies.model.MovieDetail;
import net.estebanrodriguez.apps.popularmovies.model.MovieReview;

import java.util.ArrayList;
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
                        movieReview.setContent((map.get(key)));
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
