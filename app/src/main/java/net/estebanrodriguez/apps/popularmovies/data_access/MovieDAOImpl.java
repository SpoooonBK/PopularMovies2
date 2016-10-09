package net.estebanrodriguez.apps.popularmovies.data_access;

import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * Created by Spoooon on 10/9/2016.
 */

public class MovieDAOImpl implements MovieDAO {


    @Override
    public List<MovieItem> getAllMovies() {
        String movieData = null;



       RetrieveMovieDataTask task = new RetrieveMovieDataTask();
       task.execute();
        //Validation for task
        try {
            if(task.get() != null)
            movieData = task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }



        return null;
    }
}
