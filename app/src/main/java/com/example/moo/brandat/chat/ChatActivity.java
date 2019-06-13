package com.example.moo.brandat.chat;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.moo.brandat.R;

public class ChatActivity extends AppCompatActivity {
    private String mRecieverUid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        Intent intent=getIntent();
        if (intent!=null)
        mRecieverUid=intent.getStringExtra(getString(R.string.key_chat_uid_reciever));

        FragmentChat fragmentChat=new FragmentChat();
        Bundle bundle=new Bundle();
        bundle.putString("userUID",mRecieverUid);
        fragmentChat.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_chat,fragmentChat).commit();
    }
}
