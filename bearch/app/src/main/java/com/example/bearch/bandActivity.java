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
    Button button;
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


        String bandLocation = getPreference("BandLocation");
        String bandGenre = getPreference("BandGenre");

        String [] items = new String[] {"Limburg", "Noord-Brabant", "Zeeland", "Zuid-Holland", "Noord-Holland", "Utrecht", "Gelderland", "Overijssel", "Drenthe", "Friesland", "Groningen"};
        String[] items1 = new String[] {"Diverse", "Classic", "Folk", "Latin", "Schlager", "Jazz", "R&B", "Rock", "Pop", "Electronic"};

        bandName = findViewById(R.id.name);
        band = getPreference("Band");
        bandName.setText(band);
        bandDescription = findViewById(R.id.description);
        BandDescription = getPreference("BandDescription");
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

//        set the correct values selected on the spinners for province and city by going through all
//        the provinces and city and wait untill correct city is found, nrCity is used because
//        it'll break if you set spinner2 because of the onClickListener of spinner1
        for (int i = 0; i < items.length; i ++){
            String [] cities = getCities(items[i]);
            for (int j = 0; j < cities.length; j++){
                if (cities[j].equals(bandLocation)){
                    spinner1.setSelection(i);
                    nrCity = j;

                }
            }
        }
        for (int i = 0; i < items1.length; i++){
            if (items1[i].equals(bandGenre)){
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

    public void Requests(View view){
        Intent intent = new Intent(this, requestsActivity.class);
        startActivity(intent);
    }
    public void Members(View view){
        Intent intent = new Intent(this, memberActivity.class);
        startActivity(intent);
    }

    public String[] getCities(String Region){
        if (Region == "Limburg"){
            String[] items = new String[] {"Ambt Montfort","Arcen en Velden","Beek","Beesel","Bergen","Brunssum","Echt-Susteren","Eijsden","Gennep","Gulpen-Wittem","Haelen","Heel","Heerlen","Helden","Heythuysen","Horst aan de Maas","Hunsel","Kerkrad","Eijsden","Gennep","Gulpen-Wittem","Haelen","Heel","Heerlen","Helden","Heythuysen","Houm","Meerssen","Meijel","Mook en Middelaar","Nederweert","Nuth","Onderbanken","Roerdalen","Roermond","Roggel en Neer","Srst aan de Maas","Hunsel","Kerkrade","Kessel","Landgraaf","Maasbracht","Maasbree","Maalkenburg aan de Geul","Venlo","Venray","Voerendaal","Weert","stricht","Margraten","Meerlo-Wanssum","Meerssen","Meijel","Mook en Middelaar","Nederweert","Nuth","Onderbanken","Roerdalen","Roermond","Roggel en Neer","Schinnen","Sevenum","Simpelveld","Sittard-Geleen","Stein","Swalmen","Thorn","Vaals","Valkenburg aan de Geul","Venlo","Venray","Voerendaal","Weert"};
            return items;
        }
        else if (Region == "Noord-Brabant"){
            String[] items = new String[] {"Aalburg","Alphen-Chaam","Asten","Baarle-Nassau","Bergeijk","Bergen op Zoom","Bernheze","Best","Bladel","Boekel","Boxmeer","Boxtel","Breda","Cranendonck","Cuijk","Deurne","Dongen","Drimmelen","Eersel","Eindhoven","Etten-Leur","Geertruidenberg","Geldrop-Mierlo","Gemert-Bakel","Gilze en Rijen","Goirle","Grave","Haaren","Halderberge","Heeze-Leende","Helmond","'s-Hertogenbosch","Heusden","Hilvarenbeek","Laarbeek","Landerd","Lith","Loon op Zand","Maasdonk","Mill en Sint Hubert","Moerdijk","Oirschot","Oisterwijk","Oosterhout","Oss","Reusel-De Mierden","Roosendaal","Rucphen","Schijndel","Sint Anthonis","Sint-Michielsgestel","Sint-Oedenrode","Someren","Son en Breugel","Steenbergen","Tilburg","Uden","Valkenswaard","Veghel","Veldhoven","Vught","Waalre","Waalwijk","Werkendam","Woensdrecht","Woudrichem","Zundert"};
            return items;
        }
        else if (Region == "Zeeland"){
            String[] items = new String[] {"Borsele","Goes","Hulst","Kapelle","Middelburg","Noord-Beveland","Reimerswaal","Schouwen-Duiveland","Sluis","Terneuzen","Tholen","Veere","Vlissingen"};
            return items;
        }
        else if (Region == "Noord-Holland"){
            String[] items = new String[] {"Aalsmeer","Alkmaar","Amstelveen","Amsterdam","Andijk","Anna Paulowna","Beemster","Bennebroek","Bergen","Beverwijk","Blaricum","Bloemendaal","Bussum","Castricum","Diemen","Drechterland","Edam-Volendam","Enkhuizen","Graft-De Rijp","Haarlem","Haarlemmerliede en Spaarnwoude","Haarlemmermeer","Harenkarspel","Heemskerk","Heemstede","Heerhugowaard","Heiloo","Den Helder","Hilversum","Hoorn","Huizen","Landsmeer","Langedijk","Laren","Medemblik","Muiden","Naarden","Niedorp","Noorder-Koggenland","Obdam","Oostzaan","Opmeer","Ouder-Amstel","Purmerend","Schagen","Schermer","Stede Broec","Texel","Uitgeest","Uithoorn","Velsen","Waterland","Weesp","Wervershoof","Wester-Koggenland","Wieringen","Wieringermeer","Wijdemeren","Wognum","Wormerland","Zaanstad","Zandvoort","Zeevang","Zijpe"};
            return items;
        }
        else if (Region == "Zuid-Holland"){
            String[] items = new String[] {"Ter Aar","Alblasserdam","Albrandswaard","Alkemade","Alphen aan den Rijn","Barendrecht","Bergambacht","Bergschenhoek","Berkel en Rodenrijs","Bernisse","Binnenmaas","Bleiswijk","Bodegraven","Boskoop","Brielle","Capelle aan den IJssel","Cromstrijen","Delft","Dirksland","Dordrecht","Giessenlanden","Goedereede","Gorinchem","Gouda","Graafstroom","'s-Gravendeel","'s-Gravenhage","Hardinxveld-Giessendam","Hellevoetsluis","Hendrik-Ido-Ambacht","Hillegom","Jacobswoude","Katwijk","Korendijk","Krimpen aan den IJssel","Leerdam","Leiden","Leiderdorp","Leidschendam-Voorburg","Liemeer","Liesveld","Lisse","Maassluis","Middelharnis","Midden-Delfland","Moordrecht","Nederlek","Nieuwerkerk aan den IJssel","Nieuwkoop","Nieuw-Lekkerland","Noordwijk","Noordwijkerhout","Oegstgeest","Oostflakkee","Oud-Beijerland","Ouderkerk","Papendrecht","Pijnacker-Nootdorp","Reeuwijk","Ridderkerk","Rijnwoude","Rijswijk","Rotterdam","Rozenburg","Schiedam","Schoonhoven","Sliedrecht","Spijkenisse","Strijen","Teylingen","Vlaardingen","Vlist","Voorschoten","Waddinxveen","Wassenaar","Westland","Westvoorne","Zederik","Zevenhuizen-Moerkapelle","Zoetermeer","Zoeterwoude","Zwijndrecht"};
            return items;
        }
        else if (Region == "Utrecht"){
            String[] items = new String[] {"Abcoude","Amersfoort","Baarn","De Bilt","Breukelen","Bunnik","Bunschoten","Eemnes","Houten","IJsselstein","Leusden","Loenen","Lopik","Maarssen","Montfoort","Nieuwegein","Oudewater","Renswoude","Rhenen","De Ronde Venen","Soest","Utrecht","Utrechtse Heuvelrug","Veenendaal","Vianen","Wijk bij Duurstede","Woerden","Woudenberg","Zeist"};
            return items;
        }
        else if (Region == "Gelderland"){
            String[] items = new String[] {"Aalten","Apeldoorn","Arnhem","Barneveld","Berkelland","Beuningen","Bronckhorst","Brummen","Buren","Culemborg","Doesburg","Doetinchem","Druten","Duiven","Ede","Elburg","Epe","Ermelo","Geldermalsen","Groenlo","Groesbeek","Harderwijk","Hattem","Heerde","Heumen","Lingewaal","Lingewaard","Lochem","Maasdriel","Millingen aan de Rijn","Montferland","Neder-Betuwe","Neerijnen","Nijkerk","Nijmegen","Nunspeet","Oldebroek","Oude IJsselstreek","Overbetuwe","Putten","Renkum","Rheden","Rijnwaarden","Rozendaal","Scherpenzeel","Tiel","Ubbergen","Voorst","Wageningen","West Maas en Waal","Westervoort","Wijchen","Winterswijk","Zaltbommel","Zevenaar","Zutphen"};
            return items;
        }
        else if (Region == "Overijssel"){
            String[] items = new String[] {"Almelo","Borne","Dalfsen","Deventer","Dinkelland","Enschede","Haaksbergen","Hardenberg","Hellendoorn","Hengelo","Hof van Twente","Kampen","Losser","Oldenzaal","Olst-Wijhe","Ommen","Raalte","Rijssen-Holten","Staphorst","Steenwijkerland","Tubbergen","Twenterand","Wierden","Zwartewaterland","Zwolle"};
            return items;
        }
        else if (Region == "Drenthe"){
            String[] items = new String[] {"Aa en Hunze","Assen","Borger-Odoorn","Coevorden","Emmen","Hoogeveen","Meppel","Midden-Drenthe","Noordenveld","Tynaarlo","Westerveld","De Wolden"};
            return items;
        }
        else if (Region == "Friesland"){
            String[] items = new String[] {"Achtkarspelen","Ameland","het Bildt","Boarnsterhim","Bolsward","Dantumadeel","Dongeradeel","Ferwerderadiel","Franekeradeel","GaasterlÃ¢n-Sleat","Harlingen","Heerenveen","Kollumerland en Nieuwkruisland","Leeuwarden","Leeuwarderadeel","Lemsterland","Littenseradiel","Menaldumadeel","Nijefurd","Ooststellingwerf","Opsterland","Schiermonnikoog","SkarsterlÃ¢n","Smallingerland","Sneek","Terschelling","Tytsjerksteradiel","Vlieland","Weststellingwerf","WÃ»nseradiel","Wymbritseradiel"};
            return items;
        }
        else{
            String[] items = new String[] {"Appingedam","Bedum","Bellingwedde","Ten Boer","Delfzijl","Eemsmond","Groningen","Grootegast","Haren","Hoogezand-Sappemeer","Leek","Loppersum","De Marne","Marum","Menterwolde","Pekela","Reiderland","Scheemda","Slochteren","Stadskanaal","Veendam","Vlagtwedde","Winschoten","Winsum","Zuidhorn"};
            return items;
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
        openGallery();
    }

    public void openGallery() {
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
                Bitmap resized = getResizedBitmap(bitmap,1500,2500);
                encodeImage = BitMapToString(resized);
                imageView.setImageBitmap(bitmap);
            }catch(Exception e){
                e.printStackTrace();
            }
        }
    }
    public String BitMapToString(Bitmap bitmap){
        ByteArrayOutputStream baos=new  ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,10, baos);
        byte [] b=baos.toByteArray();
        String temp= Base64.encodeToString(b, Base64.DEFAULT);
        return temp;
    }
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
    public void onClick1(View view){

        bandLocation = spinner2.getSelectedItem().toString();
        bandGenre = spinner3.getSelectedItem().toString();
        BandDescription = bandDescription.getText().toString();
        new SaveBand().execute(bandName.getText().toString(), bandGenre, bandLocation, BandDescription);
    }
    public class SaveBand extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            SharedPreferences sharedPreferences6=getSharedPreferences("BandImageURI",MODE_PRIVATE);
            SharedPreferences.Editor editor6 = sharedPreferences6.edit();
            editor6.putString("BandImageURI",encodeImage);
            editor6.apply();
            String Name = strings[0];
            String Genre = strings[1];
            String Location = strings[2];
            String Description = strings[3];
            Log.d("BANDNAME = ", Description);
            String finalURL = "http://10.0.2.2/api/save_band.php" + "?band_name1="+ Name +
                    "&band_name="+ band +
                    "&band_location=" + Location +
                    "&band_genre=" + Genre +
                    "&band_description=" + Description;
            SharedPreferences sharedPreferences10 = getSharedPreferences("bandDescription", MODE_PRIVATE);
            SharedPreferences sharedPreferences11 = getSharedPreferences("bandLocation", MODE_PRIVATE);
            SharedPreferences sharedPreferences12 = getSharedPreferences("bandGenre", MODE_PRIVATE);
            SharedPreferences.Editor editor10 = sharedPreferences10.edit();
            SharedPreferences.Editor editor11 = sharedPreferences11.edit();
            SharedPreferences.Editor editor12 = sharedPreferences12.edit();
            editor10.putString("bandDescription", Description);
            editor11.putString("bandLocation", Location);
            editor12.putString("bandGenre", Genre);
            editor10.apply();
            editor11.apply();
            editor12.apply();
            band = bandName.getText().toString();
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
                Log.d("RESULTTTTT = ", result);
                if (response.isSuccessful()){
                    showToast("Band Updated succesfully");
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
                Toast.makeText(bandActivity.this,
                        Text, Toast.LENGTH_LONG).show();
            }
        });
    }
}
