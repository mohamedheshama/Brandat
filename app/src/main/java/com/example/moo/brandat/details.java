package com.example.moo.brandat;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mikhaellopez.circularimageview.CircularImageView;
import com.squareup.picasso.Picasso;

public class details extends AppCompatActivity {
    TextView fname;TextView category;TextView casee;TextView cost;TextView pdescribe;TextView location;TextView ownername;TextView phone;TextView email;
ImageView imageView;
CircularImageView circularImageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        fname=(TextView)findViewById(R.id.fullName);
        category=(TextView)findViewById(R.id.category);
        casee=(TextView)findViewById(R.id.case2);
        cost=(TextView)findViewById(R.id.cost);
        ownername=(TextView)findViewById(R.id.ownerName);
        pdescribe=(TextView)findViewById(R.id.descripe);
        location=(TextView)findViewById(R.id.location);
        phone=(TextView)findViewById(R.id.phone);
        email=(TextView)findViewById(R.id.email);
imageView=findViewById(R.id.viewImage);
circularImageView=findViewById(R.id.circularImageView);





        Intent intent = getIntent();
       String name = intent.getStringExtra("fname");
        String cat = intent.getStringExtra("category");
        String cos = intent.getStringExtra("cost");
        String em = intent.getStringExtra("email");
        String im = intent.getStringExtra("img");
        String loc = intent.getStringExtra("location");
        String ow = intent.getStringExtra("owner");
        String pcas = intent.getStringExtra("pcase");
        String fon = intent.getStringExtra("phone");
        final String uImg = intent.getStringExtra("img_url");
        final String uId = intent.getStringExtra("user_id");

        Log.d("imgitem", "onImageClick: "+uImg);

        fname.setText(name);
        category.setText(cat);
        cost.setText(cos);
        email.setText(em);
        location.setText(loc);
        ownername.setText(ow);
        casee.setText(pcas);
        phone.setText(fon);

        fname.setText(name);



        Picasso.with(this).load(im).into(imageView);
        Picasso.with(this).load(uImg).into(circularImageView);

        circularImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1=new Intent(details.this,UserProfile.class);
                intent1.putExtra("UserId",uId);
                intent1.putExtra("img_url",uImg);

                startActivity(intent1);

            }
        });





    }
}
