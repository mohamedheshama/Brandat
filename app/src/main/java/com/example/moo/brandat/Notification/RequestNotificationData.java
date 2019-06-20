package com.example.moo.brandat.Notification;

public class RequestNotificationData {
    String senderId,contetnText,photUrl;

    public RequestNotificationData(String senderId, String contetnText, String photUrl) {
        this.senderId = senderId;
        this.contetnText = contetnText;
        this.photUrl = photUrl;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getContetnText() {
        return contetnText;
    }

    public void setContetnText(String contetnText) {
        this.contetnText = contetnText;
    }

    public String getPhotUrl() {
        return photUrl;
    }

    public void setPhotUrl(String photUrl) {
        this.photUrl = photUrl;
    }
}
