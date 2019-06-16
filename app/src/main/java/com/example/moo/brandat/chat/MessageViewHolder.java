package com.example.moo.brandat.chat;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moo.brandat.R;
import com.squareup.picasso.Picasso;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    private TextView mSenderContent,mRecieveContent;
    private ImageView mSenderName,mRecieveName;
    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        mSenderName=itemView.findViewById(R.id.name_sender_message);
        mRecieveName=itemView.findViewById(R.id.name_recieve_message);
        mSenderContent=itemView.findViewById(R.id.content_sender_message);
        mRecieveContent=itemView.findViewById(R.id.content_recieve_message);



    }

    public void onBind(String name, String content , boolean isSender, Context context){
        if (isSender){
            mRecieveName.setVisibility(TextView.GONE);
            mRecieveContent.setVisibility(TextView.GONE);

            mSenderName.setVisibility(TextView.VISIBLE);
            mSenderContent.setVisibility(TextView.VISIBLE);

            Picasso.with(context)
                    .load(name)
                    .resize(50, 50)
                    .centerCrop()
                    .into(mSenderName);
            mSenderContent.setText(content);
        }else{

            mSenderName.setVisibility(TextView.GONE);
            mSenderContent.setVisibility(TextView.GONE);

            mRecieveName.setVisibility(TextView.VISIBLE);
            mRecieveContent.setVisibility(TextView.VISIBLE);

            Picasso.with(context)
                    .load(name)
                    .resize(80, 80)
                    .centerCrop()
                    .into(mRecieveName);
            mRecieveContent.setText(content);
        }
    }
}
