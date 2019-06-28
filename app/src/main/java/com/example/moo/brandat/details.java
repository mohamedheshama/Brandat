package com.example.moo.brandat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.Map;
import java.util.Scanner;

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
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    FloatingActionButton editActivity;

     String name,cat,cos,em,im,loc,ow,pcas,fon,uImg,uId,prodescribe,product_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();

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
        mAuth=FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
        editActivity=(FloatingActionButton)findViewById(R.id.edit_floating_action_button);

         Intent intent = getIntent();
        if (intent.hasExtra("fname")) {
            name = intent.getStringExtra("fname");
            cat = intent.getStringExtra("category");
            cos = intent.getStringExtra("cost");
            em = intent.getStringExtra("email");
            im = intent.getStringExtra("img");
            loc = intent.getStringExtra("location");
            ow = intent.getStringExtra("owner");
            pcas = intent.getStringExtra("pcase");
            fon = intent.getStringExtra("phone");
            uImg = intent.getStringExtra("img_url");
            uId = intent.getStringExtra("user_id");
            prodescribe = intent.getStringExtra("prodescribe");
            product_key = intent.getStringExtra("product_key");
            setUp();
        }else{
            String productId=intent.getStringExtra("productId");
            String userId=intent.getStringExtra("userId");
            mDatabaseReference.child("userss").child(userId).child("products").child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    products product =dataSnapshot.getValue(products.class);
                    name = product.getFname();
                    cat = product.getCategory();
                    cos = product.getCost();
                    em = product.getEmail();
                    im = product.getImgesrc();
                    loc = product.getLocation();
                    ow = product.getOwnername();
                    pcas = product.getproduct_case();
                    fon = product.getPhone();
                    uImg = product.getImg_url();
                    uId = product.getUser_id();
                    prodescribe = product.getproduct_des();
                    product_key = product.getProduct_key();
                    setUp();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


        client = LocationServices.getFusedLocationProviderClient(details.this);
        requestPermission();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.more);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                // TODO: code from  firebase from product location
                DatabaseReference s = mDatabaseReference.child("userss").child(uId).child("products").child(product_key);

                s.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Intent intent1=new Intent(details.this, Main_map.class);
                        double l=30.00351;
                        double g= 30.053748;



                        String location=dataSnapshot.child("location").getValue(String.class);
                        if (location!=null) {
                            try {
                                Scanner input = new Scanner(location);

                                l = input.nextDouble();
                                g = input.nextDouble();
                                Log.d("mano", "onClick: " + location + "  l=" + l + "  g" + g);

                                String ls = "" + l;
                                String gs = "" + g;
                                intent1.putExtra("long", ls);
                                intent1.putExtra("lat", gs);
                                startActivity(intent1);
                            }catch (Exception ex){
                                Log.d("mano", "onDataChange: error in location"+location);
                            }
                        }

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });



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



        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("tesssssssssssst", "onClick: "+uId+"         "+mCurrentUser.getUid());
                if(mCurrentUser.getUid().equals(uId)){

                    Intent intent1=new Intent(details.this,my_profile.class);
                    startActivity(intent1);


                }else {
                    Intent intent2 = new Intent(details.this, UserProfile.class);
                    intent2.putExtra("UserId", uId);
                    intent2.putExtra("img_url", uImg);

                    startActivity(intent2);
                }
            }
        });
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.chat);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), ChatActivity.class);
                intent.putExtra(getString(R.string.key_chat_name_reciever),ow);
                intent.putExtra(getString(R.string.key_chat_uid_reciever),uId);
                intent.putExtra(getString(R.string.key_of_img_url_user_recieve),uImg);
                startActivity(intent);
            }
        });



        editActivity.hide();
        if(mCurrentUser.getUid().equals(uId)) {

            editActivity.show();

            editActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(details.this, UpdateProduct.class);

                    // String fname = products.getFname();
                    intent.putExtra("fname", name);
                    // String category = products.getCategory();
                    intent.putExtra("category", cat);
                    //   String cost = products.getCost();
                    intent.putExtra("cost", cos);
                    // String email = products.getEmail();
                    intent.putExtra("email", em);
                    // String img = products.getImg_src();
                    intent.putExtra("img", im);
                    //  String location = products.getLocation();
                    intent.putExtra("location", loc);
                    // String owner = products.getOwnername();
                    intent.putExtra("owner", ow);
                    //  String pcase = products.getproduct_case();
                    intent.putExtra("pcase", pcas);
                    // String phone = products.getPhone();
                    intent.putExtra("phone", fon);
                    intent.putExtra("user_id", uId);
                    intent.putExtra("prodescribe", prodescribe);
                    intent.putExtra("product_key",product_key);

                    //String userImg = products.getPhone();
                    intent.putExtra("img_url", uImg);
                    //  Log.d("imgitem", "onImageClick: "+products.getImg_url());
                    startActivity(intent);
                    //  Toast.makeText(getActivity(),""+products.getCategory(),Toast.LENGTH_SHORT).show();


                }
            });
        }

    }

    private void setUp() {
        fname.setText(name);
        category.setText(cat);
        casee.setText(pcas);
        pdescribe.setText(prodescribe);
        cost.setText(cos);
        email.setText(em);
        location.setText(loc);
        ownername.setText(ow);
        phone.setText(fon);
        Picasso.with(this).load(im) .resize(200, 200)
                .centerCrop().into(imageView);
        Picasso.with(this).load(uImg).into(circularImageView);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


    }
}
