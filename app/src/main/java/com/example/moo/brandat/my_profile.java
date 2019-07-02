package com.example.moo.brandat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moo.brandat.chat.ChatActivity;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
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

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;


public class my_profile extends AppCompatActivity implements View.OnClickListener {

    // BottomSheetBehavior variable
    private BottomSheetBehavior bottomSheetBehavior;

    // TextView variable
    private TextView bottomSheetHeading;

    // Button variables
    private Button expandBottomSheetButton;
    private Button collapseBottomSheetButton;
    private Button hideBottomSheetButton;
    private Button showBottomSheetDialogButton;
    private Button go;
    private FloatingActionButton more;
    DatabaseReference favv;
    DatabaseReference path1;
TextView showmore1;
    TextView showmore2;
TextView noproducts;
    private final static int REQUEST_CODE_1 = 2;
    TextView bottom_sheet_manual;
    TextView bottom_sheet_gps;
    private FusedLocationProviderClient client;
    private BottomSheetBehavior bottomSheetBehavior2;

    private static final int PHOTO_PICKER = 2;
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    String img_url=null;
    private FirebaseAuth.AuthStateListener mAuthListner;

    StorageReference mstorStorageReference;
    private DatabaseReference mDatabasecat;
    private FirebaseUser mCurrentUser;
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

    TextView isshop;
    TextView locationTV;
    TextView desplay_name,delteAccoutnText;
    FloatingActionButton editActivity;
    FloatingActionButton addProduct;
    List<products> productsList;
    List<products> productsfavList;

    productsAdapter cardsAdapter;
    productsAdapter cardsAdapter2;

    String UserId;
    private DatabaseReference mDatabase;
    private static DatabaseReference mDatabaseReference;
    private DatabaseReference mDatabaseProducts;
    private DatabaseReference mDatabaseuser_info;
    private DatabaseReference mDatabaseUser;
    RecyclerView productRecycler;
    RecyclerView productfavRecycler;

    Context c;
    ArrayList<String>favour;
    ArrayList<String>favourcat;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_sheets_abb_bar);
        more = (FloatingActionButton) findViewById(R.id.more);
        initViews();
        initListeners();
        favour=new ArrayList<>();
        favourcat=new ArrayList<>();
        client = LocationServices.getFusedLocationProviderClient(my_profile.this);
        requestPermission();

        productsList = new ArrayList<>();
        productsfavList=new ArrayList<>();
        mAuth = FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
        mAuthListner = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                if (firebaseAuth.getCurrentUser() == null) {
                    Intent login = new Intent(my_profile.this, login.class);
                    login.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(login);
                }
            }
        };
        mDatabase = FirebaseDatabase.getInstance().getReference().child("categories");
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("userss");
        mDatabaseReference=FirebaseDatabase.getInstance().getReference();

        mDatabase.keepSynced(true);
        mDatabaseUser.keepSynced(true);

        productRecycler = (RecyclerView) findViewById(R.id.prodRec);
        productfavRecycler=(RecyclerView)findViewById(R.id.favRec);
        circularImageView = (CircularImageView) findViewById(R.id.circularImageView);
        desplay_name = (TextView) findViewById(R.id.textView11);
        emailTV =(TextView) findViewById(R.id.email);
        phoneTV =(TextView) findViewById(R.id.phonn);
        addressTV=(TextView)findViewById(R.id.address);
        isshop=(TextView)findViewById(R.id.textView12) ;
showmore1=(TextView)findViewById(R.id.viewmore1);
        showmore2=(TextView)findViewById(R.id.viewmore2);
        noproducts=(TextView)findViewById(R.id.noproducts);

showmore1.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
    Intent t=new Intent(my_profile.this,categoryItem.class);
    t.putExtra("products",1);
        startActivity(t);
    }
});
showmore2.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        Intent t2=new Intent(my_profile.this,categoryItem.class);
        t2.putExtra("favs",2);
        startActivity(t2);
    }
});
        editActivity = (FloatingActionButton) findViewById(R.id.edit_floating_action_button);
        addProduct = (FloatingActionButton) findViewById(R.id.add);





        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(my_profile.this, Upload.class));


            }
        });



        Intent intent = getIntent();


        mDatabaseuser_info = mDatabaseUser.child(mAuth.getCurrentUser().getUid());
        mDatabaseProducts = mDatabaseuser_info.child("products");
        mDatabaseProducts.keepSynced(true);
        mDatabaseuser_info.keepSynced(true);
        getUserData();





        editActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                startActivity(new Intent(my_profile.this, profileEdit.class));


            }
        });











        mDatabasecat= FirebaseDatabase.getInstance().getReference().child("categories");
        //DatabaseReference  categoriesData=mDatabasecat.child(cat).child(product_key);


         favv=FirebaseDatabase.getInstance().getReference().child("userss").child(mCurrentUser.getUid());
        Log.d("errrror", "onCancelledd: "+"heyyyyy"+mCurrentUser.getUid());

        path1=favv.child("favs");


        mDatabasecat.keepSynced(true);
        favv.keepSynced(true);
        path1.keepSynced(true);
      //  final DatabaseReference path2=favv.child("favs").child(product_key);

        Log.d("errrror", "onCancelledd: "+"heyyyyy");


        path1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
favour.clear();
favourcat.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    favour.add(postSnapshot.getKey());
                    favourcat.add((String) postSnapshot.getValue());




                }

                try {







                 //   Log.d("errrror", "onCancelledd: before"+favour.get(0));
                    new fetchfavs().execute();

                 //   Log.d("errrror", "onCancelledd: before"+favour.get(0));

                 //   Log.d("errrror", "onCancelledd: after "+favour.get(0));
                } catch (Exception e) {
                    Log.d("error", "error"+e.getMessage());
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.d("errrror", "onCancelled: "+"likes error");
            }
        });

        String mydate = java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
        Log.d("dateeee", "onCreate: "+mydate);



//     Log.d("errrrorrrrrrrrrrrrrr", "onCancelleddrrrrrrrr: "+favour.get(0));
//       Log.d("errrrorrrrrrrrrrrrrr", "onCancelleddrrrrrrrr: "+favourcat.get(0));






        final TextView followNumberTextview=(TextView)findViewById(R.id.follower_number);
        final DatabaseReference followNumPath=mDatabaseReference.child("userss").child(MainActivity.usernameId);
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
        try {
            new fetchProducts().execute();
            Log.d("error", "error product list"+productsList.size());

        } catch (Exception e) {
            Log.d("error", "error"+e.getMessage());
        }






//        Log.d("errrrorrrrrrrrrrrrrr", "onCancelleddrrrrrrrr2: "+favour.get(0));







//        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
//
//        productRecycler.setHasFixedSize(true);
//        productRecycler.setLayoutManager(layoutManager);


        checkUserExist();


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.location_map);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        /*FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.mapbutton);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View w) {
                Intent intent1=new Intent(my_profile.this, Main_map.class);
                double l= 30.053748;
                double g= 30.053748;
                String ls=""+l;
                String gs=""+g;
                intent1.putExtra("long", ls);
                intent1.putExtra("lat", gs);
                startActivity(intent1);
            }
        });

    */
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
                        product.setImgesrc(postSnapshot.child("imagesrc").getValue(String.class));
                        product.setFname(postSnapshot.child("product_name").getValue(String.class));
                        productsList.add(product);
                        // TODO: handle the post
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
                Toast.makeText(my_profile.this, "error", Toast.LENGTH_LONG);

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

            Intent intent = new Intent(my_profile.this, my_product.class);

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
            String prodescribe=products.getproduct_des();
            intent.putExtra("prodescribe",prodescribe);
            intent.putExtra("user_id",products.getUser_id());
            intent.putExtra("product_key",products.getProduct_key());
            intent.putExtra("quantity",products.getQuantity());
            intent.putExtra("time",products.getTime());
            //  String userImg = products.getPhone();
            intent.putExtra("img_url",products.getImg_url());
            startActivity(intent);
            Toast.makeText(getApplicationContext(),""+products.getCategory(),Toast.LENGTH_SHORT).show();

        }
    }



    public class fetchfavs extends AsyncTask<String, Void, List> implements  productsAdapter.ImageClickHandler {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            productsfavList.clear();
            //  pb.setVisibility(View.VISIBLE);
           // Log.d("errrrorrrrrrrrrrrrrr", "onCancelleddrrrrrrrr3: "+favour.get(0));


        }

        @Override
        protected List doInBackground(String... params) {

            Log.d("mano", "doInBackground: ");

            for (int i = 0; i < favour.size(); i++) {
             //   Log.d("errrror", "onCancelledd: 0"+favour.get(i));
             //   Log.d("errrror", "onCancelledd: 0"+favourcat.get(i));

                DatabaseReference categoriesData = mDatabasecat.child(favourcat.get(i)).child(favour.get(i));

                categoriesData.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
//likes.clear();
                        //  for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                        products product = dataSnapshot.getValue(products.class);

                        productsfavList.add(product);


                     //   Log.d("errrror", "onCancelleddrrrrrrrr:4 " + product.getCost());


                    }


                    //}

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Log.d("errrror", "onCancelled: " + "likes error");
                    }
                });


            }

          //  setupLayout(productsfavList);
            return productsfavList;
        }





        @Override
        protected void onPostExecute(List movies) {

            if (movies != null) {
                productsfavList.clear();
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
                Toast.makeText(my_profile.this, "error", Toast.LENGTH_LONG).show();

            }
        }

        public void setupLayout(List<products> products){
            productsfavList= products;
//    LinearLayoutManager layoutManager=new LinearLayoutManager(UserProfile.this);
//    layoutManager.setReverseLayout(true);
//    layoutManager.setStackFromEnd(true);
          //  Log.d("errrrorrrrrrrrrrrrrr", "onCancelleddrrrrrrrr:5 "+favour.get(0));

            productfavRecycler.setHasFixedSize(true);

            StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL);
            productfavRecycler.setLayoutManager(sglm);

            //productRecycler.setLayoutManager(layoutManager);



            cardsAdapter2 = new productsAdapter(productsfavList, getApplicationContext(),  this,false);
           // Log.d("errrrorrrrrrrrrrrrrr", "onCancelleddrrrrrrrr:6 "+favour.get(0));

            productfavRecycler.setAdapter(cardsAdapter2);
           // Log.d("errrrorrrrrrrrrrrrrr", "onCancelleddrrrrrrrr:7 "+favour.get(0));

        }








        @Override
        public void onImageClick(products products) {

            Intent intent = new Intent(my_profile.this, details.class);

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
            String prodescribe=products.getproduct_des();
            intent.putExtra("prodescribe",prodescribe);
            intent.putExtra("phone", phone);
            intent.putExtra("user_id",products.getUser_id());
            intent.putExtra("product_key",products.getProduct_key());
            intent.putExtra("quantity",products.getQuantity());
            intent.putExtra("time",products.getTime());
            //  String userImg = products.getPhone();
            intent.putExtra("img_url",products.getImg_url());
            startActivity(intent);
            Toast.makeText(getApplicationContext(),""+products.getCategory(),Toast.LENGTH_SHORT).show();

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
                addressTV.setText((String)dataSnapshot.child("address").getValue())  ;
                isshop.setText((String)dataSnapshot.child("shop").getValue())  ;
               // boolean s= (boolean) dataSnapshot.child("shop").getValue();





                    phoneTV.setText((String)dataSnapshot.child("phone").getValue())  ;



             emailTV.setText((String)dataSnapshot.child("email").getValue())  ;
              // locationTV.setText((String)dataSnapshot.child("location").getValue())  ;
                if(MainActivity.userImageUrl!=null){

                    Picasso.with(c)
                            .load(MainActivity.userImageUrl)
                            .resize(120, 120)
                            .centerCrop()
                            .into(circularImageView);

                }else{

                    if(MainActivity.userImageUrl==null){
                        Picasso.with(c)
                                .load(MainActivity.userImageUrl)
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
                        Intent login=new Intent(my_profile.this, splash.class);
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






    private void initViews() {


        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottom1));

        bottomSheetBehavior2 = BottomSheetBehavior.from(findViewById(R.id.bottom2));

        bottom_sheet_manual= (TextView) findViewById(R.id.location_manual);


        bottom_sheet_gps= (TextView) findViewById(R.id.gps_auto);
        delteAccoutnText=(TextView)findViewById(R.id.delete_account);

      //  go = (Button) findViewById(R.id.go);

    }


    /**
     * method to initialize the listeners
     */
    private void initListeners() {

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_HIDDEN);
        bottom_sheet_gps.setOnClickListener(this);
        bottom_sheet_manual.setOnClickListener(  this);
        delteAccoutnText.setOnClickListener(this);
        //go.setOnClickListener(this);

    }

    /**
     * onClick Listener to capture button click
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.location_manual:
        bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_HIDDEN);
        Intent intent1 = new Intent(my_profile.this, map_auto.class);


            startActivityForResult(intent1,REQUEST_CODE_1);
        break;
        case R.id.gps_auto:
        bottomSheetBehavior2.setState(BottomSheetBehavior.STATE_HIDDEN);
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Intent intent3 = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent3);
            if (ActivityCompat.checkSelfPermission(my_profile.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            client.getLastLocation().addOnSuccessListener(my_profile.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        double l = location.getLatitude();
                        double g = location.getLongitude();
                        //to firebase
                        setMyLocationToFirebase(l,g);

                        String ls = "" + l;
                        String gs = "" + g;
                        Toast.makeText(my_profile.this, ls, Toast.LENGTH_SHORT).show();
                        Toast.makeText(my_profile.this, gs, Toast.LENGTH_SHORT).show();

                    }
                }
            });


        } else {
            if (ActivityCompat.checkSelfPermission(my_profile.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                client.getLastLocation().addOnSuccessListener(my_profile.this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            double l = location.getLatitude();
                            double g = location.getLongitude();
                            String ls = "" + l;
                            String gs = "" + g;
                            //to firebase
                            setMyLocationToFirebase(l,g);

                            Toast.makeText(my_profile.this, ls, Toast.LENGTH_SHORT).show();
                            Toast.makeText(my_profile.this, gs, Toast.LENGTH_SHORT).show();

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
            client.getLastLocation().addOnSuccessListener(my_profile.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        double l = location.getLongitude();
                        double g = location.getLatitude();
                        String ls = "" + l;
                        String gs = "" + g;
                        //to firebase
                        setMyLocationToFirebase(l,g);

                        Toast.makeText(my_profile.this, ls, Toast.LENGTH_SHORT).show();
                        Toast.makeText(my_profile.this, gs, Toast.LENGTH_SHORT).show();

                    }
                }
            });


//                    Intent intent2=new Intent(details.this, map_auto.class);
//                    startActivity(intent2);

        }
        if (ActivityCompat.checkSelfPermission(my_profile.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            client.getLastLocation().addOnSuccessListener(my_profile.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        double l = location.getLatitude();
                        double g = location.getLongitude();
                        String ls = "" + l;
                        String gs = "" + g;
                        //to firebase
                        setMyLocationToFirebase(l,g);

                        Toast.makeText(my_profile.this, ls, Toast.LENGTH_SHORT).show();
                        Toast.makeText(my_profile.this, gs, Toast.LENGTH_SHORT).show();

                    }
                }
            });
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //  public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                             int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        client.getLastLocation().addOnSuccessListener(my_profile.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    double l = location.getLatitude();
                    double g = location.getLongitude();
                    String ls = "" + l;
                    String gs = "" + g;
                    //to firebase
                    setMyLocationToFirebase(l,g);

                    Toast.makeText(my_profile.this, ls, Toast.LENGTH_SHORT).show();
                    Toast.makeText(my_profile.this, gs, Toast.LENGTH_SHORT).show();

                }
            }
        });


        break;
            case R.id.delete_account:
                Toast.makeText(getApplicationContext(), "delete", Toast.LENGTH_SHORT).show();
                deleteAccount(getApplicationContext(),MainActivity.usernameId);
                FirebaseAuth.getInstance().getCurrentUser().delete()
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    goToHome();
                                }
                            }
                        });
            break;

    }
}

    public static void deleteAccount(final Context c, final String usernameId) {
        if (mDatabaseReference==null){
            mDatabaseReference=FirebaseDatabase.getInstance().getReference();
        }
        Log.d("mano", "deleteAccount: "+usernameId);
        mDatabaseReference.child("userss").child(usernameId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot.hasChild("following")) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.child("following").getChildren()) {
                        Log.d("mano", "onDataChange: followin id "+dataSnapshot1.getKey());
                        mDatabaseReference.child("userss").child(dataSnapshot1.getKey()).child("followers").child(usernameId).removeValue();
                    }
                }

                if (dataSnapshot.hasChild("products")) {
                    for (DataSnapshot dataSnapshot1 : dataSnapshot.child("products").getChildren()) {
                        mDatabaseReference
                                .child("categories")
                                .child(dataSnapshot1.child("category").getValue(String.class))
                                .child(dataSnapshot1.getKey()).removeValue();
                    }
                }
                mDatabaseReference.child("userss").child(usernameId).removeValue();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    private void setMyLocationToFirebase(double l, double g) {
        String myLocation=l+" "+g;
        mDatabaseUser.child(MainActivity.usernameId).child("location").setValue(myLocation);
        Log.d("mano", "setMyLocationToFirebase: done");
    }

    public void more(View view) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

    }
    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);


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
    private void goToHome() {
        Intent intent=new Intent(my_profile.this,splahScreen.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("Exit",true);
        startActivity(intent);
        finish();
        ActivityCompat.finishAffinity(my_profile.this);


    }
}
