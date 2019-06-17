package com.example.bearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class requestsActivity extends AppCompatActivity {

    ListView nListView;
    ArrayList<String> list;
    String bandRequests;
    String bandMembers;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requests);
        list = new ArrayList<>();
        nListView = findViewById(R.id.listView3);
        nListView.setOnItemClickListener(new requestsActivity.GridItemClickListener());
        SharedPreferences sharedPreferences = getSharedPreferences("bandRequests", MODE_PRIVATE);
        bandRequests = sharedPreferences.getString("bandRequests", "None");
        SharedPreferences sharedPreferences1 = getSharedPreferences("bandMembers", MODE_PRIVATE);
        bandMembers = sharedPreferences1.getString("bandMembers", "None");
        String[] results = bandRequests.split("<>");
        for(int i=0;i<results.length;i++) {
            list.add(results[i]);
        }
        nListView.setAdapter(new requestListAdapter(this, 0, list));
    }

    private class GridItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String Email = list.get(position);
            String string = "";
            String string1;
            if(!bandMembers.equals("Null")){
                string1 = bandMembers + "<>" + Email;
            }else{
                string1 = Email;
            }

            String[] results = bandRequests.split("<>");
            for(int i=0;i<results.length;i++){
                if(!results[i].equals(Email)){
                    string += results[i] + "<>";
                }
            }
            bandRequests = string;
            bandMembers = string1;
            SharedPreferences sharedPreferences13 = getSharedPreferences("bandRequests", MODE_PRIVATE);
            SharedPreferences sharedPreferences14 = getSharedPreferences("bandMembers", MODE_PRIVATE);
            SharedPreferences.Editor editor13 = sharedPreferences13.edit();
            SharedPreferences.Editor editor14 = sharedPreferences14.edit();
            editor13.putString("bandRequests", string);
            editor14.putString("bandMembers", string1);
            editor13.apply();
            editor14.apply();
            list = new ArrayList<>();
            results = bandRequests.split("<>");
            for(int i=0;i<results.length;i++) {
                list.add(results[i]);
            }
            nListView.setAdapter(new requestListAdapter(requestsActivity.this, 0, list));
            SharedPreferences sharedPreferences=getSharedPreferences("Band",MODE_PRIVATE);
            String band = sharedPreferences.getString("Band", "None");
            new AddBandMember().execute(Email, band, string, string1);

        }
    }

    public class AddBandMember extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String Email = strings[0];
            String Band = strings[1];
            String string = strings[2];
            String string1 = strings[3];
            String finalURL = "https://joostappapi.000webhostapp.com/accept_request.php" +
                    "?user_id=" + Email +
                    "&band_name=" + Band +
                    "&string="+ string +
                    "&string1="+ string1;

            Log.d("URL = ", finalURL);
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request =  new Request.Builder()
                    .url(finalURL)
                    .get()
                    .build();
            Response response = null;
            try{
                response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()){
                    String result = response.body().string();
                    Log.d("result = ", result);
                    if (result.equalsIgnoreCase("User registered successfully")){

                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }
    }
}
