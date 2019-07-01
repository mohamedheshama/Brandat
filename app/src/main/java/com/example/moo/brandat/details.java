package com.example.moo.brandat;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.support.v7.app.AlertDialog;
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

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
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
     FloatingActionButton fab3;
    TextView reportTextView;
    ValueEventListener s;
    String name,cat,cos,em,im,loc,ow,pcas,fon,uImg,uId,prodescribe,product_key;
    FloatingActionButton like;
    FloatingActionButton favs;
TextView noLikes;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabasecat;
    DatabaseReference productsData;
    DatabaseReference categoriesData;
    ArrayList<String>likes;    ArrayList<String>favour;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
noLikes=(TextView) findViewById(R.id.nolikes);
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();
likes=new ArrayList<>();
favour=new ArrayList<>();
        fname = (TextView) findViewById(R.id.fullName);
        category = (TextView) findViewById(R.id.category);
        casee = (TextView) findViewById(R.id.case2);
        cost = (TextView) findViewById(R.id.cost);
        ownername = (TextView) findViewById(R.id.ownerName);
        pdescribe = (TextView) findViewById(R.id.descripe);
     //   location = (TextView) findViewById(R.id.location);
        phone = (TextView) findViewById(R.id.phone);
        email = (TextView) findViewById(R.id.publish_date);
        imageView = findViewById(R.id.viewImage);
        circularImageView = findViewById(R.id.circularImageView);
        like=(FloatingActionButton) findViewById(R.id.like);
        favs=(FloatingActionButton) findViewById(R.id.fav);


        mAuth=FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
        editActivity=(FloatingActionButton)findViewById(R.id.edit_floating_action_button);
         fab3 = (FloatingActionButton) findViewById(R.id.reporting);
          reportTextView=(TextView)findViewById(R.id.text_report);

        mDatabase= FirebaseDatabase.getInstance().getReference().child("userss").child(mCurrentUser.getUid());

         final Intent intent = getIntent();
        mDatabasecat= FirebaseDatabase.getInstance().getReference().child("categories");

        if (intent.hasExtra("fname")) {
            name = intent.getStringExtra("fname");
            cat = intent.getStringExtra("category");
            cos = intent.getStringExtra("cost");
            em = intent.getStringExtra("time");
            im = intent.getStringExtra("img");
            loc = intent.getStringExtra("location");

            ow = intent.getStringExtra("owner");
            pcas = intent.getStringExtra("pcase");
            fon = intent.getStringExtra("quantity");
            uImg = intent.getStringExtra("img_url");
            uId = intent.getStringExtra("user_id");
            prodescribe = intent.getStringExtra("prodescribe");
            product_key = intent.getStringExtra("product_key");
            Log.d("mano", "onCreate: product key from intent "+product_key);
            //likes=intent.getStringArrayListExtra("likes");

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
                    em = product.getTime();
                    im = product.getImgesrc();
                    loc = product.getLocation();
                    ow = product.getOwnername();
                    pcas = product.getproduct_case();
                    fon = product.getQuantity();
                    uImg = product.getImg_url();
                    uId = product.getUser_id();
                    prodescribe = product.getproduct_des();
                    product_key = product.getProduct_key();
                    Log.d("mano", "onCreate: product key from firebase "+product_key);

                    setUp();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }



        mDatabase= FirebaseDatabase.getInstance().getReference().child("userss").child(uId);
        mDatabasecat= FirebaseDatabase.getInstance().getReference().child("categories");
        final DatabaseReference favv=FirebaseDatabase.getInstance().getReference().child("userss").child(mCurrentUser.getUid());

        final DatabaseReference path1=favv.child("favs");
        final DatabaseReference path2=favv.child("favs").child(product_key);



        path1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
//likes.clear();
                int i=0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    favour.add(postSnapshot.getKey());

                    if(favour.get(i).equals(product_key)){
                        favs.setImageResource(R.drawable.ic_star_on_24dp);

                    }i++;
                    Log.d("errrror", "onCancelledd: "+favour.get(0));


                }}

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("errrror", "onCancelled: "+"likes error");
            }
        });





        favs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final List<String> newfavs=new ArrayList<>();

                path1.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            newfavs.add(postSnapshot.getKey());


                        }}

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("errrror", "onCancelled: "+"likes error");
                    }
                });

                path2.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String value=dataSnapshot.getValue(String.class);

                        if (newfavs.contains(product_key)&&value!=null) {


                            dataSnapshot.getRef().removeValue();
                           // categoriesData.child(mCurrentUser.getUid()).getRef().removeValue();
                            favs.setImageResource(R.drawable.ic_star_off_24dp);
                            //likes.remove(mCurrentUser.getUid());
                            Toast.makeText(getApplicationContext(), "done2", Toast.LENGTH_SHORT).show();

                        } else {
                            path2.setValue(cat);
                           // categoriesData.child(mCurrentUser.getUid()).setValue("true");
                            favs.setImageResource(R.drawable.ic_star_on_24dp);
                            Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();

                        }

                        // dataSnapshot.child(mCurrentUser.getUid()).getRef().removeValue();


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                    }
                });

            }
        });









        productsData=mDatabase.child("products").child(product_key).child("likes");
        String  key=productsData.getKey();
        categoriesData=mDatabasecat.child(cat).child(product_key).child("likes");
        final DatabaseReference path=productsData.child(mCurrentUser.getUid());
        Log.d("userrrrr id", "onCreate: "+mCurrentUser.getUid());

        productsData.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
//likes.clear();
                int i=0;
                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    likes.add(postSnapshot.getKey());

                    if(likes.get(i).equals(mCurrentUser.getUid())){
                        like.setImageResource(R.drawable.like6);

                    }i++;
                    Log.d("errrror", "onCancelledd: "+likes.get(0));


                }
            noLikes.setText(""+i);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("errrror", "onCancelled: "+"likes error");
            }
        });
        if(likes.contains(mCurrentUser.getUid())){

            like.setImageResource(R.drawable.like6);
            Log.d("errrror", "onCancelledddddd: "+likes.get(0));
            Log.d("errrror", "onCancelledddddddddddddddddddddddd: "+mCurrentUser.getUid());

        }

        like.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {


                                        final List<String> newLikes=new ArrayList<>();

                                        productsData.addValueEventListener(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                                                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                    newLikes.add(postSnapshot.getKey());


                                                }
                                            noLikes.setText(""+newLikes.size());
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Log.d("errrror", "onCancelled: "+"likes error");
                                            }
                                        });

                                        path.addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                 String value=dataSnapshot.getValue(String.class);

                                                if (newLikes.contains(mCurrentUser.getUid())&&value!=null) {


                                                    dataSnapshot.getRef().removeValue();
                                                    categoriesData.child(mCurrentUser.getUid()).getRef().removeValue();
                                                    like.setImageResource(R.drawable.like3);
                                                    //likes.remove(mCurrentUser.getUid());
                                                    Toast.makeText(getApplicationContext(), "done2", Toast.LENGTH_SHORT).show();

                                                    final List<String> newLikes=new ArrayList<>();

                                                    productsData.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                                newLikes.add(postSnapshot.getKey());


                                                            }
                                                            noLikes.setText(""+newLikes.size());
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                            Log.d("errrror", "onCancelled: "+"likes error");
                                                        }
                                                    });




                                                } else {
                                                    path.setValue("true");
                                                    categoriesData.child(mCurrentUser.getUid()).setValue("true");
                                                    like.setImageResource(R.drawable.like6);
                                                    Toast.makeText(getApplicationContext(), "done", Toast.LENGTH_SHORT).show();
                                                    final List<String> newLikes=new ArrayList<>();

                                                    productsData.addValueEventListener(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                                                            for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                                                                newLikes.add(postSnapshot.getKey());


                                                            }
                                                            noLikes.setText(""+newLikes.size());
                                                        }

                                                        @Override
                                                        public void onCancelled(@NonNull DatabaseError databaseError) {
                                                            Log.d("errrror", "onCancelled: "+"likes error");
                                                        }
                                                    });


                                                }

                                                // dataSnapshot.child(mCurrentUser.getUid()).getRef().removeValue();


                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {
                                                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_LONG).show();

                                            }
                                        });

                                    }
                                });

















        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mano", "onClick: doneeee");
                if (reportTextView.getText().toString().equals("report")) {
                    reportTextView.setText("reported");
                    Log.d("mano", "onClick: "+uId+"  set value true");

                    final CharSequence[] items = { "محتوي احتيالي", "مكان الاعلان غير مناسب", "اعلان غير اخلاقي", "مبيعات غير مسرح بها" };

                    AlertDialog.Builder builder = new AlertDialog.Builder(details.this);
                    builder.setTitle("Make your selection");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            // will toast your selection


                            mDatabaseReference
                                    .child("userss")
                                    .child(uId)
                                    .child("products")
                                    .child(product_key)
                                    .child("reporting")
                                    .child(MainActivity.usernameId)
                                    .setValue(String.valueOf(item));
                            mDatabaseReference
                                    .child("categories")
                                    .child(cat)
                                    .child(product_key)
                                    .child("reporting")
                                    .child(MainActivity.usernameId)
                                    .setValue(String.valueOf(item));
                            fab3.setImageResource(R.drawable.ic_report_green_24dp);
                            Log.d("mano", "onClick: ");
                            dialog.dismiss();

                        }
                    }).show();

                }else {
                    reportTextView.setText("report");
                    Log.d("mano", "onClick: "+"  remove vlaue");
                    mDatabaseReference
                            .child("userss")
                            .child(uId)
                            .child("products")
                            .child(product_key)
                            .child("reporting")
                            .child(MainActivity.usernameId)
                            .removeValue();
                    mDatabaseReference
                            .child("categories")
                            .child(cat)
                            .child(product_key)
                            .child("reporting")
                            .child(MainActivity.usernameId)
                            .removeValue();
                    fab3.setImageResource(R.drawable.ic_report_black_24dp);
                }
            }
        });
        client = LocationServices.getFusedLocationProviderClient(details.this);
        requestPermission();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.more);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

if (loc !=null) {
    if(loc.isEmpty()){
        Toast.makeText(details.this, "rrrrrrrrrrrrrr", Toast.LENGTH_SHORT).show();
        alertOneButton();


    }else{
    Toast.makeText(details.this, "ll", Toast.LENGTH_SHORT).show();


double l;
double g;

                    Scanner input = new Scanner(loc);
Intent intent1=new Intent(details.this,Main_map.class);
String []ss=loc.split(" ");

                    g = Double.parseDouble (ss[0]);
                    l = Double.parseDouble (ss[1]);
                    Toast.makeText(details.this, ""+l+"kkkkk"+g, Toast.LENGTH_SHORT).show();
                    String ls = "" + l;
                    String gs = "" + g;
                    intent1.putExtra("long", ls);
                    intent1.putExtra("lat", gs);
                    startActivity(intent1);

            }





}
else {
    alertOneButton();
}
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
                    intent2.putExtra((getString(R.string.key_chat_name_reciever)),ow);

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
                    intent.putExtra("time", em);
                    // String img = products.getImg_src();
                    intent.putExtra("getImg_src", im);
                    //  String location = products.getLocation();
                    intent.putExtra("location", loc);
                    // String owner = products.getOwnername();
                    intent.putExtra("owner", ow);
                    //  String pcase = products.getproduct_case();
                    intent.putExtra("pcase", pcas);
                    // String phone = products.getPhone();
                    intent.putExtra("quantity", fon);
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

    public void alertOneButton() {

        new AlertDialog.Builder(details.this)
                .setTitle("NO location")
                .setMessage("this user don't add location")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }


    private void setUp() {
        fname.setText(name);
        category.setText(cat);
        casee.setText(pcas);
        pdescribe.setText(prodescribe);
        cost.setText(cos+" .LE");
        email.setText(em);
       // location.setText(loc);
        ownername.setText(ow);
        phone.setText(fon);
        Picasso.with(this).load(im) .resize(200, 200)
                .centerCrop().into(imageView);
        Picasso.with(this).load(uImg).into(circularImageView);
        mDatabaseReference
                .child("userss")
                .child(uId)
                .child("products")
                .child(product_key)
                .child("reporting")
                .child(MainActivity.usernameId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.getValue()!=null)){
                    Log.d("mano", "onDataChange:one other is reported before me ");
                    fab3.setImageResource(R.drawable.ic_report_green_24dp);
                    reportTextView.setText("reported");

                }else {
                    Log.d("mano", "onDataChange: no one reported you before");
                    fab3.setImageResource(R.drawable.ic_report_black_24dp);
                    reportTextView.setText("report");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        checkForProduct(uId,product_key,cat);
    }

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


    }
    @Override
    protected void onPause() {
        super.onPause();
        mDatabaseReference.child("userss").child(MainActivity.usernameId).child("state").setValue("offline");

    }

    @Override
    protected void onResume() {
        super.onResume();
        mDatabaseReference.child("userss").child(MainActivity.usernameId).child("state").setValue("online");


    }

    private void checkForProduct(final String userId, final String productKey, final String categories) {
        Log.d("mano", "checkForProduct: "+userId+"  "+productKey+"  "+categories);
        mDatabaseReference.child("userss").child(userId).child("products").child(productKey).child("reporting").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int arr[] = new int[4];
                arr[0] = 0;
                arr[1] = 0;
                arr[2]= 0;
                arr[3]= 0;

                for (DataSnapshot dataReport : dataSnapshot.getChildren()) {
                    Log.d("mano", "onDataChange reported : "+dataReport.getKey()+"  parent key  "+dataSnapshot.getKey());

                    if (dataReport.getValue(String.class).equals("0")) {
                        Log.d("mano", "onDataChange: reported  0");
                        arr[0]++;

                    } else if (dataReport.getValue(String.class).equals("1")) {
                        Log.d("mano", "onDataChange: reported  1");
                        arr[1]++;
                    } else if (dataReport.getValue(String.class).equals("2")) {
                        Log.d("mano", "onDataChange: reported  2");
                        arr[2]++;
                    } else if (dataReport.getValue(String.class).equals("3")) {
                        Log.d("mano", "onDataChange: reported 3");
                        arr[3]++;
                    }


                    Log.d("mano", "onDataChange: reported  " + arr[0] + "  " + arr[1]);
                }
                if (arr[0] >= 5) {
                    Log.d("mano", "onDataChange: " + userId);
                    deleteProduct(userId, productKey, categories);
                    goToHome();
                } else if (arr[1] >= 25) {
                    deleteProduct(userId, productKey, categories);
                    goToHome();
                } else if (arr[2] >= 15) {
                    deleteProduct(userId, productKey, categories);
                    goToHome();
                } else if (arr[3] >= 10) {
                    deleteProduct(userId, productKey, categories);
                    goToHome();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
    void deleteProduct(String userId,String productKey,String categories){
        mDatabaseReference.child("categories").child(categories).child(productKey).removeValue();
        mDatabaseReference.child("userss").child(userId).child("products").child(productKey).removeValue();
    }


    private void goToHome() {
        Intent intent=new Intent(details.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();



    }

}


