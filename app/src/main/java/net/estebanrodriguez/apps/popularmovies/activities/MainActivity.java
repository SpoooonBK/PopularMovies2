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
import net.estebanrodriguez.apps.popularmovies.utility.NetworkChecker;
import net.estebanrodriguez.apps.popularmovies.fragments.PreferencesFragment;
import net.estebanrodriguez.apps.popularmovies.utility.FragmentStateHolder;
import net.estebanrodriguez.apps.popularmovies.utility.ImageSizer;

/**
 * The type Main activity.
 */
public class MainActivity extends AppCompatActivity {


    private final static String LOG_TAG = MainActivity.class.getSimpleName();
    private final static String VISIBLE_FRAGMENT = "visible";



    private Fragment mGridFragment;
    private Fragment mDetailFragment;
    private FragmentManager mFragmentManager = getFragmentManager();



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FavoriteManager.getInstance(this.getContentResolver());

        //Checks display widith to dynamically set image sizes
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

        displayGridView();

        if (savedInstanceState != null) {

            restoreVisibleFragment(savedInstanceState);

        }

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
        FragmentStateHolder.saveState(getCurrentFragmentName(), FragmentStateHolder.SETTINGS);
        displaySettings();
        return super.onOptionsItemSelected(item);
    }






    private int getDisplaySizeWidth() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }



    private String getCurrentFragmentName() {

        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager.getBackStackEntryCount() > 0) {
            int index = fragmentManager.getBackStackEntryCount();
            FragmentManager.BackStackEntry backStackEntry = fragmentManager.getBackStackEntryAt(index - 1);
            String currentFragment = backStackEntry.getBreadCrumbShortTitle().toString();
            Log.v(LOG_TAG, "Backstack entry: " + currentFragment);
            return currentFragment;
        } else return "gridview";
    }


    private void clearBackStack() {

        if (FragmentStateHolder.hasState()) {
            String backstackFragment = FragmentStateHolder.getRefferingFragmentName();
            String currentFragment = FragmentStateHolder.getCurrent();

            int count = mFragmentManager.getBackStackEntryCount();
            for (int i = 0; i < count; i++) {
                mFragmentManager.popBackStack();
                Log.v(LOG_TAG, i + ": Back Stack popped");
            }
            Log.v(LOG_TAG, "Backstack cleared");

        }

    }

    private void restoreVisibleFragment(Bundle savedInstanceState) {

        String visibleFragment = savedInstanceState.getString(VISIBLE_FRAGMENT);


        if (visibleFragment != null) {
            FragmentTransaction ft = mFragmentManager.beginTransaction();

            switch (visibleFragment) {

                case FragmentStateHolder.DETAILS: {
                    displayDetails();
                    break;
                }

                case FragmentStateHolder.SETTINGS: {
                        displaySettings();
                    break;
                }

                case FragmentStateHolder.GRIDVIEW: {
                    displayGridView();

                    break;
                }

                default: {

                }
            }


        }

    }

    public void displayGridView() {

        mFragmentManager.beginTransaction()
                .hide(mDetailFragment)
                .show(mGridFragment)
                .setBreadCrumbShortTitle(FragmentStateHolder.GRIDVIEW)
                .commit();
        Log.v(LOG_TAG, "Showing: Grid Fragment; Hiding: Detail; Detach: Preferences;");

    }

    public void displaySettings() {




        String currentFragment = getCurrentFragmentName();


        switch (currentFragment){

            case FragmentStateHolder.GRIDVIEW: {
                displaySettingsFromGridview();
                break;
            }
            case FragmentStateHolder.DETAILS: {
                displaySettingsFromDetails();

            }
            case FragmentStateHolder.SETTINGS: {

                String referringFragment = FragmentStateHolder.getRefferingFragmentName();
                if(referringFragment.equals(FragmentStateHolder.GRIDVIEW)){
                    mFragmentManager.popBackStack();
                    displaySettingsFromGridview();

                }else if(referringFragment.equals(FragmentStateHolder.DETAILS)){
                    mFragmentManager.popBackStack();
                    displayDetails();
                    displaySettingsFromDetails();
                }

            }

        }
    }



    public void displayDetails() {
        mFragmentManager.beginTransaction()
                .hide(mGridFragment)
                .show(mDetailFragment)
                .setBreadCrumbShortTitle(FragmentStateHolder.DETAILS)
                .commit();
        Log.v(LOG_TAG, "Showing: Detail Fragment;  Hiding: Grid Fragment");
    }

    private void displaySettingsFromGridview(){

        PreferencesFragment settings = new PreferencesFragment();
        mFragmentManager.beginTransaction()
                .hide(mGridFragment)
                .add(R.id.activity_main_frame_layout, settings)
                .show(settings)
                .addToBackStack(null)
                .setBreadCrumbShortTitle(FragmentStateHolder.SETTINGS)
                .commit();

    }

    private void displaySettingsFromDetails(){
        PreferencesFragment settings = new PreferencesFragment();
        mFragmentManager.beginTransaction()
                .hide(mDetailFragment)
                .add(R.id.activity_main_frame_layout, settings)
                .show(settings)
                .addToBackStack(null)
                .setBreadCrumbShortTitle(FragmentStateHolder.SETTINGS)
                .commit();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        outState.putString(VISIBLE_FRAGMENT, getCurrentFragmentName());

    }





}
