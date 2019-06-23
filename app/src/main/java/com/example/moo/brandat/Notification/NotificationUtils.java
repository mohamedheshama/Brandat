package com.example.moo.brandat.Notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.ImageView;

import com.example.moo.brandat.MainActivity;
import com.example.moo.brandat.R;
import com.example.moo.brandat.chat.ChatActivity;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class NotificationUtils {
    public static final String CHANNEL_NOTIFICATION_ID="FIRST";

    public static void  setNotification(Context context ,String name,String body,String uid,String imgeSender){
        Log.d("mano", "createContentIntent: "+uid);

        NotificationManager notificationManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel=new NotificationChannel(CHANNEL_NOTIFICATION_ID,"ma", NotificationManager.IMPORTANCE_HIGH);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        NotificationCompat.Builder notificationBuilder=new NotificationCompat.Builder(context,CHANNEL_NOTIFICATION_ID)
                .setContentTitle(name)
                .setSmallIcon(R.drawable.mapbox_logo_icon)
                .setContentIntent(createContentIntent(context,uid,imgeSender,name))
                .setAutoCancel(true)
                .setContentText(body);
        notificationManager.notify(CHANNEL_NOTIFICATION_ID,1,notificationBuilder.build());
    }

    public static PendingIntent createContentIntent(Context context,String uid,String imgeSender,String name){
        Intent intent=new Intent(context, ChatActivity.class);
        Log.d("mano", "createContentIntent: "+uid);
        intent.putExtra(context.getString(R.string.key_chat_uid_reciever),uid);
        intent.putExtra(context.getString(R.string.key_chat_name_reciever),name);
        intent.putExtra(context.getString(R.string.key_of_img_url_user_recieve),imgeSender);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_IMMUTABLE);
        return pendingIntent;
    }


}
