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

    private GoogleMap mMap;
    private EditText editTextSearch;
    private Button buttonSearch;

    private final LatLng myShopLocation = new LatLng(7.203265, 79.870508);
    private FusedLocationProviderClient fusedLocationClient;
    private Location currentLocation;

    private Polyline liveLocationPolyline;
    private Polyline searchLocationPolyline;

    private Marker liveLocationMarker;
    private Marker searchLocationMarker;
    private Marker shopMarker;

    private static final int LOCATION_PERMISSION_REQUEST = 1001;

    private LocationRequest locationRequest;
    private LocationCallback locationCallback;

    // Store last searched LatLng for keeping search marker and line
    private LatLng lastSearchedLatLng = null;

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
        } else {
            Log.e(TAG, "Map fragment is null!");
        }

        // LocationRequest setup
        locationRequest = LocationRequest.create();
        locationRequest.setInterval(5000); // 5 seconds
        locationRequest.setFastestInterval(2000);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        // LocationCallback updates live user location and green polyline
        locationCallback = new LocationCallback() {
            @Override
            public void onLocationResult(LocationResult locationResult) {
                if (locationResult == null) return;

                Location location = locationResult.getLastLocation();
                if (location != null && mMap != null) {
                    currentLocation = location;
                    LatLng userLatLng = new LatLng(location.getLatitude(), location.getLongitude());

                    // Update live location marker
                    if (liveLocationMarker == null) {
                        liveLocationMarker = mMap.addMarker(new MarkerOptions()
                                .position(userLatLng)
                                .title("Your Location")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
                    } else {
                        liveLocationMarker.setPosition(userLatLng);
                    }

                    // Update live location polyline (green)
                    if (liveLocationPolyline != null) liveLocationPolyline.remove();
                    PolylineOptions liveLineOptions = new PolylineOptions()
                            .add(userLatLng)
                            .add(myShopLocation)
                            .width(8)
                            .color(Color.GREEN)
                            .geodesic(true);
                    liveLocationPolyline = mMap.addPolyline(liveLineOptions);

                    // If search location exists, also draw blue line and marker for search
                    if (lastSearchedLatLng != null) {
                        // Update or add search location marker
                        if (searchLocationMarker == null) {
                            searchLocationMarker = mMap.addMarker(new MarkerOptions()
                                    .position(lastSearchedLatLng)
                                    .title("Your Search")
                                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                        } else {
                            searchLocationMarker.setPosition(lastSearchedLatLng);
                        }

                        // Update or add blue polyline from searched location to shop
                        if (searchLocationPolyline != null) searchLocationPolyline.remove();
                        PolylineOptions searchLineOptions = new PolylineOptions()
                                .add(lastSearchedLatLng)
                                .add(myShopLocation)
                                .width(8)
                                .color(Color.BLUE)
                                .geodesic(true);
                        searchLocationPolyline = mMap.addPolyline(searchLineOptions);
                    }

                    // Add shop marker if not added yet
                    if (shopMarker == null) {
                        shopMarker = mMap.addMarker(new MarkerOptions()
                                .position(myShopLocation)
                                .title("\uD83D\uDCCD You found us! Welcome to TailorEase \uD83D\uDC4B\uD83E\uDDF5")
                                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
                        if (shopMarker != null) shopMarker.showInfoWindow();
                    }

                    // Zoom to show all three markers nicely
                    LatLngBounds.Builder builder = new LatLngBounds.Builder();
                    builder.include(userLatLng);
                    builder.include(myShopLocation);
                    if (lastSearchedLatLng != null) builder.include(lastSearchedLatLng);

                    // Animate camera with padding
                    mMap.animateCamera(CameraUpdateFactory.newLatLngBounds(builder.build(), 150));
                }
            }
        };

        buttonSearch.setOnClickListener(v -> {
            searchLocation();
        });
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

        // Add shop marker
        if (shopMarker == null) {
            shopMarker = mMap.addMarker(new MarkerOptions()
                    .position(myShopLocation)
                    .title("\uD83D\uDCCD You found us! Welcome to TailorEase \uD83D\uDC4B\uD83E\uDDF5")
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            if (shopMarker != null) shopMarker.showInfoWindow();
        }

        // Move camera initially to shop location with zoom 15
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myShopLocation, 15f));

        // Start live location updates
        startLocationUpdates();
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
                lastSearchedLatLng = new LatLng(address.getLatitude(), address.getLongitude());

                // Add or update search marker
                if (searchLocationMarker == null) {
                    searchLocationMarker = mMap.addMarker(new MarkerOptions()
                            .position(lastSearchedLatLng)
                            .title("Your Search")
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
                } else {
                    searchLocationMarker.setPosition(lastSearchedLatLng);
                }

                // Add or update blue polyline from searched location to shop
                if (searchLocationPolyline != null) searchLocationPolyline.remove();
                PolylineOptions searchLineOptions = new PolylineOptions()
                        .add(lastSearchedLatLng)
                        .add(myShopLocation)
                        .width(8)
                        .color(Color.BLUE)
                        .geodesic(true);
                searchLocationPolyline = mMap.addPolyline(searchLineOptions);

                // If live location known, keep green line and marker updated (trigger a fake location callback)
                if (currentLocation != null) {
                    locationCallback.onLocationResult(LocationResult.create(Collections.singletonList(currentLocation)));
                } else {
                    // Move camera to searched location if live location not available
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

    private void startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, LOCATION_PERMISSION_REQUEST);
            return;
        }
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null);
    }

    private void stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback);
    }

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
        stopLocationUpdates();
    }
}
