package net.estebanrodriguez.apps.popularmovies.activities;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import net.estebanrodriguez.apps.popularmovies.R;
import net.estebanrodriguez.apps.popularmovies.data_access.ConstantsVault;
import net.estebanrodriguez.apps.popularmovies.data_access.FavoriteManager;
import net.estebanrodriguez.apps.popularmovies.data_access.NetworkChecker;
import net.estebanrodriguez.apps.popularmovies.fragments.DetailFragment;
import net.estebanrodriguez.apps.popularmovies.fragments.PreferencesFragment;
import net.estebanrodriguez.apps.popularmovies.utility.ImageSizer;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {

    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    private final static String VIS_FRAGMENT = "visible";

    private final static int GRIDVIEW_FRAGMENT = 0;
    private final static int SETTINGS_FRAGMENT = 1;
    private final static int DETAILS_FRAGMENT = 2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Initializes FavoriteManager
        FavoriteManager.getInstance(this.getContentResolver());

        //Dynamically set image sizes
        ImageSizer.setDefaultImageSize(getDisplaySizeWidth());

        //Confirms network is working.  If not, show error message

        if (NetworkChecker.isNetworkAvailable(this)) {
            setContentView(R.layout.activity_main);
        } else {
            setContentView(R.layout.activity_error);
            Toast toast = Toast.makeText(this, ConstantsVault.NETWORK_ERROR_MESSAGE, Toast.LENGTH_LONG);
            toast.show();
        }


        FragmentManager fragmentManager = getFragmentManager();
        DetailFragment detailFragment = (DetailFragment) fragmentManager.findFragmentById(R.id.fragment_detail);
        Fragment gridviewfragment = fragmentManager.findFragmentById(R.id.fragment_gridview);

        int visible_fragment = GRIDVIEW_FRAGMENT;



        if(savedInstanceState != null){
            String currentFragment = savedInstanceState.getString(VIS_FRAGMENT);
            if(currentFragment!= null && currentFragment.equals(getString(R.string.settings))){
                visible_fragment = SETTINGS_FRAGMENT;
            }else if(currentFragment!= null && currentFragment.equals(getString(R.string.detail_fragment))){
                visible_fragment = DETAILS_FRAGMENT;
            }
        }

        FragmentTransaction ft = fragmentManager.beginTransaction();
        switch (visible_fragment){

            case SETTINGS_FRAGMENT:
                ft.add(R.id.activity_main_frame_layout, new PreferencesFragment());
                ft.hide(gridviewfragment);
                ft.hide(detailFragment);
                ft.setBreadCrumbShortTitle(getString(R.string.settings));
                ft.commit();
                break;

            case DETAILS_FRAGMENT:
                ft.hide(gridviewfragment);
                ft.show(detailFragment);
                ft.setBreadCrumbShortTitle(getString(R.string.detail_fragment));
                ft.commit();
                break;

            case GRIDVIEW_FRAGMENT:
                ft.hide(detailFragment);
                ft.show(gridviewfragment);
                ft.setBreadCrumbShortTitle(getString(R.string.gridview_fragment));
                ft.commit();
                break;
        }

//
//
//        FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.hide(detailFragment);
//        ft.commit();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        if (NetworkChecker.isNetworkAvailable(this)) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.main_menu, menu);
        }
        return super.onCreateOptionsMenu(menu);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        FragmentManager fragmentManager = getFragmentManager();
        Fragment gridviewfragment = fragmentManager.findFragmentById(R.id.fragment_gridview);
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.add(R.id.activity_main_frame_layout, new PreferencesFragment());
        ft.hide(gridviewfragment);
        ft.addToBackStack(gridviewfragment.getTag());
        ft.setBreadCrumbShortTitle(R.string.settings);
        ft.commit();
        return super.onOptionsItemSelected(item);

    }

    private int getDisplaySizeWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager.getBackStackEntryCount() > 0){
            int index = fragmentManager.getBackStackEntryCount();
            FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(index - 1);
            String currentFragment = backStackEntry.getBreadCrumbShortTitle().toString();
            Log.v(LOG_TAG, "Backstack entry: " + currentFragment);
            outState.putString(VIS_FRAGMENT, currentFragment);
        }



    }
}
