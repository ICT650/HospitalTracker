package com.example.hospitaltracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class RahsiaActivity extends AppCompatActivity implements View.OnClickListener {

    GoogleSignInClient mGoogleSignInClient;
    String email, name;
    TextView tvcoords, tvaddress;
    private LocationCallback locationCallback;
    LocationRequest locationRequest;
    RequestQueue queue;
    final String URL = "http://192.168.1.102/comments/api.php";

    String[] perms = {"android.permission.ACCESS_FINE_LOCATION",
            "android.permission.ACCESS_COARSE_LOCATION",
            "android.permission.INTERNET",
            "android.permission.ACCESS_NETWORK_STATE"};
    public CardView tracker1, about, yt;
    private FusedLocationProviderClient fusedLocationClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rahsia);

        queue= Volley.newRequestQueue(getApplicationContext());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, 200);
            return;
        }


        TextView tvName = (TextView) findViewById(R.id.tvname);
        TextView tvemail=(TextView)findViewById(R.id.tvemail);


        name = getIntent().getStringExtra("Name");

        email = getIntent().getStringExtra("Email");


        tvName.setText(name);
        tvemail.setText(email);

        tvcoords=(TextView)findViewById(R.id.tvcoords);
        tvaddress=(TextView)findViewById(R.id.tvaddress1);


        tracker1=(CardView)findViewById(R.id.tracker1);
        about=(CardView)findViewById(R.id.about);
        yt=(CardView)findViewById(R.id.github);

        tracker1.setOnClickListener(this);
        about.setOnClickListener(this);
        yt.setOnClickListener(this);


        Button signout = findViewById(R.id.signout);
        signout.setOnClickListener(this);



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, 200);

            return;
        }

        locationRequest = LocationRequest.create();

        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10 * 1000);
        locationRequest.setFastestInterval(2000);


        locationCallback = new LocationCallback() {
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) {
                    Toast.makeText(getApplicationContext(),"Unable detect location",Toast.LENGTH_SHORT).show();
                    return;
                }
                for (Location location : locationResult.getLocations()) {
                    // Update UI with location data
                   double lat = location.getLatitude();
                   double lon = location.getLongitude();



                  //  tvcoords.setText(""+lat+","+lon);

                    Geocoder geocoder=new Geocoder(getApplicationContext());

                    List<Address> addressList= null;
                    try {
                        addressList = geocoder.getFromLocation(lat,lon,1);
                        Address address=addressList.get(0);

                        String line=address.getAddressLine(0);
                        String area=address.getAdminArea();
                        String postcode=address.getPostalCode();

                        tvaddress.setText(line+" "+area);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                }
            }
        };

        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                double lat= location.getLatitude();
                double lon= location.getLongitude();
                String lat1= "Lattitude "+location.getLatitude();
                String lon1= "Longitude"+location.getLongitude();

                tvcoords.setText(""+lat+","+lon);
                makeRequest(lat1,lon1);



            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.signout:
                signOut();
                break;
        }

        Intent i;
        switch(view.getId())
        {
            case R.id.about:
                i=new Intent(this,about_us.class);
                startActivity(i);
                break;

            case R.id.tracker1:
                i=new Intent(this,MapsActivity.class);
                startActivity(i);
                break;


            case R.id.github:
                i=new Intent(this,tracker.class);
                startActivity(i);
                break;
        }


    }
    //conn to database
    public void makeRequest(String lat1,String lon1) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();

            }

        },errorListener)
        {
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params=new HashMap<>();

                params.put("name",account.getDisplayName());
                params.put("email",account.getEmail());
                params.put("lat",lat1);
                params.put("lon",lon1);



                return params;
            }
        };
        queue.add(stringRequest);
    }
    public Response.ErrorListener errorListener=new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            Toast.makeText(getApplicationContext(),error.getMessage(),Toast.LENGTH_LONG).show();

        }
    };
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getApplicationContext(), "user signed out", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
    @Override
    protected void onResume() {
        super.onResume();

        startLocationUpdates();

    }
    public void startLocationUpdates()
    {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, perms, 200);
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest,
                locationCallback,
                Looper.getMainLooper());
    }
}