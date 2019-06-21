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
import android.widget.Toast;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class bandDetail extends AppCompatActivity {

    TextView name;
    TextView description;
    TextView location;
    TextView genre;
    TextView Members;
    String request;
    String band;
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

        name.setText(musican1[1]);
        band = musican1[1];
        description.setText(musican1[2]);
        location.setText(musican1[3]);
        genre.setText(musican1[4]);
        request = musican1[5];
        String[] members = musican1[6].split("<>");
        String members1 = "head member = ";
        for(int i=0; i<members.length;i++){
            members1 += members[i] + "\n";
        }
        Members = findViewById(R.id.members);
        Members.setText(members1.toString());
        Button btn = findViewById(R.id.button);
        SharedPreferences sharedPreferences=getSharedPreferences("Band",MODE_PRIVATE);
        String Band = sharedPreferences.getString("Band", "None");
        if (!Band.equals("None")){
            btn.setClickable(false);
            btn.setText("Already in a band");
        }
        new getBandImage().execute(musican1[1]);
    }
    public class getBandImage extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            String name = strings[0];

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS)
                    .retryOnConnectionFailure(true)
                    .build();

            String url_band = "http://10.0.2.2/api/read_band_image.php" + "?band_name="
                    + name;
            Request request = new Request.Builder()
                    .url(url_band)
                    .build();

            Response response = null;

            try{
                response = okHttpClient.newCall(request).execute();
                if(response.isSuccessful()) {
                    String result = response.body().string();
                    Log.d("JOE", "JOE"+result);
                    if (!result.equals("null") && !result.equals("")){
                        try {
                            byte [] encodeByte= Base64.decode(result,Base64.DEFAULT);
                            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
                            ImageView imageView = findViewById(R.id.imageView4);
                            imageView.setImageBitmap(bitmap);
                        } catch(Exception e) {
                            e.getMessage();
                        }
                    }

                }

            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
    public void onClick(View view){
        SharedPreferences sharedPreferences=getSharedPreferences("Band",MODE_PRIVATE);
        String Band = sharedPreferences.getString("Band", "None");
        if (Band.equals("None")){
            SharedPreferences sharedPreferences1=getSharedPreferences("Email",MODE_PRIVATE);
            String Email = sharedPreferences1.getString("Email", "None");
            request += "<>" + Email;
            Button btn = findViewById(R.id.button);
            btn.setClickable(false);
            btn.setText("Already in a band");
            new AddRequestMember().execute(band, Email);
        }else{
            showToast("You already in a band or made a request for a band!");
        }

    }

    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(bandDetail.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }

    public class AddRequestMember extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String Band = strings[0];
            String Email = strings[1];
            Log.d("BAND = ", "JOE" + band);
            String finalURL = "http://10.0.2.2/api/make_request.php" +
                    "?string=" + request +
                    "&band_name=" + band +
                    "&user_id=" + Email;

            OkHttpClient okHttpClient = new OkHttpClient();
            Request request =  new Request.Builder()
                    .url(finalURL)
                    .get()
                    .build();
            Response response = null;
            try{
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
