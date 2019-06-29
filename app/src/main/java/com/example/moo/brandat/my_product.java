package com.example.moo.brandat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class my_product extends AppCompatActivity implements View.OnClickListener {
    private BottomSheetBehavior bottomSheetBehavior;

    // TextView variable
    private TextView bottomSheetHeading;
    private final static int REQUEST_CODE_1 = 1;

    // Button variables
    private Button expandBottomSheetButton;
    private Button collapseBottomSheetButton;
    private Button hideBottomSheetButton;
    private Button showBottomSheetDialogButton;
    private Button go;
    private FloatingActionButton more;

    TextView fname;
    TextView category;
    TextView casee;
    TextView cost;
    TextView bottom_sheet_manual;
    TextView bottom_sheet_gps;
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
    FloatingActionButton editActivity;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    String product_key,categories_key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_sheet_bar);






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

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();

        final Intent intent = getIntent();
        final String name = intent.getStringExtra("fname");
        final String cat = intent.getStringExtra("category");
        final String cos = intent.getStringExtra("cost");
        final String em = intent.getStringExtra("email");
        final String im = intent.getStringExtra("img");
        final String loc = intent.getStringExtra("location");
        final String ow = intent.getStringExtra("owner");
        final String pcas = intent.getStringExtra("pcase");
        final String fon = intent.getStringExtra("phone");
        final String uImg = intent.getStringExtra("img_url");
        final String uId = intent.getStringExtra("user_id");
        final String prodescribe=intent.getStringExtra("prodescribe");
        final String product_key=intent.getStringExtra("product_key");
        this.product_key=product_key;
        categories_key=cat;

        fname.setText(name);
        category.setText(cat);
        casee.setText(pcas);
        pdescribe.setText(prodescribe);
        cost.setText(cos);
        email.setText(em);
        location.setText(loc);
        ownername.setText(ow);
        phone.setText(fon);
        fname.setText(name);
        client = LocationServices.getFusedLocationProviderClient(my_product.this);
        requestPermission();


        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                Intent intent1=new Intent(my_product.this, Main_map.class);
                double l=30.00351;
                double g= 30.053748;
                String ls=""+l;
                String gs=""+g;
                intent1.putExtra("long", ls);
                intent1.putExtra("lat", gs);
                startActivity(intent1);
            }
        });
        initViews();
        initListeners();
           FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.more);
    fab1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    });


        Picasso.with(this).load(im).into(imageView);
        Picasso.with(this).load(uImg).into(circularImageView);

        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.d("tesssssssssssst", "onClick: "+uId+"         "+mCurrentUser.getUid());
                if(mCurrentUser.getUid().equals(uId)){

                    Intent intent1=new Intent(my_product.this,my_profile.class);
                    startActivity(intent1);


                }else {
                    Intent intent2 = new Intent(my_product.this, UserProfile.class);
                    intent2.putExtra("UserId", uId);
                    intent2.putExtra("img_url", uImg);

                    startActivity(intent2);
                }
            }
        });


        editActivity.hide();
        if(mCurrentUser.getUid().equals(uId)) {

            editActivity.show();

            editActivity.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    Intent intent = new Intent(my_product.this, UpdateProduct.class);

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
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


    }


    private void initViews() {


        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayoutlocation));


       bottom_sheet_manual= (TextView) findViewById(R.id.location_manual);


        bottom_sheet_gps= (TextView) findViewById(R.id.gps_auto);
    }


    /**
     * method to initialize the listeners
     */
    private void initListeners() {

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottom_sheet_gps.setOnClickListener(this);
        bottom_sheet_manual.setOnClickListener(  this);

    }

    /**
     * onClick Listener to capture button click
     *
     * @param v
     */
    //@Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.location_manual:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                Intent intent1=new Intent(my_product.this, map_auto.class);

                startActivityForResult(intent1,REQUEST_CODE_1);
                break;
            case R.id.gps_auto:
                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
                final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                    Intent intent3 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivity(intent3);
                    if (ActivityCompat.checkSelfPermission(my_product.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    client.getLastLocation().addOnSuccessListener(my_product.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location !=null){
                                double l= location.getLatitude();
                                double g=location.getLongitude();
                                String ls=""+l;
                                String gs=""+g;
                                setMyLocationToFirebase(l,g);
                                Toast.makeText(my_product.this, ls, Toast.LENGTH_SHORT).show();
                                Toast.makeText(my_product.this, gs, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


                }
                else {
                    if (ActivityCompat.checkSelfPermission(my_product.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        client.getLastLocation().addOnSuccessListener(my_product.this, new OnSuccessListener<Location>() {
                            @Override
                            public void onSuccess(Location location) {
                                if (location !=null){
                                    double l= location.getLatitude();
                                    double g=location.getLongitude();
                                    String ls=""+l;
                                    String gs=""+g;
                                    setMyLocationToFirebase(l,g);
                                    Toast.makeText(my_product.this, ls, Toast.LENGTH_SHORT).show();
                                    Toast.makeText(my_product.this, gs, Toast.LENGTH_SHORT).show();

                                }
                            }
                        });

                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    client.getLastLocation().addOnSuccessListener(my_product.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location != null) {
                                double l = location.getLongitude();
                                double g = location.getLatitude();
                                String ls = "" + l;
                                String gs =  ""+ g;
                                setMyLocationToFirebase(l,g);

                                Toast.makeText(my_product.this, ls, Toast.LENGTH_SHORT).show();
                                Toast.makeText(my_product.this, gs, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });


//                    Intent intent2=new Intent(details.this, map_auto.class);
//                    startActivity(intent2);

                }
                if (ActivityCompat.checkSelfPermission(my_product.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                    client.getLastLocation().addOnSuccessListener(my_product.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            if (location !=null){
                                double l= location.getLatitude();
                                double g=location.getLongitude();
                                String ls=""+l;
                                String gs=""+g;
                                setMyLocationToFirebase(l,g);

                                Toast.makeText(my_product.this, ls, Toast.LENGTH_SHORT).show();
                                Toast.makeText(my_product.this, gs, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //  public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                             int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                client.getLastLocation().addOnSuccessListener(my_product.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location !=null){
                            double l= location.getLatitude();
                            double g=location.getLongitude();
                            String ls=""+l;
                            String gs=""+g;
                            setMyLocationToFirebase(l,g);

                            Toast.makeText(my_product.this, ls, Toast.LENGTH_SHORT).show();
                            Toast.makeText(my_product.this, gs, Toast.LENGTH_SHORT).show();

                        }
                    }
                });


                break;


        }
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
    private void setMyLocationToFirebase(double l, double g) {
        String myLocation=l+" "+g;
        mDatabaseReference.child("userss").child(MainActivity.usernameId).child("products").child(product_key).child("location").setValue(myLocation);


         mDatabaseReference.child("categories").child(categories_key).child(product_key).child("location").setValue(myLocation);
        Log.d("mano", "setMyLocationToFirebase: done");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent dataIntent) {
        super.onActivityResult(requestCode, resultCode, dataIntent);

        // The returned result data is identified by requestCode.
        // The request code is specified in startActivityForResult(intent, REQUEST_CODE_1); method.
        switch (requestCode)
        {
            // This request code is set by startActivityForResult(intent, REQUEST_CODE_1) method.
            case REQUEST_CODE_1:

                if(resultCode == RESULT_OK)
                {
                    String messageReturn = dataIntent.getStringExtra("long");
                    String messageReturn2 = dataIntent.getStringExtra("lat");
                    Toast.makeText(this, messageReturn, Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, messageReturn2, Toast.LENGTH_SHORT).show();
                    double l = Double.parseDouble(messageReturn2);
                    double g = Double.parseDouble(messageReturn);
                    setMyLocationToFirebase(l,g);

                }
        }
    }

}
