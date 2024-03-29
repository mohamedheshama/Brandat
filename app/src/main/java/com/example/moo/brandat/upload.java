package com.example.moo.brandat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import static android.app.Activity.RESULT_OK;

public class upload  extends Fragment {

TextView publish;
    EditText fname;EditText category;EditText casee;EditText cost;EditText pdescribe;EditText location;EditText ownername;EditText phone;EditText email;
    ImageView imageView;
    Uri mImgUri=null;
    private static final int GALLARY_REQUEST=1;
    private StorageReference mStorageReference;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabasecat;
    private DatabaseReference mDatabaseuser;
    private static final int PHOTO_PICKER = 2;


    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_upload, null);

        fname=(EditText)rootView.findViewById(R.id.fullName);
        category=(EditText)rootView.findViewById(R.id.category);
        casee=(EditText)rootView.findViewById(R.id.casee);
        cost=(EditText)rootView.findViewById(R.id.cost);
        ownername=(EditText)rootView.findViewById(R.id.ownerName);
        pdescribe=(EditText)rootView.findViewById(R.id.descripe);
        location=(EditText)rootView.findViewById(R.id.location);
        phone=(EditText)rootView.findViewById(R.id.phoneNumber);
        email=(EditText)rootView.findViewById(R.id.email);
        publish=(TextView) rootView.findViewById(R.id.textView6);
        imageView=(ImageView) rootView.findViewById(R.id.viewImage);
        mProgress=new ProgressDialog(rootView.getContext());
mStorageReference= FirebaseStorage.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
mDatabase= FirebaseDatabase.getInstance().getReference().child("userss").child(mCurrentUser.getUid());

        mDatabasecat= FirebaseDatabase.getInstance().getReference().child("categories");
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

publish.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        publish();

    }
});
        return rootView;
    }
//    public void uploadimge(View v){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        //  setResult(RESULT_OK,intent);
//
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PHOTO_PICKER);
//    }
    private void publish(){
        final String pname=fname.getText().toString().trim();
        final String categories=category.getText().toString().trim();
        final String caseee=casee.getText().toString().trim();
        final String costs=cost.getText().toString().trim();
        final String oname=ownername.getText().toString().trim();
        final String prodes=pdescribe.getText().toString().trim();
      final  String loc=location.getText().toString().trim();
        final String fone=phone.getText().toString().trim();
        final String emai=email.getText().toString().trim();
mProgress.setMessage("Posting ...");
mProgress.show();

  final StorageReference filepath=mStorageReference.child("App_Images").child(mImgUri.getLastPathSegment());
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

final DatabaseReference productsData=mDatabase.child("products").push();
String key=productsData.getKey();
                    final DatabaseReference categoriesData=mDatabasecat.child(categories).child(key);

                    mDatabase.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
productsData.child("product_name").setValue(pname);
                            productsData.child("category").setValue(categories);
                            productsData.child("product_case").setValue(caseee);
                            productsData.child("cost").setValue(costs);
                            productsData.child("ownername").setValue(oname);
                            productsData.child("product_des").setValue(prodes);
                            productsData.child("location").setValue(loc);
                            productsData.child("phone").setValue(fone);
                            productsData.child("email").setValue(emai);
                            productsData.child("imagesrc").setValue(downloadUri.toString());
                            productsData.child("img_url").setValue(mAuth.getCurrentUser().getPhotoUrl().toString());
                            productsData.child("user_id").setValue(mAuth.getCurrentUser().getUid());



                            categoriesData.child("product_name").setValue(pname);
                            categoriesData.child("category").setValue(categories);
                            categoriesData.child("product_case").setValue(caseee);
                            categoriesData.child("cost").setValue(costs);
                            categoriesData.child("ownername").setValue(oname);
                            categoriesData.child("product_des").setValue(prodes);
                            categoriesData.child("location").setValue(loc);
                            categoriesData.child("phone").setValue(fone);
                            categoriesData.child("email").setValue(emai);
                            categoriesData.child("imagesrc").setValue(downloadUri.toString());
                            categoriesData.child("img_url").setValue(mAuth.getCurrentUser().getPhotoUrl().toString());

                            categoriesData.child("user_id").setValue(mAuth.getCurrentUser().getUid());

                            mProgress.dismiss();
                            Toast.makeText(getContext(),"Your ptoduct's data has inserted succesfully.",Toast.LENGTH_LONG ).show();

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getContext(),"Error",Toast.LENGTH_LONG ).show();
                        }
                    });


                } else {
 mProgress.dismiss();

                    Toast.makeText(getContext(),"task not succesful",Toast.LENGTH_LONG ).show();

                    // Handle failures
                    // ...
                }
            }
        });








    }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==PHOTO_PICKER &&resultCode==RESULT_OK){

            mImgUri=data.getData();
            imageView.setImageURI(mImgUri);

        }


}}
