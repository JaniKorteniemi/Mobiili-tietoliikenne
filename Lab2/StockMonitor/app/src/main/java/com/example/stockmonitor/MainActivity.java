package com.example.stockmonitor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String FINANCE = "https://financialmodelingprep.com/api/v3/company/profile/";
    private RequestQueue mQueue;

    ListView lV;
    TextView tv;

    MyAdapter myAdapter;
    ArrayAdapter<String> arrayAdapter;
    ArrayList<String> arrayList = new ArrayList<String>();
    String[] comp = {"AAPL", "NOK", "GOOG", "FB", "MFO", "WMT", "NVDA", "WWE", "TTNP", "NKE", "DAL","SPCB", "MSFT", "ZNGA", "WBA", "PIH"};

    ArrayList<String> companyList = new ArrayList<String>();
    ArrayList<Float> priceList = new ArrayList<Float>();
    ArrayList<Float> changeList = new ArrayList<Float>();
    ArrayList<String> imageList = new ArrayList<String>();

    int img[] = {R.drawable.ic_positive_arrow_green, R.drawable.ic_equal_grey, R.drawable.ic_negative_arrow_red};

    String companyName;
    int price;
    int change;
    String image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mQueue = Volley.newRequestQueue(this);
        lV = findViewById(R.id.listV);

        for (int i = 0; i < comp.length; i++){jsonParse(comp[i]);}

        myAdapter = new MyAdapter(this, companyList, priceList, changeList, imageList, img);
        //arrayAdapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_expandable_list_item_1, arrayList);
        lV.setAdapter(myAdapter);
    }

    private void jsonParse(String eUrl){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, FINANCE + eUrl, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject jsonObject = response.getJSONObject("profile");
                            companyName = jsonObject.getString("companyName");
                            price = jsonObject.getInt("price");
                            change = jsonObject.getInt("changes");
                            image = jsonObject.getString("image");
                            //arrayList.add(companyName);
                            companyList.add(companyName);
                            priceList.add((float)price);
                            changeList.add((float)change);
                            imageList.add(image);
                            myAdapter.notifyDataSetChanged();
                            //Toast.makeText(MainActivity.this, companyName + ": " + price + " USD" , Toast.LENGTH_SHORT).show();
                        } catch (JSONException e) {
                            e.printStackTrace();
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

    class MyAdapter extends ArrayAdapter<String>{
        Context rcontext;
        ArrayList<String> rcompany;
        ArrayList<Float> rprice;
        ArrayList<Float> rchange;
        ArrayList<String> rmainImg;
        int rcImg[];

        MyAdapter(Context context, ArrayList<String> company, ArrayList<Float> price, ArrayList<Float> change, ArrayList<String> mainImg, int cImg[]) {
            super(context, R.layout.row, R.id.rowCompanyText, company);
            this.rcontext = context;
            this.rcompany = company;
            this.rprice = price;
            this.rchange = change;
            this.rmainImg = mainImg;
            this.rcImg = cImg;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View row = layoutInflater.inflate(R.layout.row, parent, false);
            ImageView mainImage = row.findViewById(R.id.listImage);
            ImageView changeImage = row.findViewById(R.id.rowCahngeImage);
            TextView compText = row.findViewById(R.id.rowCompanyText);
            TextView priceText = row.findViewById(R.id.rowPriceText);
            TextView changeText = row.findViewById(R.id.rowCahngeText);

            Picasso.with(MainActivity.this).load(Uri.parse(rmainImg.get(position))).into(mainImage);
            //mainImage.setImageURI(Uri.parse(rmainImg.get(position)));
            compText.setText(rcompany.get(position));
            priceText.setText(String.valueOf(rprice.get(position)));
            changeText.setText(String.valueOf(rchange.get(position)));

            if (rchange.get(position) < 0.0f){
                changeImage.setImageResource(rcImg[2]);
            } else if(rchange.get(position) > 0.0f){
                changeImage.setImageResource(rcImg[0]);
            }else {
                changeImage.setImageResource(rcImg[1]);
            }

            return row;
        }
    }
/*
    public void test(View v){
        if (arrayList.size() > 0){
            for (int i = 0; i < arrayList.size();  i++){
                Toast.makeText(MainActivity.this, arrayList.get(i), Toast.LENGTH_SHORT).show();
                tv.append(arrayList.get(i));
            }
        }else {
            Toast.makeText(MainActivity.this, "No items in arrayList " + arrayList.size(), Toast.LENGTH_SHORT).show();
        }
    }*/
}
