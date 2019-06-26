package com.example.bearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.bearch.ProfileActivity.getResId;

public class RegisterActivity extends AppCompatActivity {

    EditText etName, etEmail, etPassword, etPassword1;
    Button btnRegister;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    String Name;
    String Email;
    String Password;
    String Province;
    String Location;
    String Genre;
    String Instrument;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        RegisterActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.brushed2);

        etName = findViewById(R.id.editText8);
        etEmail = findViewById(R.id.editText5);
        etPassword = findViewById(R.id.editText6);
        etPassword1 = findViewById(R.id.editText7);

        btnRegister = findViewById(R.id.button8);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);

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
        ArrayAdapter InstrumentAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.Instruments,
                R.layout.color_spinner_layout
        );

//        set dropdowns View Resource for spinners
        ProvincesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner1.setAdapter(ProvincesAdapter);
        GenreAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner3.setAdapter(GenreAdapter);
        InstrumentAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner4.setAdapter(InstrumentAdapter);

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
                        RegisterActivity.this,
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

//    function for registering new user
    public void onClick(View view) {

//        get all the values and call the register function
        Name = etName.getText().toString();
        Email = etEmail.getText().toString();
        Password = etPassword.getText().toString();
        Province = spinner1.getSelectedItem().toString();
        Location = spinner2.getSelectedItem().toString();
        Genre = spinner3.getSelectedItem().toString();
        Instrument = spinner4.getSelectedItem().toString();
        new RegisterUser().execute();
    }

//    class for calling register_user function of api
    public class RegisterUser extends AsyncTask<String, Void, String>{

        Response response;
        String result;

//        if finished
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

//        if server responded succesfully
        if (response.isSuccessful()){
            if (result.equalsIgnoreCase("User registered successfully")){
                showToast("Register succesfull");
                Intent i = new Intent(RegisterActivity.this,
                        LogInActivity.class);
                startActivity(i);
                finish();
            }else if(result.equalsIgnoreCase("User already exists")){
                showToast("User already exists");
            }else{
                Log.d("RESULT", result);
                showToast("Oops something went wrong!");
            }
        }
    }

//    what happens on background
    @Override
        protected String doInBackground(String... strings) {

            OkHttpClient okHttpClient = new OkHttpClient();

//            make form body with post data
            RequestBody formBody = new FormBody.Builder()
                .add("user_id", Email)
                .add("user_name", Name)
                .add("user_password", Password)
                .add("user_location",Location)
                .add("user_instrument",Instrument)
                .add("user_genre",Genre)
                .add("user_province",Province)
                .build();

//            location of api
            String url_register = "http://10.0.2.2/api/register_user.php";

//            make and execute request
            Request request =  new Request.Builder()
                    .url(url_register)
                    .get()
                    .post(formBody)
                    .build();

//            check if passwords match
            if (etPassword.getText().toString().equals(etPassword1.getText().toString())){
                try{
                    response = okHttpClient.newCall(request).execute();
                    result = response.body().string();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                showToast("Password don't match");
            }

            return null;
        }
    }

    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }



}
