package com.example.moo.brandat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

//import butterknife.BindView;
//import butterknife.ButterKnife;

import static android.app.Activity.RESULT_OK;

public class account extends Fragment {
    private static final String TAG="account";
    private static final int PHOTO_PICKER = 2;
    private ImageView mPhotoButton;
    Uri imgUri;
    StorageReference mStorageReference;
//    @BindView(R.id.fullName) EditText fullname;
//    @BindView(R.id.email) EditText email;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView= inflater.inflate(R.layout.activity_profile_edit, null);
        mPhotoButton=(ImageView)rootView.findViewById(R.id.viewImageeeeee);
        mStorageReference= FirebaseStorage.getInstance().getReference();
    //  ButterKnife.bind(this,rootView);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();
        final StorageReference filepath=mStorageReference.child("App_Images").child(imgUri.getLastPathSegment());
       UploadTask uploadTask = filepath.putFile(imgUri);

        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
            @Override
            public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                if (!task.isSuccessful()) {
                    throw task.getException();
                }
                    filepath.getDownloadUrl();
                // Continue with the task to get the download URL
                return filepath.getDownloadUrl();
            }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
            @Override
            public void onComplete(@NonNull Task<Uri> task) {
                if (task.isSuccessful()) {
                    Uri downloadUri = task.getResult();




                } else {
                    // Handle failures
                    // ...
                }
            }
        });

return rootView;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

if(requestCode==PHOTO_PICKER &&resultCode==RESULT_OK){

     imgUri=data.getData();
mPhotoButton.setImageURI(imgUri);

}

    }
}
