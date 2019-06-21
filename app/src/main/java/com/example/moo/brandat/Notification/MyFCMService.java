package com.example.moo.brandat.Notification;



import android.util.Log;

import com.example.moo.brandat.chat.FragmentChat;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

public class MyFCMService extends FirebaseMessagingService {


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        Log.d("noti", "onMessageReceived: ");
        if (remoteMessage.getData().size() > 0) {
            Map<String, String> data = remoteMessage.getData();
            String title = data.get("name");
            String message = data.get("body");
            String sender = data.get("sender");
            String imgeSender=data.get("imgeurlsender");


            Log.d("hi", "onMessageReceived: " + remoteMessage.getData());
            Log.d("hi", "onMessageReceived: " + title + "   " + message+"  "+sender);
            if (!FragmentChat.IS_ACTIVATE&&!FragmentChat.RECIEVER_UID.equals(sender)) {
                NotificationUtils.setNotification(getApplicationContext(), title, message,sender,imgeSender);
            }
        }
    }


}
