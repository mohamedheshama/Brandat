package com.example.moo.brandat;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class FavoriteFragment extends Fragment implements productsAdapter.ImageClickHandler {
    private static String TAG="mano";
    private RecyclerView mFavoriteRecycler;
    private DatabaseReference mDatabaseReference;
    private productsAdapter adapter;
    List<products> productsList;

    ArrayList<String>favour;
    ArrayList<String>favourcat;


    public FavoriteFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_favorite_item,container,false);
        mFavoriteRecycler=rootView.findViewById(R.id.favorite_recycler_view);


        mFavoriteRecycler.setHasFixedSize(true);

        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        mFavoriteRecycler.setLayoutManager(sglm);

        productsList=new ArrayList<>();
        favour=new ArrayList<>();
        favourcat=new ArrayList<>();

        adapter = new productsAdapter(productsList, getContext(),  this,true);

        mFavoriteRecycler.setAdapter(adapter);
        fetchAllDataFromFirebase();
        return rootView;
    }

    private void fetchAllDataFromFirebase() {
        mDatabaseReference=FirebaseDatabase.getInstance().getReference();
        mDatabaseReference
                .child("userss")
                .child(MainActivity.usernameId)
                .child("favs").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                favour.clear();
                favourcat.clear();

                for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                    favour.add(postSnapshot.getKey());
                    favourcat.add((String) postSnapshot.getValue());
                    Log.d(TAG, "onDataChange: "+favourcat.get(0));

                }

                for (int i = 0; i < favour.size(); i++) {


                    DatabaseReference categoriesData = mDatabaseReference.child("categories").child(favourcat.get(i)).child(favour.get(i));

                    categoriesData.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NotNull DataSnapshot dataSnapshot) {

                            products product = dataSnapshot.getValue(products.class);
                            if (product!=null) {
                                product.setFname(dataSnapshot.child("product_name").getValue(String.class));
                                product.setImgesrc(dataSnapshot.child("imagesrc").getValue(String.class));
                                productsList.add(product);

                                Log.d(TAG, "onDataChange: prduct " + product.getProduct_key());
                                adapter.notifyDataSetChanged();
                            }
                        }


                        //}

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Log.d("errrror", "onCancelled: " + "likes error");
                        }
                    });


                }






            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onImageClick(products products) {

        Intent intent = new Intent(getContext(), details.class);

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
        Toast.makeText(getContext(),""+products.getCategory(),Toast.LENGTH_SHORT).show();

    }

}
