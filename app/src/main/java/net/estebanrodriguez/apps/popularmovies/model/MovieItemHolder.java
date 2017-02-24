package net.estebanrodriguez.apps.popularmovies.model;

/**
 * Created by spoooon on 2/24/17.
 */

public class MovieItemHolder {

    private static MovieItem sMovieItem;

    private MovieItemHolder(){}

    public static MovieItem getMovieItem() {
        return sMovieItem;
    }

    public static void setMovieItem(MovieItem movieItem) {
        sMovieItem = movieItem;
    }

    public static boolean hasMovieItem(){
        if(sMovieItem != null){
            return true;
        } else return false;
    }
}

