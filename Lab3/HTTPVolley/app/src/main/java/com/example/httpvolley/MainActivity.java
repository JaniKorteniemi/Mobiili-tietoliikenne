package com.example.httpvolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    EditText edit;
    Button button;
    TextView text;

    private RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);

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

                String url = htmlText;
                StringRequest sReq = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                text.setText(response.substring(0, 500));
                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        text.setText("That didnt't work");
                    }
                });
                mQueue.add(sReq);

                return  "Complete";
            }catch (Exception e){
                e.printStackTrace();
                Log.d("############ERROR#########", e.toString());
                return htmlText + "error";
            }
        }
    }
}
