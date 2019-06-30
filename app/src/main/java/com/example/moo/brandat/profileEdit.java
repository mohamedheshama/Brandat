package com.example.moo.brandat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

public class profileEdit extends AppCompatActivity {
    private static final int PHOTO_PICKER = 2;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
//DFDBDB  E6DCDC
    private FirebaseAuth.AuthStateListener mAuthListner;

    StorageReference mstorStorageReference;

    EditText fullname;
    EditText email;
    EditText password;
    EditText confirmpass;
    EditText phone;
    EditText location;
    Switch isShop;
    Button submit;
    TextView login;
    String img_url=null;



    CircularImageView circularImageView;
    TextView emailTV;
    TextView phoneTV;
    TextView locationTV;
    TextView desplay_name;
    FloatingActionButton editActivity;
    List<products> productsList;
    productsAdapter cardsAdapter;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseProducts;
    private DatabaseReference mDatabaseuser_info;
    private DatabaseReference mDatabaseUser;
    RecyclerView productRecycler;
    Context c;

    private StorageReference mStorageReference;



    ImageView imageView;
    Uri mImgUri=null;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_edit);

        fullname=(EditText) findViewById(R.id.fullName);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        confirmpass=(EditText) findViewById(R.id.confirmPassword);
        phone=(EditText) findViewById(R.id.phone);
        location=(EditText) findViewById(R.id.location);
        isShop=(Switch) findViewById(R.id.switch1);
        submit=(Button)findViewById(R.id.button2);
imageView=(ImageView)findViewById(R.id.viewImageeeeee);
        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("userss");
        progressDialog=new ProgressDialog(this);
        mStorageReference= FirebaseStorage.getInstance().getReference();
       location .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {

                Intent intent1=new Intent(profileEdit.this, map_auto.class);
                startActivity(intent1);
            }
        });



        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("userss");


        mDatabaseUser.keepSynced(true);

        mDatabaseuser_info = mDatabaseUser.child(mAuth.getCurrentUser().getUid());
//        mDatabaseProducts = mDatabaseuser_info.child("products");
//        mDatabaseProducts.keepSynced(true);
        mDatabaseuser_info.keepSynced(true);



        mDatabaseuser_info.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                fullname.setText((String)dataSnapshot.child("name").getValue())  ;
                location.setText((String)dataSnapshot.child("address").getValue())  ;


                if(dataSnapshot.child("phone").getValue()!=null){

                    phone.setText((String)dataSnapshot.child("phone").getValue())  ;


                }else{
//                phoneTV.setText("none")  ;

                }
                email.setText((String)dataSnapshot.child("email").getValue())  ;
                // locationTV.setText((String)dataSnapshot.child("location").getValue())  ;
                if(img_url!=null){

                    Picasso.with(c)
                            .load(img_url)
                            .resize(120, 120)
                            .centerCrop()
                            .into(circularImageView);

                }else{

                    if(mAuth.getCurrentUser().getPhotoUrl()!=null){
                        Picasso.with(c)
                                .load(mAuth.getCurrentUser().getPhotoUrl().toString())
                                .resize(120, 120)
                                .centerCrop()
                                .into(imageView);}


                    else{
                        circularImageView.setImageResource(R.mipmap.baseline_account_circle_black_48);

                    }
                }

//                Log.d("immmg", "onCreate: "+mAuth.getCurrentUser().getPhotoUrl().toString());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });










        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitVerification();
            }
        });


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                //  setResult(RESULT_OK,intent);

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PHOTO_PICKER);

            }
        });

    }

    public void submitVerification(){

        final String fname=fullname.getText().toString().trim();
//        String pass=password.getText().toString().trim();
    //    final String cpass=confirmpass.getText().toString().trim();
        final String emai=email.getText().toString().trim();

        final String fone=phone.getText().toString().trim();
        final String locash=location.getText().toString().trim();


        if(!TextUtils.isEmpty(fname)&&!TextUtils.isEmpty(emai)&&!TextUtils.isEmpty(fone)&&!TextUtils.isEmpty(locash)) {
            progressDialog.setMessage("Updating ...");
            progressDialog.show();

            if (mImgUri != null) {
                final StorageReference filepath = mStorageReference.child("App_Images").child(mImgUri.getLastPathSegment());
                UploadTask uploadTask = filepath.putFile(mImgUri);

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
                            final Uri downloadUri = task.getResult();


                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                                    .setDisplayName(fname)
                                    .setPhotoUri(downloadUri)
                                    .build();

                            user.updateProfile(profileUpdates)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {

                                                Log.d("usser", "User profile updated.");
                                                String Uid = mAuth.getCurrentUser().getUid();

                                                DatabaseReference currentUserDB = mDatabase.child(Uid);
                                                currentUserDB.child("name").setValue(fname);
                                                currentUserDB.child("email").setValue(emai);

                                                // currentUserDB.child("password").setValue(cpass);
                                                currentUserDB.child("phone").setValue(fone);
                                                currentUserDB.child("address").setValue(locash);
                                                currentUserDB.child("image_url").setValue(downloadUri.toString());

                                                boolean s=isShop.isChecked();
                                                if(s==true){

                                                    currentUserDB.child("shop").setValue("I'am shop owner");
                                                }else{
                                                    currentUserDB.child("shop").setValue("I'am not shop owner");


                                                }
                                                progressDialog.dismiss();
                                                startActivity(new Intent(profileEdit.this, my_profile.class));
                                                profileEdit.this.finish();


                                            } else {
                                                progressDialog.dismiss();
                                                Toast.makeText(profileEdit.this, "error inserting your data", Toast.LENGTH_LONG).show();
                                            }


                                        }

                                    });


                        }
                    }
                });


            } else {

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                        .setDisplayName(fname)
                        // .setPhotoUri(downloadUri)
                        .build();

                user.updateProfile(profileUpdates)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {

                                    Log.d("usser", "User profile updated.");
                                    String Uid = mAuth.getCurrentUser().getUid();

                                    DatabaseReference currentUserDB = mDatabase.child(Uid);
                                    currentUserDB.child("name").setValue(fname);
                                    currentUserDB.child("email").setValue(emai);

                                    // currentUserDB.child("password").setValue(cpass);
                                    currentUserDB.child("phone").setValue(fone);
                                    currentUserDB.child("address").setValue(locash);
                                    //currentUserDB.child("image_url").setValue(downloadUri.toString());
                                    boolean s=isShop.isChecked();
                                    if(s==true){

                                    currentUserDB.child("shop").setValue("I'am shop owner");
                                    }else{
                                        currentUserDB.child("shop").setValue("I'am not shop owner");


                                    }


                                    progressDialog.dismiss();
                                    startActivity(new Intent(profileEdit.this, my_profile.class));
                                    profileEdit.this.finish();

                                } else {
                                    progressDialog.dismiss();
                                    Toast.makeText(profileEdit.this, "error inserting your data", Toast.LENGTH_LONG).show();
                                }


                            }

                        });


            }
        }


}

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==PHOTO_PICKER &&resultCode==RESULT_OK){

            mImgUri=data.getData();
            imageView.setImageURI(mImgUri);

        }


    }

}