package com.example.moo.brandat.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.moo.brandat.R;

import java.util.ArrayList;

class MessageAdapter extends RecyclerView.Adapter<MessageViewHolder> {
    private ArrayList mMessageList;
    private String mMyUid,mSenderImageUrl,mRecieverImageUrl;
    Context mContext;

    public MessageAdapter(ArrayList MessageList, String myUid, String imageSender, String imageReciever, Context context){
        mMessageList=MessageList;
        mMyUid=myUid;
        mRecieverImageUrl=imageReciever;
        mSenderImageUrl=imageSender;
        mContext=context;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        return new MessageViewHolder(
                LayoutInflater
                        .from(viewGroup.getContext())
                        .inflate(R.layout.message_recycler_item,viewGroup,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder messageViewHolder, int i) {
        MessageData message=(MessageData)mMessageList.get(i);
        boolean isSender=false;
        String imgUrl=mRecieverImageUrl;
        if (mMyUid.equals(message.getSender())){
            isSender=true;
            imgUrl=mSenderImageUrl;
        }
        Log.d("mano", "onBindViewHolder: "+message.getContent());
        messageViewHolder.onBind(imgUrl,message.getContent(),isSender,mContext,message.getPhotoUrl());
    }

    @Override
    public int getItemCount() {
        return mMessageList.size();
    }
}
