package com.example.coronavirusinfinland;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toolbar;

import com.android.volley.RequestQueue;

public class MainActivity extends AppCompatActivity {
    ArrayAdapter arrayAdapter;
    String[] viruCase = {"Confirmed", "Deaths", "Recovered"};

    Toolbar tB;
    ListView lV;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tB = findViewById(R.id.toolBar);
        tB.setTitle("Corona Virus FIN");
        lV = findViewById(R.id.listV);

        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_expandable_list_item_1, viruCase);
        lV.setAdapter(arrayAdapter);

        lV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               // String lowerCaps = viruCase[position];
                //lowerCaps = lowerCaps.toLowerCase();
                Log.d("####CASE####", viruCase[position]);

                Intent intent = new Intent(MainActivity.this, CaseActivity.class);
                intent.putExtra("CASENAME", viruCase[position]);
                startActivity(intent);
            }
        });
    }
}
