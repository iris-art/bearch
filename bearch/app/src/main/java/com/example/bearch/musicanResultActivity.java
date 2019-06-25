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

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class musicanResultActivity extends AppCompatActivity {

    ListView nListView;
    ArrayList<String> MusicanResults;
    String instrument_filter;
    String genre_filter;
    String city_filter;
    String region_filter;
    String[] provinces;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_musican_result);
        musicanResultActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.background7);

//        define ListView and list
        nListView = findViewById(R.id.listView2);
        nListView.setOnItemClickListener(new GridItemClickListener());
        MusicanResults = new ArrayList<>();

//        get extras from intent
        Intent intent = getIntent();
        instrument_filter = intent.getStringExtra("instrument");
        genre_filter = intent.getStringExtra("genre");
        city_filter = intent.getStringExtra("city");
        region_filter = intent.getStringExtra("region");

//        String[] with provinces
        provinces = getResources().getStringArray(R.array.Provinces);

        new GetUsers().execute();

    }

//    function for getting all the users from the API
    public class GetUsers extends AsyncTask<String, Void, String> {

    String result;

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

//        get all the users from string
        String[] results = result.split("~");
        for(int i = 0; i < results.length;i++) {

//            define values
            String province = null;
            String genre = null;
            String instrument = null;
            String location = null;

//            try getting all the users from JSONobject
            try {

//                set all values if possible
                JSONObject Jobject = new JSONObject(results[i]);
                location = Jobject.getString("location");
                instrument = Jobject.getString("instrument");
                genre = Jobject.getString("genre");
                province = Jobject.getString("province");
            }catch(Exception e){
                e.printStackTrace();
            }

//            check if user meet all filter requirements
            if (region_filter.equals("All") || region_filter.equals(province)){
                if (city_filter.equals("All") || location.equals(city_filter)){
                    if (genre_filter.equals("All") || genre.equals(genre_filter)){
                        if (instrument.equals(instrument_filter)){
                            MusicanResults.add(results[i]);
                        }
                    }
                }
            }
        }

//        set the adapter for listview
        runOnUiThread(new Runnable(){
            @Override
            public void run() {
                if(MusicanResults.size() > 0){
                    nListView.setAdapter(new musicanListAdapter(musicanResultActivity.this, 0, MusicanResults));
                }


            }
        });
    }

//    function for connecting to the api on the background
    @Override
        protected String doInBackground(String... strings) {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS).build();

            RequestBody formBody = new FormBody.Builder()
                    .build();

        String url_users = "http://10.0.2.2/api/read_users.php";

            Request request = new Request.Builder()
                    .url(url_users)
                    .post(formBody)
                    .build();

            Response response = null;

            try{
                response = okHttpClient.newCall(request).execute();

                if(response.isSuccessful()){
                    result = response.body().string();
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }

//    gridItemClickListener for listview
    private class GridItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent = new Intent(musicanResultActivity.this, musicanDetail.class);
            intent.putExtra("musican", MusicanResults.get(position));
            startActivity(intent);
        }
    }
}
