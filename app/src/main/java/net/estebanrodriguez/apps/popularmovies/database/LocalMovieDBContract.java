package net.estebanrodriguez.apps.popularmovies.database;

import android.provider.BaseColumns;

/**
 * Created by Spoooon on 11/17/2016.
 */

public final class LocalMovieDBContract {

    private LocalMovieDBContract() {}

    public static class LocalMovieDBEntry implements BaseColumns {

        public static final String TABLE_NAME = "movies";
        public static final String COLUMN_NAME_ID= "id";

    }
}
