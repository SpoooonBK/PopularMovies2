package net.estebanrodriguez.apps.popularmovies;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Dynamically set image sizes
        ImageSizer.setDefaultImageSize(getDisplaySizeWidth());


        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        FragmentManager fragmentManager = getFragmentManager();

        Fragment gridviewfragment = fragmentManager.findFragmentById(R.id.fragment_gridview);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.activity_main, new MoviePreferencesFragment());
        ft.hide(gridviewfragment);
        ft.addToBackStack(gridviewfragment.getTag());
        ft.commit();
        return super.onOptionsItemSelected(item);
    }

    private int getDisplaySizeWidth(){
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

}
