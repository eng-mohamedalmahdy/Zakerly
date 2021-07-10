package com.graduationproject.zakerly.core.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;
import androidx.navigation.NavController;
import androidx.navigation.NavDeepLinkBuilder;
import androidx.navigation.fragment.NavHostFragment;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.graduationproject.zakerly.MainActivity;
import com.graduationproject.zakerly.R;
import com.graduationproject.zakerly.core.cache.DataStoreManger;
import com.graduationproject.zakerly.core.constants.NotificationType;
import com.graduationproject.zakerly.core.models.NotificationData;
import com.graduationproject.zakerly.core.models.PushNotification;
import com.graduationproject.zakerly.core.models.User;
import com.graduationproject.zakerly.core.network.firebase.FireBaseAuthenticationClient;
import com.graduationproject.zakerly.core.network.firebase.FirebaseDataBaseClient;

import org.jitsi.meet.sdk.JitsiMeetActivity;
import org.jitsi.meet.sdk.JitsiMeetConferenceOptions;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import timber.log.Timber;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;


public class FirebaseNotificationService extends FirebaseMessagingService {


    private static final String REQUESTS_CHANNEL = "Requests";
    private static final String TAG = "FirebaseNotificationSer";

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);


        Gson gson = new Gson();
        JsonElement jsonElement = gson.toJsonTree(remoteMessage.getData());
        NotificationData notificationData = gson.fromJson(jsonElement, NotificationData.class);

        Timber.d("onMessageReceived: incoming request%s", notificationData);
        switch (notificationData.getNotificationType()) {
            case REQUEST: {
                sendRequestNotification(notificationData);
                break;
            }
            case VIDEO_MEETING:
            case VOICE_MEETING:
            case CANCEL: {
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("NOTIFICATION_DATA", notificationData);
                intent.putExtra("NOTIFICATION_TYPE", notificationData.getNotificationType().toString());
                intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            }
            case ANSWER: {
                try {
                    JitsiMeetConferenceOptions options = new JitsiMeetConferenceOptions.Builder()
                            .setServerURL(new URL("https://meet.jit.si"))
                            .setRoom(("zakerly" + notificationData.getNotificationId()))
                            .setAudioMuted(false)
                            .setVideoMuted(false)
                            .setAudioOnly(notificationData.getNotificationType() == NotificationType.VOICE_MEETING)
                            .setWelcomePageEnabled(false)
                            .build();

                    Intent intent = new Intent(this, JitsiMeetActivity.class);
                    intent.setAction("org.jitsi.meet.CONFERENCE");
                    intent.putExtra("JitsiMeetConferenceOptions", options);
                    intent.addFlags(FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
                break;
            }
        }
    }

    private void sendRequestNotification(NotificationData notificationData) {
        Intent intent = new Intent(this, MainActivity.class);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = new Random().nextInt();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel(notificationManager);
        }
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

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
        if (FireBaseAuthenticationClient.getInstance().getCurrentUser() != null) {
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
