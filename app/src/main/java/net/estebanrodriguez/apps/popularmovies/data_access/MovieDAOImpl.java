package net.estebanrodriguez.apps.popularmovies.data_access;

import net.estebanrodriguez.apps.popularmovies.model.MovieItem;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import static net.estebanrodriguez.apps.popularmovies.data_access.MovieDataParser.parseJsonMovieDataString;

/**
 * Created by Spoooon on 10/9/2016.
 */

public class MovieDAOImpl implements MovieDAO {


    private MovieDAOImpl(){

    }
    private static class MovieDAOHelper{
        private static final MovieDAOImpl INSTANCE = new MovieDAOImpl();
    }

    public static MovieDAOImpl getInstance(){
        return MovieDAOHelper.INSTANCE;
    }

    @Override
    public List<MovieItem> getAllMovies() {



       RetrieveMovieDataTask task = new RetrieveMovieDataTask();
       task.execute();
        //Validation for task
        try {
            if(task.get() != null){

                List<Map<String, String>> mapList = MovieDataParser.parseJsonMovieDataString(task.get());
                return MovieItemFactory.buildMovieList(mapList);
            }


        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        return null;
    }
}
