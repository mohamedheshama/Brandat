package com.example.moo.brandat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

public class login extends AppCompatActivity {
    EditText email;
    EditText password;
    TextView forgotPass;
    TextView signup;
    Button login;
    CheckBox isRemember;


    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListner;
    StorageReference mstorStorageReference;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        email=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        forgotPass=(TextView) findViewById(R.id.textView5);
        signup=(TextView) findViewById(R.id.textView4);
        login=(Button) findViewById(R.id.button2);
        isRemember=(CheckBox) findViewById(R.id.checkremember);
        progressDialog=new ProgressDialog(this);

        mAuth= FirebaseAuth.getInstance();

        forgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(login.this, SignUp.class));

            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitVerification();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();

      //  FirebaseUser currentUser = mAuth.getCurrentUser();

    }






    public void submitVerification(){


    String pass=password.getText().toString().trim();
     String emai=email.getText().toString().trim();




if(!TextUtils.isEmpty(emai)&&!TextUtils.isEmpty(pass)){
        progressDialog.setMessage("logging in ...");
        progressDialog.show();
    mAuth.signInWithEmailAndPassword(emai, pass)
            .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                            String Uid = mAuth.getCurrentUser().getUid();
                            DatabaseReference currentUserDB = mDatabase.child(Uid);

                        mDatabase.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if(dataSnapshot.hasChild(mAuth.getCurrentUser().getUid())){
                                    Intent main=new Intent(login.this,MainActivity.class);
                                    main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(main);


                                }
                                else{
                                    Intent main=new Intent(login.this,SignUp.class);
                                    main.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(main);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                            progressDialog.dismiss();
                          //  startActivity(new Intent(login.this, MainActivity.class));

                        } else {
                            Toast.makeText(login.this, "email or password error", Toast.LENGTH_LONG).show();
                            progressDialog.dismiss();
                        }

                    }



                });
    }







}











}
