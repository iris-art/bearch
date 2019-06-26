package com.example.bearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class bandDetail extends AppCompatActivity {

    TextView name;
    TextView description;
    TextView location;
    TextView genre;
    TextView Members;
    String request;
    String band;
    String Name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band_detail);
        bandDetail.this.getWindow().setBackgroundDrawableResource(R.drawable.brushed3);

//        get textviews and set values in it
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        location = findViewById(R.id.location);
        genre = findViewById(R.id.genre10);

        Intent intent = getIntent();
        String band = intent.getStringExtra("band");
        String[] propperties = band.split("~");

        name.setText(propperties[1]);
        description.setText(propperties[2]);
        location.setText(propperties[3]);
        genre.setText(propperties[4]);
        request = propperties[5];

        Name = propperties[1];
//        put members in textview beneath eachother
        String[] members = propperties[6].split("<>");
        String members1 = "band members\nhead member =\nO   " + members[0]+ "\nother members = \n";
        for(int i=1; i<members.length;i++){
            members1 += "O  " + members[i] + "\n";
        }
        Members = findViewById(R.id.members);
        Members.setText(members1);

//        see if someone is in a band, if not let them make a new band or choose one.
        SharedPreferences sharedPreferences=getSharedPreferences("Band",MODE_PRIVATE);
        String Band = sharedPreferences.getString("Band", "None");
        SharedPreferences sharedPreferences1=getSharedPreferences("Name",MODE_PRIVATE);
        String Name =sharedPreferences1.getString("Name","None");

        if (!Band.equals("None")){
            Button btn = findViewById(R.id.button);
            btn.setClickable(false);
            btn.setText("Already in a band");
        }else if(Name.equals("None")){
            Button btn = findViewById(R.id.button);
            btn.setClickable(false);
            btn.setVisibility(View.INVISIBLE);
        }

//        get the band Image
        new getBandImage().execute(propperties[1]);
    }

//    get image of the band the user clicked on
    public class getBandImage extends AsyncTask<String, Void, String> {
        Bitmap bitmap;
        String result;

//        wait for result and than set picture in imageView
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            ImageView imageView = findViewById(R.id.imageView4);


            if (!result.equals("null") && !result.equals("") && result.length() >0){
//                if correct result, make bitmap and set image
                try {
                    byte [] encodeByte= Base64.decode(result,Base64.DEFAULT);
                    bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

                    imageView.setImageBitmap(bitmap);
                } catch(Exception e) {
                    e.getMessage();
                }
            }

        }

//        make the request to the api, the api will get the image from the database
        @Override
        protected String doInBackground(String... strings) {
//            get variables
            String name = strings[0];

//            make OkHttpClient with propperties
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

//            make string where api is located
            String url_band = "http://10.0.2.2/api/read_band_image.php" + "?band_name="
                    + name;

//            build request
            Request request = new Request.Builder()
                    .url(url_band)
                    .build();

            Response response = null;

//            try doing something with the response from the api
            try{
                response = okHttpClient.newCall(request).execute();
                if(response.isSuccessful()) {
                    result = response.body().string();
                }

//             log error
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

    public void createReaction(View view){
        SharedPreferences sharedPreferences=getSharedPreferences("Band",MODE_PRIVATE);
        String Band = sharedPreferences.getString("Band", "None");

//
        if (Band.equals("None")) {
            SharedPreferences sharedPreferences1 = getSharedPreferences("Email", MODE_PRIVATE);
            String Email = sharedPreferences1.getString("Email", "None");

//            add email of the user who wants to join the band to the requests of the band
            request += Email + "<>";

//            change text of the button because you're only allowed to make one request
            Button btn = findViewById(R.id.button);
            btn.setClickable(false);
            btn.setText("Already in a band");
            new AddRequestMember().execute(band, Email);
        }

    }

//    function to make the api add the new request to the database
    public class AddRequestMember extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String Band = strings[0];
            String Email = strings[1];

//            url to locate the api
            String finalURL = "http://10.0.2.2/api/make_request.php";

//            create OkHttp and Request
            OkHttpClient okHttpClient = new OkHttpClient();

//            make form body with post data
            RequestBody formBody = new FormBody.Builder()
                    .add("string", request)
                    .add("band_name", Name)
                    .add("user_id", Email)
                    .build();

//            create and execute request
            Request request =  new Request.Builder()
                    .url(finalURL)
                    .get()
                    .post(formBody)
                    .build();
            Response response = null;
            try{
//                try to get a good response if it's succesfull than save values
                response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()){
                    SharedPreferences sharedPreferences5=getSharedPreferences("Band",MODE_PRIVATE);
                    SharedPreferences.Editor editor5 = sharedPreferences5.edit();
                    editor5.putString("Band", "request %" + Band);
                    editor5.apply();
                    Intent intent = new Intent(bandDetail.this, MainActivity.class);
                    startActivity(intent);
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }
    }
}
