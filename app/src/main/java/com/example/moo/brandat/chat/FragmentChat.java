package com.example.moo.brandat.chat;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.moo.brandat.MainActivity;
import com.example.moo.brandat.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FragmentChat extends Fragment {
    public static String TAG="fragmentchat";
    public static boolean IS_ACTIVATE=false;
    public static String RECIEVER_UID="no";
    private LinearLayoutManager layoutManager;
    private RecyclerView recyclerView;
    private ArrayList<MessageData> mMessagesList;
    private Button mSendMessage;
    private EditText mMessageEditText;

    private String mUserIdSender,mUserIdRecieve,mSenderImageUrl,mRecieverImageUrl;

    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;
    private MessageAdapter messageAdapter;

    public FragmentChat(){
    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_chat,container,false);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();

        mSendMessage=rootView.findViewById(R.id.sendButton);
        recyclerView=rootView.findViewById(R.id.message_recycler_view);
        mMessageEditText=rootView.findViewById(R.id.message_edit_text);

        //get user id's reciever
        Bundle bundle=getArguments();
        if (bundle!=null) {
            mUserIdRecieve = bundle.getString(getString(R.string.key_user_uid_fragment));
            mSenderImageUrl=bundle.getString(getString(R.string.key_imge_url_sender_fragment));
            if (bundle.containsKey(getString(R.string.key_imge_url_reciever_fragment))) {
                mRecieverImageUrl = bundle.getString(getString(R.string.key_imge_url_reciever_fragment));
            }
        }


        //get user id's sender
        mUserIdSender= MainActivity.usernameId;

        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

         mMessagesList=new ArrayList<>();

         messageAdapter=new MessageAdapter(mMessagesList,mUserIdSender,mSenderImageUrl,mRecieverImageUrl,getContext());

        recyclerView.setAdapter(messageAdapter);


        Log.d(TAG, "onCreateView: user sender id = "+mUserIdSender+"     "+mUserIdRecieve);

        recieveAllMessages(mUserIdSender,mUserIdRecieve);

        mSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MessageData messageData=new MessageData();
                messageData.setContent(mMessageEditText.getText().toString());
                messageData.setName(MainActivity.usernameUser);
                messageData.setSender(mUserIdSender);
                messageData.setReciever(mUserIdRecieve);

                opentChatBetween(mUserIdSender,mUserIdRecieve,messageData);

                mMessageEditText.setText("");

                mDatabaseReference
                        .child("Notification")
                        .child(mUserIdRecieve)
                        .child(mUserIdSender)
                        .child("contetnText")
                        .setValue(messageData.getContent());


            }
        });

        return rootView;
    }

    private void recieveAllMessages(final String userIdSender, final String userIdRecieve) {
        DatabaseReference userDataRefPath=mDatabaseReference.child("userss").child(userIdSender).child("mymessages").child(userIdRecieve);
        userDataRefPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String idMessages=dataSnapshot.getValue(String.class);
                Log.d(TAG, "onDataChange: id messages multi is "+idMessages);
                if (idMessages!=null){
                    DatabaseReference userDataRefPath=mDatabaseReference.child("allmessages").child(idMessages);
                    userDataRefPath.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                            mMessagesList.add(dataSnapshot.getValue(MessageData.class));

                            recyclerView.smoothScrollToPosition(messageAdapter.getItemCount()-1);

                            messageAdapter.notifyDataSetChanged();
                        }

                        @Override
                        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                        }

                        @Override
                        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                }

                );
                }else {
                    opentChatBetween(userIdSender,userIdRecieve,null);
                    recieveAllMessages(userIdSender,userIdRecieve);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void opentChatBetween(final String mUserIdSender, final String mUserIdRecieve, final MessageData messageData) {
        DatabaseReference userDataRefPath=mDatabaseReference.child("userss").child(mUserIdSender).child("mymessages");

        //first check if have  user's click in my message special
        userDataRefPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(mUserIdRecieve)){
                    //found the user recieve data
                    //get id message special
                    mDatabaseReference.child("userss").child(mUserIdSender).child("mymessages").child(mUserIdRecieve).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String idSpecialMessage= (String)dataSnapshot.getValue();
                            //go to activty
                            if (messageData!=null)
                            mDatabaseReference.child("allmessages")
                                    .child(idSpecialMessage)
                                    .push()
                                    .setValue(messageData);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                    //go to new activity with id message special



                }else{
                    //don't found it
                    //create message special
                    String idMessageSpecial=mDatabaseReference.child("allmessages").push().getKey();

                    //add user uid in my message special and add id message special
                    mDatabaseReference.child("userss").child(mUserIdSender).child("mymessages").child(mUserIdRecieve).setValue(idMessageSpecial);

                    //add in user reciever's uid my uid and add id message special
                    mDatabaseReference.child("userss").child(mUserIdRecieve).child("mymessages").child(mUserIdSender).setValue(idMessageSpecial);

                    //go to new activity with id message special
                    if (messageData!=null)
                    mDatabaseReference.child("allmessages")
                            .child(idMessageSpecial)
                            .push()
                            .setValue(messageData);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onPause() {
        super.onPause();
        IS_ACTIVATE=false;
        RECIEVER_UID="no";
    }

    @Override
    public void onStart() {
        super.onStart();
        RECIEVER_UID=mUserIdRecieve;
        IS_ACTIVATE=true;
    }

    @Override
    public void onStop() {
        super.onStop();
        RECIEVER_UID="no";
        IS_ACTIVATE=false;
    }

    @Override
    public void onResume() {
        super.onResume();
        RECIEVER_UID=mUserIdRecieve;
        IS_ACTIVATE=true;
    }

}
