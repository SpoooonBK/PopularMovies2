package net.estebanrodriguez.apps.popularmovies.utility;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by spoooon on 12/14/16.
 */

public class DateParser {

    public static String parseDate(Date date){


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(date);

    }
}
