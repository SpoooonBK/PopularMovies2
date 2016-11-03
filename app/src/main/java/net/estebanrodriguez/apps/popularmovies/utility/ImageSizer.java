package net.estebanrodriguez.apps.popularmovies.utility;

import android.util.Log;

import net.estebanrodriguez.apps.popularmovies.data_access.ConstantsVault;

/**
 * Created by Spoooon on 10/12/2016.
 */

public class ImageSizer {

    public static String sDefaultImageSize = ConstantsVault.IMAGE_SIZE_RECOMMENDED_W185;
    public static final String LOG_TAG = ImageSizer.class.getSimpleName();

    public static String getDefaultImageSize() {
        return sDefaultImageSize;
    }

    public static void setDefaultImageSize(int width) {


        if (width > 1000) {
            sDefaultImageSize = ConstantsVault.IMAGE_SIZE_LARGE_W500;
        }

        Log.v(LOG_TAG, "Screen Width: " + width + " Default image size set to " + sDefaultImageSize);
    }

}
