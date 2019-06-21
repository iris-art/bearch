package com.example.bearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class LogInActivity extends AppCompatActivity {

    String stEmail;
    String stPassword;
    final static String url_login = "http://10.0.2.2/api/login_user.php";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void onClick(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void onClick1(View view){
        EditText etEmail = findViewById(R.id.editText3);
        stEmail = etEmail.getText().toString();
        EditText etPassword = findViewById(R.id.editText4);
        stPassword = etPassword.getText().toString();
        new LoginUser().execute(stEmail, stPassword);
    }

    public class LoginUser extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String email = strings[0];
            String password = strings[1];
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS).build();
            RequestBody formBody = new FormBody.Builder()
                    .add("user_id", email)
                    .add("user_password", password)
                    .build();
            Request request = new Request.Builder()
                    .url(url_login)
                    .post(formBody)
                    .build();
            Response response = null;

            try{
                response = okHttpClient.newCall(request).execute();
                if(response.isSuccessful()){
                    String result = response.body().string();

                    Log.d("result = ", result);
                    if (result.equals("fail")){
                        Toast.makeText(LogInActivity.this, "Email or Password mismatched!", Toast.LENGTH_SHORT).show();
                    }else{
                        String[] string = result.split("~");
                        for (int i = 0; i < string.length; i++){
                            Log.d("INFORMATION = ", i + string[i]);
                        }
                        String encodeImage;
                        try{
                            encodeImage = string[0];
                            Log.d("econdeImage", encodeImage);
                        }catch(Exception e){
                            encodeImage = " ";
                        }
                        saveUserInformation(string[2], string[1], string[4], string[3], string[6], string[5], encodeImage, string[6]);


                        if (!string[5].equals("None")){
                            new getBandInformation().execute(string[5]);
                        }
                        Intent i = new Intent(LogInActivity.this,
                                MainActivity.class);
                        startActivity(i);
                        finish();
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
    public class getBandInformation extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            String bandName = strings[0];
            Log.d("bandName = ", bandName);
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS).build();
            try{
                String[] band = bandName.split("%");
                bandName = band[1];
            }catch(Exception e){

            }
            String url_band = "http://10.0.2.2/api/read_bands.php" + "?band_name="
                    + bandName;
            Request request = new Request.Builder()
                    .url(url_band)
                    .build();

            Response response = null;

            try{
                response = okHttpClient.newCall(request).execute();
                if(response.isSuccessful()){
                    String result = response.body().string();

                    Log.d("result = ", result);
                    if (result.equals("fail")){
                        Toast.makeText(LogInActivity.this, "Email or Password mismatched!", Toast.LENGTH_SHORT).show();
                    }else{
                        String[] results = result.split("~");
                        String encodeImage;
                        for(int i=0; i <results.length;i++){
                            Log.d("joeeeeeee = ", results[i]);
                        }
                        try{
                            encodeImage = results[0];
                        }catch(Exception e){
                            encodeImage = " ";
                        }
                        SharedPreferences sharedPreferences10 = getSharedPreferences("bandDescription", MODE_PRIVATE);
                        SharedPreferences sharedPreferences11 = getSharedPreferences("bandLocation", MODE_PRIVATE);
                        SharedPreferences sharedPreferences12 = getSharedPreferences("bandGenre", MODE_PRIVATE);
                        SharedPreferences sharedPreferences13 = getSharedPreferences("bandRequests", MODE_PRIVATE);
                        SharedPreferences sharedPreferences14 = getSharedPreferences("bandMembers", MODE_PRIVATE);
                        SharedPreferences sharedPreferences15=getSharedPreferences("BandImageURI",MODE_PRIVATE);
                        SharedPreferences.Editor editor10 = sharedPreferences10.edit();
                        SharedPreferences.Editor editor11 = sharedPreferences11.edit();
                        SharedPreferences.Editor editor12 = sharedPreferences12.edit();
                        SharedPreferences.Editor editor13 = sharedPreferences13.edit();
                        SharedPreferences.Editor editor14 = sharedPreferences14.edit();
                        SharedPreferences.Editor editor15 = sharedPreferences15.edit();
                        editor10.putString("bandDescription", results[2]);
                        editor11.putString("bandLocation", results[3]);
                        editor12.putString("bandGenre", results[4]);
                        editor13.putString("bandRequests", results[5]);
                        editor14.putString("bandMembers", results[6]);
                        editor15.putString("BandImageURI", encodeImage);
                        editor10.apply();
                        editor11.apply();
                        editor12.apply();
                        editor13.apply();
                        editor14.apply();
                        editor15.apply();
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
    public void saveUserInformation(String Name, String Email, String Genre, String Instrument, String Location, String Band, String imageURI, String province){
        Log.d("imageURI = ", imageURI);
        SharedPreferences sharedPreferences=getSharedPreferences("Name",MODE_PRIVATE);
        SharedPreferences sharedPreferences1=getSharedPreferences("Email",MODE_PRIVATE);
        SharedPreferences sharedPreferences2=getSharedPreferences("Genre",MODE_PRIVATE);
        SharedPreferences sharedPreferences3=getSharedPreferences("Instrument",MODE_PRIVATE);
        SharedPreferences sharedPreferences4=getSharedPreferences("Location",MODE_PRIVATE);
        SharedPreferences sharedPreferences5=getSharedPreferences("Band",MODE_PRIVATE);
        SharedPreferences sharedPreferences6=getSharedPreferences("ImageURI",MODE_PRIVATE);
        SharedPreferences sharedPreferences7=getSharedPreferences("Province",MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        SharedPreferences.Editor editor1 = sharedPreferences1.edit();
        SharedPreferences.Editor editor2 = sharedPreferences2.edit();
        SharedPreferences.Editor editor3 = sharedPreferences3.edit();
        SharedPreferences.Editor editor4 = sharedPreferences4.edit();
        SharedPreferences.Editor editor5 = sharedPreferences5.edit();
        SharedPreferences.Editor editor6 = sharedPreferences6.edit();
        SharedPreferences.Editor editor7 = sharedPreferences7.edit();

        editor.putString("Name",Name);
        editor1.putString("Email",Email);
        editor2.putString("Genre",Genre);
        editor3.putString("Instrument",Instrument);
        editor4.putString("Location",Location);
        editor5.putString("Band",Band);
        editor6.putString("ImageURI",imageURI);
        editor7.putString("Province",province);
        editor.apply();
        editor1.apply();
        editor2.apply();
        editor3.apply();
        editor4.apply();
        editor5.apply();
        editor6.apply();
        editor7.apply();
    }
}
