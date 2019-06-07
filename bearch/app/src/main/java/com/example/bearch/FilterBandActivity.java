package com.example.bearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class FilterBandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_band);
    }

    public void onClick(View view){
        Intent intent = new Intent(this, bandResultActivity.class);
        startActivity(intent);
    }
}
