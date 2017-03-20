package net.estebanrodriguez.apps.popularmovies.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Spoooon on 10/27/2016.
 * This is the Activity for the splash page.
 */
public class SplashActivity extends AppCompatActivity {
    //The Splash Activity runs while mainActivity is loading to help smooth the starting aesthetics
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
