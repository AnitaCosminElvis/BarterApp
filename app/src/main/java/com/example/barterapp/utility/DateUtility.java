package com.example.barterapp.utility;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class DateUtility {

    private static final String                DATE_FORMAT                     = "EEE MMM dd hh:mm:ss yyyy ";

    public static String getDateFromTimestampByFormat(long timestamp){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        return DateFormat.format(DATE_FORMAT, cal).toString();
    }
}
