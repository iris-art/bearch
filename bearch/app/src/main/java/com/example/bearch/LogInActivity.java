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

    String Email;
    String Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        LogInActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.brushed2);
    }

//    function for going to register activity
    public void Register(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

//    call function for login function
    public void logIn(View view){
        EditText etEmail = findViewById(R.id.editText3);
        Email = etEmail.getText().toString();
        EditText etPassword = findViewById(R.id.editText4);
        Password = etPassword.getText().toString();
        new LoginUser().execute();
    }

//    class for connecting to api for login function
    public class LoginUser extends AsyncTask<String, Void, String>{
        Response response;
        String result;
        String encodeImage;

//        if request is finished
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            if fail, than don't save values and stay on activity
            if (result.equals("fail")){
                Toast.makeText(LogInActivity.this, "Email or Password mismatched!", Toast.LENGTH_SHORT).show();
            }else{
//                get all the returned values
                String[] string = result.split("~");
                try{
                    encodeImage = string[0];
                }catch(Exception e){
                    encodeImage = " ";
                }

                setPreference("Name",string[2]);
                setPreference("Email", string[1]);
                setPreference("Genre",string[4]);
                setPreference("Instrument",string[3]);
                setPreference("location",string[6]);
                setPreference("Band",string[5]);
                setPreference("ImageURI",encodeImage);
                setPreference("Province",string[7]);

//                if the user is in a band, get the propperties of that band and save in preferences
                if (!string[5].equals("None")){
                    new getBandInformation().execute(string[5]);
                }

//                start MainActivity
                Intent i = new Intent(LogInActivity.this,
                        MainActivity.class);
                startActivity(i);
                finish();
            }
        }

//        request to api handled in the background
        @Override
        protected String doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS).build();

//            make form body with post data
            RequestBody formBody = new FormBody.Builder()
                    .add("user_id", Email)
                    .add("user_password", Password)
                    .build();

//            string for finding api
            String url_login = "http://10.0.2.2/api/login_user.php";

//            make and execute request
            Request request = new Request.Builder()
                    .url(url_login)
                    .post(formBody)
                    .build();

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

//    class for getting the band information if user is in band
    public class getBandInformation extends AsyncTask<String, Void, String>{
    String result;
    String encodeImage;

//        if request is finished
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        String[] results = result.split("~");
        try{
            encodeImage = results[0];
        }catch(Exception e){
            encodeImage = " ";
        }

//        save all the values in the preferences
        try{
            setPreference("bandDescription",results[2]);
            setPreference("bandLocation",results[3]);
            setPreference("bandGenre",results[4]);
            setPreference("bandRequests",results[5]);
            setPreference("bandMembers",results[6]);
            setPreference("BandImageURI",encodeImage);
        }catch(Exception e){

        }


    }

//        function that connects with api in the background
        @Override
        protected String doInBackground(String... strings) {
            String bandName = strings[0];

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS).build();

//            if user has made a request, his band Name will be:
//            "request %"+ band_name so here the band will be loaded but the user isn't in yet.
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

//            look if the response is successful
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

//    easy function for setting the shared preferences
    private void setPreference(String name, String value){
        SharedPreferences sharedPreferences=getSharedPreferences(name,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name,value);
        editor.apply();
    }
}
