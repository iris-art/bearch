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

import java.net.URI;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreateBandActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 100;
    Uri imageURI;
    Button button;
    ImageView imageView;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    String members = "added: \n";
    String members1;
    TextView textView1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_band);
        Spinner spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        String[] items = new String[] {"Limburg", "Noord-Brabant", "Zeeland", "Zuid-Holland", "Noord-Holland", "Utrecht", "Gelderland", "Overijssel", "Drenthe", "Friesland", "Groningen"};
        String[] items1 = new String[] {"Diverse", "Classic", "Folk", "Latin", "Schlager", "Jazz", "R&B", "Rock", "Pop", "Electronic"};
        setAdapter(items, spinner1);
        setAdapter(items1, spinner3);
        members1 = new String();
        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d("joe", items[position]);
                if (items[position] == "Limburg"){
                    String[] items = new String[] {"Ambt Montfort","Arcen en Velden","Beek","Beesel","Bergen","Brunssum","Echt-Susteren","Eijsden","Gennep","Gulpen-Wittem","Haelen","Heel","Heerlen","Helden","Heythuysen","Horst aan de Maas","Hunsel","Kerkrad","Eijsden","Gennep","Gulpen-Wittem","Haelen","Heel","Heerlen","Helden","Heythuysen","Houm","Meerssen","Meijel","Mook en Middelaar","Nederweert","Nuth","Onderbanken","Roerdalen","Roermond","Roggel en Neer","Srst aan de Maas","Hunsel","Kerkrade","Kessel","Landgraaf","Maasbracht","Maasbree","Maalkenburg aan de Geul","Venlo","Venray","Voerendaal","Weert","stricht","Margraten","Meerlo-Wanssum","Meerssen","Meijel","Mook en Middelaar","Nederweert","Nuth","Onderbanken","Roerdalen","Roermond","Roggel en Neer","Schinnen","Sevenum","Simpelveld","Sittard-Geleen","Stein","Swalmen","Thorn","Vaals","Valkenburg aan de Geul","Venlo","Venray","Voerendaal","Weert"};
                    setAdapter(items, spinner2);
                }
                else if (items[position] == "Noord-Brabant"){
                    String[] items = new String[] {"Aalburg","Alphen-Chaam","Asten","Baarle-Nassau","Bergeijk","Bergen op Zoom","Bernheze","Best","Bladel","Boekel","Boxmeer","Boxtel","Breda","Cranendonck","Cuijk","Deurne","Dongen","Drimmelen","Eersel","Eindhoven","Etten-Leur","Geertruidenberg","Geldrop-Mierlo","Gemert-Bakel","Gilze en Rijen","Goirle","Grave","Haaren","Halderberge","Heeze-Leende","Helmond","'s-Hertogenbosch","Heusden","Hilvarenbeek","Laarbeek","Landerd","Lith","Loon op Zand","Maasdonk","Mill en Sint Hubert","Moerdijk","Oirschot","Oisterwijk","Oosterhout","Oss","Reusel-De Mierden","Roosendaal","Rucphen","Schijndel","Sint Anthonis","Sint-Michielsgestel","Sint-Oedenrode","Someren","Son en Breugel","Steenbergen","Tilburg","Uden","Valkenswaard","Veghel","Veldhoven","Vught","Waalre","Waalwijk","Werkendam","Woensdrecht","Woudrichem","Zundert"};
                    setAdapter(items, spinner2);
                }
                else if (items[position] == "Zeeland"){
                    String[] items = new String[] {"Borsele","Goes","Hulst","Kapelle","Middelburg","Noord-Beveland","Reimerswaal","Schouwen-Duiveland","Sluis","Terneuzen","Tholen","Veere","Vlissingen"};
                    setAdapter(items, spinner2);
                }
                else if (items[position] == "Noord-Holland"){
                    String[] items = new String[] {"Aalsmeer","Alkmaar","Amstelveen","Amsterdam","Andijk","Anna Paulowna","Beemster","Bennebroek","Bergen","Beverwijk","Blaricum","Bloemendaal","Bussum","Castricum","Diemen","Drechterland","Edam-Volendam","Enkhuizen","Graft-De Rijp","Haarlem","Haarlemmerliede en Spaarnwoude","Haarlemmermeer","Harenkarspel","Heemskerk","Heemstede","Heerhugowaard","Heiloo","Den Helder","Hilversum","Hoorn","Huizen","Landsmeer","Langedijk","Laren","Medemblik","Muiden","Naarden","Niedorp","Noorder-Koggenland","Obdam","Oostzaan","Opmeer","Ouder-Amstel","Purmerend","Schagen","Schermer","Stede Broec","Texel","Uitgeest","Uithoorn","Velsen","Waterland","Weesp","Wervershoof","Wester-Koggenland","Wieringen","Wieringermeer","Wijdemeren","Wognum","Wormerland","Zaanstad","Zandvoort","Zeevang","Zijpe"};
                    setAdapter(items, spinner2);
                }
                else if (items[position] == "Zuid-Holland"){
                    String[] items = new String[] {"Ter Aar","Alblasserdam","Albrandswaard","Alkemade","Alphen aan den Rijn","Barendrecht","Bergambacht","Bergschenhoek","Berkel en Rodenrijs","Bernisse","Binnenmaas","Bleiswijk","Bodegraven","Boskoop","Brielle","Capelle aan den IJssel","Cromstrijen","Delft","Dirksland","Dordrecht","Giessenlanden","Goedereede","Gorinchem","Gouda","Graafstroom","'s-Gravendeel","'s-Gravenhage","Hardinxveld-Giessendam","Hellevoetsluis","Hendrik-Ido-Ambacht","Hillegom","Jacobswoude","Katwijk","Korendijk","Krimpen aan den IJssel","Leerdam","Leiden","Leiderdorp","Leidschendam-Voorburg","Liemeer","Liesveld","Lisse","Maassluis","Middelharnis","Midden-Delfland","Moordrecht","Nederlek","Nieuwerkerk aan den IJssel","Nieuwkoop","Nieuw-Lekkerland","Noordwijk","Noordwijkerhout","Oegstgeest","Oostflakkee","Oud-Beijerland","Ouderkerk","Papendrecht","Pijnacker-Nootdorp","Reeuwijk","Ridderkerk","Rijnwoude","Rijswijk","Rotterdam","Rozenburg","Schiedam","Schoonhoven","Sliedrecht","Spijkenisse","Strijen","Teylingen","Vlaardingen","Vlist","Voorschoten","Waddinxveen","Wassenaar","Westland","Westvoorne","Zederik","Zevenhuizen-Moerkapelle","Zoetermeer","Zoeterwoude","Zwijndrecht"};
                    setAdapter(items, spinner2);
                }
                else if (items[position] == "Utrecht"){
                    String[] items = new String[] {"Abcoude","Amersfoort","Baarn","De Bilt","Breukelen","Bunnik","Bunschoten","Eemnes","Houten","IJsselstein","Leusden","Loenen","Lopik","Maarssen","Montfoort","Nieuwegein","Oudewater","Renswoude","Rhenen","De Ronde Venen","Soest","Utrecht","Utrechtse Heuvelrug","Veenendaal","Vianen","Wijk bij Duurstede","Woerden","Woudenberg","Zeist"};
                    setAdapter(items, spinner2);
                }
                else if (items[position] == "Gelderland"){
                    String[] items = new String[] {"Aalten","Apeldoorn","Arnhem","Barneveld","Berkelland","Beuningen","Bronckhorst","Brummen","Buren","Culemborg","Doesburg","Doetinchem","Druten","Duiven","Ede","Elburg","Epe","Ermelo","Geldermalsen","Groenlo","Groesbeek","Harderwijk","Hattem","Heerde","Heumen","Lingewaal","Lingewaard","Lochem","Maasdriel","Millingen aan de Rijn","Montferland","Neder-Betuwe","Neerijnen","Nijkerk","Nijmegen","Nunspeet","Oldebroek","Oude IJsselstreek","Overbetuwe","Putten","Renkum","Rheden","Rijnwaarden","Rozendaal","Scherpenzeel","Tiel","Ubbergen","Voorst","Wageningen","West Maas en Waal","Westervoort","Wijchen","Winterswijk","Zaltbommel","Zevenaar","Zutphen"};
                    setAdapter(items, spinner2);
                }
                else if (items[position] == "Overijssel"){
                    String[] items = new String[] {"Almelo","Borne","Dalfsen","Deventer","Dinkelland","Enschede","Haaksbergen","Hardenberg","Hellendoorn","Hengelo","Hof van Twente","Kampen","Losser","Oldenzaal","Olst-Wijhe","Ommen","Raalte","Rijssen-Holten","Staphorst","Steenwijkerland","Tubbergen","Twenterand","Wierden","Zwartewaterland","Zwolle"};
                    setAdapter(items, spinner2);
                }
                else if (items[position] == "Drenthe"){
                    String[] items = new String[] {"Aa en Hunze","Assen","Borger-Odoorn","Coevorden","Emmen","Hoogeveen","Meppel","Midden-Drenthe","Noordenveld","Tynaarlo","Westerveld","De Wolden"};
                    setAdapter(items, spinner2);
                }
                else if (items[position] == "Friesland"){
                    String[] items = new String[] {"Achtkarspelen","Ameland","het Bildt","Boarnsterhim","Bolsward","Dantumadeel","Dongeradeel","Ferwerderadiel","Franekeradeel","GaasterlÃ¢n-Sleat","Harlingen","Heerenveen","Kollumerland en Nieuwkruisland","Leeuwarden","Leeuwarderadeel","Lemsterland","Littenseradiel","Menaldumadeel","Nijefurd","Ooststellingwerf","Opsterland","Schiermonnikoog","SkarsterlÃ¢n","Smallingerland","Sneek","Terschelling","Tytsjerksteradiel","Vlieland","Weststellingwerf","WÃ»nseradiel","Wymbritseradiel"};
                    setAdapter(items, spinner2);
                }
                else{
                    String[] items = new String[] {"Appingedam","Bedum","Bellingwedde","Ten Boer","Delfzijl","Eemsmond","Groningen","Grootegast","Haren","Hoogezand-Sappemeer","Leek","Loppersum","De Marne","Marum","Menterwolde","Pekela","Reiderland","Scheemda","Slochteren","Stadskanaal","Veendam","Vlagtwedde","Winschoten","Winsum","Zuidhorn"};
                    setAdapter(items, spinner2);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void setAdapter(String[] items, Spinner dropdown){
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);
    }
    public void onClick1(View view){
        EditText name = findViewById(R.id.editText8);
        String Name = name.getText().toString();
        EditText description = findViewById(R.id.editText9);
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
            String finalURL = "http://10.0.2.2/api/create_band.php" + "?band_name="+ Name +
                    "&band_description=" + Description
                    + "&user_id=" + Email
                    + "&band_location=" + spinner2.getSelectedItem().toString()
                    + "&band_genre=" + spinner3.getSelectedItem().toString();

            Log.d("results= ", "'" +finalURL+ "'");
            OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
                    .connectTimeout(100, TimeUnit.SECONDS)
                    .writeTimeout(100, TimeUnit.SECONDS)
                    .readTimeout(300, TimeUnit.SECONDS).build();
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
