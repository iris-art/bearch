package com.example.bearch;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

public class requestsActivity extends AppCompatActivity {

    ListView nListView;
    ArrayList<String> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        list = new ArrayList<>();
        nListView = findViewById(R.id.listView3);
        SharedPreferences sharedPreferences = getSharedPreferences("bandRequests", MODE_PRIVATE);
        String result = sharedPreferences.getString("bandRequests", "None");
        String[] results = result.split("#");
        for(int i=0;i<results.length;i++) {
            list.add(results[i]);
        }
        nListView.setAdapter(new requestListAdapter(this, 0, list));
    }
}
