package com.graduationproject.zakerly.core.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.cache.DataStoreManger;
import com.graduationproject.zakerly.core.models.NotificationData;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;

import java.util.Random;


public class FirebaseNotificationService extends FirebaseMessagingService {


    private static final String REQUESTS_CHANNEL = "Requests";
    private static final String TAG = "FirebaseNotificationSer";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        Intent intent = new Intent(this, MainActivity.class);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = new Random().nextInt();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(remoteMessage.getData());
        NotificationData notificationData = gson.fromJson(jsonElement, NotificationData.class);
        Notification notification =
                new NotificationCompat.Builder(this, REQUESTS_CHANNEL)
                        .setContentTitle(notificationData.getTitle())
                        .setContentText(notificationData.getBody())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent)
                        .build();

        notificationManager.notify(notificationId, notification);
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        if (FireBaseAuthenticationClient.getInstance().getCurrentUser()!=null) {
            FirebaseDataBaseClient.getInstance().setToken(s);
            DataStoreManger.getInstance(this).setToken(s);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createNotificationChannel(NotificationManager notificationManager) {
        NotificationChannel channel = new NotificationChannel(REQUESTS_CHANNEL,
                REQUESTS_CHANNEL,
                NotificationManager.IMPORTANCE_HIGH);
        notificationManager.createNotificationChannel(channel);
    }
}
