package net.estebanrodriguez.apps.popularmovies.utility;

import android.util.Log;

import net.estebanrodriguez.apps.popularmovies.data_access.ConstantsVault;

/**
 * Created by Spoooon on 10/12/2016.
 */
public class ImageSizer {

    /**
     * The constant sDefaultImageSize.
     */
    public static String sDefaultImageSize = ConstantsVault.IMAGE_SIZE_RECOMMENDED_W185;
    /**
     * The constant LOG_TAG.
     */
    public static final String LOG_TAG = ImageSizer.class.getSimpleName();

    /**
     * Gets default image size.
     *
     * @return the default image size
     */
    public static String getDefaultImageSize() {
        return sDefaultImageSize;
    }

    /**
     * Sets default image size.
     *
     * @param width the width
     */
    public static void setDefaultImageSize(int width) {


        if (width > 1000) {
            sDefaultImageSize = ConstantsVault.IMAGE_SIZE_LARGE_W500;
        }

        Log.v(LOG_TAG, "Screen Width: " + width + " Default image size set to " + sDefaultImageSize);
    }

}
