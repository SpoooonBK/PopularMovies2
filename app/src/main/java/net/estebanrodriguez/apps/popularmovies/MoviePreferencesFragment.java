package net.estebanrodriguez.apps.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceFragment;

import net.estebanrodriguez.apps.popularmovies.data_access.MovieDAOImpl;

/**
 * Created by Spoooon on 10/19/2016.
 */
public class MoviePreferencesFragment extends PreferenceFragment implements SharedPreferences.OnSharedPreferenceChangeListener{


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.preferences);
    }


    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

        if(key.equals(getString(R.string.sort_preference_key))){

            MovieDAOImpl.NotifyPreferenceChange();
        }


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
