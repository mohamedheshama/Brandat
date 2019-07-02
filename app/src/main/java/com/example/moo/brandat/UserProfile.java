package com.example.moo.brandat;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Movie;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moo.brandat.chat.ChatActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Comment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.example.moo.brandat.R.layout.list_item_article;

public class UserProfile extends AppCompatActivity {
    private static final int PHOTO_PICKER = 2;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
String img_url=null;
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
    CircularImageView circularImageView;
    TextView emailTV;
    TextView phoneTV;
    TextView addressTV;
    TextView desplay_name;
    TextView isshop;
FloatingActionButton editActivity;
    List<products> productsList;
productsAdapter cardsAdapter;
String UserId,userNameReciever;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseProducts;
    private DatabaseReference mDatabaseuser_info;
    private DatabaseReference mDatabaseUser;
RecyclerView productRecycler;
Context c;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
TextView noproducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);




        productsList = new ArrayList<>();

        mAuth=FirebaseAuth.getInstance();
mAuthListner=new FirebaseAuth.AuthStateListener() {
    @Override
    public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

        if(firebaseAuth.getCurrentUser()==null){
            Intent login=new Intent(UserProfile.this, login.class);
            login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(login);
        }
    }
};
mDatabase= FirebaseDatabase.getInstance().getReference().child("categories");
        mDatabaseUser= FirebaseDatabase.getInstance().getReference().child("userss");

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();


        mDatabase.keepSynced(true);
mDatabaseUser.keepSynced(true);

productRecycler=(RecyclerView)findViewById(R.id.prodRec);

        circularImageView=(CircularImageView)findViewById(R.id.circularImageView);
        desplay_name=(TextView)findViewById(R.id.textView11);
        emailTV=(TextView)findViewById(R.id.email);
        phoneTV=(TextView)findViewById(R.id.phonn);
        addressTV=(TextView)findViewById(R.id.address);
        isshop=(TextView)findViewById(R.id.textView12);
        noproducts=(TextView)findViewById(R.id.noproducts);




        Intent intent=getIntent();
if(intent==null) {


    mDatabaseuser_info=mDatabaseUser.child(mAuth.getCurrentUser().getUid());
    mDatabaseProducts=mDatabaseuser_info.child("products");
    mDatabaseProducts.keepSynced(true);

    getUserData();

    try {
        new fetchProducts().execute();

    } catch (Exception e) {
        Log.d("error", "error");
    }
//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
//
//        productRecycler.setHasFixedSize(true);
//        productRecycler.setLayoutManager(layoutManager);


    checkUserExist();

}else{
 UserId= intent.getStringExtra("UserId");
    img_url=intent.getStringExtra("img_url");
    if (intent.hasExtra(getString(R.string.key_chat_name_reciever))){
        userNameReciever=intent.getStringExtra(getString(R.string.key_chat_name_reciever));
    }
    Log.d("imgeUUUrl", "onCreate: "+img_url);
    mDatabaseuser_info=mDatabaseUser.child(UserId);
    mDatabaseProducts=mDatabaseuser_info.child("products");
    mDatabaseProducts.keepSynced(true);

    try {
        new fetchProducts().execute();
    } catch (Exception e) {
        Log.d("error", "error");
    }


    getUserData();

}
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.chat);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getBaseContext(), ChatActivity.class);
                intent.putExtra(getString(R.string.key_chat_uid_reciever),UserId);
                intent.putExtra(getString(R.string.key_of_img_url_user_recieve),img_url);
                intent.putExtra(getString(R.string.key_chat_name_reciever),userNameReciever);
                startActivity(intent);
            }
        });

        checkForLimitReportUser(UserId);

        final FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.report_user);
        final TextView reportTextView=(TextView)findViewById(R.id.text_report_user);
        mDatabaseReference.child("userss").child(UserId).child("reporting").child(MainActivity.usernameId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if ((dataSnapshot.getValue()!=null)){
                    fab3.setImageResource(R.drawable.ic_report_green_24dp);
                    reportTextView.setText("reported");

                }else {
                    fab3.setImageResource(R.drawable.ic_report_black_24dp);
                    reportTextView.setText("report");

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        fab3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("mano", "onClick: doneeee");
                if (reportTextView.getText().toString().equals("report")) {

                    Log.d("mano", "onClick: "+UserId+"  set value true");

                    final CharSequence[] items = { "شخص محتال", "حساب زائف"};

                    AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);
                    builder.setTitle("Make your selection");
                    builder.setItems(items, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int item) {

                            // will toast your selection

                            mDatabaseReference.child("userss").child(UserId).child("reporting").child(MainActivity.usernameId).setValue(String.valueOf(item));
                            fab3.setImageResource(R.drawable.ic_report_green_24dp);
                            reportTextView.setText("reported");
                            Log.d("mano", "onClick: ");
                            dialog.dismiss();

                        }
                    }).show();

                }else {
                    reportTextView.setText("report");
                    Log.d("mano", "onClick: "+"  remove vlaue");
                    mDatabaseReference.child("userss").child(UserId).child("reporting").child(MainActivity.usernameId).removeValue();
                    fab3.setImageResource(R.drawable.ic_report_black_24dp);
                }
            }
        });

    FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.mapbutton);
        fab1.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View w) {


            // TODO: code from firebase from user
            DatabaseReference s = mDatabaseReference.child("userss").child(UserId);
            s.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Intent intent1=new Intent(UserProfile.this, Main_map.class);
                    double l= 30.053748;
                    double g= 30.053748;
                    String ls=""+l;
                    String gs=""+g;



                    String location=dataSnapshot.child("location").getValue(String.class);

                    if (location!=null) {
                        Scanner input = new Scanner(location);
                        try {
                            l = input.nextDouble();
                            g = input.nextDouble();
                            Log.d("mano", "onClick: " + location + "  l=" + l + "  g" + g);
                            intent1.putExtra("long", "" + g);
                            intent1.putExtra("lat", "" + l);
                            startActivity(intent1);
                        }catch (Exception ex){
                            Log.d("mano", "onDataChange: error in location  "+location);
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    });

        final TextView followNumberTextview=(TextView)findViewById(R.id.follower_number);
        final DatabaseReference followNumPath=mDatabaseReference.child("userss").child(UserId);
        followNumPath.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("mano", "onChildAdded: "+dataSnapshot.hasChild("email")+"  "+dataSnapshot.getKey());
                updateNumberFollower(dataSnapshot);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                updateNumberFollower(dataSnapshot);

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
               if (dataSnapshot.getKey().equals("followers")){
                   int x=Integer.valueOf((String) followNumberTextview.getText());
                   x--;
                   followNumberTextview.setText(String.valueOf(x));
               }


            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
            public void updateNumberFollower(DataSnapshot dataSnapshot){
                if (dataSnapshot.getKey().equals("followers")){
                    followNumberTextview.setText(String.valueOf(dataSnapshot.getChildrenCount()));

                }
            }
        });


    final FloatingActionButton fabFollowMe= (FloatingActionButton) findViewById(R.id.follow_me);
    final DatabaseReference path=mDatabaseReference.child("userss").child(UserId).child("followers").child(MainActivity.usernameId);
        path.addListenerForSingleValueEvent(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            String value=dataSnapshot.getValue(String.class);
            Log.d("mano", "onDataChange:  value  "+value);
            if (value==null){
                fabFollowMe.setImageResource(R.drawable.follow_uncheck);
                fabFollowMe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EDEAFD")));

            }else if(value.equals("true")){
                fabFollowMe.setImageResource(R.drawable.follow_check);
                fabFollowMe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5A01FA")));
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

        fabFollowMe.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {


            path.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String value=dataSnapshot.getValue(String.class);
                    Log.d("mano", "onDataChange:  value  "+value);
                    if (value==null){

                        path.setValue("true");
                        fabFollowMe.setImageResource(R.drawable.follow_check);
                        fabFollowMe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#5A01FA")));
                        mDatabaseReference.child("userss").child(MainActivity.usernameId).child("following").child(UserId).setValue("true");

                    }else {
                        dataSnapshot.getRef().removeValue();
                        fabFollowMe.setImageResource(R.drawable.follow_uncheck);
                        fabFollowMe.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EDEAFD")));
                        mDatabaseReference.child("userss").child(MainActivity.usernameId).child("following").child(UserId).removeValue();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }
    });

    }


    public class fetchProducts extends AsyncTask<String, Void, List> implements  productsAdapter.ImageClickHandler {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            productsList.clear();
          //  pb.setVisibility(View.VISIBLE);


        }

        @Override
        protected List doInBackground(String... params) {
           // String url = params[0];

            Query productQuery = mDatabaseProducts;

            productQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        products product = postSnapshot.getValue(products.class);
                        Log.d("mano", "onDataChange: "+postSnapshot.child("imagesrc").getValue(String.class)+product.getImgesrc());
                        product.setImgesrc(postSnapshot.child("imagesrc").getValue(String.class));
                        product.setFname(postSnapshot.child("product_name").getValue(String.class));
                        product.setProduct_key(postSnapshot.getKey());
                        productsList.add(product);

                    }



                    noproducts.setText(""+productsList.size());
                    Log.d("error", "error product list"+productsList.size());

                    setupLayout(productsList);



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    // Getting Post failed, log a message

                    Log.w("cancelled", "loadPost:onCancelled", databaseError.toException());
                    // ...
                }
            });


            return productsList;

        }

        @Override
        protected void onPostExecute(List movies) {

            if (movies != null) {
               productsList.clear();
            setupLayout(movies);
              //  productsList = movies;




//                GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity2.this, 2);
//                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
//
//                pAdapter = new ImageAdapter(moviesList, getApplicationContext(), this);
//                recyclerView.setAdapter(imageAdapter);
//
//                recyclerView.setLayoutManager(gridLayoutManager);
//                recyclerView.setHasFixedSize(true);
//                pb.setVisibility(View.GONE);


            } else {

            }
        }

public void setupLayout(List<products> products){
productsList= products;
//    LinearLayoutManager layoutManager=new LinearLayoutManager(UserProfile.this);
//    layoutManager.setReverseLayout(true);
//    layoutManager.setStackFromEnd(true);

    productRecycler.setHasFixedSize(true);

    StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
    productRecycler.setLayoutManager(sglm);

    //productRecycler.setLayoutManager(layoutManager);



    cardsAdapter = new productsAdapter(productsList, getApplicationContext(),  this,false);

    productRecycler.setAdapter(cardsAdapter);

}


        @Override
        public void onImageClick(products products) {

            if(mAuth.getCurrentUser().getUid().equals(products.getUser_id())){

                Intent intent = new Intent(UserProfile.this, my_product.class);
                String fname = products.getFname();
                intent.putExtra("fname", fname);
                String category = products.getCategory();
                intent.putExtra("category", category);
                String cost = products.getCost();
                intent.putExtra("cost", cost);
                String email = products.getEmail();
                intent.putExtra("email", email);
                String img = products.getImgesrc();
                intent.putExtra("img", img);
                String location = products.getLocation();
                intent.putExtra("location", location);
                String owner = products.getOwnername();
                intent.putExtra("owner", owner);
                String pcase = products.getproduct_case();
                intent.putExtra("pcase", pcase);
                String phone = products.getPhone();
                intent.putExtra("phone", phone);
                intent.putExtra("user_id",products.getUser_id());
                String prodescribe=products.getproduct_des();
                intent.putExtra("prodescribe",prodescribe);
                //String userImg = products.getPhone();
                intent.putExtra("img_url",products.getImg_url());
                intent.putExtra("product_key",products.getProduct_key());
                intent.putExtra("quantity",products.getQuantity());
                intent.putExtra("time",products.getTime());
                Log.d("imgitem", "onImageClick: "+products.getImg_url());
                startActivity(intent);


           }else {


                Intent intent = new Intent(UserProfile.this, details.class);

                String fname = products.getFname();
                intent.putExtra("fname", fname);
                String category = products.getCategory();
                intent.putExtra("category", category);
                String cost = products.getCost();
                intent.putExtra("cost", cost);
                String email = products.getEmail();
                intent.putExtra("email", email);
                String img = products.getImgesrc();
                intent.putExtra("img", img);
                String location = products.getLocation();
                intent.putExtra("location", location);
                String owner = products.getOwnername();
                intent.putExtra("owner", owner);
                String pcase = products.getproduct_case();
                intent.putExtra("pcase", pcase);
                String phone = products.getPhone();
                intent.putExtra("phone", phone);
                intent.putExtra("user_id", products.getUser_id());
                String prodescribe = products.getproduct_des();
                intent.putExtra("prodescribe", prodescribe);
                //String userImg = products.getPhone();
                intent.putExtra("img_url", products.getImg_url());
                intent.putExtra("product_key", products.getProduct_key());
                intent.putExtra("quantity",products.getQuantity());
                intent.putExtra("time",products.getTime());
                Log.d("imgitem", "onImageClick: " + products.getImg_url());
                startActivity(intent);


            }

        }
    }













    public void getUserData() {

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
     /*   if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                String providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                String name = profile.getDisplayName();
                String email = profile.getEmail();
                String phone = profile.getPhoneNumber();
                Uri photoUrl = profile.getPhotoUrl();
                desplay_name.setText(name);
              //  phoneTV.setText(phone);
              //  emailTV.setText(email);

                Picasso.with(c)
                        .load(photoUrl)
                        .resize(120, 120)
                        .centerCrop()
                        .into(circularImageView);

            }
        }*/

        mDatabaseuser_info.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

             desplay_name.setText((String)dataSnapshot.child("name").getValue())  ;
                emailTV.setText((String)dataSnapshot.child("email").getValue())  ;
                addressTV.setText((String)dataSnapshot.child("address").getValue())  ;
               isshop.setText((String)dataSnapshot.child("shop").getValue())  ;





                    phoneTV.setText((String)dataSnapshot.child("phone").getValue())  ;



//                emailTV.setText((String)dataSnapshot.child("email").getValue())  ;
//                locationTV.setText((String)dataSnapshot.child("location").getValue())  ;
                if(img_url!=null){

                    Picasso.with(c)
                            .load(img_url)
                            .resize(120, 120)
                            .centerCrop()
                            .into(circularImageView);

                }else{

                    if(mAuth.getCurrentUser().getPhotoUrl()!=null){
                        Picasso.with(c)
                                .load(mAuth.getCurrentUser().getPhotoUrl().toString())
                                .resize(120, 120)
                                .centerCrop()
                                .into(circularImageView);}


                    else{
                        circularImageView.setImageResource(R.mipmap.baseline_account_circle_black_48);

                    }
                }

//                Log.d("immmg", "onCreate: "+mAuth.getCurrentUser().getPhotoUrl().toString());



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }


    private void checkUserExist(){


        if(mAuth.getCurrentUser()!=null){

            final String user_id=mAuth.getCurrentUser().getUid();

            mDatabaseUser.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(!dataSnapshot.hasChild(user_id)){
                        Intent login=new Intent(UserProfile.this, splash.class);
                        login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(login);
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

    }


    @Override
    protected void onStart() {
        super.onStart();


    /*    Log.d("tagg", "onCreate: on start");

        mAuth.addAuthStateListener(mAuthListner);
        Log.d("tagg", "onCreate: on start 2" );

        FirebaseRecyclerAdapter<products, productsViewHolder> adapter = new FirebaseRecyclerAdapter<products, productsViewHolder>(
                products.class,
                list_item_article,
                productsViewHolder.class, //see this is missing so you should add it
                mDatabaseProducts) {


//View v=(View) findViewById(list_item_article);
        //    productsViewHolder viewHolder=new productsViewHolder((View) list_item_article);
            @Override
            protected void populateViewHolder(productsViewHolder viewHolder, products model, int position) {
                Log.d("tagg", "onCreate:1 ");

                viewHolder.setCategory(model.getCategory());
viewHolder.setCost(model.getCost());
                Log.d("tagg", "onCreate: 2"+position);

viewHolder.setImg_src(model.getImg_src());
                Log.d("tagg", "onCreate:3 ");


            }


        };
        productRecycler.setAdapter(adapter);
    }
    public static class productsViewHolder extends RecyclerView.ViewHolder{
View mView;

        public productsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView=itemView;
        }


//        public void setFname(String fname) {
//            this.fname = fname;
//        }
//
//        public void setUser_id(String user_id) {
//            this.user_id = user_id;
//        }
        public void setCategory(String category) {
            TextView catTV=(TextView)mView.findViewById(R.id.article_subtitle);
            Log.d("tagg", "onCreate: "+category);

            catTV.setText(category);
        }


      //  public void setCasee(String casee) {
       //     this.casee = casee;
       // }


        public void setCost(String cost) {
            TextView costTV=(TextView)mView.findViewById(R.id.article_title);
            Log.d("tagg", "onCreate: "+cost);

            costTV.setText(cost);
        }


//        public void setPdescribe(String pdescribe) {
//            this.pdescribe = pdescribe;
//        }
//
//
//        public void setLocation(String location) {
//            this.location = location;
//        }
//
//
//        public void setOwnername(String ownername) {
//            this.ownername = ownername;
//        }
//
//
//        public void setPhone(String phone) {
//            this.phone = phone;
//        }
//
//
//        public void setEmail(String email) {
//            this.email = email;
//        }
        public void setImg_src(String img_src) {
            ImageView thumpnail=(ImageView)mView.findViewById(R.id.thumbnail);
            Log.d("tagg", "onCreate: "+img_src);

            Picasso.with(mView.getContext())
                    .load(img_src.toString())
                    .resize(80, 80)
                    .centerCrop()
                    .into(thumpnail);
        }
*/


    }




    private void checkForLimitReportUser(final String userId) {
        mDatabaseReference.child("userss").child(userId).child("reporting").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int arr[]=new int[2];
                arr[0]=0;
                arr[1]=0;
                for (DataSnapshot dataReport:dataSnapshot.getChildren()){
                    if (dataReport.getValue(String.class).equals("0")){
                        Log.d("mano", "onDataChange: reported  0");
                        arr[0]++;

                    }else if (dataReport.getValue(String.class).equals("1")){
                        Log.d("mano", "onDataChange: reported  1");
                        arr[1]++;
                    }

                    Log.d("mano", "onDataChange: reported  "+arr[0]+"  "+arr[1]);
                }
                if (arr[0]>=5){
                    Log.d("mano", "onDataChange: "+userId);
                    my_profile.deleteAccount(getApplicationContext(),userId);
                    goToHome();
                }else if (arr[1]>=5){
                    my_profile.deleteAccount(getApplicationContext(),userId);
                    goToHome();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void goToHome() {
        Intent intent=new Intent(UserProfile.this,MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }


}