package net.estebanrodriguez.apps.popularmovies.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.data_access.MovieDAOImpl;

/**
 * Created by Spoooon on 10/19/2016.
 */
public class MoviePreferencesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener {

    private final String LOG_TAG = MoviePreferencesFragment.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        RecyclerViewGridFragment gridViewFragment = (RecyclerViewGridFragment) getFragmentManager().findFragmentById(R.id.fragment_gridview);

        MovieDAOImpl.NotifyPreferenceChange();
        gridViewFragment.notifyOnPreferenceChanged();

    }

    @Override
    public void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences().
                unregisterOnSharedPreferenceChangeListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }




}
