package com.we2030.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import androidx.core.app.ActivityCompat;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.Task;

public class MapManager {
    private Context context;
    private GoogleMap googleMap;
    private FusedLocationProviderClient fusedLocationClient;
    
    public MapManager(Context context) {
        this.context = context;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(context);
    }
    
    public void setGoogleMap(GoogleMap map) {
        this.googleMap = map;
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) 
            == PackageManager.PERMISSION_GRANTED) {
            googleMap.setMyLocationEnabled(true);
        }
    }
    
    public void moveToLocation(LatLng location, float zoom) {
        if (googleMap != null) {
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, zoom));
        }
    }
    
    public void addMarker(LatLng position, String title, String snippet) {
        if (googleMap != null) {
            googleMap.addMarker(new MarkerOptions()
                .position(position)
                .title(title)
                .snippet(snippet));
        }
    }
    
    public Task<Location> getLastKnownLocation() {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) 
            == PackageManager.PERMISSION_GRANTED) {
            return fusedLocationClient.getLastLocation();
        }
        return null;
    }
    
    public void clearMap() {
        if (googleMap != null) {
            googleMap.clear();
        }
    }
} 