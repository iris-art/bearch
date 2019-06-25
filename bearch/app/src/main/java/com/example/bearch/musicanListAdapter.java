package com.example.bearch;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.json.JSONObject;

import java.util.ArrayList;

public class musicanListAdapter extends ArrayAdapter<String> {

//    global value for all values that'll enter the list
    ArrayList<String> ListItems;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

//        don't reload every view every time
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.musicanlistitem, parent, false);
        }

//        get all the textviews
        TextView Name = convertView.findViewById(R.id.textView102);
        TextView Location = convertView.findViewById(R.id.textView101);
        TextView Genre = convertView.findViewById(R.id.textView100);
        TextView Instrument = convertView.findViewById(R.id.textView103);

//        define values
        String genre = null;
        String instrument = null;
        String location = null;
        String name = null;

        try{
//            get specific band from all the bands
            String user = ListItems.get(position);

            JSONObject Jobject = new JSONObject(user);

//            set values
            name = Jobject.getString("name");
            location = Jobject.getString("location");
            instrument = Jobject.getString("instrument");
            genre = Jobject.getString("genre");
        }catch(Exception e){
            e.printStackTrace();
        }

//        set values in textviews
        Name.setText(name);
        Location.setText(location);
        Genre.setText(genre);
        Instrument.setText(instrument);

        return convertView;
    }

//    call function for the list adapter
    public musicanListAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        ListItems = objects;
    }
}
