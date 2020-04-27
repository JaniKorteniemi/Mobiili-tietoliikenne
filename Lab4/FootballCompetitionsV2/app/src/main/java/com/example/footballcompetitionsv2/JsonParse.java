package com.example.footballcompetitionsv2;

import android.content.Context;
import android.util.Log;

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

public class JsonParse {
    private RequestQueue mQueue;

    public MyAdapter myAdapter;
    public ArrayList<Integer> areaIDList = new ArrayList<Integer>();
    private ArrayList<String> nameList = new ArrayList<String>();

    private String name;
    private int ID = 0;

    public JsonParse(Context context, String str){
        mQueue = Volley.newRequestQueue(context);
        jsonParse(str);
        myAdapter = new MyAdapter(context, nameList);
    }

    public JsonParse(Context context, String str, int ID){
        mQueue = Volley.newRequestQueue(context);
        this.ID = ID;
        jsonParse(str);
        myAdapter = new MyAdapter(context, nameList);
    }

    private void jsonParse(String str){
        String HTTPS = "https://api.football-data.org/v2/";
        String AREAS = "areas";
        String COMPETITIONS = "competitions?areas=";
        String SEARCH = HTTPS + AREAS;
        if (str.equals("COMPETITIONS") && ID != 0){
            SEARCH = HTTPS + COMPETITIONS + ID;
        }
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, SEARCH, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (ID == 0){
                                JSONArray jsonArray = response.getJSONArray("areas");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    name = jsonObject.getString("name");
                                    nameList.add(name);
                                    ID = jsonObject.getInt("id");
                                    areaIDList.add(ID);
                                }
                            }else {
                                JSONArray jsonArray = response.getJSONArray("competitions");
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                                    name = jsonObject.getString("name");
                                    nameList.add(name);
                                }
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
}
