package com.example.moo.brandat;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
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
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SearchFragment extends Fragment implements ImageAdapterTwo.ImageClickHandler {
    public final String TAG="searchfragment";
    List<products> productsList;
    private static FirebaseDatabase mFirebaseDatabase;
    private static DatabaseReference mDatabaseReference;
    private RecyclerView productRecycler;
    private ImageAdapterTwo cardsAdapter;

    public SearchFragment(){
        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mDatabaseReference=mFirebaseDatabase.getReference();
    }

    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_article_list_search, container, false);

        productRecycler = (RecyclerView)rootView.findViewById(R.id.recycler_view);

        productsList =new ArrayList<products>();

        setupLayout(productsList);

        return rootView;
    }



    public void searchWord(final String wordSearch){
        productsList.clear();
        cardsAdapter.notifyDataSetChanged();
        Query path=mDatabaseReference.child("categories").orderByChild("product_name");

        path.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                for (Map.Entry<String, Object> entry : ((Map<String,Object>) dataSnapshot.getValue()).entrySet()){
                    Map<String,Object> singleUser = (Map<String,Object>) entry.getValue();
                    for (Map.Entry<String, Object> entry1 : singleUser.entrySet()){
                        Map test=(Map)entry1.getValue();
                        String name =(String) test.get("product_name");
                        if (name.contains(wordSearch)){
                            products pro=new products();
                            pro.setCategory((String) test.get("product_name"));
                            pro.setCost((String) test.get("cost"));
                            pro.setEmail((String) test.get("email"));
                            pro.setImgesrc((String) test.get("imagesrc"));
                            pro.setImg_url((String) test.get("img_url"));
                            pro.setLocation((String) test.get("location"));
                            pro.setOwnername((String) test.get("ownername"));
                            pro.setPhone((String) test.get("phone"));
                            pro.setproduct_case((String) test.get("product_case"));
                            pro.setproduct_des((String) test.get("product_des"));
                            pro.setFname((String) test.get("product_name"));
                            pro.setUser_id((String) test.get("user_id"));
                            pro.setProduct_key((String) test.get("product_key"));

                            Log.d(TAG, "onDataChange: result done");
                            productsList.add(pro);
                        }
                    }
                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.w("cancelled", "loadPost:onCancelled", databaseError.toException());

            }
        });

        return ;








    }


    public void setupLayout(List<products> products){
        productsList= products;

        productRecycler.setHasFixedSize(true);

        StaggeredGridLayoutManager sglm = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        productRecycler.setLayoutManager(sglm);

         cardsAdapter = new ImageAdapterTwo(productsList, getContext(),  this,false);

        productRecycler.setAdapter(cardsAdapter);

    }

    @Override
    public void onImageClick(products products) {


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
        intent.putExtra("user_id",products.getUser_id());

        String prodescribe=products.getproduct_des();

        intent.putExtra("prodescribe",prodescribe);
        //String userImg = products.getPhone();
        intent.putExtra("product_key",products.getProduct_key());
        //String userImg = products.getPhone();
        intent.putExtra("img_url",products.getImg_url());
        Log.d("imgitem", "onImageClick: "+products.getImg_url());
        startActivity(intent);
        Toast.makeText(getActivity(),""+products.getCategory(),Toast.LENGTH_SHORT).show();



    }



}

