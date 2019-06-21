package com.example.moo.brandat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moo.brandat.chat.ChatActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.Collections;

public class details extends AppCompatActivity {
    TextView fname;
    TextView category;
    TextView casee;
    TextView cost;
    TextView pdescribe;
    TextView location;
    TextView ownername;
    TextView phone;
    TextView email;
    ImageView imageView;
    CircularImageView circularImageView;
    private FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        fname = (TextView) findViewById(R.id.fullName);
        category = (TextView) findViewById(R.id.category);
        casee = (TextView) findViewById(R.id.case2);
        cost = (TextView) findViewById(R.id.cost);
        ownername = (TextView) findViewById(R.id.ownerName);
        pdescribe = (TextView) findViewById(R.id.descripe);
        location = (TextView) findViewById(R.id.location);
        phone = (TextView) findViewById(R.id.phone);
        email = (TextView) findViewById(R.id.email);
        imageView = findViewById(R.id.viewImage);
        circularImageView = findViewById(R.id.circularImageView);


        final Intent intent = getIntent();
        String name = intent.getStringExtra("fname");
        String cat = intent.getStringExtra("category");
        String cos = intent.getStringExtra("cost");
        String em = intent.getStringExtra("email");
        String im = intent.getStringExtra("img");
        String loc = intent.getStringExtra("location");
        String ow = intent.getStringExtra("owner");
        String pcas = intent.getStringExtra("pcase");
        String fon = intent.getStringExtra("phone");
        final String uImg = intent.getStringExtra("img_url");
        final String uId = intent.getStringExtra("user_id");

        Log.d("imgitem", "onImageClick: " + uImg);

        fname.setText(name);
        category.setText(cat);
        cost.setText(cos);
        email.setText(em);
        location.setText(loc);
        ownername.setText(ow);
        casee.setText(pcas);
        phone.setText(fon);

        fname.setText(name);
        client = LocationServices.getFusedLocationProviderClient(details.this);
        requestPermission();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.more);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent1=new Intent(details.this, Main_map.class);
                double l=30.00351;
                double g= 30.053748;
                String ls=""+l;
                String gs=""+g;
                intent1.putExtra("long", ls);
                intent1.putExtra("lat", gs);
                startActivity(intent1);
            }
      });
//        phone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View w) {
//
//                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
//                    Intent intent3 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                    startActivity(intent3);
//                    if (ActivityCompat.checkSelfPermission(details.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
//                    client.getLastLocation().addOnSuccessListener(details.this, new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            if (location !=null){
//                                double l= location.getLatitude();
//                                double g=location.getLongitude();
//                                String ls=""+l;
//                                String gs=""+g;
//                                Toast.makeText(details.this, ls, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(details.this, gs, Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });
//
//
//                }
//else {
//                    if (ActivityCompat.checkSelfPermission(details.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO: Consider calling
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
//                    client.getLastLocation().addOnSuccessListener(details.this, new OnSuccessListener<Location>() {
//                        @Override
//                        public void onSuccess(Location location) {
//                            if (location != null) {
//                                double l = location.getLongitude();
//                                double g = location.getLatitude();
//                                String ls = "" + l;
//                                String gs =  ""+ g;
//                                Toast.makeText(details.this, ls, Toast.LENGTH_SHORT).show();
//                                Toast.makeText(details.this, gs, Toast.LENGTH_SHORT).show();
//
//                            }
//                        }
//                    });
//
//
////                    Intent intent2=new Intent(details.this, map_auto.class);
////                    startActivity(intent2);
//
//                }
//                if (ActivityCompat.checkSelfPermission(details.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
//                    // TODO: Consider calling
//                    //    ActivityCompat#requestPermissions
//                    // here to request the missing permissions, and then overriding
//                   //  public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                 //                                             int[] grantResults)
//                    // to handle the case where the user grants the permission. See the documentation
//                    // for ActivityCompat#requestPermissions for more details.
//                    return;
//                }
//                client.getLastLocation().addOnSuccessListener(details.this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if (location !=null){
//                            double l= location.getLatitude();
//                            double g=location.getLongitude();
//                            String ls=""+l;
//                            String gs=""+g;
//
//                            Toast.makeText(details.this, ls, Toast.LENGTH_SHORT).show();
//                            Toast.makeText(details.this, gs, Toast.LENGTH_SHORT).show();
//
//                        }
//                    }
//                });
//            }
//        });
//
//
//        location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View w) {
//                Intent intent1=new Intent(details.this, Main_map.class);
//                double l=30.00351;
//                double g= 30.053748;
//                String ls=""+l;
//                String gs=""+g;
//                intent1.putExtra("long", ls);
//                intent1.putExtra("lat", gs);
//                startActivity(intent1);
//            }
//        });
//        category.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View w) {
//                Intent intent1=new Intent(details.this, map_auto.class);
//                startActivity(intent1);
//            }
//        });

        Picasso.with(this).load(im).into(imageView);
        Picasso.with(this).load(uImg).into(circularImageView);

        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(details.this,UserProfile.class);
                intent1.putExtra("UserId",uId);
                intent1.putExtra("img_url",uImg);

                startActivity(intent1);

            }
        });

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.chat);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), ChatActivity.class);
                intent.putExtra(getString(R.string.key_chat_uid_reciever),uId);
                intent.putExtra(getString(R.string.key_of_img_url_user_recieve),uImg);
                startActivity(intent);
            }
        });



    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


    }
}
