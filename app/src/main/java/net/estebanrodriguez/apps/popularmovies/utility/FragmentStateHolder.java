package net.estebanrodriguez.apps.popularmovies.utility;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by spoooon on 3/7/17.
 */

public class FragmentStateHolder {

    private FragmentStateHolder(){}

    public final static String GRIDVIEW = "gridview";
    public final static String DETAILS = "details";
    public final static String SETTINGS = "settings";

    private static Map<String, String> sFragmentNames = new HashMap<>(2);
    private static final String LOG_TAG = FragmentStateHolder.class.getSimpleName();


    private static final String BACKSTACK = "backstack";
    private static final String CURRENT = "current";

    public static void saveState(String backstackFragment, String currentFragment){
        sFragmentNames.clear();
        sFragmentNames.put(BACKSTACK, backstackFragment);
        sFragmentNames.put(CURRENT, currentFragment);
        Log.v(LOG_TAG, sFragmentNames.get(BACKSTACK) + " ----> " + sFragmentNames.get(CURRENT));
    }

    public static void clear(){
        sFragmentNames.clear();
    }

    public static String getReferringFragmentName(){
        return sFragmentNames.get(BACKSTACK);
    }

    public static String getCurrent(){
        return sFragmentNames.get(CURRENT);
    }

    public static boolean hasState(){
        return sFragmentNames.size() > 0;
    }

}