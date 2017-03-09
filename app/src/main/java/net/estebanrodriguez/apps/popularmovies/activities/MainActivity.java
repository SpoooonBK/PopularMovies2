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
import net.estebanrodriguez.apps.popularmovies.utility.FragmentStateHolder;
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



    private PreferencesFragment mPreferencesFragment = new PreferencesFragment();
    private Fragment mGridFragment;
    private Fragment mDetailFragment;
    private FragmentManager mFragmentManager = getFragmentManager();




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


        mDetailFragment = mFragmentManager.findFragmentById(R.id.fragment_detail);
        mGridFragment = mFragmentManager.findFragmentById(R.id.fragment_gridview);


        int visible_fragment = GRIDVIEW_FRAGMENT;


        if(savedInstanceState != null){
            restoreBackStack();
            String currentFragment = savedInstanceState.getString(VIS_FRAGMENT);
            if(currentFragment!= null && currentFragment.equals(FragmentStateHolder.SETTINGS)){
                visible_fragment = SETTINGS_FRAGMENT;
            }else if(currentFragment!= null && currentFragment.equals(getString(R.string.detail_fragment))){
                visible_fragment = DETAILS_FRAGMENT;
            }
        }

        FragmentTransaction ft = mFragmentManager.beginTransaction();
        switch (visible_fragment){

            case SETTINGS_FRAGMENT:
                ft.show(mPreferencesFragment);
                ft.hide(mGridFragment);
                ft.hide(mDetailFragment);
                ft.setBreadCrumbShortTitle(FragmentStateHolder.SETTINGS);
                ft.commit();
                Log.v(LOG_TAG, "Adding: Preferences; Hiding: Grid Fragment, Detail Fragment");
                break;

            case DETAILS_FRAGMENT:
                ft.detach(mPreferencesFragment);
                ft.hide(mGridFragment);
                ft.show(mDetailFragment);
                ft.setBreadCrumbShortTitle(FragmentStateHolder.DETAILS);
                ft.commit();
                Log.v(LOG_TAG, "Showing: Detail Fragment;  Hiding: Grid Fragment; Detach: Preferences;");
                break;

            case GRIDVIEW_FRAGMENT:
                FragmentStateHolder.clear();
                ft.add(R.id.activity_main_frame_layout, mPreferencesFragment);
                ft.detach(mPreferencesFragment);
                ft.hide(mDetailFragment);
                ft.show(mGridFragment);
                ft.setBreadCrumbShortTitle(FragmentStateHolder.GRIDVIEW);
                ft.commit();
                Log.v(LOG_TAG, "Showing: Grid Fragment; Adding: Preferences; Hiding: Detail; Detach: Preferences;");
                break;
        }

//
//
//        FragmentTransaction ft = fragmentManager.beginTransaction();
//        ft.hide(mDetailFragment);
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
        displaySettings();
        return super.onOptionsItemSelected(item);
    }

    private void displaySettings(){

        FragmentStateHolder.saveState(getCurrentFragmentName(), FragmentStateHolder.SETTINGS);

        FragmentTransaction ft = mFragmentManager.beginTransaction();

        String currentFragment = getCurrentFragmentName();

        if(currentFragment.equals(FragmentStateHolder.GRIDVIEW)){


            ft.attach(mPreferencesFragment);
            ft.show(mPreferencesFragment);
            ft.hide(mGridFragment);
            ft.addToBackStack("Settings from " + getCurrentFragmentName());
            ft.setBreadCrumbShortTitle(FragmentStateHolder.SETTINGS);
            ft.commit();
            Log.v(LOG_TAG, "Showing: Preferences; Hiding: Grid Fragment; Attach: Preferences");
        }else if(currentFragment.equals(FragmentStateHolder.DETAILS)){
            ft.attach(mPreferencesFragment);
            ft.show(mPreferencesFragment);
            ft.hide(mDetailFragment);
            ft.addToBackStack("Settings from " + getCurrentFragmentName());
            ft.setBreadCrumbShortTitle(FragmentStateHolder.SETTINGS);
            ft.commit();
            Log.v(LOG_TAG, "Showing: Preferences; Hiding: Detail Fragment; Attach: Preferences");
        }

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


            outState.putString(VIS_FRAGMENT, getCurrentFragmentName());

    }

    private String getCurrentFragmentName(){

        FragmentManager fragmentManager = getFragmentManager();
        if(fragmentManager.getBackStackEntryCount() > 0) {
            int index = fragmentManager.getBackStackEntryCount();
            FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(index - 1);
            String currentFragment = backStackEntry.getBreadCrumbShortTitle().toString();
            Log.v(LOG_TAG, "Backstack entry: " + currentFragment);
            return currentFragment;
        } else return "gridview";
    }



    private void restoreBackStack(){

        if(FragmentStateHolder.hasState()){
            String backstackFragment = FragmentStateHolder.getBackstack();
            String currentFragment = FragmentStateHolder.getCurrent();

            int count = mFragmentManager.getBackStackEntryCount();
            for(int i = 0; i < count; i++){
                mFragmentManager.popBackStack();
                Log.v(LOG_TAG, i + ": Back Stack popped");
            }

        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.v(LOG_TAG, "Main Activity Destroyed");
    }
}
