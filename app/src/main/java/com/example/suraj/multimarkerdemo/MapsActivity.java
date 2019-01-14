package com.example.suraj.multimarkerdemo;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MarkerOptions options = new MarkerOptions();
    RequestQueue requestQueue;
    StringRequest stringRequest;
    LatLng sydney;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        requestQueue = Volley.newRequestQueue(this);


    }



    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        latitude();


    }

    public void latitude(){
        stringRequest = new StringRequest(Request.Method.GET, Util.LatLong, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(MapsActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                try {
                    int count = 1;
                    JSONObject jo = new JSONObject(response);
                    JSONArray ja = jo.getJSONArray("Districts");

                    for(int i =0;i<ja.length();i++)
                    {
                        JSONObject jo1 = ja.getJSONObject(i);

                        // Lat , Long columns de names va Table ch
                        double lat = Double.parseDouble(jo1.getString("Lat"));
                        double longg = Double.parseDouble(jo1.getString("Long"));

                        // count di jagah block ya village da name aa jayega

                        sydney = new LatLng(lat, longg);
                        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker number "+count++));


                    }

                    mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));


                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MapsActivity.this, "Some exeception"+e, Toast.LENGTH_SHORT).show();

                }
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MapsActivity.this, "Error"+error, Toast.LENGTH_SHORT).show();

                    }
                }
        );
        requestQueue.add(stringRequest);

    }
}
