package com.graduationproject.zakerly.core.util;


import android.util.Log;

import com.graduationproject.zakerly.core.models.PushNotification;
import com.graduationproject.zakerly.core.network.retrofit.RetrofitClient;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class Util {
    private static final String TAG = "Util";

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

    public static void sendNotification(PushNotification notification) {

        Disposable d = RetrofitClient.getApi()
                .postNotification(notification)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(responseBodyResponse -> Log.d(TAG, "sendNotification: result" + responseBodyResponse.message()),
                        throwable -> Log.d(TAG, "sendNotification: error" + throwable.getMessage()));
    }
}
