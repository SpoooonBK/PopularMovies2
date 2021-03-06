package net.estebanrodriguez.apps.popularmovies.data_access;

import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.util.List;

/**
 * Created by Spoooon on 10/9/2016.
 */
public interface MovieDAO {

    /**
     * Gets all movies.
     *
     * @return the all movies
     */
    List<MovieItem> getAllMovies();


}
