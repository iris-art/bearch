package com.example.bearch;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class memberListAdapter extends ArrayAdapter<String> {

//    global value for all values that'll enter the list
    ArrayList<String> ListItems;

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
//        don't reload every view every time
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.requestlistitem, parent, false);
        }

//        get specific member from all the members
        String member = ListItems.get(position);

//        set member in listview
        TextView name = convertView.findViewById(R.id.textView11);
        name.setText(member);


        return convertView;
    }

//    call function for the list adapter
    public memberListAdapter(Context context, int resource, ArrayList<String> objects) {
        super(context, resource, objects);
        ListItems = objects;
    }
}
