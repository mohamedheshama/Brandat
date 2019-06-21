package com.example.moo.brandat;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class my_profile extends AppCompatActivity implements View.OnClickListener {

    // BottomSheetBehavior variable
    private BottomSheetBehavior bottomSheetBehavior;

    // TextView variable
    private TextView bottomSheetHeading;

    // Button variables
    private Button expandBottomSheetButton;
    private Button collapseBottomSheetButton;
    private Button hideBottomSheetButton;
    private Button showBottomSheetDialogButton;
    private Button go;
    private FloatingActionButton more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bottom_sheets_abb_bar);
        more=(FloatingActionButton)findViewById(R.id.more);
        initViews();
        initListeners();




    }


    private void initViews() {


        bottomSheetBehavior = BottomSheetBehavior.from(findViewById(R.id.bottomSheetLayout));


      //  go = (Button) findViewById(R.id.go);

    }


    /**
     * method to initialize the listeners
     */
    private void initListeners() {

        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
        go.setOnClickListener(this);

    }

    /**
     * onClick Listener to capture button click
     *
     * @param v
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {

//            case R.id.go:
//                bottomSheetBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);
//
//                break;

        }
    }

    public void more(View view) {
        bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);

    }
}
