package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;
import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.activity.ImageActivity;

public class FCMClass extends FirebaseMessagingService {

    private final String TAG = "Notification";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String topic = remoteMessage.getFrom();


        if (remoteMessage.getNotification() != null) {

            Intent intent = new Intent(this, ImageActivity.class);
            TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
            stackBuilder.addNextIntentWithParentStack(intent);

            Bundle bundle = new Bundle();
            bundle.putString("CAMINFO", remoteMessage.getData().get("CAMJSON"));
            intent.putExtras(bundle);
            intent.putExtra("STREET_NAME", remoteMessage.getData().get("STREET_NAME"));

            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true)
                    .setContentIntent(pendingIntent);

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            notificationManager.notify(generateRandom(), notificationBuilder.build());

        } else {
            try {
                LocalBroadcastManager broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
                Intent intent = new Intent("Camera");
                int camera = Integer.parseInt(remoteMessage.getData().get("camera"));
                int status = Integer.parseInt(remoteMessage.getData().get("status"));
                String time = remoteMessage.getData().get("time");
                String imgURL = remoteMessage.getData().get("img");
                intent.putExtra("CAMERA_ID", camera);
                intent.putExtra("STATUS", status);
                intent.putExtra("TIME", time);
                intent.putExtra("IMG", imgURL);
                broadcaster.sendBroadcast(intent);
                Log.e(TAG, "Data: " + remoteMessage.getData());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    private int generateRandom() {
        Random random = new Random();
        return random.nextInt(9999 - 1000) + 1000;
    }
}
