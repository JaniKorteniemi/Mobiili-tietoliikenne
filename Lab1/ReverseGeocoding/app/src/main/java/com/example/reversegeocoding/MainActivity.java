package com.example.reversegeocoding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private static final int MY_FINE_LICATION_PREMISSION = 101;

    private FusedLocationProviderClient fusedLocationClient;
    // Logic to handle location object

    Geocoder geo;
    List<Address> addresses;

    TextView text;
    TextView text1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        text = findViewById(R.id.text);
        text1 = findViewById(R.id.text1);

        geo = new Geocoder(this, Locale.getDefault());


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){

            fusedLocationClient.getLastLocation()
                    .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {
                            // Got last known location. In some rare situations this can be null.
                            if (location != null) {

                               // double lat = location.getLatitude();
                               // double lon = location.getLongitude();
                                double lat = 65.01;
                                double lon = 25.47;

                                try {
                                    addresses = geo.getFromLocation(lat, lon, 1);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                                Address address = addresses.get(0);
                                text1.setText(address.getLocality() + "\n"+ address.getCountryName());
                                /*for (int i = 0; i < 5; i++){
                                    text.setText(text.getText() + addresses.get(i).toString());
                                }*/

                                //text.setText("kek");
                                text.setText(String.valueOf(lat + ", "+ lon));
                            }
                        }
                    });

        }else {
            requestPermissions(new String[] {Manifest.permission.ACCESS_FINE_LOCATION}, MY_FINE_LICATION_PREMISSION);
        }

        /*fusedLocationClient.getLastLocation().addOnFailureListener(
                new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("TAG", "onFailure");
                    }
                });*/
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode){
            case MY_FINE_LICATION_PREMISSION:{
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                }else {
                    Toast.makeText(this, "permission denied!", Toast.LENGTH_SHORT).show();
                }
                break;
            }
        }
    }
}
