package com.example.moo.brandat.chat;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.moo.brandat.MainActivity;
import com.example.moo.brandat.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.concurrent.Semaphore;
import java.util.zip.Inflater;

public class ChatActivity extends AppCompatActivity {
    private String mRecieverUid,mRecieverImageUrl,mSenderImageUrl,mRecieverName;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    private static ImageView mImgeRecieverImgeView,stateUserImgeView;
    private TextView mNameRecieverTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        Toolbar mTopToolbar = (Toolbar) findViewById(R.id.toolbar_chat);
        setSupportActionBar(mTopToolbar);

        ActionBar actionBar = getSupportActionBar();

        LayoutInflater layoutInflater=(LayoutInflater) this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View myBar=layoutInflater.inflate(R.layout.chat_cutom_bar,null);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle("");
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setCustomView(myBar);


        mImgeRecieverImgeView=myBar.findViewById(R.id.imge_reciever_message_custom_bar);
        mNameRecieverTextView=myBar.findViewById(R.id.name__reciever_message_custom_bar);
        stateUserImgeView=myBar.findViewById(R.id.state_user);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();


        mSenderImageUrl= MainActivity.userImageUrl;
        Intent intent=getIntent();
        if (intent!=null) {


            mRecieverUid = intent.getStringExtra(getString(R.string.key_chat_uid_reciever));
            Log.d("mano ", "onCreate: "+mRecieverUid);
            if (intent.hasExtra(getString(R.string.key_of_img_url_user_recieve))) {
                Log.d("mano", "onCreate:imge   go to has extra");
                mRecieverImageUrl = intent.getStringExtra(getString(R.string.key_of_img_url_user_recieve));
            }
            if (intent.hasExtra(getString(R.string.key_chat_name_reciever))){
                mRecieverName=intent.getStringExtra(getString(R.string.key_chat_name_reciever));
            }
            if (intent.hasExtra("state")){
                setStateOnline(intent.getStringExtra("state"));
            }
            String  test=intent.getStringExtra("mano");
            Log.d("mano", "ؤ  "+mRecieverUid+"   "+test);
        }

        Picasso.with(this)
                .load(mRecieverImageUrl)
                .resize(50, 50)
                .centerCrop()
                .into(mImgeRecieverImgeView);
        mNameRecieverTextView.setText(mRecieverName);



        FragmentChat fragmentChat=new FragmentChat();
        Bundle bundle=new Bundle();
        bundle.putString(getString(R.string.key_user_uid_fragment),mRecieverUid);
        bundle.putString(getString(R.string.key_imge_url_sender_fragment),mSenderImageUrl);
        bundle.putString(getString(R.string.key_imge_url_reciever_fragment),mRecieverImageUrl);
        fragmentChat.setArguments(bundle);
        getSupportFragmentManager().beginTransaction().replace(R.id.container_chat,fragmentChat).commit();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // app icon in action bar clicked; go home
                Intent intent = new Intent(this, MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public static void setStateOnline(String state){
        if (state.equals("online")){
            stateUserImgeView.setImageResource(R.drawable.online_green);
        }else {
            stateUserImgeView.setImageResource(R.drawable.offline_gray);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mDatabaseReference.child("userss").child(MainActivity.usernameId).child("state").setValue("offline");

    }

    @Override
    protected void onResume() {
        super.onResume();
        mDatabaseReference.child("userss").child(MainActivity.usernameId).child("state").setValue("online");

    }

}
