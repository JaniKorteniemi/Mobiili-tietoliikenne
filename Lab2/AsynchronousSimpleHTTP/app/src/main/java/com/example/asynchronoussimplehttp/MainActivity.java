package com.example.asynchronoussimplehttp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
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
            //String str = edit.getText().toString();

            ExAsynckTask task = new ExAsynckTask();
            task.execute("kek");
            //loadFromWeb(str);
        }
    }

    private class ExAsynckTask extends AsyncTask<String, Integer, String> {
        String htmlText ="";


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            htmlText = edit.getText().toString();
        }

        @Override
        protected String doInBackground(String... urls) {

            try {
                URL url = new URL(htmlText);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                InputStream ins = new BufferedInputStream(connection.getInputStream());
                //htmlText = Utilies.fromStream(ins);

                return  Utilies.fromStream(ins);
            }catch (Exception e){
                e.printStackTrace();
                Log.d("############ERROR#########", e.toString());
                return htmlText + "error";
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            text.setText(s);
        }
    }

    protected void loadFromWeb(String urlStr) {

    }
}
