package com.example.moo.brandat;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Movie;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.moo.brandat.chat.ChatActivity;
import com.example.moo.brandat.chat.FragmentChat;
import com.example.moo.brandat.chat.FragmentListUserChat;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener ,
        BottomNavigationView.OnNavigationItemSelectedListener {
    private static final int PHOTO_PICKER = 2;
    CircularImageView nav_head_account_image;
    List<model> moviesList;
    FirebaseAuth mAuth;
TextView user,signed_in;
    private FirebaseAuth.AuthStateListener mAuthListner;
    private DatabaseReference mDatabaseReference;
    public static String usernameId ,usernameUser,userImageUrl;
    //android:onClick="onclick"


    StorageReference mstorStorageReference;
@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_contender);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        Intent intent=getIntent();
        if (intent.hasExtra("Exit")){
            finish();
        }
    LifeCyclerr.getInstance().setOnVisibilityChangeListener(new LifeCyclerr.ValueChangeListener() {
        @Override
        public void onChanged(Boolean value) {
            Log.d("isAppInBackground", String.valueOf(value));
            if (String.valueOf(value).equals("true")){
                mDatabaseReference.child("userss").child(usernameId).child("state").setValue("offline");
            }else {
                mDatabaseReference.child("userss").child(usernameId).child("state").setValue("online");
            }
        }
    });

        BottomNavigationView navigation =  findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    View headerLayout = navigationView.getHeaderView(0);
    signed_in = (TextView) headerLayout.findViewById(R.id.signed_in);
    nav_head_account_image = (CircularImageView) headerLayout.findViewById(R.id.imageView_account);  //for navegation head action image and account
    user = (TextView) headerLayout.findViewById(R.id.user);

    mDatabaseReference=FirebaseDatabase.getInstance().getReference();

    loadfragment(new home());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Fragment fragment=null;
//                fragment=new upload();
//                loadfragment(fragment);
                startActivity(new Intent(MainActivity.this, Upload.class));

            }
        });
    mAuth = FirebaseAuth.getInstance();

//    FloatingActionButton fab1 = (FloatingActionButton) findViewById(R.id.floatingActionButton);
//    fab1.setOnClickListener(new View.OnClickListener() {
//        @Override
//        public void onClick(View view) {
////            Fragment fragment=null;
////            fragment=new upload();
////            loadfragment(fragment);
//            startActivity(new Intent(MainActivity.this, Upload.class));
//
//        }
//    });

    mAuthListner=new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            if(firebaseAuth.getCurrentUser() ==null) {
                startActivity(new Intent(MainActivity.this, splash.class));

            }
//                }else {
//                    signIn();
//                }
        }
    };


mstorStorageReference= FirebaseStorage.getInstance().getReference();
StorageReference filepath=mstorStorageReference.child("App_Images");

//    String url= mAuth.getCurrentUser().getPhotoUrl().toString();

 //   Log.d("hh", url);
if(mAuth.getCurrentUser()!=null){
    Log.d("mano", "onComplete: done user id");
    FragmentChat.IS_ACTIVATE=true;
    usernameId = mAuth.getCurrentUser().getUid();
    usernameUser=mAuth.getCurrentUser().getDisplayName();
    if(mAuth.getCurrentUser().getPhotoUrl()==null) {
        mDatabaseReference.child("userss").child(usernameId).child("img_url").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.getValue(String.class) != null) {
                    userImageUrl = dataSnapshot.getValue(String.class);


                    PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                            .edit()
                            .putString(getApplicationContext().getString(R.string.user_uid_shared_preference), usernameId)
                            .putString(getApplicationContext().getString(R.string.user_imge_url_shared_preference), userImageUrl)
                            .apply();
                    user.setText(usernameUser);
                    signed_in.setText("Signed in..");
                    String hh = String.valueOf(userImageUrl);
                    if (hh != null) {
                        Picasso.with(MainActivity.this)
                                .load(hh)
                                .resize(80, 80)
                                .centerCrop()
                                .into(nav_head_account_image);
                    } else {
                        nav_head_account_image.setImageResource(R.mipmap.baseline_account_circle_black_48);

                    }


                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }else {
        userImageUrl = mAuth.getCurrentUser().getPhotoUrl().toString();


        PreferenceManager.getDefaultSharedPreferences(getApplicationContext())
                .edit()
                .putString(getApplicationContext().getString(R.string.user_uid_shared_preference), usernameId)
                .putString(getApplicationContext().getString(R.string.user_imge_url_shared_preference), userImageUrl)
                .apply();
        user.setText(usernameUser);
        signed_in.setText("Signed in..");
        String hh = String.valueOf(userImageUrl);
        if (hh != null) {
            Picasso.with(MainActivity.this)
                    .load(hh)
                    .resize(80, 80)
                    .centerCrop()
                    .into(nav_head_account_image);
        } else {
            nav_head_account_image.setImageResource(R.mipmap.baseline_account_circle_black_48);

        }

        Log.d("immmg", "onCreate: " + mAuth.getCurrentUser().getPhotoUrl());
        nav_head_account_image.setImageURI(mAuth.getCurrentUser().getPhotoUrl());
    }

}
  nav_head_account_image.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {

        startActivity(new Intent(MainActivity.this, my_profile.class));

    }
   });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListner);
        mDatabaseReference.child("userss").child(usernameId).child("state").setValue("online");


    }

    public void uploadimg(View v){
        Intent intent = new Intent();
        intent.setType("image/*");
        //  setResult(RESULT_OK,intent);

        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PHOTO_PICKER);
    }
   public void imgprofclck(View v){


       startActivity(new Intent(MainActivity.this, UserProfile.class));

//       Fragment fragment=null;
//       fragment=new account();
////
//        loadfragment(fragment);
   }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        getApplication().onTerminate();
        super.onBackPressed();
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        if (id == R.id.action_setting2) {
            return true;
        }
        if (id == R.id.action_setting3) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")

    private  boolean loadfragment(Fragment fragment){
        if(fragment !=null){
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contanair, fragment).commit();
            return true;
        }return false;
    }
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment=null;
        switch (item .getItemId()){
            case R.id.navigation_home:
                try {
                  //  new moviessearchTask().execute("https://api.themoviedb.org/3/movie/popular?api_key=cb77b332ccbf1c0fb73ba7dca9973100");
                } catch (Exception e) {
                    Log.d("error", "error");


                }


                fragment=new home();

//                Bundle bundle1=new Bundle();
//                bundle1.putStringArrayList("test",(ArrayList) moviesList);
//                fragment.setArguments(bundle1);

                break;
            case R.id.navigation_shops:
                fragment=new CategoriesFragment();
                String [] namesCategoriesArray=getResources().getStringArray(R.array.names_categories);
                List<String> arrImmutable= Arrays.asList(namesCategoriesArray);
                ArrayList<String> arrMutable=new ArrayList<>(arrImmutable);

                Bundle bundle=new Bundle();
                bundle.putStringArrayList(getResources().getString(R.string.names_categories_key),arrMutable);
                fragment.setArguments(bundle);
                break;
            case R.id.navigation_notifications:
                Bundle l=new Bundle();

                fragment=new FragmentListUserChat();
                break;
            case R.id.navigation_shopping_car:
                fragment=new FavoriteFragment();
                break;

            case R.id.nav_camera:
                fragment=new account();
                onBackPressed();
                break;
                case R.id.nav_gallery:
                fragment=new account();
                    onBackPressed();
                break;
                case  R.id.nav_slideshow:
                fragment=new home();
                    onBackPressed();
                break;
                case R.id.my_favorite:
                fragment=new account();
                    onBackPressed();
                break;
                case R.id.nav_LogOut:
                    FragmentChat.IS_ACTIVATE=false;
                    mAuth.signOut();
                    startActivity(new Intent(MainActivity.this,splash.class));

                    onBackPressed();

                break;
            case R.id.about_app:

               Intent intent=new Intent(this,AboutApp.class);
               startActivity(intent);
                //   startActivity(new Intent(MainActivity.this,splash.class));

                onBackPressed();

                break;
            case R.id.nav_manage:
                fragment=new home();
                onBackPressed();
                break;
            case R.id.nav_header:
                fragment=new home();
                onBackPressed();
                break;

        }


        return loadfragment(fragment);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            fragment.onActivityResult(requestCode, resultCode, data);


        }

    }

    public void searchText(View view) {
        startActivity(new Intent(MainActivity.this,search.class));
    }
    public void searchButton(View view) {
        startActivity(new Intent(MainActivity.this,search.class));
    }














  /*  public class moviessearchTask extends AsyncTask<String, Void, List> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            moviesList.clear();
            //pb.setVisibility(View.VISIBLE);


        }

        @Override
        protected List doInBackground(String... params) {
            String url = params[0];


            RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);
            StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {


                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject ob1 = new JSONObject(response);
                        JSONArray a1 = ob1.getJSONArray("results");
                        for (int i = 0; i < a1.length(); i++) {
                            JSONObject ob = a1.getJSONObject(i);
                            String baseUrl = "http://image.tmdb.org/t/p/w342/";
                            String image = baseUrl + ob.getString("poster_path");

                            String Title = ob.getString("original_title");
                            Double vote1 = ob.getDouble("vote_average");
                            String vote = vote1.toString();
                            String releaseDate = ob.getString("release_date");
                            String overview = ob.getString("overview");
                            model m = new model(image, Title, releaseDate);
                            moviesList.add(m);
                        }


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(MainActivity.this, "error", Toast.LENGTH_SHORT).show();
                    //  recyclerView.setVisibility(View.INVISIBLE);

                }
            });

            requestQueue.add(stringRequest);
            return moviesList;o

        }

        @Override
        protected void onPostExecute(List movies) {

            if (movies != null || movies.isEmpty()) {

                moviesList = movies;
            /*    GridLayoutManager gridLayoutManager = new GridLayoutManager(MainActivity2.this, 2);
                gridLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

                imageAdapter = new ImageAdapter(moviesList, getApplicationContext(), this);
                recyclerView.setAdapter(imageAdapter);

                recyclerView.setLayoutManager(gridLayoutManager);
                recyclerView.setHasFixedSize(true);
                pb.setVisibility(View.GONE);


            } else {
                Toast.makeText(MainActivity.this, "error", Toast.LENGTH_LONG);

            }
        }

    }*/





}








