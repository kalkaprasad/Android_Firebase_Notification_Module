package com.devi.firebase_notification;

import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class mymessaging extends FirebaseMessagingService {

    Bitmap bitmap;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        String imageUri = remoteMessage.getData().get("image");

        bitmap = getBitmapfromUrl(imageUri);
        shownotifiaction(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody(),bitmap);


    }

    private Bitmap getBitmapfromUrl(String imageUri) {



        try {
            URL url = new URL(imageUri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            return bitmap;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return null;

        }






    }

    public void shownotifiaction(String title, String message, Bitmap image)
    {
        Intent intent= new Intent(this,MainActivity.class);

        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent notifyPendingIntent = PendingIntent.getActivity(
                this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder= new NotificationCompat.Builder(this,"MyNotifications")

                .setContentTitle(title)
                .setSmallIcon(R.drawable.user)
                .setAutoCancel(true)
                .setContentIntent(notifyPendingIntent)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(image))
                .setContentText(message);

        NotificationManagerCompat manage=NotificationManagerCompat.from(this);
        manage.notify(999,builder.build());
    }
}
