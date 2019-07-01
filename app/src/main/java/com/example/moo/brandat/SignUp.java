package com.example.moo.brandat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;

public class SignUp extends AppCompatActivity {
    private static final int PHOTO_PICKER = 2;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    ImageView imageView;

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
    Uri mImgUri=null;
    private static final int GALLARY_REQUEST=1;
    private StorageReference mStorageReference;

    private DatabaseReference mDatabase;
    private Uri downloadUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);


        //  ButterKnife.bind(this);
        fullname=(EditText) findViewById(R.id.fullName);
        email=(EditText) findViewById(R.id.email);
        password=(EditText) findViewById(R.id.password);
        confirmpass=(EditText) findViewById(R.id.confirmPassword);
        phone=(EditText) findViewById(R.id.phone);
        location=(EditText) findViewById(R.id.location);
        isShop=(Switch) findViewById(R.id.switch1);
        submit=(Button)findViewById(R.id.button2);
        mAuth= FirebaseAuth.getInstance();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("userss");
        progressDialog=new ProgressDialog(this);
        email=(EditText)findViewById(R.id.email);
        imageView=(ImageView) findViewById(R.id.viewImage);
        mStorageReference= FirebaseStorage.getInstance().getReference();
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


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                submitVerification();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void submitVerification() {

        final String fname = fullname.getText().toString().trim();
        final String pass = password.getText().toString().trim();
        final String cpass = confirmpass.getText().toString().trim();
        final String emai = email.getText().toString().trim();

        final String fone = phone.getText().toString().trim();
        final String locash = location.getText().toString().trim();


        if (!TextUtils.isEmpty(fname) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(cpass) && !TextUtils.isEmpty(emai) && !TextUtils.isEmpty(fone) && !TextUtils.isEmpty(locash)) {
            Log.d("usser", "User profile updated." + emai+""+pass);
            Log.d("usser", "User profile updated."+""+pass);


            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword( emai, pass )
                    .addOnSuccessListener( new OnSuccessListener< AuthResult >(){
                        @Override
                        public void onSuccess( AuthResult authResult ){
                            final String uid = authResult.getUser().getUid();

                            DatabaseReference path = mDatabase.child(uid);
                            path.child("name").setValue(fname);
                            path.child("email").setValue(emai);
                            path.child("phone").setValue(fone);
                            path.child("address").setValue(locash);


                            Log.d("mano", "onSuccess: "+urlStr);
                            FirebaseDatabase.getInstance().getReference( "userss" )
                                    .child( uid )
                                    .child( "img_url" )
                                    .setValue( urlStr );



                        }
                    } )
                    .addOnFailureListener( new OnFailureListener(){
                        @Override
                        public void onFailure( @NonNull Exception e ){
                            Log.d("mano", "onFailure:  failll");
                        }
                    } );


        }
    }






    String urlStr;


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==PHOTO_PICKER &&resultCode==RESULT_OK){

            mImgUri=data.getData();
            Log.d("mano", "onActivityResult: "+mImgUri.toString());
            imageView.setImageURI(mImgUri);

            uploadImage();


        }


    }
    public void uploadImage(){
        final StorageReference filePath = mStorageReference.child("App_Images").child(mImgUri.getLastPathSegment());
        filePath.putFile(mImgUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Log.d("mano", "onActivityResult: "+uri.toString());
                        urlStr=uri.toString();
                    }
                });
            }
        });
    }






}
