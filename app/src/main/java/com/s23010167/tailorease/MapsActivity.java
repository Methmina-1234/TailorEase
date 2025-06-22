package com.s23010167.tailorease;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import com.google.android.gms.location.*;
import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.*;
import java.net.*;
import java.util.*;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private EditText editTextSearch;
    private Button buttonSearch;

    private final LatLng myShopLocation = new LatLng(7.203352, 79.870462);
    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation;

    private static final int LOCATION_PERMISSION_REQUEST = 1001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }

        buttonSearch.setOnClickListener(v -> searchLocation());
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
            return;
        }

        mMap.setMyLocationEnabled(true);

        fusedLocationClient.getLastLocation()
                .addOnSuccessListener(location -> {
                    if (location != null) {
                        currentLocation = location;
                        LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                        addShopMarker();
                        drawRoute(userLatLng, myShopLocation, R.color.green); // ✅ Green line
                    }
                });
    }

    private void addShopMarker() {
        Marker shopMarker = mMap.addMarker(new MarkerOptions()
                .position(myShopLocation)
                .title("We are here!")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));

        if (shopMarker != null) {
            shopMarker.showInfoWindow();
        }
    }

    private void searchLocation() {
        String location = editTextSearch.getText().toString().trim();
        if (location.isEmpty()) {
            Toast.makeText(this, "Please enter a location", Toast.LENGTH_SHORT).show();
            return;
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addressList = geocoder.getFromLocationName(location, 1);
            if (addressList != null && !addressList.isEmpty()) {
                Address address = addressList.get(0);
                LatLng searchedLatLng = new LatLng(address.getLatitude(), address.getLongitude());

                // Don't clear map — we want to keep both paths visible
                mMap.addMarker(new MarkerOptions().position(searchedLatLng).title("Your Search"));

                // ✅ Draw blue line from searched location to shop
                drawRoute(searchedLatLng, myShopLocation, R.color.blue);

                // Move camera to show searched location
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(searchedLatLng, 15f));
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error fetching location", Toast.LENGTH_SHORT).show();
        }
    }


    private void drawRoute(LatLng origin, LatLng destination, int colorResId) {
        String url = getDirectionsUrl(origin, destination);

        new Thread(() -> {
            try {
                Log.d("MAP_ROUTE", "Fetching directions: " + url);
                URL directionUrl = new URL(url);
                HttpURLConnection conn = (HttpURLConnection) directionUrl.openConnection();
                conn.connect();

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder result = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }

                Log.d("MAP_ROUTE", "Response: " + result.toString());

                JSONObject jsonObject = new JSONObject(result.toString());
                JSONArray routes = jsonObject.getJSONArray("routes");

                if (routes.length() > 0) {
                    JSONObject route = routes.getJSONObject(0);
                    JSONObject polyline = route.getJSONObject("overview_polyline");
                    String encodedPoints = polyline.getString("points");

                    List<LatLng> path = decodePolyline(encodedPoints);

                    Log.d("MAP_ROUTE", "Decoded path points: " + path.size());

                    runOnUiThread(() -> {
                        PolylineOptions polylineOptions = new PolylineOptions()
                                .addAll(path)
                                .width(10)
                                .color(getResources().getColor(colorResId))
                                .geodesic(true);
                        mMap.addPolyline(polylineOptions);
                    });
                } else {
                    Log.w("MAP_ROUTE", "No routes found");
                }

            } catch (Exception e) {
                Log.e("MAP_ROUTE", "Error in drawRoute", e);
            }
        }).start();
    }

    private String getDirectionsUrl(LatLng origin, LatLng dest) {
        String str_origin = "origin=" + origin.latitude + "," + origin.longitude;
        String str_dest = "destination=" + dest.latitude + "," + dest.longitude;
        String sensor = "sensor=false";
        String key = "AIzaSyD2DiHHDv6pKgor5_GJyyp-RdRxqWVrJWg"; // Replace with your valid API key

        return "https://maps.googleapis.com/maps/api/directions/json?" +
                str_origin + "&" + str_dest + "&" + sensor + "&mode=driving&key=" + key;
    }

    private List<LatLng> decodePolyline(String encoded) {
        List<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len) {
            int b, shift = 0, result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;
            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);
            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            poly.add(new LatLng(lat / 1E5, lng / 1E5));
        }

        return poly;
    }

    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                onMapReady(mMap);
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
