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

import okhttp3.OkHttpClient;
import okhttp3.Request;
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
    final String url_register = "http://10.0.2.2/api/register_user.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
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


    public class RegisterUser extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... strings) {

            Log.d("INSTRUMENT = ", Instrument);
            System.out.println(Name + Email + Password);
            String finalURL = url_register + "?user_name="+ Name +
                    "&user_id=" + Email +
                    "&user_password=" + Password +
                    "&user_location=" + Location +
                    "&user_instrument=" + Instrument +
                    "&user_genre=" + Genre +
                    "&user_province=" + Province;

            Log.d("URL = ", finalURL);
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request =  new Request.Builder()
                    .url(finalURL)
                    .get()
                    .build();
            Response response = null;
            if (etPassword.getText().toString().equals(etPassword1.getText().toString())){
                try{
                    response = okHttpClient.newCall(request).execute();
                    if (response.isSuccessful()){
                        String result = response.body().string();
                        Log.d("result = ", result);
                        if (result.equalsIgnoreCase("User registered successfully")){
                            Log.d("joe", "2");
                            showToast("Register succesfull");
                            Intent i = new Intent(RegisterActivity.this,
                                    LogInActivity.class);
                            startActivity(i);
                            finish();
                        }else if(result.equalsIgnoreCase("User already exists")){
                            Log.d("joe", "3");
                            showToast("User already exists");
                        }else{
                            System.out.println(Name + Email + etPassword.getText().toString() + etPassword1.getText().toString());
                            showToast("Oops something went wrong!");
                        }
                    }
                }catch (Exception e){
                    Log.d("joe", "6");
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
