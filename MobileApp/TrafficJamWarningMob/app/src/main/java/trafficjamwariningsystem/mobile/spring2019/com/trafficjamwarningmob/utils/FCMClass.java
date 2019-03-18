package trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.utils;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import trafficjamwariningsystem.mobile.spring2019.com.trafficjamwarningmob.R;

public class FCMClass extends FirebaseMessagingService {

    private final String TAG = "Notification";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        if (remoteMessage.getNotification() != null) {
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, "channel_id")
                    .setContentTitle(remoteMessage.getNotification().getTitle())
                    .setContentText(remoteMessage.getNotification().getBody())
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                    .setStyle(new NotificationCompat.BigTextStyle())
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setAutoCancel(true);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

            notificationManager.notify(0, notificationBuilder.build());
        }

        if (remoteMessage.getData().size() > 0) {
            Log.e(TAG, "Data: " + remoteMessage.getData());
        }
    }
}
