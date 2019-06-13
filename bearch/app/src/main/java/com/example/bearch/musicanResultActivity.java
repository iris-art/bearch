package com.example.bearch;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class musicanResultActivity extends AppCompatActivity {

    ListView nListView;
    ArrayList<String> MusicanResults;
    String stEmail;
    String stPassword;
    String instrument;
    String genre;
    String city;
    String region;


    final static String url_users = "https://joostappapi.000webhostapp.com/read_users.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musican_result);
        nListView = findViewById(R.id.listView2);
        nListView.setOnItemClickListener(new GridItemClickListener());
        MusicanResults = new ArrayList<>();
        Intent intent = getIntent();
        instrument = intent.getStringExtra("instrument");
        genre = intent.getStringExtra("genre");
        city = intent.getStringExtra("city");
        region = intent.getStringExtra("region");
        new GetUsers().execute();

    }

    public class GetUsers extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .build();
            Request request = new Request.Builder()
                    .url(url_users)
                    .post(formBody)
                    .build();
            Response response = null;

            try{
                response = okHttpClient.newCall(request).execute();

                if(response.isSuccessful()){
                    String result = response.body().string();
                    String[] results = result.split("%");
                    for(int i = 0; i < results.length;i++){
                        String[] user = results[i].split("~");
                        for(int j = 0 ; j < user.length; j ++){

                        }
                        Log.d("results", user[4] + instrument);
                        if (city.equals("All") || user[2].equals(city)){
                            if (genre.equals("Diverse") || user[5].equals(genre)){
                                    if (instrument.equals("All") || user[4].equals(instrument)){
                                        MusicanResults.add(results[i]);
                                    }
                            }
                        }
                    }
                    runOnUiThread(new Runnable(){
                        @Override
                        public void run() {
                            nListView.setAdapter(new musicanListAdapter(musicanResultActivity.this, 0, MusicanResults));
                        }
                    });


                }
                else{
                    Log.d("joe", "joe");
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }

    private class GridItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        }
    }
}
