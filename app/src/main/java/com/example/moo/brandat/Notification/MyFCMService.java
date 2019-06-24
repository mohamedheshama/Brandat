package com.example.moo.brandat.Notification;



import android.util.Log;

import com.example.moo.brandat.chat.FragmentChat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFCMService extends FirebaseMessagingService {
    private static final String TAG="MyFCMService";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();

            String title = data.get("name");

            if (!title.equals("follow")) {

                String message = data.get("body");
                String sender = data.get("sender");
                String imgeSender = data.get("imgeurlsender");


                Log.d("hi", "onMessageReceived: " + remoteMessage.getData());
                Log.d("hi", "onMessageReceived: " + title + "   " + message + "  " + sender);

                if (!FragmentChat.IS_ACTIVATE && !FragmentChat.RECIEVER_UID.equals(sender)) {
                    Log.d(TAG, "onMessageReceived: notification from chat");
                    NotificationUtils.setNotification(getApplicationContext(), title, message, sender, imgeSender);
                }

            }else{
                String message = data.get("body");
                String productId = data.get("product");
                String userid = data.get("userid");
                Log.d("mano", ":  the product id is "+userid+"   "+productId+"   "+message);


                Log.d("hi", "onMessageReceived: " + remoteMessage.getData());
                NotificationUtils.setNotificationYourFollower(getApplicationContext(), message,productId,userid);

            }
        }
    }


}
