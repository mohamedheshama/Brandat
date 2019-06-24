package com.example.moo.brandat;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moo.brandat.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

public class splash extends AppCompatActivity {
    int timeSec;
    GoogleApiClient mGoogleApiClient;
    private static final int RC_SIGN_IN =1;
    Button googlebtn;
    Button loginbtn;
    TextView later2;

    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;

    private ProgressDialog progressDialog;
private FirebaseAuth.AuthStateListener mAuthListner;


    Uri mImgUri=null;
    private static final int GALLARY_REQUEST=1;
    private StorageReference mStorageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);
        SharedPreferences pref2 = getSharedPreferences("DialogPREF", Context.MODE_PRIVATE);
        if(pref2.getBoolean("Dialog_executed", false)){

        } else {
            final Dialog dialog = new Dialog(this);

            //dialog.setTitle("Setting");

            dialog.setContentView(R.layout.layout_dialog);
            dialog.setCanceledOnTouchOutside(true);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            //Spinner spin = (Spinner)dialog.findViewById(R.id.spinner1);
            TextView ok = (TextView) dialog.findViewById(R.id.dialogButtonOK2);
            // if button is clicked, close the custom dialog
            ok.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Toast.makeText(splash.this,"sucess data " , Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            });
            TextView cancel = (TextView) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
            dialog.show();
            SharedPreferences.Editor ed = pref2.edit();
            ed.putBoolean("Dialog_executed", true);
            ed.commit();
        }

        //        radioSexGroup = (RadioGroup) findViewById(R.id.radioSex);
//        btnDisplay = (Button) findViewById(R.id.btnDisplay);
//
//        btnDisplay.setOnClickListener(new OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//
//                // get selected radio button from radioGroup
//                int selectedId = radioSexGroup.getCheckedRadioButtonId();
//
//                // find the radiobutton by returned id
//                radioSexButton = (RadioButton) findViewById(selectedId);
//
//                Toast.makeText(MyAndroidAppActivity.this,
//                        radioSexButton.getText(), Toast.LENGTH_SHORT).show();
//
//            }
//
//        });
        googlebtn=(Button)findViewById(R.id.button);
        later2=(TextView)findViewById(R.id.later);

        mAuth = FirebaseAuth.getInstance();
        progressDialog=new ProgressDialog(this);
        mDatabase= FirebaseDatabase.getInstance().getReference().child("userss");
        mStorageReference= FirebaseStorage.getInstance().getReference();
        loginbtn=(Button)findViewById(R.id.button3);
        mAuthListner=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() !=null) {





                    startActivity(new Intent(splash.this, MainActivity.class));
                    finish();

                }
//                }else {
//                    signIn();
//                }
            }
        };

        //  new Handler().postDelayed(new Runnable() {
       /* Thread splash_screen = new Thread() {
            //for not moving to any activity after 3000
            @Override
            public void run() {
                // This method will be executed once the timer is over
                timeSec = 500;
                // Start your app main activity
                try {
                    sleep(timeSec);

                } catch (Exception e) {
                    // TODO: handle exception
                    e.printStackTrace();
                } finally {

                    // it must return to main thread
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SharedPreferences share = getSharedPreferences("da", Context.MODE_PRIVATE);
                            String User = share.getString("name", null);
                            String pass = share.getString("password", null);

                            if (TextUtils.isEmpty(User)) {

//                                Intent intent = new Intent(splash.this, sign_in  .class);
//                                startActivity(intent);
//                                finish();

                            } else {

                                signIn();


                            }
                        }
                    });
                }
            }
        };

        splash_screen.start();
*/

        // You must provide a custom layout XML resource and configure at least one
// provider button ID. It's important that that you set the button ID for every provider
// that you have enabled.
       /* AuthMethodPickerLayout customLayout = new AuthMethodPickerLayout
                .Builder(R.layout.your_custom_layout_xml)
                .setGoogleButtonId(R.id.bar)
                .setEmailButtonId(R.id.foo)
                .setGithubButtonId(R.id.github)
                // ...
                .setTosAndPrivacyPolicyId(R.id.baz)
                .build();

        AuthUI.getInstance(this).createSignInIntentBuilder()
                // ...
                .setAuthMethodPickerLayout(customLayout)
                .build());

    }*/

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient =new GoogleApiClient.Builder(getApplicationContext()).enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
            @Override
            public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
            Toast.makeText(splash.this,"You got an error",Toast.LENGTH_SHORT).show();

            }
        }).addApi(Auth.GOOGLE_SIGN_IN_API,gso)
                .build();

        googlebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                progressDialog.setMessage("Signing in....");
                progressDialog.show();
                signIn();
            }
        });

    }
    private void signIn() {
        Intent signInIntent =Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account==null){
            signIn();
        }else{
            Log.e("start", "onStart: startnot signed");
        }
    }



    public void SignUp (View v){
        startActivity(new Intent(splash.this,SignUp.class));

    }
    public void LogIn (View v){
        startActivity(new Intent(splash.this,login.class));
}







    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);

            if (result.isSuccess()){
                GoogleSignInAccount account =result.getSignInAccount();
                firebaseAuthWithGoogle(account);

            }
            else{

                Log.e("result", "onStart: resylt not signed");

            }

        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
      //  Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                          //  Log.d(TAG, "signInWithCredential:success");
                          //  FirebaseUser user = mAuth.getCurrentUser();
                          //  user.get
                            mImgUri=(Uri) mAuth.getCurrentUser().getPhotoUrl();

//                            final StorageReference filepath=mStorageReference.child("App_Images").child(mImgUri.getLastPathSegment());
//                            UploadTask uploadTask = filepath.putFile(mImgUri);
//
//                            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
//                                @Override
//                                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
//                                    if (!task.isSuccessful()) {
//                                        throw task.getException();
//                                    }
//                                    filepath.getDownloadUrl();
//                                    // Continue with the task to get the download URL
//                                    return filepath.getDownloadUrl();
//                                }
//                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
//                                @Override
//                                public void onComplete(@NonNull Task<Uri> task) {
//                                    if (task.isSuccessful()) {
//                                        final Uri downloadUri = task.getResult();

                            FirebaseInstanceId.getInstance().getInstanceId()
                                    .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                                        @Override
                                        public void onComplete(@NonNull Task<InstanceIdResult> task) {

                                            if (task.isSuccessful()){
                                                DatabaseReference currentUserDB = mDatabase.child(mAuth.getCurrentUser().getUid());
                                                Log.d("mano", "onComplete: "+task.getResult().getToken());
                                                currentUserDB.child("notificationTokens").setValue(task.getResult().getToken());
                                                currentUserDB.child("name").setValue(mAuth.getCurrentUser().getDisplayName());
                                                currentUserDB.child("email").setValue(mAuth.getCurrentUser().getEmail());
                                                currentUserDB.child("phone").setValue(mAuth.getCurrentUser().getPhoneNumber());

                                                currentUserDB.child("img_url").setValue(mImgUri.toString());
                                            }
                                            // Get new Instance ID token
                                            String token = task.getResult().getToken();
                                            Log.w("mano", "getInstanceId failed  "+token);


                                        }
                                    });
                            mAuth.getCurrentUser().getIdToken(true).addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                                @Override
                                public void onComplete(@NonNull Task<GetTokenResult> task) {

                                }
                            });




                            progressDialog.dismiss();
                            startActivity(new Intent(splash.this, MainActivity.class));
                                //    }}
                             //   });
                            //   updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                         //   Log.w(TAG, "signInWithCredential:failure", task.getException());
                         //   Snackbar.make(findViewById(R.id.main_layout), "Authentication Failed.", Snackbar.LENGTH_SHORT).show();
                           // updateUI(null);
                            Toast.makeText(splash.this,"error signning with google",Toast.LENGTH_LONG).show();
                        }

                        // ...
                    }
                });
    }
    public void later(View view) {
        startActivity(new Intent(splash.this, MainActivity.class));


    }

}