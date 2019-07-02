package com.example.moo.brandat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class SignUp extends AppCompatActivity implements TextWatcher {
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
        login=(TextView)findViewById(R.id.textView4);
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

        fullname.addTextChangedListener(this);
        email.addTextChangedListener(this);
        password.addTextChangedListener(this);
        confirmpass.addTextChangedListener(this);
        phone.addTextChangedListener(this);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int i = 1;
                if (mImgUri != null) {
                    i=1;
                } else
                {
                    i=3;
                }
                if ( !(fullname.getText().toString().length() == 0) ){
                    fullname.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
                }else {
                    fullname.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_red,0, 0, 0);
                    i=2;

                }
                if (!(email.getText().toString().length() == 0) ){
                    email.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
                }
                else{
                    email.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_red,0, 0, 0);
                    i=2;

                }



                if (!(password.getText().toString().length() == 0)&&password.getText().toString().length()>= 8 ){
                    password.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
                }
                else{
                    password.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_red,0, 0, 0);
                    i=2;

                }

                if ( !(confirmpass.getText().toString().length() == 0) ){
                    confirmpass.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
                }
                else{
                    confirmpass.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_red,0, 0, 0);
                    i=2;

                }

                if ( !(phone.getText().toString().length() == 0) ){
                    phone.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
                }
                else {
                    phone.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_red,0, 0, 0);
                    i=2;

                }

                if (i == 1) {

                    submitVerification();

                }else if (i==3){
                    alertOnimage();
                }else {

                    alertOneButton("error in input data");

                }
            }

        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(SignUp.this, splash.class);
startActivity(i);
            }
        });


}

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    public void submitVerification() {

        final String fname = fullname.getText().toString().trim();
        String pass = password.getText().toString().trim();
        final String cpass = confirmpass.getText().toString().trim();
        final String emai = email.getText().toString().trim();

        final String fone = phone.getText().toString().trim();
        final String locash = location.getText().toString().trim();


        if (!TextUtils.isEmpty(fname) && !TextUtils.isEmpty(pass) && !TextUtils.isEmpty(cpass) && !TextUtils.isEmpty(emai) && !TextUtils.isEmpty(fone) && !TextUtils.isEmpty(locash)) {
            progressDialog.setMessage("Signing up ...");
            progressDialog.show();
            Log.d("", "User ." + emai+""+pass);
            Log.d("usser", "User "+""+pass);
           // Log.d("usser", "User " + mImgUri.toString());



            createUserAccount(fname, emai, cpass,fone,locash);

        }
    }




    private void createUserAccount(final String userName, final String email, final String password, final String fone, final String loc) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d("usser", "User created" + mImgUri);

                            updateUserInfo(userName,email,password, mImgUri, mAuth.getCurrentUser(),fone,loc);

                            if(mAuth.getCurrentUser().getPhotoUrl()!=null){
                           Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finish();
                            }else{
                                updateUserInfo(userName,email,password, mImgUri, mAuth.getCurrentUser(),fone,loc);

                            }




                        } else {






                        }
                    }
                });

    }



    private void updateUserInfo(final String userName, final String email, final String cpass, Uri imageUrl, final FirebaseUser currentUser, final String fone, final String locash) {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("App_Images");
        final StorageReference reference = storageReference.child(imageUrl.getLastPathSegment());
        Log.d("usser", "storage reference." + "user profile updated"+imageUrl);

        reference.putFile(imageUrl).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(final Uri uri) {

                        UserProfileChangeRequest profileUpdate = new UserProfileChangeRequest.Builder()
                                .setDisplayName(userName)
                                .setPhotoUri(uri)
                                .build();
                        currentUser.updateProfile(profileUpdate)
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                      //  Log.d("usser", "User updated"+ mAuth.getCurrentUser().getPhotoUrl().toString());


                                            if (task.isSuccessful()) {


                                                Log.d("usser", "User profile updated." + mAuth.getCurrentUser().getPhotoUrl().toString());
                                                Log.d("usser", "User profile updated." + userName+email+cpass+fone+locash+uri.toString());


                                                String Uid = mAuth.getCurrentUser().getUid();

                                                DatabaseReference currentUserDB = mDatabase.child(Uid);
                                                currentUserDB.child("name").setValue(userName);
                                                currentUserDB.child("email").setValue(email);

                                                currentUserDB.child("password").setValue(cpass);
                                                currentUserDB.child("phone").setValue(fone);
                                                currentUserDB.child("address").setValue(locash);
                                                currentUserDB.child("image_url").setValue(uri.toString());

                                                //currentUserDB.child("shop").setValue(isShop.isEnabled());

                                                boolean s = isShop.isChecked();
                                                if (s == true) {

                                                    currentUserDB.child("shop").setValue("I'am shop owner");
                                                } else {
                                                    currentUserDB.child("shop").setValue("I'am not shop owner");


                                                }

                                                Log.d("usser", "User profile updated." + "finish");
                                                progressDialog.dismiss();
                                              startActivity(new Intent(SignUp.this, MainActivity.class));
                                            } else {
                                                Log.d("usser", "User profile updated." + "user update error");
                                                progressDialog.dismiss();
                                            }







                                    }
                                });


                    }
                });

            }
        });

    }















  /*  private void photoAndprofile (final String fname, final String emai, final String cpass, final String fone, final String locash){





      final Uri downloaduri= uploadphoto() ;



                   FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                   UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                           .setDisplayName(fname)
                           .setPhotoUri(downloaduri)
                           .build();

                   user.updateProfile(profileUpdates)
                           .addOnCompleteListener(new OnCompleteListener<Void>() {
                               @Override
                               public void onComplete(@NonNull Task<Void> task) {
                                   if (task.isSuccessful()) {


                                       Log.d("usser", "User profile updated." + "user profile updated");


                                       String Uid = mAuth.getCurrentUser().getUid();

                                       DatabaseReference currentUserDB = mDatabase.child(Uid);
                                       currentUserDB.child("name").setValue(fname);
                                       currentUserDB.child("email").setValue(emai);

                                       currentUserDB.child("password").setValue(cpass);
                                       currentUserDB.child("phone").setValue(fone);
                                       currentUserDB.child("location").setValue(locash);
                                       currentUserDB.child("image_url").setValue(downloaduri.toString());

                                       //currentUserDB.child("shop").setValue(isShop.isEnabled());

                                       boolean s = isShop.isChecked();
                                       if (s == true) {

                                           currentUserDB.child("shop").setValue("I'am shop owner");
                                       } else {
                                           currentUserDB.child("shop").setValue("I'am not shop owner");


                                       }

                                       Log.d("usser", "User profile updated." + "finish");
                                       progressDialog.dismiss();
                                       startActivity(new Intent(SignUp.this, MainActivity.class));
                                   } else {
                                       Log.d("usser", "User profile updated." + "user update error");
                                       progressDialog.dismiss();
                                       Toast.makeText(SignUp.this, "error inserting your data", Toast.LENGTH_LONG).show();
                                   }


                               }

                           });










   }

  private Uri uploadphoto(){

      final StorageReference filepath = mStorageReference.child("App_Images").child(mImgUri.getLastPathSegment());
      UploadTask uploadTask = filepath.putFile(mImgUri);
      Log.d("usser", "User profile updated. first" + mImgUri.toString());



        Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
@Override
public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
        if (!task.isSuccessful()) {
        Log.d("usser", "User profile updated.error img" + "img ctreated");
        throw task.getException();
        }
        filepath.getDownloadUrl();
    Log.d("usser", "User profile updated.error img download uri" + filepath.getDownloadUrl());

    // Continue with the task to get the download URL
        return filepath.getDownloadUrl();
        }
        }).addOnCompleteListener(new OnCompleteListener<Uri>() {
@Override
public void onComplete(@NonNull Task<Uri> task) {
        if (task.isSuccessful()) {
      downloadUri = task.getResult();
        Log.d("usser", "User profile updated." + downloadUri);





        }else{
            Log.d("usser", "User profile updated." + "error download uri");


        }
}
        });

return downloadUri;
        }





*/

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

      //  if(requestCode==PHOTO_PICKER &&resultCode==RESULT_OK){
if(data!=null) {
    mImgUri = data.getData();
    imageView.setImageURI(mImgUri);
}
      //  }


    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
    public void alertOneButton(String message) {

        new AlertDialog.Builder(SignUp.this)
                .setTitle("error ")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }
    public void alertOnimage() {

        new AlertDialog.Builder(SignUp.this)
                .setTitle("error in input image")
                .setMessage("error in image input ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        //  setResult(RESULT_OK,intent);

                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PHOTO_PICKER);


                        dialog.cancel();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }

}
