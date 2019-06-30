package com.example.moo.brandat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

public class UpdateProduct extends AppCompatActivity {


    TextView publish;
    EditText fname;EditText category;EditText casee;EditText cost;EditText pdescribe;EditText location;EditText ownername;EditText phone;EditText email;
    ImageView imageView;
    Uri mImgUri=null;
    private static final int GALLARY_REQUEST=1;
    private StorageReference mStorageReference;
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabasecat;
    private DatabaseReference mDatabaseuser;
    private static final int PHOTO_PICKER = 2;


    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;


     String product_key;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_product);



        fname=(EditText)findViewById(R.id.fullName);
        category=(EditText)findViewById(R.id.category);
        casee=(EditText)findViewById(R.id.casee);
        cost=(EditText)findViewById(R.id.cost);
        ownername=(EditText)findViewById(R.id.ownerName);
        pdescribe=(EditText)findViewById(R.id.descripe);
        location=(EditText)findViewById(R.id.location);
        phone=(EditText)findViewById(R.id.phoneNumber);
        email=(EditText)findViewById(R.id.email);
        publish=(TextView) findViewById(R.id.textView6);
        imageView=(ImageView) findViewById(R.id.viewImage);
        mProgress=new ProgressDialog(this);
        mStorageReference= FirebaseStorage.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
        mDatabase= FirebaseDatabase.getInstance().getReference().child("userss").child(mCurrentUser.getUid());

        mDatabasecat= FirebaseDatabase.getInstance().getReference().child("categories");







        Intent intent = getIntent();
        final String name = intent.getStringExtra("fname");
        final String cat = intent.getStringExtra("category");
        final String cos = intent.getStringExtra("cost");
        final String em = intent.getStringExtra("time");
        final Uri im = Uri.parse(intent.getStringExtra("img"));
        final String loc = intent.getStringExtra("location");
        final String ow = intent.getStringExtra("owner");
        final String pcas = intent.getStringExtra("pcase");
        final String fon = intent.getStringExtra("quantity");
        final String uImg = intent.getStringExtra("img_url");
         String uId = intent.getStringExtra("user_id");
        String prodescribe=intent.getStringExtra("prodescribe");
         product_key=intent.getStringExtra("product_key");
fname.setText(name);
category.setText(cat);
casee.setText(pcas);
cost.setText(cos);
ownername.setText(ow);
pdescribe.setText(prodescribe);
location.setText(loc);
phone.setText(fon);
email.setText(em);
        Picasso.with(this)
                .load(im)
                .resize(80, 80)
                .centerCrop()
                .into(imageView);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                //  setResult(RESULT_OK,intent);

                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Picture"), PHOTO_PICKER);

            }
        });



        publish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                publish();

            }
        });

    }




    private void publish() {
        final String pname = fname.getText().toString().trim();
        final String categories = category.getText().toString().trim();
        final String caseee = casee.getText().toString().trim();
        final String costs = cost.getText().toString().trim();
        final String oname = ownername.getText().toString().trim();
        final String prodes = pdescribe.getText().toString().trim();
        final String loc = location.getText().toString().trim();
        final String fone = phone.getText().toString().trim();
        final String emai = email.getText().toString().trim();
        mProgress.setMessage("Posting ...");
        mProgress.show();
        if (mImgUri != null) {
            final StorageReference filepath = mStorageReference.child("App_Images").child(mImgUri.getLastPathSegment());
            UploadTask uploadTask = filepath.putFile(mImgUri);

            Task<Uri> urlTask = uploadTask.continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                @Override
                public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                    if (!task.isSuccessful()) {
                        throw task.getException();
                    }
                    filepath.getDownloadUrl();
                    // Continue with the task to get the download URL
                    return filepath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    if (task.isSuccessful()) {
                        final Uri downloadUri = task.getResult();

                        final DatabaseReference productsData = mDatabase.child("products").child(product_key);
                        String key = productsData.getKey();
                        final DatabaseReference categoriesData = mDatabasecat.child(categories).child(key);


                                productsData.child("product_name").setValue(pname);
                                productsData.child("category").setValue(categories);
                                productsData.child("product_case").setValue(caseee);
                                productsData.child("cost").setValue(costs);
                                productsData.child("ownername").setValue(oname);
                                productsData.child("product_des").setValue(prodes);
                                productsData.child("location").setValue(loc);
                        productsData.child("quantity").setValue(fone);
                        Log.d("quantity", "onComplete: "+fone+"  "+java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));
                        productsData.child("time").setValue(java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));

                        productsData.child("imagesrc").setValue(downloadUri.toString());
                        Log.d("photo", "onComplete: "+downloadUri);
                                productsData.child("img_url").setValue(mAuth.getCurrentUser().getPhotoUrl().toString());
                                productsData.child("user_id").setValue(mAuth.getCurrentUser().getUid());


                                categoriesData.child("product_name").setValue(pname);
                                categoriesData.child("category").setValue(categories);
                                categoriesData.child("product_case").setValue(caseee);
                                categoriesData.child("cost").setValue(costs);
                                categoriesData.child("ownername").setValue(oname);
                                categoriesData.child("product_des").setValue(prodes);
                                categoriesData.child("location").setValue(loc);
                        categoriesData.child("quantity").setValue(fone);
                        categoriesData.child("time").setValue(java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));

                        categoriesData.child("imagesrc").setValue(downloadUri.toString());
                                categoriesData.child("img_url").setValue(mAuth.getCurrentUser().getPhotoUrl().toString());

                                categoriesData.child("user_id").setValue(mAuth.getCurrentUser().getUid());

                                mProgress.dismiss();
                                Toast.makeText(getApplicationContext(), "Your ptoduct's data has updated succesfully.", Toast.LENGTH_SHORT).show();



startActivity(new Intent(UpdateProduct.this,MainActivity.class));


                    } else {
                        mProgress.dismiss();

                        Toast.makeText(getApplicationContext(), "task not succesful", Toast.LENGTH_LONG).show();

                        // Handle failures
                        // ...
                    }
                }
            });


        } else {

            final DatabaseReference productsData = mDatabase.child("products").child(product_key);
            String key = productsData.getKey();
            final DatabaseReference categoriesData = mDatabasecat.child(categories).child(product_key);


                    productsData.child("product_name").setValue(pname);
                    productsData.child("category").setValue(categories);
                    productsData.child("product_case").setValue(caseee);
                    productsData.child("cost").setValue(costs);
                    productsData.child("ownername").setValue(oname);
                    productsData.child("product_des").setValue(prodes);
                    productsData.child("location").setValue(loc);
            productsData.child("quantity").setValue(fone);
            productsData.child("time").setValue(java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));

            // productsData.child("imagesrc").setValue(downloadUri.toString());
                    productsData.child("img_url").setValue(mAuth.getCurrentUser().getPhotoUrl().toString());
                    productsData.child("user_id").setValue(mAuth.getCurrentUser().getUid());


                    categoriesData.child("product_name").setValue(pname);
                    categoriesData.child("category").setValue(categories);
                    categoriesData.child("product_case").setValue(caseee);
                    categoriesData.child("cost").setValue(costs);
                    categoriesData.child("ownername").setValue(oname);
                    categoriesData.child("product_des").setValue(prodes);
                    categoriesData.child("location").setValue(loc);
            categoriesData.child("quantity").setValue(fone);
            categoriesData.child("time").setValue(java.text.DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime()));

            //categoriesData.child("imagesrc").setValue(downloadUri.toString());
                    categoriesData.child("img_url").setValue(mAuth.getCurrentUser().getPhotoUrl().toString());

                    categoriesData.child("user_id").setValue(mAuth.getCurrentUser().getUid());

                    mProgress.dismiss();
                    Toast.makeText(getApplicationContext(), "Your ptoduct's data has inserted succesfully.", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(UpdateProduct.this,MainActivity.class));


                }


        }





    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode==PHOTO_PICKER &&resultCode==RESULT_OK){

            mImgUri=data.getData();
            imageView.setImageURI(mImgUri);

        }


    }

    public void category(View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("select Category type");

// add a list
        String[] categries = {"Cars", "Mobiles", "Electronics","Animals","Music and Books","Jops","Houses","Clothes"};
        builder.setItems(categries, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:
                        category.setText("Cars");
                        break;
                    case 1:
                        category.setText("Mobiles");
                        break;
                    case 2:
                        category.setText("Electronics");
                        break;
                    case 3:
                        category.setText("Animals");
                        break;
                    case 4:
                        category.setText("Music and Books");
                        break;
                    case 5:
                        category.setText("Jops");
                        break;
                    case 6:
                        category.setText("Houses");
                        break;
                    case 7:
                        category.setText("Clothes");
                        break;

                }
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }

}
