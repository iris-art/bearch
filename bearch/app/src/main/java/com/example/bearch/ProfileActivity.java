package com.example.bearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
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
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ProfileActivity extends AppCompatActivity {

    Integer nrCity;
    private static final int PICK_IMAGE = 100;

    String encodeImage;
    ImageView imageView;
    TextView name;
    String Email;
    TextView email;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        ProfileActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.brushed2);

//        get all the values from sharedpreferences and set in contentview
        String Name = getPreference("Name");
        name = findViewById(R.id.name);
        name.setText(Name);

        Email = getPreference("Email");
        email = findViewById(R.id.email);
        email.setText(Email);

        imageView = findViewById(R.id.imageView4);
        String Genre = getPreference("Genre");
        String Instrument = getPreference("Instrument");
        String Location = getPreference("Location");
        String Province = getPreference("Province");
        encodeImage = getPreference("ImageURI");
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);


//        check if there is a bitmap in the database for setting the user's image
//        else use standard picture
        if(encodeImage.length() > 10){
            try {
//                make bitmap from the encoded String from the database
                byte [] encodeByte=Base64.decode(encodeImage,Base64.DEFAULT);
                Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte,
                        0,
                        encodeByte.length);
                imageView.setImageBitmap(bitmap);
            } catch(Exception e) {
                e.getMessage();
            }
        }

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

        String[] provinces = getResources().getStringArray(R.array.Provinces);

//        set the correct values selected on the spinners for province and city by going through all
//        the provinces and city and wait untill correct city is found, nrCity is used because
//        it'll break if you set spinner2 because of the onClickListener of spinner1
        for (int i = 0; i < provinces.length; i ++){
            int resId = getResId(provinces[i], R.array.class);
            String [] cities = getResources().getStringArray(resId);

            for (int j = 0; j < cities.length; j++){
                if (cities[j].equals(Location)){
                    spinner1.setSelection(i);
                    nrCity = j;

                }
            }
        }

//        Loop through all genres and set the correct one selected on the spinner
        for (int i = 0; i < getResources().getStringArray(R.array.Genres).length; i++){
            if (getResources().getStringArray(R.array.Genres)[i].equals(Instrument)){
                spinner3.setSelection(i);
            }
        }
//        Loop through all Instruments and set the correct one selected on the spinner
        for (int i = 0; i < getResources().getStringArray(R.array.Instruments).length; i++){
            if (getResources().getStringArray(R.array.Instruments)[i].equals(Genre)){
                spinner4.setSelection(i);

            }
        }

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
                        ProfileActivity.this,
                        resId,
                        R.layout.color_spinner_layout
                );
//                set dropdowns View Resource for spinners
                PlaceAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                spinner2.setAdapter(PlaceAdapter);

//                because at the start nrCity is defined we are able to set the correct city here
                if (nrCity != null){
                    spinner2.setSelection(nrCity);
                    nrCity = null;
                }
            }

//            nothing happens here.
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });


    }

//    easy way to get the sharedPreferences
    private String getPreference(String name){
        SharedPreferences sharedPreferences=getSharedPreferences(name,MODE_PRIVATE);
        String Name = sharedPreferences.getString(name, "None");
        return Name;
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

//  onclick function for saving users
    public void saveUser(View view){
        new SaveUser().execute(name.getText().toString(),
                email.getText().toString(),
                spinner2.getSelectedItem().toString(),
                spinner3.getSelectedItem().toString(),
                spinner4.getSelectedItem().toString(),
                Email,
                spinner1.getSelectedItem().toString());
    }

//    on Click for opening Gallery
    public void Gallery(View view){
        openGallery();
    }

//    function for opening Gallery
    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK,
                MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery,
                PICK_IMAGE);
    }

//    if something from Gallery is selected then...
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,
                resultCode,
                data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            Uri imageURI = data.getData();
            try{
//                set imageURI to bitmap and if save button is hit, encodeImage will be stored in
//                the database and sharedPreferences
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
                        imageURI);
                Bitmap resized = getResizedBitmap(bitmap,
                        1000,
                        1500);
                encodeImage = BitMapToString(resized);
                imageView.setImageBitmap(bitmap);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

//    function for converting a bitmap to a string, this is used for saving the bitmap
//    in the database
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,10, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

//    function for resizing the bitmap, my api wasn't able to echo such long bitmaps so I had to
//    resize all the images that the user uploads
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
        System.out.println(width);
        int height = bm.getHeight();
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        // create a matrix for the manipulation
        Matrix matrix = new Matrix();

        // resize the bit map
        matrix.postScale(scaleWidth, scaleHeight);

        // recreate the new Bitmap
        Bitmap resizedBitmap = Bitmap.createBitmap(bm, 0, 0, width, height, matrix, false);

        return resizedBitmap;
    }

//    class for connecting to api for save_user function
    public class SaveUser extends AsyncTask<String, Void, String> {
        Response response;

//        if request is finished
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

//            if succes, show toast
            if (response.isSuccessful()){
                showToast("Profile Updated succesfully");
            }else{
                showToast("Oops something went wrong");
            }
        }

//            request to api handled in the background
        @Override
        protected String doInBackground(String... strings) {

//            get values from call function
            String Name = strings[0];
            String Email = strings[1];
            String Location = strings[2];
            String Genre = strings[3];
            String Instrument = strings[4];
            String Email1 = strings[5];
            String Province = strings[6];

//            string for finding api
            String finalURL = "http://10.0.2.2/api/save_user.php";

//            set values in preferences
            setPreference("Name",Name);
            setPreference("Email",Email);
            setPreference("Genre",Genre);
            setPreference("Instrument",Instrument);
            setPreference("Location",Location);
            setPreference("Province",Province);
            setPreference("ImageURI",encodeImage);

            OkHttpClient okHttpClient = new OkHttpClient();

//            make form body with post data
            RequestBody formBody = new FormBody.Builder()
                    .add("image_uri", encodeImage)
                    .add("user_name",Name)
                    .add("user_id1",Email1)
                    .add("user_id",Email)
                    .add("user_location",Location)
                    .add("user_instrument",Instrument)
                    .add("user_genre",Genre)
                    .add("user_province",Province)
                    .build();

//            make and execute request
            Request request =  new Request.Builder()
                    .url(finalURL)
                    .get()
                    .post(formBody)
                    .build();

//            try getting a good response
            try{
                response = okHttpClient.newCall(request).execute();

            }catch (Exception e){
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

//    function for showing Toast messages
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
