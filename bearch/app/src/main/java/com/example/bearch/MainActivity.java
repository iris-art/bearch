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

        SharedPreferences sharedPreferences=getSharedPreferences("Name",MODE_PRIVATE);
        String Name =sharedPreferences.getString("Name","None");
        SharedPreferences sharedPreferences1=getSharedPreferences("Band",MODE_PRIVATE);
        String Band =sharedPreferences1.getString("Band","None");
        Button btnCreate = findViewById(R.id.button1);
        Button btnLogin = findViewById(R.id.loginbutton);
        Button btnRegister = findViewById(R.id.registerbutton);
        Button btnAccount = findViewById(R.id.accountButton);
        Button btnLogout = findViewById(R.id.logoutButton);

        if (Name.equals("None")){
            btnCreate.setClickable(false);
            btnCreate.setText("Please log in to create your band");
        }
        else{
            btnLogin.setVisibility(View.INVISIBLE);
            btnLogin.setClickable(false);
            btnRegister.setClickable(false);
            btnRegister.setVisibility(View.INVISIBLE);
            btnAccount.setVisibility(View.VISIBLE);
            btnAccount.setClickable(true);
            btnLogout.setClickable(true);
            btnLogout.setVisibility(View.VISIBLE);
        }
        try{
            String[] band = Band.split("%");
            btnCreate.setText("Request to:" + band[1]);
            btnCreate.setClickable(false);
        }catch(Exception e){
            e.printStackTrace();
            if (!Band.equals("None")) {

                btnCreate.setVisibility(View.VISIBLE);
                btnCreate.setClickable(true);
                btnCreate.setText("My band");
                btnCreate.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, bandActivity.class);
                        startActivity(intent);
                    }
                });
            }
        }


    }

    public void onClick1(View view){
        Intent intent = new Intent(this, CreateBandActivity.class);

        startActivity(intent);
    }

    public void onClick2(View view){
        Intent intent = new Intent(this, FilterBandActivity.class);
        startActivity(intent);
    }

    public void onClick3(View view){
        Intent intent = new Intent(this, FilterMusicanActivity.class);
        startActivity(intent);
    }

    public void onClick4(View view){
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
    }

    public void onClick5(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onClick6(View view){
        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
    }
    public void onClick7(View view){
        Intent intent = new Intent(this, logoutActivity.class);
        startActivity(intent);
    }


}
