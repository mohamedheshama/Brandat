package com.example.moo.brandat;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class categoryItem extends AppCompatActivity {

    List<products> productsList;
    ImageAdapterTwo cardsAdapter;

    private Context context;


    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseProducts;
    private DatabaseReference mDatabaseuser_info;
    private DatabaseReference mDatabaseUser;
    RecyclerView productRecycler;
    Context c;
    String itemToFetch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_list);
//URL="https://api.themoviedb.org/3/movie/popular?api_key=cb77b332ccbf1c0fb73ba7dca9973100";
Intent intent=getIntent();
itemToFetch=intent.getStringExtra("item");
        productRecycler = (RecyclerView) findViewById(R.id.recycler_view);
        //  moviesList = new ArrayList<>();
        productsList = new ArrayList<>();


        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance().getReference().child("categories").child(itemToFetch);
        mDatabaseUser = FirebaseDatabase.getInstance().getReference().child("userss");


        mDatabaseuser_info = mDatabaseUser.child(mAuth.getCurrentUser().getUid());
        mDatabaseProducts = mDatabaseuser_info.child("products");

        mDatabase.keepSynced(true);
        mDatabaseUser.keepSynced(true);
        mDatabaseProducts.keepSynced(true);


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


    }

    public class fetchProducts extends AsyncTask<String, Void, List> implements ImageAdapterTwo.ImageClickHandler {


        @Override
        protected List doInBackground(String... strings) {


            Query productQuery = mDatabase;//////////filtering

            productQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {


                        products product = postSnapshot.getValue(products.class);
                      //  product.setProduct_key(postSnapshot.getKey());

                        productsList.add(product);
                        // TODO: handle the post
                    }
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


        public void setupLayout(List<products> products) {
            productsList = products;
//    LinearLayoutManager layoutManager=new LinearLayoutManager(UserProfile.this);
//    layoutManager.setReverseLayout(true);
//    layoutManager.setStackFromEnd(true);

            productRecycler.setHasFixedSize(true);

            StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
            productRecycler.setLayoutManager(sglm);

            //productRecycler.setLayoutManager(layoutManager);


            cardsAdapter = new ImageAdapterTwo(productsList, categoryItem.this, this,false);

            productRecycler.setAdapter(cardsAdapter);

        }

        @Override
        public void onImageClick(products products) {

            if(mAuth.getCurrentUser().getUid().equals(products.getUser_id())){

                Intent intent = new Intent(categoryItem.this, my_product.class);
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

                Log.d("imgitem", "onImageClick: "+products.getImg_url());
                startActivity(intent);
                Toast.makeText(categoryItem.this,""+products.getCategory(),Toast.LENGTH_SHORT).show();


            }else {


                Intent intent = new Intent(categoryItem.this, details.class);

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

                Log.d("imgitem", "onImageClick: " + products.getImg_url());
                startActivity(intent);
                Toast.makeText(categoryItem.this, "" + products.getCategory(), Toast.LENGTH_SHORT).show();


            }


        }
    }
}


