package com.example.footballcompetitions;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private final static String COMPETITIONS = "https://api.football-data.org/v2/competitions";
    private RequestQueue mQueue;

    ListView ls;
    ImageView fI;
    String name;
    String flag;
    MyAdapter myAdapter;
    ArrayList<String> nameList = new ArrayList<String>();
    ArrayList<String> flagList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mQueue = Volley.newRequestQueue(this);
        ls = findViewById(R.id.listV);
        jsonParse();

        //GlideApp

        myAdapter = new MyAdapter(this, nameList, flagList);
        ls.setAdapter(myAdapter);
    }
    private void jsonParse(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, COMPETITIONS, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //String jsonString = response.getString("count");
                            //JSONObject jsonObject = response.getJSONObject("count");
                            //count = jsonString;
                            //Log.d("####COUNT######", "count: " + count);

                            JSONArray jsonArray = response.getJSONArray("competitions");

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                name = jsonObject.getString("name");
                                nameList.add(name);

                                Log.d("####LEAGUE######", "name" + i + ": " + name);
                                Log.d("####ArrayList######", "Arraylist" + i + ": " + nameList.get(i));

                                JSONObject jsonObject1 = jsonObject.getJSONObject("area");
                                flag = jsonObject1.getString("ensignUrl");
                                if (flag != null){
                                    flag = "https://upload.wikimedia.org/wikipedia/en/a/ae/Flag_of_the_United_Kingdom.svg";
                                }
                                flagList.add(flag);

                                Log.d("####LEAGUE######", "Flag" + i + ": " + flag);
                                Log.d("####LEAGUE######", "Flag" + i + ": " + flag);
                            }
                            myAdapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("####ERROR######", "At jsonParse Error: " + e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mQueue.add(request);
    }

    class MyAdapter extends ArrayAdapter<String> {
        Context rcontext;
        ArrayList<String> rName;
        ArrayList<String> rUri;

        MyAdapter(Context context, ArrayList<String> name , ArrayList<String> uri) {
            super(context, R.layout.row, R.id.rowNameText, name);
            this.rcontext = context;
            this.rName = name;
            this.rUri = uri;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView compText = row.findViewById(R.id.rowNameText);
            //ImageView flagimage = row.findViewById(R.id.flagImage);

            //Glide.with(rcontext).load(Uri.parse(rUri.get(position))).apply(RequestOptions.centerCropTransform()).into(flagimage);

            compText.setText(rName.get(position));

            return row;
        }
    }
}
