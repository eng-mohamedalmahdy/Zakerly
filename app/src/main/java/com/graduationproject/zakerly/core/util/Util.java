package com.graduationproject.zakerly.core.util;


import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Util {

    public static String getDateFromStamp(long timeStamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeStamp);
        String day = new DecimalFormat("00").format(c.get(Calendar.DAY_OF_MONTH));
        String month = new DecimalFormat("00").format(c.get(Calendar.MONTH));
        String year = new DecimalFormat("00").format(c.get(Calendar.YEAR));
        return String.format(Locale.getDefault(), "%s-%s-%s", day, month, year);

    }

    public static String getTimeFromStamp(long timeStamp) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(timeStamp);
        String hour = new DecimalFormat("00").format(c.get(Calendar.HOUR));
        String minute = new DecimalFormat("00").format(c.get(Calendar.MINUTE));
        String second = new DecimalFormat("00").format(c.get(Calendar.SECOND));
        return String.format(Locale.getDefault(), "%s:%s:%s", hour, minute, second);
    }

    public static long getTimeStamp(String dateTime) {

        SimpleDateFormat f = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());
        try {
            Date d = f.parse(dateTime);
            return d.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }
}
