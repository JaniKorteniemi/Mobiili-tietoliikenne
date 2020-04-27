package com.example.coronavirusinfinland;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CaseActivity extends AppCompatActivity {
    private final static String HttpFIN = "https://w3qa5ydb4l.execute-api.eu-west-1.amazonaws.com/prod/finnishCoronaData";
    private RequestQueue mQueue;

    Toolbar tB;
    ListView lV;

    MyAdapter myAdapter;
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> district = new ArrayList<>();
    String virusCase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_case);
        mQueue = Volley.newRequestQueue(this);

        tB = findViewById(R.id.toolBar);
        lV = findViewById(R.id.listV);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null){
            virusCase = bundle.getString("CASENAME");
            tB.setTitle(virusCase);
        }
        jsonParse();


        myAdapter = new MyAdapter(this, date, district);
        lV.setAdapter(myAdapter);
    }

    private void jsonParse(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, HttpFIN, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            //String jsonString = response.getString("count");
                            //JSONObject jsonObject = response.getJSONObject("count");
                            //count = jsonString;
                            //Log.d("####COUNT######", "count: " + count);
                            String jsonArrayName = virusCase.toLowerCase();

                            JSONArray jsonArray = response.getJSONArray(jsonArrayName);

                            for (int i = 0; i < jsonArray.length(); i++){
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String strDate = jsonObject.getString("date");
                                String strDistrict = jsonObject.getString("healthCareDistrict");

                                if (strDate != "null"){ date.add(strDate); }
                                else { date.add("Unknown"); }
                                if (strDistrict != "null"){ district.add(strDistrict); }
                                else { district.add("Unknown"); }

                                Log.d("####Date######", "date" + i + ": " + strDate);
                                Log.d("####District######", "district" + i + ": " + strDistrict);

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
        ArrayList<String> rdate;
        ArrayList<String> rDistrict;

        MyAdapter(Context context, ArrayList<String> date , ArrayList<String> district) {
            super(context, R.layout.row, R.id.rowDateText, date);
            this.rcontext = context;
            this.rdate = date;
            this.rDistrict = district;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            TextView date = row.findViewById(R.id.rowDateText);
            TextView district = row.findViewById(R.id.rowDistrictText);
            TextView number = row.findViewById(R.id.rowNumberText);

            date.setText(rdate.get(position));
            district.setText(rDistrict.get(position));
            number.setText("Case num: " + position);

            return row;
        }
    }
}
