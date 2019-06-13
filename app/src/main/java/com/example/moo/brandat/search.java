package com.example.moo.brandat;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class search extends AppCompatActivity {
//FC1505F8
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("select search type");

// add a list
        String[] animals = {"Normal search", "Filter search", "Map search"};
        builder.setItems(animals, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0: // horse
                        Toast.makeText(search.this,"sucsess selecte -: " ,Toast.LENGTH_SHORT).show();
                    case 1: // cow
                    case 2: // camel

                }
            }
        });

// create and show the alert dialog
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
