package com.example.bearch;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

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

        String user = ListItems.get(position);
        String[] properties = user.split("~");

        Name.setText(properties[1]);
        Location.setText(properties[2]);
        Genre.setText(properties[4]);
        Instrument.setText(properties[5]);

        return convertView;
    }

    public musicanListAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        ListItems = objects;
    }
}
