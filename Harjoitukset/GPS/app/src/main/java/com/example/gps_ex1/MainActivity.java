package com.example.gps_ex1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        loadFromWeb("https://www.oamk.fi");
    }

    protected void loadFromWeb(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream ins = new BufferedInputStream(connection.getInputStream());
            String htmlText = Utilies.fromStream(ins);
            TextView text = findViewById(R.id.httpText);
            text.setText(htmlText);
        }catch (Exception e){e.printStackTrace();}
    }
}
