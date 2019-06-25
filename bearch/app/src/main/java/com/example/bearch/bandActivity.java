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

import java.io.ByteArrayOutputStream;
import java.lang.reflect.Field;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class bandActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    Uri imageURI;
    ImageView imageView;
    Integer nrCity;
    String band;
    String bandGenre;
    String bandLocation;
    TextView bandName;
    TextView bandDescription;
    Spinner spinner1;
    Spinner spinner3;
    Spinner spinner2;
    String BandDescription;
    String encodeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_band);
        imageView = findViewById(R.id.imageView4);
        bandActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.band_background);


        String bandLocation = getPreference("bandLocation");
        String bandGenre = getPreference("bandGenre");

        bandName = findViewById(R.id.name);
        band = getPreference("Band");
        bandName.setText(band);
        bandDescription = findViewById(R.id.description);
        BandDescription = getPreference("bandDescription");
        bandDescription.setText(BandDescription);
        encodeImage = getPreference("BandImageURI");

        imageView = findViewById(R.id.imageView4);
        if(encodeImage.length() > 10){
            try {
                byte [] encodeByte=Base64.decode(encodeImage,Base64.DEFAULT);
                Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
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

//        set dropdowns View Resource for spinners
        spinner1 = findViewById(R.id.spinner1);
        ProvincesAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner1.setAdapter(ProvincesAdapter);
        spinner3 = findViewById(R.id.spinner3);
        GenreAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
        spinner3.setAdapter(GenreAdapter);


        String[] provinces = getResources().getStringArray(R.array.Provinces);
//        set the correct values selected on the spinners for province and city by going through all
//        the provinces and city and wait untill correct city is found, nrCity is used because
//        it'll break if you set spinner2 because of the onClickListener of spinner1
        for (int i = 0; i < provinces.length; i ++){
            int resId = getResId(provinces[i], R.array.class);
            String [] cities = getResources().getStringArray(resId);
            Log.d("provinces=" ,provinces[i] + bandLocation);
            for (int j = 0; j < cities.length; j++){
                if (cities[j].equals(bandLocation)){
                    spinner1.setSelection(i);
                    nrCity = j;

                }
            }
        }
        for (int i = 0; i < getResources().getStringArray(R.array.Genres).length; i++){
            if (getResources().getStringArray(R.array.Genres)[i].equals(bandGenre)){
                spinner3.setSelection(i);
            }
        }

        spinner2 = findViewById(R.id.spinner2);
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
                        bandActivity.this,
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

//    start activity that shows the requests to your band
    public void Requests(View view){
        Intent intent = new Intent(this, requestsActivity.class);
        startActivity(intent);
    }

//    starts the activity that shows the members of your band
    public void Members(View view){
        Intent intent = new Intent(this, memberActivity.class);
        startActivity(intent);
    }

//    call function for opening your gallery
    public void Gallery(View view){
        openGallery();
    }

//    function to open the gallery
    public void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

//    if something is selected from the gallery
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
//        check if correct
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageURI = data.getData();
            try{
//                convert bitmap to a string which will be stored in the database
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver()
                        , imageURI);
//                resize the bitmap string
                Bitmap resized = getResizedBitmap(bitmap,1500,2500);
                encodeImage = BitMapToString(resized);
                imageView.setImageBitmap(bitmap);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }

//    function for converting the bitmap to a string
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,10, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }

//    function for resizing the bitmap to a smaller bitmap
    public Bitmap getResizedBitmap(Bitmap bm, int newHeight, int newWidth) {
        int width = bm.getWidth();
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

//    function for saving the band
    public void saveBand(View view){
//      get all the values and put in function for api
        bandLocation = spinner2.getSelectedItem().toString();
        bandGenre = spinner3.getSelectedItem().toString();
        BandDescription = bandDescription.getText().toString();
        new SaveBand().execute(bandName.getText().toString(), bandGenre, bandLocation, BandDescription);
    }

//    make request to api for storing data in database
    public class SaveBand extends AsyncTask<String, Void, String> {

    String Name;
    String Genre;
    String Location;
    String Description;
    Response response;

//    if api returns successfully
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

//        set all the values in sharedPreferences
        SharedPreferences sharedPreferences6=getSharedPreferences("BandImageURI",MODE_PRIVATE);
        SharedPreferences sharedPreferences10 = getSharedPreferences("bandDescription", MODE_PRIVATE);
        SharedPreferences sharedPreferences11 = getSharedPreferences("bandLocation", MODE_PRIVATE);
        SharedPreferences sharedPreferences12 = getSharedPreferences("bandGenre", MODE_PRIVATE);
        SharedPreferences.Editor editor6 = sharedPreferences6.edit();
        SharedPreferences.Editor editor10 = sharedPreferences10.edit();
        SharedPreferences.Editor editor11 = sharedPreferences11.edit();
        SharedPreferences.Editor editor12 = sharedPreferences12.edit();
        editor6.putString("BandImageURI",encodeImage);
        editor10.putString("bandDescription", Description);
        editor11.putString("bandLocation", Location);
        editor12.putString("bandGenre", Genre);
        editor6.apply();
        editor10.apply();
        editor11.apply();
        editor12.apply();

//        show Toast whether requests is successfull or not
        if (response.isSuccessful()){
            showToast("Band Updated succesfully");
        }else{
            showToast("Oops something went wrong");
        }
    }

//    function the connects in the background with api and sents values to it
    @Override
        protected String doInBackground(String... strings) {

//        get values from saveBand function
            Name = strings[0];
            Genre = strings[1];
            Location = strings[2];
            Description = strings[3];

//            string for api request
            String finalURL = "http://10.0.2.2/api/save_band.php";

//            build request for PHP api
            OkHttpClient okHttpClient = new OkHttpClient();

//            add all the post values
            RequestBody formBody = new FormBody.Builder()
                    .add("image_uri", encodeImage)
                    .add("band_name1",Name)
                    .add("band_name", band)
                    .add("band_location", Location)
                    .add("band_genre", Genre)
                    .add("band_description", Description)
                    .build();

//            finish request
            Request request =  new Request.Builder()
                    .url(finalURL)
                    .get()
                    .post(formBody)
                    .build();

//            try getting good response or catch
            try{
                response = okHttpClient.newCall(request).execute();
            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
    }

//    function for showing Toast Mesages on the screen
    public void showToast(final String Text){
        this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(bandActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
