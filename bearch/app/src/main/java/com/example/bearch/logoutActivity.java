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
        SharedPreferences sharedPreferences6=getSharedPreferences("bandDescription",MODE_PRIVATE);
        SharedPreferences sharedPreferences7=getSharedPreferences("bandLocation",MODE_PRIVATE);
        SharedPreferences sharedPreferences8=getSharedPreferences("bandGenre",MODE_PRIVATE);
        SharedPreferences sharedPreferences9=getSharedPreferences("bandRequests",MODE_PRIVATE);
        SharedPreferences sharedPreferences10=getSharedPreferences("bandMembers",MODE_PRIVATE);
        SharedPreferences sharedPreferences11=getSharedPreferences("BandImageURI",MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        SharedPreferences.Editor editor3 = sharedPreferences3.edit();
        SharedPreferences.Editor editor4 = sharedPreferences4.edit();
        SharedPreferences.Editor editor5 = sharedPreferences5.edit();
        SharedPreferences.Editor editor6 = sharedPreferences6.edit();
        SharedPreferences.Editor editor7 = sharedPreferences7.edit();
        SharedPreferences.Editor editor8 = sharedPreferences8.edit();
        SharedPreferences.Editor editor9 = sharedPreferences9.edit();
        SharedPreferences.Editor editor10 = sharedPreferences10.edit();
        SharedPreferences.Editor editor11 = sharedPreferences11.edit();


        editor.putString("Name","None");
        editor1.putString("Email","None");
        editor2.putString("Genre","None");
        editor3.putString("Instrument","None");
        editor4.putString("Location","None");
        editor5.putString("Band","None");
        editor.putString("bandDescription","None");
        editor.putString("bandLocation","None");
        editor.putString("bandGenre","None");
        editor.putString("bandRequests","None");
        editor.putString("bandMembers","None");
        editor.putString("BandImageURI","None");

        editor.apply();
        editor1.apply();
        editor2.apply();
        editor3.apply();
        editor4.apply();
        editor5.apply();
        editor6.apply();
        editor7.apply();
        editor8.apply();
        editor9.apply();
        editor10.apply();
        editor11.apply();

        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
