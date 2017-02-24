package net.estebanrodriguez.apps.popularmovies.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.data_access.FavoriteManager;
import net.estebanrodriguez.apps.popularmovies.interfaces.listeners.FavoritesUpdatedListener;

/**
 * The type Detail activity.
 */
public class DetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_detail);
    }

}
