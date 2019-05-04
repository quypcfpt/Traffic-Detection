package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
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


        if (!topic.equals("/topics/TJWS")) {

            Intent intent = new Intent(this, ImageActivity.class);
            intent.putExtra("CAMINFO",remoteMessage.getData().get("CAMJSON"));
            intent.putExtra("STREET_NAME",remoteMessage.getData().get("STREET_NAME"));
            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

            if (remoteMessage.getNotification() != null) {
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                        .setContentTitle(remoteMessage.getNotification().getTitle())
                        .setContentText(remoteMessage.getNotification().getBody())
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setStyle(new NotificationCompat.BigTextStyle())
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .setContentIntent(pendingIntent);

                NotificationManager notificationManager =
                        (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                notificationManager.notify(generateRandom(), notificationBuilder.build());
            }
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
