package com.example.moo.brandat.chat;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.moo.brandat.MainActivity;
import com.example.moo.brandat.R;

public class ChatActivity extends AppCompatActivity {
    private String mRecieverUid,mRecieverImageUrl,mSenderImageUrl;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        mSenderImageUrl= MainActivity.userImageUrl;
        Intent intent=getIntent();
        if (intent!=null) {
            mRecieverUid = intent.getStringExtra(getString(R.string.key_chat_uid_reciever));
            mRecieverImageUrl=intent.getStringExtra(getString(R.string.key_of_img_url_user_recieve));
        }
        FragmentChat fragmentChat=new FragmentChat();
        Bundle bundle=new Bundle();
        bundle.putString(getString(R.string.key_user_uid_fragment),mRecieverUid);
        bundle.putString(getString(R.string.key_imge_url_sender_fragment),mSenderImageUrl);
        bundle.putString(getString(R.string.key_imge_url_reciever_fragment),mRecieverImageUrl);
        fragmentChat.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_chat,fragmentChat).commit();
    }
}
