package com.example.bearch;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class LogInActivity extends AppCompatActivity {

    String stEmail;
    String stPassword;
    final static String url_login = "https://joostappapi.000webhostapp.com/login_user.php";
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
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("user_id", email)
                    .add("user_password", password)
                    .build();
            Request request = new Request.Builder()
                    .url(url_login)
                    .post(formBody)
                    .build();
            Response response = null;
            Log.d("gaat het?", "TERING");
            try{
                response = okHttpClient.newCall(request).execute();
                if(response.isSuccessful()){
                    Log.d("gaat het?", "misschien");
                    String result = response.body().string();
                    if (result.equalsIgnoreCase("login")){
                        Log.d("gaat het?", "ja");
                        Intent i = new Intent(LogInActivity.this,
                                ProfileActivity.class);
                        startActivity(i);
                        finish();
                    }else{
                        Log.d("gaat het?", "nee");
                        Toast.makeText(LogInActivity.this, "Email or Password mismatched!", Toast.LENGTH_SHORT).show();
                    }
                }
            }catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

}
