package com.example.moo.brandat.chat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moo.brandat.R;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UserListChatAdatpter extends ArrayAdapter {
    private ArrayList<UserListData> mListData;
    public UserListChatAdatpter(Context context, ArrayList<UserListData> listData) {
        super(context, 0);
        mListData=listData;
    }

    @Override
    public View getView(int position, View convertView,  ViewGroup parent) {
        if (convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.user_chat_list_item,parent,false);
        }

        ImageView imageView=convertView.findViewById(R.id.image_user_chat_list);
        TextView nameUserChatTextView=convertView.findViewById(R.id.name_user_chat_list);



            UserListData userListData =(UserListData) getItem(position);
            nameUserChatTextView.setText(userListData.getName());
        Log.d("image", "getView: "+userListData.getImg_Url());
            Picasso.with(getContext())
                    .load(userListData.getImg_Url())
                    .resize(100, 100)
                    .centerCrop()
                    .into(imageView);


        return convertView;
    }
}
