package com.example.hospitaltracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.example.hospitaltracker.databinding.ActivityMapsBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.Vector;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    MarkerOptions marker;
    LatLng centerLocation;

    private String URL = "http://192.168.1.102/maklumat/all.php";
    RequestQueue requestQueue;
    Gson gson;
    Maklumat[] maklumats;
    Vector<MarkerOptions> markerOptions;

    String[] perms = {"android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE"};
    private FusedLocationProviderClient fusedLocationClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, 200);
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {


                    }
                });

                binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        gson=new GsonBuilder().create();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        centerLocation=new LatLng(3.0,101);


        markerOptions=new Vector<>();
  //1
        markerOptions.add(new MarkerOptions().title("Hospital Slim River")
                .position(new LatLng(3.838787, 101.404624))
                .snippet("Opens 24 Hours"));

//2
        markerOptions.add(new MarkerOptions().title("Hospital Tapah")
                .position(new LatLng(4.200778, 101.258368))
                .snippet("Opens 24 Hours"));

        //4
        markerOptions.add(new MarkerOptions().title("Hospital Teluk Intan")
                .position(new LatLng(4.002979, 101.038939))
                .snippet("Opens 24 Hour"));

        //5
        markerOptions.add(new MarkerOptions().title("Pantai Hospital Manjung")
                .position(new LatLng(4.215797, 100.669932))
                .snippet("Nearest Hospital"));

        //6
        markerOptions.add(new MarkerOptions().title("Kuala Kubu Bharu Hospital")
                .position(new LatLng(3.566, 101.65267))
                .snippet("Opens 24 hours"));

        //7
        markerOptions.add(new MarkerOptions().title("Klinik Desa Behrang Ulu")
                .position(new LatLng(3.76568,
                        101.4939))
                .snippet("Open 8am close 5pm"));

        //8
        markerOptions.add(new MarkerOptions().title("Klinik Nur Sejahtera Tanjung Malim")
                .position(new LatLng(3.6927, 101.518))
                .snippet("Open 8am close 5pm"));

        //9
        markerOptions.add(new MarkerOptions().title("Pusat Kesihatan UPSI")
                .position(new LatLng(3.6918, 101.522))
                .snippet("Open 8am close 5pm"));

        //10
        markerOptions.add(new MarkerOptions().title("Tanjung Malim Health Clinic")
                .position(new LatLng(3.721,
                        101.528))
                .snippet("Open 8am close 5pm"));

        //11
        markerOptions.add(new MarkerOptions().title("Kalumpang Health Clinic")
                .position(new LatLng(3.6600, 101.571))
                .snippet("Open 8am close 5pm"));

        //12
        markerOptions.add(new MarkerOptions().title("Klinik Desa Behrang Ulu")
                .position(new LatLng(3.7656, 101.493))
                .snippet("Open 8am close 5pm"));

        //13
        markerOptions.add(new MarkerOptions().title("U.n.i KLINIK BEHRANG RESIDEN")
                .position(new LatLng(3.7503, 101.462))
                .snippet("Open 8am close 11pm"));

        //14
        markerOptions.add(new MarkerOptions().title("Klinik Anda")
                .position(new LatLng(3.8405, 101.396))
                .snippet("Open 24 hours"));

        //15
        markerOptions.add(new MarkerOptions().title("POLIKLINIK DR. HANAFI")
                .position(new LatLng(3.8430, 101.3937))
                .snippet("Open 9am close 10pm"));



    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        //LatLng sydney = new LatLng(-34, 151);
        //mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));

        for(MarkerOptions mark : markerOptions) {
            mMap.addMarker(mark);  //multiple add marker

        }
        enableMyLocation();



        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerLocation,8));

    }
    /**
     * Enables the My Location layer if the fine location permission has been granted.
     */

    private void enableMyLocation() {
        // 1. Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            if (mMap != null) {
                mMap.setMyLocationEnabled(true);
                return;
            }
        } else{
                String perms []={"android.permission.ACCESS_FINE_LOCATION"};
                // 2. Otherwise, request location permissions from the user.
                ActivityCompat.requestPermissions(this,perms,200);
            }

        }
}




