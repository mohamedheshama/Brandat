package com.example.moo.brandat.chat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.moo.brandat.MainActivity;
import com.example.moo.brandat.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class FragmentListUserChat extends Fragment {
    public static String TAG="FragmentListUserChat";
    private DatabaseReference mDatabaseReference;
    private FirebaseDatabase mFirebaseDatabase;

    private ListView mListView;
    private ArrayList<UserListData> mListUserChatArrayList;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.activity_chat_user_list,container,false);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();

        mListView=rootView.findViewById(R.id.chat_user_list_view);

        mListUserChatArrayList=new ArrayList<>();

        final UserListChatAdatpter adatpter=new UserListChatAdatpter(getContext(),mListUserChatArrayList);

        mListView.setAdapter(adatpter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserListData userListData=(UserListData)adatpter.getItem(position);
                Log.d(TAG, "onItemClick: mano "+userListData.getName());
                Intent intent=new Intent(getContext(),ChatActivity.class);
                intent.putExtra(getString(R.string.key_chat_uid_reciever),userListData.getUserId());
                intent.putExtra(getString(R.string.key_of_img_url_user_recieve),userListData.getImg_Url());
                startActivity(intent);
            }
        });


        DatabaseReference userDataRefPath=mDatabaseReference
                .child("userss")
                .child(MainActivity.usernameId)
                .child("mymessages");
        userDataRefPath.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    String userId=ds.getKey();
                    DatabaseReference userDataRefPath2=mDatabaseReference
                            .child("userss")
                            .child(userId);

                    userDataRefPath2.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                           UserListData userListData=new UserListData();

                            Log.d(TAG, "onDataChange: the user id"+dataSnapshot.getKey());
                            userListData=dataSnapshot.getValue(UserListData.class);
                            userListData.setUserId(dataSnapshot.getKey());
                            Log.d(TAG, "onDataChange: "+userListData.getName() +"       "+ userListData.getImg_Url());
                            adatpter.add(userListData);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        return rootView;
    }
}
