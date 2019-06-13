package com.example.bearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.net.URI;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreateBandActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    Uri imageURI;
    Button button;
    ImageView imageView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_band);
        imageView = findViewById(R.id.imageView2);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageURI = data.getData();
            imageView.setImageURI(imageURI);
        }
    }

    public void onClick1(View view){
        EditText name = findViewById(R.id.editText);
        String Name = name.getText().toString();
        EditText description = findViewById(R.id.editText12);
        String Description = description.getText().toString();
        new CreateBand().execute(Name, Description);
    }

    public class CreateBand extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {
            String Name = strings[0];
            String Description = strings[1];
            SharedPreferences sharedPreferences1=getSharedPreferences("Email",MODE_PRIVATE);
            String Email = sharedPreferences1.getString("Email", "None");
            String finalURL = "https://joostappapi.000webhostapp.com/create_band.php" + "?band_name="+ Name +
                    "&band_description=" + Description
                    + "&user_id=" + Email;

            Log.d("results= ", "'" +finalURL+ "'");
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
                        if (result.equalsIgnoreCase("Band created successfullyBand created successfully")) {

                            showToast("Band created successfully");
                            Intent i = new Intent(CreateBandActivity.this,
                                   logoutActivity.class);
                            startActivity(i);
                            finish();
                        }else{
                            showToast("Oops something went wrong!");
                        }
                    }
                }catch (Exception e){
                    Log.d("joe", "6");
                    e.printStackTrace();
                }
            return null;
        }
    }

    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(CreateBandActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }

}
