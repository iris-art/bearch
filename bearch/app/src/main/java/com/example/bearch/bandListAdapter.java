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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.bandlistitem, parent, false);
        }


        return super.getView(position, convertView, parent);
    }

    public bandListAdapter(Context context, int resource) {
        super(context, resource);
    }
}
