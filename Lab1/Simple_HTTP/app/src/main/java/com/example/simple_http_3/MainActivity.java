package com.example.simple_http_3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText edit;
    Button button;
    TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit = findViewById(R.id.editText);
        button = findViewById(R.id.button);
        text = findViewById(R.id.httpText);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

    }

    public void click(View v){
        if (edit != null && edit.length() > 0){
            String str = edit.getText().toString();

            loadFromWeb(str);
        }
    }

    protected void loadFromWeb(String urlStr) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream ins = new BufferedInputStream(connection.getInputStream());
            String htmlText = Utilies.fromStream(ins);
            text.setText(htmlText);
        }catch (Exception e){e.printStackTrace();}
    }
}