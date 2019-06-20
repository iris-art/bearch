package com.example.bearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {

    Button btnSave;
    EditText name;
    EditText email;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    String Email;
    String[] items;
    Integer nrCity;
    private static final int PICK_IMAGE = 100;
    Uri imageURI;
    ImageView imageView;
    String encodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences sharedPreferences=getSharedPreferences("Name",MODE_PRIVATE);
        String Name = sharedPreferences.getString("Name", "None");
        SharedPreferences sharedPreferences1=getSharedPreferences("Email",MODE_PRIVATE);
        Email = sharedPreferences1.getString("Email", "None");
        SharedPreferences sharedPreferences2=getSharedPreferences("Genre",MODE_PRIVATE);
        String Genre =sharedPreferences2.getString("Genre","None");
        SharedPreferences sharedPreferences3=getSharedPreferences("Instrument",MODE_PRIVATE);
        String Instrument =sharedPreferences3.getString("Instrument","None");
        SharedPreferences sharedPreferences4=getSharedPreferences("Location",MODE_PRIVATE);
        String Location =sharedPreferences4.getString("Location","None");

        Intent intent = getIntent();
        imageView = findViewById(R.id.imageView4);
        System.out.println(intent.getData());
        getWindow().setBackgroundDrawableResource(R.drawable.background4);

        name = findViewById(R.id.editText10);
        email = findViewById(R.id.editText9);

        name.setText(Name);
        email.setText(Email);
        btnSave = findViewById(R.id.button9);
        spinner1 = findViewById(R.id.spinner1);
        ArrayAdapter ProvincesAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.Provinces,
                R.layout.color_spinner_layout
        );
        ProvincesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner1.setAdapter(ProvincesAdapter);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        ArrayAdapter GenreAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.Genres,
                R.layout.color_spinner_layout
        );
        GenreAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner3.setAdapter(GenreAdapter);
        spinner4 = findViewById(R.id.spinner4);
        ArrayAdapter InstrumentAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.Instruments,
                R.layout.color_spinner_layout
        );
        InstrumentAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner4.setAdapter(InstrumentAdapter);


        for (int i = 0; i < getResources().getStringArray(R.array.Provinces).length; i ++){
            String [] cities = getResources().getStringArray(R.array.Provinces);
            for (int j = 0; j < cities.length; j++){
                if (cities[j].equals(Location)){
                    spinner1.setSelection(i);
                    nrCity = j;

                }
            }
        }

        for (int i = 0; i < getResources().getStringArray(R.array.Genres).length; i++){
            if (getResources().getStringArray(R.array.Genres)[i].equals(Instrument)){
                spinner3.setSelection(i);

            }
        }

        for (int i = 0; i < getResources().getStringArray(R.array.Instruments).length; i++){
            if (getResources().getStringArray(R.array.Instruments)[i].equals(Genre)){
                spinner4.setSelection(i);

            }
        }


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String Province = getResources().getStringArray(R.array.Provinces)[position];
                int resId = getResId(Province, R.array.class);
                ArrayAdapter PlaceAdapter = ArrayAdapter.createFromResource(
                        ProfileActivity.this,
                        resId,
                        R.layout.color_spinner_layout
                );
                PlaceAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                spinner2.setAdapter(PlaceAdapter);
                if (nrCity != null){
                    spinner2.setSelection(nrCity);
                    nrCity = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner2.setSelection(nrCity);
            }
        });


    }
    public static int getResId(String resName, Class<?> c) {

        try {
            Field idField = c.getDeclaredField(resName);
            return idField.getInt(idField);
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    public void setAdapter(String[] items, Spinner dropdown){
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }

    public void onClick(View view){
        new SaveUser().execute(name.getText().toString(),
                email.getText().toString(), spinner2.getSelectedItem().toString(), spinner3.getSelectedItem().toString(), spinner4.getSelectedItem().toString(), Email);
    }

    public void onClick1(View view){
        openGallery();
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageURI = data.getData();
            try{
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageURI);
                encodeImage = BitMapToString(bitmap);
                Log.d("Image =", encodeImage);
                imageView.setImageBitmap(bitmap);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
    public class SaveUser extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            String Name = strings[0];
            String Email = strings[1];
            String Location = strings[2];
            String Genre = strings[3];
            String Instrument = strings[4];
            String Email1 = strings[5];
            Log.d("INSTRUMENT = ", Instrument);
            String finalURL = "http://10.0.2.2/api/save_user.php" + "?user_name="+ Name +
                    "&user_id1=" + Email1 +
                    "&user_id=" + Email +
                    "&user_location=" + Location +
                    "&user_instrument=" + Instrument +
                    "&user_genre=" + Genre;

            Log.d("URL = ", Name + Email + Location + Genre + Instrument);
            OkHttpClient okHttpClient = new OkHttpClient();
            RequestBody formBody = new FormBody.Builder()
                    .add("image_uri", encodeImage)
                    .build();
            Request request =  new Request.Builder()
                    .url(finalURL)
                    .get()
                    .post(formBody)
                    .build();
            Response response = null;
            try{
                response = okHttpClient.newCall(request).execute();
                String result = response.body().string();
                Log.d("Result", result);
                if (response.isSuccessful()){
                    showToast("Profile Updated succesfully");
                }else{
                    showToast("Oops something went wrong");
                }

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }
    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(ProfileActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }

}
