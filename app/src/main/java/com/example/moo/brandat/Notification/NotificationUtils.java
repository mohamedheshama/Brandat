package com.example.moo.brandat.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.example.moo.brandat.MainActivity;
import com.example.moo.brandat.R;
import com.example.moo.brandat.chat.ChatActivity;

public class NotificationUtils {
    public static final String CHANNEL_NOTIFICATION_ID="FIRST";

    public static void  setNotification(Context context ,String name,String body,String uid){
        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_NOTIFICATION_ID,"ma", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context,CHANNEL_NOTIFICATION_ID)
                .setContentTitle(name)
                .setSmallIcon(R.drawable.mapbox_logo_icon)
                .setContentIntent(createContentIntent(context,uid))
                .setContentText(body);
        notificationManager.notify(CHANNEL_NOTIFICATION_ID,1,notificationBuilder.build());
    }

    public static PendingIntent createContentIntent(Context context,String uid){
        Intent intent=new Intent(context, ChatActivity.class);
        intent.setAction(context.getString(R.string.notification_recieved_action));
        intent.putExtra(context.getString(R.string.key_chat_uid_reciever),uid);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_IMMUTABLE);
        return pendingIntent;
    }
}
