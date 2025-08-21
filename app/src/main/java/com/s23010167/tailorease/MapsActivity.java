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

import java.io.IOException;
import java.util.*;

import android.graphics.Color;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private static final String TAG = "MapsActivity";

    private GoogleMap mMap; // Map object
    private EditText editTextSearch; // Input field for location search
    private Button buttonSearch; // Button to trigger search

    private final LatLng myShopLocation = new LatLng(7.203265, 79.870508); // Tailor shop location
    private FusedLocationProviderClient fusedLocationClient; // For live location updates
    private Location currentLocation; // Stores user's current location

    private Polyline liveLocationPolyline; // Green polyline from user to shop
    private Polyline searchLocationPolyline; // Blue polyline from searched location to shop

    private Marker liveLocationMarker; // Marker for live location
    private Marker searchLocationMarker; // Marker for searched location
    private Marker shopMarker; // Marker for shop

    private static final int LOCATION_PERMISSION_REQUEST = 1001; // Request code for permissions

    private LocationRequest locationRequest; // Settings for live location updates
    private LocationCallback locationCallback; // Callback for receiving live location updates

    private LatLng lastSearchedLatLng = null; // Store last searched location

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        // Bind views
        editTextSearch = findViewById(R.id.editTextSearch);
        buttonSearch = findViewById(R.id.buttonSearch);
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);

        // Initialize map fragment
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this); // Async callback when map is ready
        } else {
            Log.e(TAG, "Map fragment is null!");
        }

        // Configure location request parameters
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000); // Update every 5 seconds
        locationRequest.setFastestInterval(2000); // Fastest update interval
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY); // High accuracy

        // Location callback: updates user marker and polylines
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;

                Location location = locationResult.getLastLocation();
                if (location != null && mMap != null) {
                    currentLocation = location;
                    LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                    // Add or update live location marker
                    if (liveLocationMarker == null) {
                        liveLocationMarker = mMap.addMarker(new MarkerOptions()
                                .position(userLatLng)
                                .title("Your Location")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    } else {
                        liveLocationMarker.setPosition(userLatLng);
                    }

                    // Add or update green polyline from user to shop
                    if (liveLocationPolyline != null) liveLocationPolyline.remove();
                    PolylineOptions liveLineOptions = new PolylineOptions()
                            .add(userLatLng)
                            .add(myShopLocation)
                            .width(8)
                            .color(Color.GREEN)
                            .geodesic(true);
                    liveLocationPolyline = mMap.addPolyline(liveLineOptions);

                    // If a search location exists, add/update blue line and marker
                    if (lastSearchedLatLng != null) {
                        if (searchLocationMarker == null) {
                            searchLocationMarker = mMap.addMarker(new MarkerOptions()
                                    .position(lastSearchedLatLng)
                                    .title("Your Search")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        } else {
                            searchLocationMarker.setPosition(lastSearchedLatLng);
                        }

                        if (searchLocationPolyline != null) searchLocationPolyline.remove();
                        PolylineOptions searchLineOptions = new PolylineOptions()
                                .add(lastSearchedLatLng)
                                .add(myShopLocation)
                                .width(8)
                                .color(Color.BLUE)
                                .geodesic(true);
                        searchLocationPolyline = mMap.addPolyline(searchLineOptions);
                    }

                    // Add shop marker if not already added
                    if (shopMarker == null) {
                        shopMarker = mMap.addMarker(new MarkerOptions()
                                .position(myShopLocation)
                                .title("📍 You found us! Welcome to TailorEase 👋🧵")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        if (shopMarker != null) shopMarker.showInfoWindow();
                    }

                    // Zoom map to include all markers
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(userLatLng);
                    builder.include(myShopLocation);
                    if (lastSearchedLatLng != null) builder.include(lastSearchedLatLng);

                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 150));
                }
            }
        };

        // Search button click
        buttonSearch.setOnClickListener(v -> {
            searchLocation(); // Perform search
        });
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        // Check location permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
            return;
        }

        mMap.setMyLocationEnabled(true); // Enable blue dot

        // Add shop marker
        if (shopMarker == null) {
            shopMarker = mMap.addMarker(new MarkerOptions()
                    .position(myShopLocation)
                    .title("📍 You found us! Welcome to TailorEase 👋🧵")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            if (shopMarker != null) shopMarker.showInfoWindow();
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myShopLocation, 15f)); // Initial zoom

        startLocationUpdates(); // Begin live location updates
    }

    // Search location using Geocoder
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
                lastSearchedLatLng = new LatLng(address.getLatitude(), address.getLongitude());

                // Add or update marker
                if (searchLocationMarker == null) {
                    searchLocationMarker = mMap.addMarker(new MarkerOptions()
                            .position(lastSearchedLatLng)
                            .title("Your Search")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                } else {
                    searchLocationMarker.setPosition(lastSearchedLatLng);
                }

                // Add or update blue polyline
                if (searchLocationPolyline != null) searchLocationPolyline.remove();
                PolylineOptions searchLineOptions = new PolylineOptions()
                        .add(lastSearchedLatLng)
                        .add(myShopLocation)
                        .width(8)
                        .color(Color.BLUE)
                        .geodesic(true);
                searchLocationPolyline = mMap.addPolyline(searchLineOptions);

                // Update live location line if available
                if (currentLocation != null) {
                    locationCallback.onLocationResult(LocationResult.create(Collections.singletonList(currentLocation)));
                } else {
                    mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(lastSearchedLatLng, 15f));
                }
            } else {
                Toast.makeText(this, "Location not found", Toast.LENGTH_SHORT).show();
            }
        } catch (IOException e) {
            Log.e(TAG, "Geocoder I/O Exception", e);
            Toast.makeText(this, "Error fetching location", Toast.LENGTH_SHORT).show();
        }
    }

    // Request live location updates
    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    // Stop live location updates
    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

    // Handle permission results
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (mMap != null) onMapReady(mMap);
            } else {
                Toast.makeText(this, "Location permission denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopLocationUpdates(); // Stop updates when activity destroyed
    }
}
