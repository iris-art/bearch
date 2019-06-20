package com.example.bearch;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class memberActivity extends AppCompatActivity {

    ArrayList<String> list;
    String bandMembers;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        list = new ArrayList<>();
        ListView listView = findViewById(R.id.listView);

        SharedPreferences sharedPreferences1 = getSharedPreferences("bandMembers", MODE_PRIVATE);
        bandMembers = sharedPreferences1.getString("bandMembers", "None");
        String[] results = bandMembers.split("<>");
        for(int i=0;i<results.length;i++) {
            list.add(results[i]);
        }

        listView.setAdapter(new memberListAdapter(this, 0, list));
    }
}
