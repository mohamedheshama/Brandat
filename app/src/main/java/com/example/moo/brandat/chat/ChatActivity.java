package com.example.moo.brandat.chat;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.moo.brandat.MainActivity;
import com.example.moo.brandat.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ChatActivity extends AppCompatActivity {
    private String mRecieverUid,mRecieverImageUrl,mSenderImageUrl;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();

        mSenderImageUrl= MainActivity.userImageUrl;
        Intent intent=getIntent();
        if (intent!=null) {
            mRecieverUid = intent.getStringExtra(getString(R.string.key_chat_uid_reciever));
            if (intent.hasExtra(getString(R.string.key_of_img_url_user_recieve))) {
                mRecieverImageUrl = intent.getStringExtra(getString(R.string.key_of_img_url_user_recieve));
            }else {
                mRecieverImageUrl=getImageUrlFor(mRecieverUid);
            }
        }
        FragmentChat fragmentChat=new FragmentChat();
        Bundle bundle=new Bundle();
        bundle.putString(getString(R.string.key_user_uid_fragment),mRecieverUid);
        bundle.putString(getString(R.string.key_imge_url_sender_fragment),mSenderImageUrl);
        bundle.putString(getString(R.string.key_imge_url_reciever_fragment),mRecieverImageUrl);
        fragmentChat.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_chat,fragmentChat).commit();
    }

    private String getImageUrlFor(String mRecieverUid) {
        final String[] imgUrl = new String[1];
        mDatabaseReference.child("userss").child(mRecieverUid).child("img_url").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                 imgUrl[0] =dataSnapshot.getValue(String.class);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        return imgUrl[0];
    }
}
