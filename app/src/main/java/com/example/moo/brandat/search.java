package com.example.moo.brandat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class search extends AppCompatActivity {
    private Button mSearchButton;
    private EditText mSearchEditText;
//FC1505F8
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        final SearchFragment searchFragment=new SearchFragment();


        mSearchButton=findViewById(R.id.search_button);
        mSearchEditText=findViewById(R.id.search_edit_text);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_contanair2, searchFragment).commit();


        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("select search type");
//
//// add a list
//        String[] animals = {"Normal search", "Filter search", "Map search"};
//        builder.setItems(animals, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                switch (which) {
//                    case 0: // horse
//                        Toast.makeText(search.this,"sucsess selecte -: " ,Toast.LENGTH_SHORT).show();
//                    case 1: // cow
//                    case 2: // camel
//
//                }
//            }
//        });
//
//// create and show the alert dialog
//        AlertDialog dialog = builder.create();
//        dialog.show();

        mSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchFragment.searchWord(mSearchEditText.getText().toString());
                if(getCurrentFocus()!=null) {
                    InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                }
            }
        });
    }
}
