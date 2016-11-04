package net.estebanrodriguez.apps.popularmovies.data_access;

import android.util.Log;

import net.estebanrodriguez.apps.popularmovies.model.MovieClip;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Spoooon on 11/2/2016.
 */

public class MovieClipFactory {

    public static final String LOG_TAG = MovieClipFactory.class.getSimpleName();


    public static List<MovieClip> buildMovieClipList(List<Map<String, String>> mapList) {
        List<MovieClip> movieClips = new ArrayList<>();

        for (Map<String, String> map : mapList)
            movieClips.add(buildMovieClip(map));
        return movieClips;
    }


    public static MovieClip buildMovieClip(Map<String, String> dataMap) {
        MovieClip movieClip = new MovieClip();
        Set<String> keyset = dataMap.keySet();

        for (String key : keyset) {
            if (dataMap.get(key) != null) {
                switch (key) {

                    case ConstantsVault.CLIP_ID: {
                        movieClip.setClipID(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.LANGUAGE_CODE_3116_1: {
                        movieClip.setLanguagecodeiso3166(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.LANGUAGE_CODE_639_1: {
                        movieClip.setLanguageCodeISO639(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.CLIP_KEY: {
                        movieClip.setKey(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.CLIP_NAME: {
                        movieClip.setName(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.CLIP_SITE: {
                        movieClip.setSite(dataMap.get(key));
                        break;
                    }
                    case ConstantsVault.CLIP_SIZE: {
                        movieClip.setSize(dataMap.get(key));
                    }

                    case ConstantsVault.CLIP_TYPE: {
                        movieClip.setClipType(dataMap.get(key));
                    }


                }

            }

        }
        Log.v(LOG_TAG, movieClip.getName());
        return movieClip;
    }
}
