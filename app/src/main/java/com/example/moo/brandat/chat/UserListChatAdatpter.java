package com.example.moo.brandat.chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moo.brandat.R;
import com.google.firebase.database.DatabaseReference;

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


        if (mListData.size()!=0) {
            UserListData userListData = mListData.get(position);
            nameUserChatTextView.setText(userListData.getName());
        }
        return convertView;
    }
}
