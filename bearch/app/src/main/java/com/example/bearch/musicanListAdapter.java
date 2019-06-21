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
    ArrayList<String> ListItems;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.musicanlistitem, parent, false);
        }
        TextView Name = convertView.findViewById(R.id.textView102);
        TextView Location = convertView.findViewById(R.id.textView101);
        TextView Genre = convertView.findViewById(R.id.textView100);
        TextView Instrument = convertView.findViewById(R.id.textView103);


        String province = null;
        String imageURI = null;
        String genre = null;
        String band = null;
        String instrument = null;
        String location = null;
        String email = null;
        String name = null;
        try{
            String user = ListItems.get(position);
            JSONObject Jobject = new JSONObject(user);

            name = Jobject.getString("name");
            email = Jobject.getString("email");
            location = Jobject.getString("location");
            band = Jobject.getString("band");
            instrument = Jobject.getString("instrument");
            genre = Jobject.getString("genre");
            province = Jobject.getString("province");
        }catch(Exception e){
            e.printStackTrace();
        }
        Name.setText(name);
        Location.setText(location);
        Genre.setText(genre);
        Instrument.setText(instrument);

        return convertView;
    }

    public musicanListAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        ListItems = objects;
    }
}
