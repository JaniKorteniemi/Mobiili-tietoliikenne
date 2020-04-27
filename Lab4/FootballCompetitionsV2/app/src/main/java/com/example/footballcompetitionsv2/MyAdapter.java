package com.example.footballcompetitionsv2;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

class MyAdapter extends ArrayAdapter<String> {
    Context rcontext;
    ArrayList<String> rName;

    MyAdapter(Context context, ArrayList<String> name) {
        super(context, R.layout.row, R.id.rowAreasText, name);
        this.rcontext = context;
        this.rName = name;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) rcontext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row, parent, false);
        TextView compText = row.findViewById(R.id.rowAreasText);

        compText.setText(rName.get(position));

        return row;
    }
}
