package com.we2030;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.util.HashMap;
import java.util.Map;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback {
    private GoogleMap mMap;
    private Map<String, LatLng> stadiums;
    private Map<String, LatLng> hotels;
    private Map<String, LatLng> fanzones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initializeLocations();
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void initializeLocations() {
        // Stades
        stadiums = new HashMap<>();
        stadiums.put("Stade Mohammed V", new LatLng(33.5731, -7.5898));
        stadiums.put("Stade Ibn Battouta", new LatLng(35.7267, -5.8927));
        stadiums.put("Stade de Marrakech", new LatLng(31.6295, -8.0089));
        stadiums.put("Stade de Rabat", new LatLng(34.0209, -6.8416));
        stadiums.put("Stade de Casablanca", new LatLng(33.5731, -7.5898));

        // Hôtels
        hotels = new HashMap<>();
        hotels.put("Hôtel Marrakech", new LatLng(31.6295, -7.9811));
        hotels.put("Hôtel Casablanca", new LatLng(33.5731, -7.5898));
        hotels.put("Hôtel Rabat", new LatLng(34.0209, -6.8416));
        hotels.put("Hôtel Tanger", new LatLng(35.7267, -5.8927));

        // Fanzones
        fanzones = new HashMap<>();
        fanzones.put("Fanzone Marrakech", new LatLng(31.6295, -7.9811));
        fanzones.put("Fanzone Casablanca", new LatLng(33.5731, -7.5898));
        fanzones.put("Fanzone Rabat", new LatLng(34.0209, -6.8416));
        fanzones.put("Fanzone Tanger", new LatLng(35.7267, -5.8927));
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Ajout des marqueurs pour les stades
        for (Map.Entry<String, LatLng> entry : stadiums.entrySet()) {
            mMap.addMarker(new MarkerOptions()
                    .position(entry.getValue())
                    .title(entry.getKey())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
        }

        // Ajout des marqueurs pour les hôtels
        for (Map.Entry<String, LatLng> entry : hotels.entrySet()) {
            mMap.addMarker(new MarkerOptions()
                    .position(entry.getValue())
                    .title(entry.getKey())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
        }

        // Ajout des marqueurs pour les fanzones
        for (Map.Entry<String, LatLng> entry : fanzones.entrySet()) {
            mMap.addMarker(new MarkerOptions()
                    .position(entry.getValue())
                    .title(entry.getKey())
                    .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        }

        // Centrer la carte sur le Maroc
        LatLng morocco = new LatLng(31.6295, -7.9811);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(morocco, 6));

        // Activer le bouton de localisation
        if (mMap != null) {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}