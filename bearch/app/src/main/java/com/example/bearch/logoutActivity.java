package com.example.bearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class logoutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

//        set all the shared preferences to their basics
        setPreference("Name","None");
        setPreference("Email","None");
        setPreference("Genre","None");
        setPreference("Instrument","None");
        setPreference("Location","None");
        setPreference("Band","None");
        setPreference("bandDescription","None");
        setPreference("bandLocation","None");
        setPreference("bandGenre","None");
        setPreference("bandRequests","None");
        setPreference("bandMembers","None");
        setPreference("BandImageURI","");

//        start mainactivity
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //    easy function for setting the shared preferences
    private void setPreference(String name, String value){
        SharedPreferences sharedPreferences=getSharedPreferences(name,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name,value);
        editor.apply();
    }
}
