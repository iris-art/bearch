package com.example.bearch;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class bandResultActivity extends AppCompatActivity {

    ListView nListView;
    ArrayList<String> BandResults;
    String genre;
    String city;
    String region;
    String[] provinces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_result);
        bandResultActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.background7);

//        define ListView
        nListView = findViewById(R.id.listView1);
        nListView.setOnItemClickListener(new bandResultActivity.GridItemClickListener());
        BandResults = new ArrayList<>();

//        get filter values from last intent
        Intent intent = getIntent();
        genre = intent.getStringExtra("genre");
        city = intent.getStringExtra("city");
        region = intent.getStringExtra("region");

//        String with provinces
        provinces = getResources().getStringArray(R.array.Provinces);

        new getBands().execute();
    }

//    function for getting all the bands from the API
    public class getBands extends AsyncTask<String, Void, String> {
        String result;
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            String[] results = result.split("%");

//            get every band from result
            for(int i = 0; i < results.length;i++){
                String[] band = results[i].split("~");
                String region1 = null;

//                get the province of the band
                for (int k=0; k<provinces.length;k++){
                    int resId = getResId(provinces[i], R.array.class);
                    String [] cities = getResources().getStringArray(resId);
                    for (int l = 0; l < cities.length; l++){
                        if (cities[l].equals(band[2])){
                            region1 = provinces[k];

                        }
                    }
                }

//                check if the band meets with the filter requirements
                if (region.equals("All") || region.equals(region1)){
                    if (city.equals("All") || band[2].equals(city)){
                        if (genre.equals("All") || band[3].equals(genre)){
                            BandResults.add(results[i]);
                        }
                    }
                }

            }

//            function for setting adapter on the listview
            runOnUiThread(new Runnable(){
                @Override
                public void run() {
                    nListView.setAdapter(new bandListAdapter(bandResultActivity.this, 0, BandResults));
                }
            });
        }

//        function that connects to the api
        @Override
        protected String doInBackground(String... strings) {
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS).build();
            RequestBody formBody = new FormBody.Builder()
                    .build();
            String url_bands = "http://10.0.2.2/api/read_bands.php?band_name=None";
            Request request = new Request.Builder()
                    .url(url_bands)
                    .post(formBody)
                    .build();
            Response response = null;

            try{
                response = okHttpClient.newCall(request).execute();
//                if response succesfull then filter the bands
                if(response.isSuccessful()){
                    result = response.body().string();
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            return null;
        }
    }

    //    this is used to get the id (int) for the array with list items in values->strings
    public static int getResId(String resName, Class<?> c) {
        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

//    GridItemClickListener for the listview with the band results
    private class GridItemClickListener implements AdapterView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

//            go to the bandDetail if item is clicked
            Intent intent = new Intent(bandResultActivity.this, bandDetail.class);
            intent.putExtra("band", BandResults.get(position));
            startActivity(intent);
        }
    }

}
