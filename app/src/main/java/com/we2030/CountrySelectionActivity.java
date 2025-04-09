package com.we2030;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.we2030.adapters.CountryAdapter;
import com.we2030.models.Country;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CountrySelectionActivity extends AppCompatActivity {
    private static final String TAG = "CountrySelectionActivity";
    
    private RecyclerView recyclerView;
    private CountryAdapter adapter;
    private List<Country> countries;
    private ProgressBar progressBar;
    private TextView titleView;
    
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_selection);
        
        // Initialiser Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        
        // Vérifier si un utilisateur est connecté
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser == null) {
            // Aucun utilisateur connecté, rediriger vers la connexion
            Toast.makeText(this, "Veuillez vous connecter", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        
        // Obtenir l'ID utilisateur pour Firestore
        userId = currentUser.getUid();
        
        // Initialiser les vues
        recyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.progressBar); // Assurez-vous d'avoir un ProgressBar dans le layout
        titleView = findViewById(R.id.tvTitle);
        
        // Définir le RecyclerView
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2)); // Affichage en grille de 2 colonnes
        
        // Charger et afficher les pays
        loadCountries();
    }

    private void loadCountries() {
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        
        // Dans un cas réel, vous pourriez charger ces données depuis Firestore
        // Pour l'instant, utilisons des données statiques pour simplifier
        countries = createSampleCountries();
        
        // Créer l'adaptateur et le définir sur le RecyclerView
        adapter = new CountryAdapter(countries, this::onCountrySelected);
        recyclerView.setAdapter(adapter);
        
        if (progressBar != null) progressBar.setVisibility(View.GONE);
    }
    
    // Méthode appelée lorsqu'un pays est sélectionné
    private void onCountrySelected(Country country) {
        Log.d(TAG, "Pays sélectionné: " + country.getName());
        
        if (progressBar != null) progressBar.setVisibility(View.VISIBLE);
        
        // Enregistrer la préférence de pays dans Firestore pour cet utilisateur
        Map<String, Object> updateData = new HashMap<>();
        updateData.put("paysPreferer", country.getId());
        updateData.put("paysPrefereName", country.getName());
        
        // Mise à jour du document utilisateur dans la collection 'fans'
        db.collection("fans").document(userId)
            .update(updateData)
            .addOnSuccessListener(aVoid -> {
                Log.d(TAG, "Préférence de pays enregistrée avec succès!");
                
                // Lancer MainActivity avec les données du pays
                Intent intent = new Intent(CountrySelectionActivity.this, MainActivity.class);
                intent.putExtra("country_id", country.getId());
                intent.putExtra("country_name", country.getName());
                intent.putExtra("country_flag", country.getFlagResourceId());
                intent.putExtra("country_timezone", country.getTimeZone());
                intent.putExtra("country_group", country.getGroup());
                intent.putExtra("country_stage", country.getStage());
                
                startActivity(intent);
                finish(); // Fermer cette activité
            })
            .addOnFailureListener(e -> {
                if (progressBar != null) progressBar.setVisibility(View.GONE);
                Log.e(TAG, "Erreur lors de l'enregistrement de la préférence du pays", e);
                Toast.makeText(CountrySelectionActivity.this, 
                    "Erreur lors de l'enregistrement de votre préférence: " + e.getMessage(), 
                    Toast.LENGTH_LONG).show();
            });
    }
    
    // Créer une liste de pays pour la démonstration
    private List<Country> createSampleCountries() {
        List<Country> countryList = new ArrayList<>();
        
        // Assurez-vous que les ressources drawable existent avec les bons noms
        countryList.add(new Country("mar", "Maroc", R.drawable.flag_morocco, "Africa/Casablanca", "A", "Group Stage"));
        countryList.add(new Country("fra", "France", R.drawable.flag_france, "Europe/Paris", "A", "Group Stage"));
        countryList.add(new Country("esp", "Espagne", R.drawable.flag_spain, "Europe/Madrid", "B", "Group Stage"));
        countryList.add(new Country("por", "Portugal", R.drawable.flag_portugal, "Europe/Lisbon", "B", "Group Stage"));
        countryList.add(new Country("ger", "Allemagne", R.drawable.flag_germany, "Europe/Berlin", "C", "Group Stage"));
        countryList.add(new Country("eng", "Angleterre", R.drawable.flag_england, "Europe/London", "C", "Group Stage"));
        countryList.add(new Country("bra", "Brésil", R.drawable.flag_brazil, "America/Sao_Paulo", "D", "Group Stage"));
        countryList.add(new Country("arg", "Argentine", R.drawable.flag_argentina, "America/Buenos_Aires", "D", "Group Stage"));
        
        return countryList;
    }
} 