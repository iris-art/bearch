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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.lang.reflect.Field;
import java.net.URI;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class CreateBandActivity extends AppCompatActivity {

    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    String Name;
    String Description;
    String Genre;
    String Location;
    String Email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_band);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        CreateBandActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.brushed3);

//        make ArrayAdapters for spinners
        ArrayAdapter ProvincesAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.Provinces,
                R.layout.color_spinner_layout
        );
        ArrayAdapter GenreAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.Genres,
                R.layout.color_spinner_layout
        );

//        set dropdowns View Resource for spinners
        ProvincesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner1.setAdapter(ProvincesAdapter);
        GenreAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner3.setAdapter(GenreAdapter);

//        set onItemSelectedListener for spinner1, if something is selected, the string[] for
//        spinner2 has to be changed
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                get Province and string[] with all the cities
                String Province = getResources().getStringArray(R.array.Provinces)[position];
                int resId = getResId(Province, R.array.class);
                //        make ArrayAdapters for spinners
                ArrayAdapter PlaceAdapter = ArrayAdapter.createFromResource(
                        CreateBandActivity.this,
                        resId,
                        R.layout.color_spinner_layout
                );
//                set dropdowns View Resource for spinners
                PlaceAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                spinner2.setAdapter(PlaceAdapter);
            }

//            nothing happens here.
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
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

//    call function for creating a band
    public void createBand(View view){

//        get the values and call the create band function with these values
        EditText name = findViewById(R.id.editText8);
        Name = name.getText().toString();
        EditText description = findViewById(R.id.editText9);
        Description = description.getText().toString();
        Location = spinner2.getSelectedItem().toString();
        Genre = spinner3.getSelectedItem().toString();
        SharedPreferences sharedPreferences1=getSharedPreferences("Email",MODE_PRIVATE);
        Email = sharedPreferences1.getString("Email", "None");
        new CreateBand().execute();
    }

//    function for call the api to create a band
    public class CreateBand extends AsyncTask<String, Void, String>{
        String result;
        Response response;

//        if request is finished
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("Result", result);
//            check if result is what we wanted
            if (result.equalsIgnoreCase("Band created successfullyBand created successfully")) {
                showToast("Band created successfully");

//                save all the new values in shared preferences
                setPreference("Band",Name);
                setPreference("bandDescription", Description);
                setPreference("bandLocation", Location);
                setPreference("bandGenre", Genre);

//                go to homescreen
                Intent i = new Intent(CreateBandActivity.this,
                        MainActivity.class);
                startActivity(i);
                finish();
            }

//            else something went wrong
            else{
                showToast("Oops something went wrong!");
            }
        }

//    easy function for setting the shared preferences
        private void setPreference(String name, String value){
        SharedPreferences sharedPreferences=getSharedPreferences(name,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(name,value);
        editor.apply();
    }

//        function for connecting to api in the background
        @Override
        protected String doInBackground(String... strings) {

//            url for finding the api
            String finalURL = "http://10.0.2.2/api/create_band.php";

            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS).build();

            RequestBody formBody = new FormBody.Builder()
                    .add("user_id", Email)
                    .add("band_name", Name)
                    .add("band_description", Description)
                    .add("band_location", Location)
                    .add("band_genre", Genre)
                    .build();

            Request request =  new Request.Builder()
                    .url(finalURL)
                    .get()
                    .post(formBody)
                    .build();

//            do something if response is successfull
                try{
                    response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()){
                        result = response.body().string();
                    }
                }catch (Exception e){
                    response = null;
                    e.printStackTrace();
                }
            return null;
        }
    }

//    function for show Toast messages to the screen
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
