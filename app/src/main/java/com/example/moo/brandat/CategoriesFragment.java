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
import android.widget.AdapterView;
import android.widget.ListView;
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

public class CategoriesFragment extends Fragment {
    List<products> productsList;
    ImageAdapter cardsAdapter;



    FirebaseAuth mAuth;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseProducts;
    private DatabaseReference mDatabaseuser_info;
    private DatabaseReference mDatabaseUser;
    RecyclerView productRecycler;
    Context c;
    List namesCategories;
    ListView listView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.fragment_categories,container,false);

         listView=rootView.findViewById(R.id.list_view_fragment);

        //get names of categories from arguments that sent from MainActivity and parse to list
       //  namesCategories =getArguments().getStringArrayList(getString(R.string.names_categories_key));
namesCategories=new ArrayList<>();
        //set adapter

        //action item list view
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "this item "+position, Toast.LENGTH_SHORT).show();

                Toast.makeText(getContext(), "this item "+position+""+namesCategories.get(position), Toast.LENGTH_SHORT).show();
                Intent item=new Intent(getContext(),categoryItem.class);

                item.putExtra("item",(String) namesCategories.get(position));
                startActivity(item);
            }
        });









        productRecycler = (RecyclerView)rootView.findViewById(R.id.recycler_view);
        //  moviesList = new ArrayList<>();
        productsList = new ArrayList<>();



        mAuth= FirebaseAuth.getInstance();

        mDatabase= FirebaseDatabase.getInstance().getReference().child("categories");
        mDatabaseUser= FirebaseDatabase.getInstance().getReference().child("userss");


        mDatabaseuser_info=mDatabaseUser.child(mAuth.getCurrentUser().getUid());
        mDatabaseProducts=mDatabaseuser_info.child("products");

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




        return rootView;


    }

    public class fetchProducts extends AsyncTask<String, Void, List> {


        @Override
        protected List doInBackground(String... strings) {


            Query productQuery = mDatabase;//////////filtering

            productQuery.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NotNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {

                        String product = (String) postSnapshot.getKey();
                        namesCategories.add(product.toString());
                        // TODO: handle the post
                    }
                    setupLayout(namesCategories);


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


        public void setupLayout(List products) {
            listView.setAdapter(new CategoriesAdapter(getContext(),products));


        }
    }
}