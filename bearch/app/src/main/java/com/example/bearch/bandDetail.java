package com.example.bearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class bandDetail extends AppCompatActivity {

    TextView name;
    TextView description;
    TextView location;
    TextView genre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_detail);
        name = findViewById(R.id.textView2);
        description = findViewById(R.id.textView7);
        location = findViewById(R.id.textView8);
        genre = findViewById(R.id.textView9);
        Intent intent = getIntent();
        String musican = intent.getStringExtra("band");
        String[] musican1 = musican.split("~");
        name.setText(musican1[0]);
        description.setText(musican1[1]);
        location.setText(musican1[2]);
        genre.setText(musican1[3]);
    }

    public void onClick(View view){
        Intent intent = new Intent(this, requestsActivity.class);
        startActivity(intent);
    }
}
