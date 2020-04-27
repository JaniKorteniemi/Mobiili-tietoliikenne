package com.example.footballcompetitionsv2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

public class CompetitionActivity extends AppCompatActivity {
    final static String SEARCH = "COMPETITIONS";
    ListView ls;

    int ID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_competition);


        ls = findViewById(R.id.listV);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            ID = bundle.getInt("COMPETITION_ID");
        }

        JsonParse jP = new JsonParse(getApplicationContext(), SEARCH, ID);

        ls.setAdapter(jP.myAdapter);
    }
}
