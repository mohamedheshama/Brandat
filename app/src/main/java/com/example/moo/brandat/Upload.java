package com.example.moo.brandat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
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

import java.text.DateFormat;
import java.util.Calendar;

import static android.app.Activity.RESULT_OK;

public class Upload  extends AppCompatActivity implements TextWatcher {

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
    private String loc="";
    Switch isShop1;
    private ProgressDialog mProgress;
    private FirebaseAuth mAuth;
    private FirebaseUser mCurrentUser;
    String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        isShop1=(Switch) findViewById(R.id.switch11);

        fname=(EditText)findViewById(R.id.fullName);
        category=(EditText)findViewById(R.id.category);
        casee=(EditText)findViewById(R.id.casee);
        cost=(EditText)findViewById(R.id.cost);
        ownername=(EditText)findViewById(R.id.ownerName);
        pdescribe=(EditText)findViewById(R.id.descripe);
      //  location=(EditText)findViewById(R.id.location);
        phone=(EditText)findViewById(R.id.phoneNumber);
        email=(EditText)findViewById(R.id.email);
        publish=(TextView)findViewById(R.id.textView6);
        imageView=(ImageView)findViewById(R.id.viewImage);
        mProgress=new ProgressDialog(this);
mStorageReference= FirebaseStorage.getInstance().getReference();
        mAuth=FirebaseAuth.getInstance();
        mCurrentUser=mAuth.getCurrentUser();
mDatabase= FirebaseDatabase.getInstance().getReference().child("userss").child(mCurrentUser.getUid());


        ownername.setText(MainActivity.usernameUser);
        mDatabasecat= FirebaseDatabase.getInstance().getReference().child("categories");
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

        category.addTextChangedListener(this);
        casee.addTextChangedListener(this);
        fname.addTextChangedListener(this);
        cost.addTextChangedListener(this);
        ownername.addTextChangedListener(this);
        pdescribe.addTextChangedListener(this);
        //location.addTextChangedListener(this);
        phone.addTextChangedListener(this);
        //email.addTextChangedListener(this);


        publish.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {


        int i = 1;
        if (mImgUri != null) {
i=1;
        } else
        {
            i=3;
        }
        if ( !(fname.getText().toString().length() == 0) ){
            fname.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }else {
            fname.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_red,0, 0, 0);
i=2;

        }
        if (!(casee.getText().toString().length() == 0) ){
            casee.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            casee.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_red,0, 0, 0);
            i=2;

        }



        if (!(category.getText().toString().length() == 0) ){
            category.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            category.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_red,0, 0, 0);
            i=2;

        }

        if ( !(cost.getText().toString().length() == 0) ){
            cost.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            cost.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_red,0, 0, 0);
            i=2;

        }
        if (!(ownername.getText().toString().length() == 0) ){
            ownername.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            ownername.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_red,0, 0, 0);
            i=2;

        }
        if (!(pdescribe.getText().toString().length() == 0) ){
            pdescribe.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            pdescribe.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_red,0, 0, 0);
            i=2;

        }
        if ( !(phone.getText().toString().length() == 0) ){
            phone.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else {
            phone.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_red,0, 0, 0);
            i=2;

        }
        String message="";
        boolean s=isShop1.isChecked();
        if(s){

            // TODO: code saad get the my location and send
            final int finalI = i;
            mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.d("mano", "onDataChange: "+dataSnapshot.hasChild("location"));
                    if (dataSnapshot.hasChild("location")){
                        loc=dataSnapshot.child("location").getValue(String.class);
                        Log.d("mano", "onDataChange: you have location  "+finalI);
                        if (finalI ==1) {

                            publish();
                        }else if (finalI==3){
                        alertOnimage();
                    }
                        else {
                            alertOneButton("error in input data");

                        }
                    }else {
                        alertOneButton("you don't put location yet please put it first");

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else {

            if (i == 1) {

                publish();
            }else if (i==3){
                         alertOnimage();
        }else {

                alertOneButton("error in input data");

            }
        }

    }
});
    }
//    public void uploadimge(View v){
//        Intent intent = new Intent();
//        intent.setType("image/*");
//        //  setResult(RESULT_OK,intent);
//
//        intent.setAction(Intent.ACTION_GET_CONTENT);
//        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PHOTO_PICKER);
//    }
    private void publish(){
        final String pname=fname.getText().toString().trim();
        final String categories=category.getText().toString().trim();
        final String caseee=casee.getText().toString().trim();
        final String costs=cost.getText().toString().trim();
        final String oname=ownername.getText().toString().trim();
        final String prodes=pdescribe.getText().toString().trim();
      //final  String loc=location.getText().toString().trim();
        final String fone=phone.getText().toString().trim();
//        final String emai=email.getText().toString().trim();
mProgress.setMessage("Posting ...");
mProgress.show();

  final StorageReference filepath=mStorageReference.child("App_Images").child(mImgUri.getLastPathSegment());
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

final DatabaseReference productsData=mDatabase.child("products").push();
 key=productsData.getKey();
                    final DatabaseReference categoriesData=mDatabasecat.child(categories).child(key);


                            String date = DateFormat.getDateTimeInstance().format(Calendar.getInstance().getTime());
                            productsData.child("product_name").setValue(pname);
                            productsData.child("category").setValue(categories);
                            productsData.child("product_case").setValue(caseee);
                            productsData.child("cost").setValue(costs);
                            productsData.child("ownername").setValue(oname);
                            productsData.child("product_des").setValue(prodes);
                            productsData.child("location").setValue(loc);
                            productsData.child("quantity").setValue(fone);
                            productsData.child("time").setValue(date);
                            productsData.child("imagesrc").setValue(downloadUri.toString());
                            if (MainActivity.userImageUrl!=null) {
                                productsData.child("img_url").setValue(MainActivity.userImageUrl);
                            }else {
                                productsData.child("img_url").setValue(mAuth.getCurrentUser().getPhotoUrl().toString());

                            }
                            productsData.child("user_id").setValue(mAuth.getCurrentUser().getUid());
                            productsData.child("product_key").setValue(key);


                            categoriesData.child("product_name").setValue(pname);
                            categoriesData.child("category").setValue(categories);
                            categoriesData.child("product_case").setValue(caseee);
                            categoriesData.child("cost").setValue(costs);
                            categoriesData.child("ownername").setValue(oname);
                            categoriesData.child("product_des").setValue(prodes);
                            categoriesData.child("location").setValue(loc);
                            categoriesData.child("quantity").setValue(fone);
                            categoriesData.child("time").setValue(date);
                            categoriesData.child("imagesrc").setValue(downloadUri.toString());
                            if (MainActivity.userImageUrl!=null) {
                                categoriesData.child("img_url").setValue(MainActivity.userImageUrl);
                            }else {
                                categoriesData.child("img_url").setValue(mAuth.getCurrentUser().getPhotoUrl().toString());

                            }


                            categoriesData.child("user_id").setValue(mAuth.getCurrentUser().getUid());
                            categoriesData.child("product_key").setValue(key);


                            mProgress.dismiss();
                            Toast.makeText(getApplicationContext(), "Your ptoduct's data has inserted succesfully.", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Upload.this, MainActivity.class));



                } else {
 mProgress.dismiss();

                    Toast.makeText(getApplicationContext(),"task not succesful",Toast.LENGTH_LONG ).show();

                    // Handle failures
                    // ...
                }
            }
        });








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


    @Override
    public void beforeTextChanged(CharSequence editable, int i, int i1, int i2) {
        if (fname.getText().hashCode() == editable.hashCode()&& !(fname.getText().toString().length() == 0) ){
            fname.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }else {
            fname.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_radio_button_unchecked_black_24dp,0, 0, 0);


        }
        if (casee.getText().hashCode() == editable.hashCode()&& !(casee.getText().toString().length() == 0) ){
            casee.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            casee.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_radio_button_unchecked_black_24dp,0, 0, 0);


        }



        if (category.getText().hashCode() == editable.hashCode()&& !(category.getText().toString().length() == 0) ){
            category.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            category.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_radio_button_unchecked_black_24dp,0, 0, 0);


        }

        if (cost.getText().hashCode() == editable.hashCode()&& !(cost.getText().toString().length() == 0) ){
            cost.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            cost.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_radio_button_unchecked_black_24dp,0, 0, 0);


        }
        if (ownername.getText().hashCode() == editable.hashCode()&& !(ownername.getText().toString().length() == 0) ){
            ownername.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            ownername.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_radio_button_unchecked_black_24dp,0, 0, 0);


        }
        if (pdescribe.getText().hashCode() == editable.hashCode()&& !(pdescribe.getText().toString().length() == 0) ){
            pdescribe.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            pdescribe.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_radio_button_unchecked_black_24dp,0, 0, 0);


        }
        if (phone.getText().hashCode() == editable.hashCode()&& !(phone.getText().toString().length() == 0) ){
            phone.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            phone.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_radio_button_unchecked_black_24dp,0, 0, 0);


        }

    }

    @Override
    public void onTextChanged(CharSequence editable, int i, int i1, int i2) {



    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (fname.getText().hashCode() == editable.hashCode()&& !(fname.getText().toString().length() == 0) ){
            fname.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }else {
            fname.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_radio_button_unchecked_black_24dp,0, 0, 0);


        }
        if (casee.getText().hashCode() == editable.hashCode()&& !(casee.getText().toString().length() == 0) ){
            casee.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            casee.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_radio_button_unchecked_black_24dp,0, 0, 0);


        }



        if (category.getText().hashCode() == editable.hashCode()&& !(category.getText().toString().length() == 0) ){
            category.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            category.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_radio_button_unchecked_black_24dp,0, 0, 0);


        }

        if (cost.getText().hashCode() == editable.hashCode()&& !(cost.getText().toString().length() == 0)&&(cost.getText().toString().length() <= 3) ){
            cost.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            cost.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_radio_button_unchecked_black_24dp,0, 0, 0);


        }
        if (ownername.getText().hashCode() == editable.hashCode()&& !(ownername.getText().toString().length() == 0) ){
            ownername.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            ownername.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_radio_button_unchecked_black_24dp,0, 0, 0);


        }
        if (pdescribe.getText().hashCode() == editable.hashCode()&& !(pdescribe.getText().toString().length() == 0) ){
            pdescribe.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else{
            pdescribe.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_radio_button_unchecked_black_24dp,0, 0, 0);


        }
        if (phone.getText().hashCode() == editable.hashCode()&& !(phone.getText().toString().length() == 0) ){
            phone.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_check_circle_black_24dp,0, 0, 0);
        }
        else {
            phone.setCompoundDrawablesWithIntrinsicBounds( R.drawable.ic_radio_button_unchecked_black_24dp,0, 0, 0);


        }
    }
    public void alertOneButton(String message) {

        new AlertDialog.Builder(Upload.this)
                .setTitle("error ")
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }
    public void alertOnimage() {

        new AlertDialog.Builder(Upload.this)
                .setTitle("error in input image")
                .setMessage("error in image input ")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        //  setResult(RESULT_OK,intent);

                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PHOTO_PICKER);

                        dialog.cancel();
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();
    }

}
