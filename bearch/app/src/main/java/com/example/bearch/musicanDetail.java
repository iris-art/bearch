package com.example.bearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class musicanDetail extends AppCompatActivity {

    TextView name;
    TextView description;
    TextView location;
    TextView genre;
    TextView instrument;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musican_detail);
        name = findViewById(R.id.textView2);
        description = findViewById(R.id.textView7);
        location = findViewById(R.id.textView8);
        genre = findViewById(R.id.textView9);
        instrument = findViewById(R.id.textView10);

        Intent intent = getIntent();
        String musican = intent.getStringExtra("musican");
        String[] musican1 = musican.split("~");
        for(int i=0;i<musican1.length;i++){
            Log.d("musican" , musican1[i]);
        }
        name.setText(musican1[0]);
        description.setText(musican1[1]);
        location.setText(musican1[2]);
        genre.setText(musican1[5]);
        instrument.setText(musican1[4]);
    }
}
