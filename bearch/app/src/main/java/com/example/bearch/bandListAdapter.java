package com.example.bearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import static java.security.AccessController.getContext;

public class bandListAdapter extends ArrayAdapter<String> {

//    global value for all values that'll enter the list
    ArrayList<String> ListItems;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        don't reload every view every time
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bandlistitem, parent, false);
        }

//        get specific band from all the bands
        String band = ListItems.get(position);

//        get all the propperties and set in the textviews
        String[] bandPropperties = band.split("~");
        String bandName = bandPropperties[0];
        String bandDescription = bandPropperties[1];
        String bandLocation = bandPropperties[2];
        String bandGenre = bandPropperties[3];

        TextView Name = convertView.findViewById(R.id.textView102);
        TextView Location = convertView.findViewById(R.id.textView101);
        TextView Genre = convertView.findViewById(R.id.textView100);


        Name.setText(bandName);
        Location.setText(bandLocation);
        Genre.setText(bandGenre);
        return convertView;
    }

//    call function for the list adapter
    public bandListAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        ListItems = objects;
    }
}
