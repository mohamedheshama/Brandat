package com.example.moo.brandat;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class home extends Fragment {
    private RecyclerView mRecyclerView;
    List<model> moviesList;
    String URL;
    private ImageAdapter imageAdapter;


    List<List<products>> productsList;
    ImageAdapter cardsAdapter;



    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseProducts;
    private DatabaseReference mDatabaseuser_info;
    private DatabaseReference mDatabaseUser;
    RecyclerView productRecycler;
    Context c;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.activity_article_list, null);
//URL="https://api.themoviedb.org/3/movie/popular?api_key=cb77b332ccbf1c0fb73ba7dca9973100";

        productRecycler = (RecyclerView)rootView.findViewById(R.id.recycler_view);
      //  moviesList = new ArrayList<>();
        productsList = new ArrayList<>();



        mAuth=FirebaseAuth.getInstance();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("categories");
        mDatabaseUser= FirebaseDatabase.getInstance().getReference().child("userss");


        mDatabaseuser_info=mDatabaseUser.child(mAuth.getCurrentUser().getUid());
        mDatabaseProducts=mDatabaseuser_info.child("products");

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




return rootView;


    }

    public class fetchProducts extends AsyncTask<String, Void, List>implements ImageAdapterTwo.ImageClickHandler {


        @Override
        protected List doInBackground(String... strings) {

            final Query productQuery = mDatabase;//////////filtering
            final List<String> namesCategries=new ArrayList<>();
            productQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                    productsList.clear();

                    for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        namesCategries.add(postSnapshot.getKey());
                        List<products> list=new ArrayList<>();
                        int i=0;
                        for (DataSnapshot postSnapshote: postSnapshot.getChildren()) {
                            i++;
                            products product = postSnapshote.getValue(products.class);
                            product.setImgesrc(postSnapshote.child("imagesrc").getValue(String.class));
                            product.setFname(postSnapshote.child("product_name").getValue(String.class));
                            product.setProduct_key(postSnapshote.getKey());
                            if (i<=5) {
                                list.add(product);
                                Log.d("onBindViewHolder", "onDataChange: test "+postSnapshote.hasChild("category")+postSnapshote.child("imagesrc").getValue(String.class));
                            }

                        }
                        productsList.add(list);




                          //  products product = postSnapshot.getValue(products.class);

                        // TODO: handle the post
                    }
                    setupLayout(productsList,namesCategries);



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


        public void setupLayout(List<List<products>> products,List namesCategres){
            productsList= products;
//    LinearLayoutManager layoutManager=new LinearLayoutManager(UserProfile.this);
//    layoutManager.setReverseLayout(true);
//    layoutManager.setStackFromEnd(true);

            productRecycler.setHasFixedSize(true);

            LinearLayoutManager sglm = new LinearLayoutManager(getContext());
            sglm.setOrientation(LinearLayoutManager.VERTICAL);
            productRecycler.setLayoutManager(sglm);

            //productRecycler.setLayoutManager(layoutManager);



            cardsAdapter = new ImageAdapter(productsList, getContext(),  this,namesCategres);


            productRecycler.setAdapter(cardsAdapter);

        }

        @Override
        public void onImageClick(products products) {



            if(mAuth.getCurrentUser().getUid().equals(products.getUser_id())){

                Intent intent = new Intent(getActivity(), my_product.class);
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
                Toast.makeText(getActivity(),""+products.getCategory(),Toast.LENGTH_SHORT).show();


            }else {


                Intent intent = new Intent(getActivity(), details.class);

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
                Toast.makeText(getActivity(), "" + products.getCategory(), Toast.LENGTH_SHORT).show();


            }
        }
    }



}
