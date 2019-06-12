package com.example.bearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class logoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("HEY WAAROM DOE JE DIT?", "JOE");
        SharedPreferences sharedPreferences=getSharedPreferences("Name",MODE_PRIVATE);
        SharedPreferences sharedPreferences1=getSharedPreferences("Email",MODE_PRIVATE);
        SharedPreferences sharedPreferences2=getSharedPreferences("Genre",MODE_PRIVATE);
        SharedPreferences sharedPreferences3=getSharedPreferences("Instrument",MODE_PRIVATE);
        SharedPreferences sharedPreferences4=getSharedPreferences("Location",MODE_PRIVATE);
        SharedPreferences sharedPreferences5=getSharedPreferences("Band",MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        SharedPreferences.Editor editor3 = sharedPreferences3.edit();
        SharedPreferences.Editor editor4 = sharedPreferences4.edit();
        SharedPreferences.Editor editor5 = sharedPreferences5.edit();

        editor.putString("Name","None");
        editor1.putString("Email","None");
        editor2.putString("Genre","None");
        editor3.putString("Instrument","None");
        editor4.putString("Location","None");
        editor5.putString("Band","None");

        editor.apply();
        editor1.apply();
        editor2.apply();
        editor3.apply();
        editor4.apply();
        editor5.apply();
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
