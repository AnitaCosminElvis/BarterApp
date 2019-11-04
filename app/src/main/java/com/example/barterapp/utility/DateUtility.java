package com.example.barterapp.utility;

import android.text.format.DateFormat;

import java.util.Calendar;
import java.util.Locale;

public class DateUtility {

    public static String getDateFromTimestampByFormat(long timestamp, String format){
        Calendar cal = Calendar.getInstance(Locale.ENGLISH);
        cal.setTimeInMillis(timestamp);
        return DateFormat.format(format, cal).toString();
    }
}
