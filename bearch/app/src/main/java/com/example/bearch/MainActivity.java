package com.example.bearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.brushed1);

//        get User values from sharedPreferences
        SharedPreferences sharedPreferences=getSharedPreferences("Name",MODE_PRIVATE);
        String Name =sharedPreferences.getString("Name","None");
        SharedPreferences sharedPreferences1=getSharedPreferences("Band",MODE_PRIVATE);
        String Band =sharedPreferences1.getString("Band","None");

//        define buttons
        Button btnBand = findViewById(R.id.button1);
        Button btnLeft = findViewById(R.id.btnLeft);
        Button btnRight = findViewById(R.id.btnRight);

        if (Name.equals("None")){
//            if user is not logged in
            btnBand.setClickable(false);
            btnBand.setText("Please log in to create your band");

//            set buttons correct to login and register
            btnLeft.setOnClickListener(this::logIn);
            btnRight.setOnClickListener(this::Register);
            btnLeft.setText("Log In");
            btnRight.setText("Register");
        }else{
            btnLeft.setOnClickListener(this::Profile);
            btnRight.setOnClickListener(this::logOut);
            btnLeft.setText("Profile");
            btnRight.setText("Log Out");
            try{
                String[] band = Band.split("%");
                btnBand.setText("Request to:" + band[1]);
                btnBand.setClickable(false);
            }catch(Exception e){
                if (!Band.equals("None")) {
                    btnBand.setClickable(true);
                    btnBand.setText("My band");
                    btnBand.setOnClickListener(this::myBand);
                }else{
                    btnBand.setClickable(true);
                    btnBand.setText("Create Band");
                    btnBand.setOnClickListener(this::createBand);
                }
            }

        }



    }

//     call function for createBand button
    public void createBand(View view){
        Intent intent = new Intent(this, CreateBandActivity.class);
        startActivity(intent);
    }

//     call function for FilterBand button
    public void FilterBand(View view){
        Intent intent = new Intent(this, FilterBandActivity.class);
        startActivity(intent);
    }

//     call function for FilterMusican button
    public void FilterMusican(View view){
        Intent intent = new Intent(this, FilterMusicanActivity.class);
        startActivity(intent);
    }

//     call function for login button
    public void logIn(View view){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

//     call function for register button
    public void Register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

//     call function for profile button
    public void Profile(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }

//     call function for logout button
    public void logOut(View view){
        Intent intent = new Intent(this, logoutActivity.class);
        startActivity(intent);
    }

//    call function for my band
    public void myBand(View v) {
        Intent intent = new Intent(MainActivity.this, bandActivity.class);
        startActivity(intent);
    }
}
