package com.example.bearch;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

public class ProfileActivity extends AppCompatActivity {

    Button btnSave;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;
    Spinner spinner4;
    String[] items;
    Integer nrCity;
    private static final int PICK_IMAGE = 100;
    Uri imageURI;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        SharedPreferences sharedPreferences=getSharedPreferences("Name",MODE_PRIVATE);
        String Name = sharedPreferences.getString("Name", "None");
        SharedPreferences sharedPreferences1=getSharedPreferences("Email",MODE_PRIVATE);
        String Email = sharedPreferences1.getString("Email", "None");
        SharedPreferences sharedPreferences2=getSharedPreferences("Genre",MODE_PRIVATE);
        String Genre =sharedPreferences2.getString("Genre","None");
        SharedPreferences sharedPreferences3=getSharedPreferences("Instrument",MODE_PRIVATE);
        String Instrument =sharedPreferences3.getString("Instrument","None");
        SharedPreferences sharedPreferences4=getSharedPreferences("Location",MODE_PRIVATE);
        String Location =sharedPreferences4.getString("Location","None");
        SharedPreferences sharedPreferences5=getSharedPreferences("Band",MODE_PRIVATE);
        String Band =sharedPreferences5.getString("Band","None");

        imageView = findViewById(R.id.imageView4);
        Button btnBand = findViewById(R.id.button10);

        if (Band.equals("None")) {
            btnBand.setVisibility(View.INVISIBLE);
        }

        EditText name = findViewById(R.id.editText10);
        EditText email = findViewById(R.id.editText9);

        name.setText(Name);
        email.setText(Email);
        btnSave = findViewById(R.id.button9);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);
        spinner4 = findViewById(R.id.spinner4);

        items = new String[] {"Limburg", "Noord-Brabant", "Zeeland", "Zuid-Holland", "Noord-Holland", "Utrecht", "Gelderland", "Overijssel", "Drenthe", "Friesland", "Groningen"};
        String[] items1 = new String[] {"Diverse", "Classic", "Folk", "Latin", "Schlager", "Jazz", "R&B", "Rock", "Pop", "Electronic"};
        String[] items2 = new String[] {"Singer", "Accordion" , "Bagpipes" , "Banjo" , "Bass guitar" , "Bassoon" , "Berimbau" , "Bongo" , "Cello" , "Clarinet" , "Cor anglais" , "Cornet" , "Cymbal" , "Didgeridoo" , "Double bass" , "Drum kit" , "Euphonium" , "Flute" , "French horn" , "Glass harmonica" , "Glockenspiel" , "Gong" , "Guitar" , "Harmonica" , "Harp" , "Harpsichord" , "Hammered dulcimer" , "Hurdy gurdy" , "Jew’s harp" , "Kalimba" , "Lute" , "Lyre" , "Mandolin" , "Marimba" , "Melodica" , "Oboe" , "Ocarina" , "Octobass" , "Organ" , "Pan Pipes" , "Pennywhistle" , "Piano" , "Piccolo" , "Pungi" , "Recorder" , "Saxophone" , "Sitar" , "Synthesizer" , "Tambourine" , "Timpani" , "Triangle" , "Trombone" , "Trumpet" , "Theremin" , "Tuba" , "Ukulele" , "Viola" , "Violin" , "Whamola" , "Xylophone" , "Zither"};
        setAdapter(items, spinner1);
        setAdapter(items1, spinner3);
        setAdapter(items2, spinner4);
        Log.d("LOCATION =", Location);
        for (int i = 0; i < items.length; i ++){
            String [] cities = getCities(items[i]);
            for (int j = 0; j < cities.length; j++){
                if (cities[j].equals(Location)){
                    spinner1.setSelection(i);
                    nrCity = j;

                }
            }
        }
        Log.d("Instrument =", Instrument);
        Log.d("Genre =", Genre);
        for (int i = 0; i < items1.length; i++){
            if (items1[i].equals(Genre)){
                spinner3.setSelection(i);

            }
        }

        for (int i = 0; i < items2.length; i++){
            if (items2[i].equals(Instrument)){
                spinner4.setSelection(i);

            }
        }


        spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setAdapter(getCities(items[position]), spinner2);
                if (nrCity != null){
                    spinner2.setSelection(nrCity);
                    nrCity = null;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                spinner2.setSelection(nrCity);
                Log.d("HEY1", nrCity.toString());
            }
        });


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
        Intent intent = new Intent(this, bandActivity.class);
        startActivity(intent);
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
            imageView.setImageURI(imageURI);
        }
    }
}
