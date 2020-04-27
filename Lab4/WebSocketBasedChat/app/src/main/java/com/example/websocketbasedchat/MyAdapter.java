package com.example.websocketbasedchat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.security.PublicKey;
import java.util.ArrayList;


public class MyAdapter extends ArrayAdapter<String> {

    String ip = "@46.132.94.71";
    Context rcontext;
    ArrayList<String> rSender;
    ArrayList<String> rMessage;
    ArrayList<String> rTime;

    MyAdapter(Context context, ArrayList<String> sender, ArrayList<String> message, ArrayList<String> time) {
        super(context, R.layout.row, R.id.rowSenderText, sender);
        this.rcontext = context;
        this.rSender = sender;
        this.rMessage = message;
        this.rTime = time;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater) rcontext.getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = layoutInflater.inflate(R.layout.row, parent, false);
        TextView sender = row.findViewById(R.id.rowSenderText);
        TextView message = row.findViewById(R.id.rowMessageText);
        TextView time = row.findViewById(R.id.rowTimeText);

        sender.setText(rSender.get(position));
        message.setText(rMessage.get(position));
        time.setText(rTime.get(position));

        return row;
    }
}
