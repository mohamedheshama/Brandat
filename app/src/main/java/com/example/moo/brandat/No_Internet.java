package com.example.moo.brandat;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.List;

public class No_Internet extends AppCompatActivity {
    private static final int PHOTO_PICKER = 2;
    CircularImageView nav_head_account_image;
    List<model> moviesList;
    FirebaseAuth mAuth;
    TextView user,signed_in;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference mDatabaseReference;
    public static String usernameId ,usernameUser,userImageUrl;
    //android:onClick="onclick"

Button b;
    StorageReference mstorStorageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.no_internet_connection);
b=(Button)findViewById(R.id.conStatusBtn);


b.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        if(isNetworkAvailable()){
            Intent intent =new Intent(No_Internet.this,splash.class);
            //    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP );
            //  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);


            startActivity(intent);
//internt connect
        }else{

            Toast.makeText(getApplicationContext(),"No internet connection",Toast.LENGTH_SHORT).show();


// no network
//you can show image here by adding layout and set visibility gone and when no connection set visible


        }




    }
});

    }


    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


}