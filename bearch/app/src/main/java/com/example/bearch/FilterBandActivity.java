package com.example.bearch;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Spinner;
import android.widget.TextView;

import static com.example.bearch.ProfileActivity.getResId;

public class FilterBandActivity extends AppCompatActivity {

    Button btnSearch;
    Spinner spinner1;
    Spinner spinner2;
    Spinner spinner3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter_band);
        FilterBandActivity.this.getWindow().setBackgroundDrawableResource(R.drawable.brushed1);

        btnSearch = findViewById(R.id.button8);
        spinner1 = findViewById(R.id.spinner1);
        spinner2 = findViewById(R.id.spinner2);
        spinner3 = findViewById(R.id.spinner3);

//        make ArrayAdapters for spinners
        ArrayAdapter ProvincesAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.Provinces_filter,
                R.layout.color_spinner_layout
        );
        ArrayAdapter GenreAdapter = ArrayAdapter.createFromResource(
                this,
                R.array.Genres_filter,
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
                if (spinner1.getSelectedItem().toString().equals("All")){
                    spinner2.setVisibility(View.INVISIBLE);
                    TextView textView = findViewById(R.id.textView);
                    textView.setVisibility(View.INVISIBLE);
                }else{
                    spinner2.setVisibility(View.VISIBLE);
//                get Province and string[] with all the cities
                String Province = getResources().getStringArray(R.array.Provinces)[position - 1]+ "_filter";
                int resId = getResId(Province, R.array.class);

//                make ArrayAdapters for spinners
                ArrayAdapter PlaceAdapter = ArrayAdapter.createFromResource(
                        FilterBandActivity.this,
                        resId,
                        R.layout.color_spinner_layout
                );
//                set dropdowns View Resource for spinners
                PlaceAdapter.setDropDownViewResource(R.layout.spinner_dropdown_layout);
                spinner2.setAdapter(PlaceAdapter);
                }
            }
//            nothing happens here.
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    //    start new intent in which the results of the filter will be shown
    public void search(View view){
//        give intent all the extra filter parts
        Intent intent = new Intent(this, bandResultActivity.class);
        intent.putExtra("region", spinner1.getSelectedItem().toString());
        if (spinner2.getVisibility() == View.VISIBLE){
            intent.putExtra("city", spinner2.getSelectedItem().toString());
        }else{
            intent.putExtra("city", "All");
        }

        intent.putExtra("genre", spinner3.getSelectedItem().toString());

        startActivity(intent);
    }
}
