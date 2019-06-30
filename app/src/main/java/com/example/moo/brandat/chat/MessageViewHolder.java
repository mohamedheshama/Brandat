package com.example.moo.brandat.chat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moo.brandat.Main_map;
import com.example.moo.brandat.R;
import com.example.moo.brandat.details;
import com.squareup.picasso.Picasso;

import java.security.AccessController;
import java.util.Scanner;

public class MessageViewHolder extends RecyclerView.ViewHolder {
    private TextView mSenderContent,mRecieveContent,mLocationTextSender,mLocationTextReciver;
    private ImageView mSenderName,mRecieveName,mMessagePhotoSender,mMessagePhotoReciever;
    private LinearLayout mLinearPinSender,mLinearPinRecieve;
    public MessageViewHolder(@NonNull View itemView) {
        super(itemView);
        mSenderName=itemView.findViewById(R.id.name_sender_message);
        mRecieveName=itemView.findViewById(R.id.name_recieve_message);
        mSenderContent=itemView.findViewById(R.id.content_sender_message);
        mRecieveContent=itemView.findViewById(R.id.content_recieve_message);

        mMessagePhotoReciever=itemView.findViewById(R.id.photoImageView_reciever);
        mMessagePhotoSender=itemView.findViewById(R.id.photoImageView_sender);

        mLinearPinRecieve=itemView.findViewById(R.id.pin_location_reciever);
        mLinearPinSender=itemView.findViewById(R.id.pin_location_sender);

        mLocationTextReciver=itemView.findViewById(R.id.location_reciever);
        mLocationTextSender=itemView.findViewById(R.id.location_sender);

        mLinearPinSender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=v.findViewById(R.id.location_sender);
                Scanner l = new Scanner(textView.getText().toString());
                String sr=l.nextLine() ;
                String []ww=sr.split("jsdflfdsljdfsdfsldkafj");
                String e=ww[0];
                String e1=ww[1];




              //  Toast.makeText(v.getContext(), e+"eeee"+e1, Toast.LENGTH_SHORT).show();
               double latitude = Double.parseDouble(e);
//                l.next();
               double longitude = Double.parseDouble(e1);
//
               codeBohlokHere(v.getContext(),latitude,longitude);
            }
        });

        mLinearPinRecieve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView=v.findViewById(R.id.location_reciever);
                String text= textView.getText().toString();
                Log.d("mano", "onClick: man "+text);
                Scanner l = new Scanner(text);
                String sr=l.nextLine() ;
                String []ww=sr.split("jsdflfdsljdfsdfsldkafj");
                String e=ww[0];
                String e1=ww[1];




              //  Toast.makeText(v.getContext(), e+"eeee"+e1, Toast.LENGTH_SHORT).show();

            double latitude = Double.parseDouble(e);
////                l.next();
             double longitude = Double.parseDouble(e1);
////
               codeBohlokHere(v.getContext(),latitude,longitude);
            }
        });





    }

    public void onBind(String name, String content , boolean isSender, Context context,String photoUrl){
        if (isSender){
            mRecieveName.setVisibility(ImageView.GONE);
            mRecieveContent.setVisibility(TextView.GONE);
            mMessagePhotoReciever.setVisibility(ImageView.GONE);
            mLinearPinRecieve.setVisibility(LinearLayout.GONE);

            mSenderName.setVisibility(ImageView.VISIBLE);
            mSenderContent.setVisibility(TextView.VISIBLE);
            mMessagePhotoSender.setVisibility(ImageView.VISIBLE);
            mLinearPinSender.setVisibility(LinearLayout.VISIBLE);

            Log.d("mano", "onBind: "+content);
            Picasso.with(context)
                    .load(name)
                    .resize(50, 50)
                    .centerCrop()
                    .into(mSenderName);

            if (photoUrl==null) {
                mSenderContent.setVisibility(TextView.VISIBLE);
                mMessagePhotoSender.setVisibility(ImageView.GONE);
                if (content.contains("jsdflfdsljdfsdfsldkafj")) {
                    mSenderContent.setVisibility(TextView.GONE);
                    mLinearPinSender.setVisibility(LinearLayout.VISIBLE);


                    mLocationTextSender.setText(content);

                }else{
                    mSenderContent.setVisibility(TextView.VISIBLE);
                    mLinearPinSender.setVisibility(LinearLayout.GONE);

                    mSenderContent.setText(content);
                }
            }else {
                mSenderContent.setVisibility(TextView.GONE);
                mMessagePhotoSender.setVisibility(ImageView.VISIBLE);

                Picasso.with(context)
                        .load(photoUrl)
//                        .resize(200, 200)
//                        .centerCrop()
                        .into(mMessagePhotoSender);
            }
        }else{
            mSenderName.setVisibility(ImageView.GONE);
            mSenderContent.setVisibility(TextView.GONE);
            mMessagePhotoSender.setVisibility(ImageView.GONE);
            mLinearPinSender.setVisibility(LinearLayout.GONE);

            mRecieveName.setVisibility(ImageView.VISIBLE);
            mRecieveContent.setVisibility(TextView.VISIBLE);
            mMessagePhotoReciever.setVisibility(ImageView.VISIBLE);
            mLinearPinRecieve.setVisibility(LinearLayout.VISIBLE);

            Picasso.with(context)
                    .load(name)
                    .resize(80, 80)
                    .centerCrop()
                    .into(mRecieveName);

            if (photoUrl==null) {
                mRecieveContent.setVisibility(TextView.VISIBLE);
                mMessagePhotoReciever.setVisibility(ImageView.GONE);

                if (content.contains("jsdflfdsljdfsdfsldkafj")) {
                    mRecieveContent.setVisibility(TextView.GONE);
                    mLinearPinRecieve.setVisibility(LinearLayout.VISIBLE);

                    mLocationTextReciver.setText(content);

                }else{
                    mRecieveContent.setVisibility(TextView.VISIBLE);
                    mLinearPinRecieve.setVisibility(LinearLayout.GONE);

                    mRecieveContent.setText(content);
                }

                mRecieveContent.setText(content);
            }else {
                mRecieveContent.setVisibility(TextView.GONE);
                mMessagePhotoReciever.setVisibility(ImageView.VISIBLE);
                Picasso.with(context)
                        .load(photoUrl)
//                        .resize(200, 200)
//                        .centerCrop()
                        .into(mMessagePhotoReciever);
            }
        }
    }

    public  void codeBohlokHere(Context context,double latitude,double longitude){
        Toast.makeText(context, latitude+"ee"+longitude, Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(context, Main_map.class);
String r=""+latitude;
        String r1=""+longitude;
        intent1.putExtra("long", r1);
        intent1.putExtra("lat", r);
        context.startActivity(intent1);
        // TODO: code bohlok open map for the two double
    }
}
