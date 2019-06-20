package com.example.bearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class bandDetail extends AppCompatActivity {

    TextView name;
    TextView description;
    TextView location;
    TextView genre;
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
        name.setText(musican1[0]);
        band = musican1[0];
        description.setText(musican1[1]);
        location.setText(musican1[2]);
        genre.setText(musican1[3]);
        request = musican1[4];
        Button btn = findViewById(R.id.button);
        SharedPreferences sharedPreferences=getSharedPreferences("Band",MODE_PRIVATE);
        String Band = sharedPreferences.getString("Band", "None");
        if (!Band.equals("None")){
            btn.setClickable(false);
            btn.setText("Already in a band");
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

            String finalURL = "http://10.0.2.2/api/make_request.php" +
                    "?string=" + request +
                    "&band_name=" + Band +
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
                    editor5.putString("Band", "request ~" + Band);
                    editor5.apply();
                }
            }catch (Exception e){
                e.printStackTrace();
            }


            return null;
        }
    }
}
