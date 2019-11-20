package com.example.barterapp.utility;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

/**
 * The Date utility.
 */
public class DateUtility {

    private static final String                DATE_FORMAT                     = "EEE MMM dd hh:mm:ss yyyy ";

    /**
     * Get date from timestamp by format string.
     *
     * @param timestamp the timestamp
     * @return the string
     */
    public static String getDateFromTimestampByFormat(long timestamp){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        return DateFormat.format(DATE_FORMAT, cal).toString();
    }
}
