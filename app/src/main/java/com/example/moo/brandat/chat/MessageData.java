package com.example.moo.brandat.chat;

public class MessageData {
    private String mName;
    private String mContent;
    private String mSender;
    private String mReciever;


    private String photoUrl;
    public MessageData(){}
    public MessageData(String name,String content,String sender,String reciever){
        mName=name;
        mContent=content;

        mSender=sender;
        mReciever=reciever;
    }

    public String getSender() {
        return mSender;
    }

    public void setSender(String mSender) {
        this.mSender = mSender;
    }

    public String getReciever() {
        return mReciever;
    }

    public void setReciever(String mReciever) {
        this.mReciever = mReciever;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String mContent) {
        this.mContent = mContent;
    }
    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }


}
