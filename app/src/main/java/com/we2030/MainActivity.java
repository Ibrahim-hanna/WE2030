package com.we2030;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.we2030.models.Country;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private BottomNavigationView bottomNavigationView;
    private Country selectedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: Démarrage de MainActivity");
        
        try {
            setContentView(R.layout.activity_main);
            Log.d(TAG, "onCreate: Layout chargé avec succès");

            // Récupérer les données du pays
            if (getIntent() != null && getIntent().hasExtra("country_id")) {
                try {
                    String countryId = getIntent().getStringExtra("country_id");
                    String countryName = getIntent().getStringExtra("country_name");
                    int countryFlag = getIntent().getIntExtra("country_flag", 0);
                    String countryTimezone = getIntent().getStringExtra("country_timezone");
                    String countryGroup = getIntent().getStringExtra("country_group");
                    String countryStage = getIntent().getStringExtra("country_stage");

                    Log.d(TAG, "onCreate: Données reçues - ID: " + countryId + 
                          ", Nom: " + countryName + 
                          ", Drapeau: " + countryFlag + 
                          ", Fuseau: " + countryTimezone + 
                          ", Groupe: " + countryGroup + 
                          ", Phase: " + countryStage);

                    if (countryId != null && countryName != null && countryFlag != 0) {
                        selectedCountry = new Country(countryId, countryName, countryFlag, 
                            countryTimezone, countryGroup, countryStage);
                        Log.d(TAG, "onCreate: Pays sélectionné initialisé avec succès");
                    } else {
                        Log.e(TAG, "onCreate: Données du pays invalides");
                        Toast.makeText(this, "Erreur: Données du pays invalides", Toast.LENGTH_SHORT).show();
                        finish();
                        return;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "onCreate: Erreur lors de la récupération des données du pays", e);
                    Toast.makeText(this, "Erreur: Format de données invalide", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
            } else {
                Log.e(TAG, "onCreate: Pas de données de pays reçues");
                Toast.makeText(this, "Erreur: Aucune donnée de pays reçue", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }

            // Configurer la navigation
            setupBottomNavigation();
            Log.d(TAG, "onCreate: Configuration de la navigation terminée");

            // Lancer CountryDetailsActivity immédiatement
            launchCountryDetails();

        } catch (Exception e) {
            Log.e(TAG, "onCreate: Erreur lors de l'initialisation", e);
            Toast.makeText(this, "Erreur lors du démarrage de l'application: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void setupBottomNavigation() {
        try {
            Log.d(TAG, "setupBottomNavigation: Début de la configuration");
            bottomNavigationView = findViewById(R.id.bottom_navigation);
            if (bottomNavigationView == null) {
                Log.e(TAG, "setupBottomNavigation: bottomNavigationView est null");
                return;
            }

            bottomNavigationView.setOnItemSelectedListener(item -> {
                try {
                    int itemId = item.getItemId();
                    Log.d(TAG, "setupBottomNavigation: Item sélectionné: " + itemId);
                    
                    if (itemId == R.id.nav_home) {
                        Log.d(TAG, "setupBottomNavigation: Navigation vers Accueil");
                        return true;
                    } else if (itemId == R.id.nav_matches) {
                        Log.d(TAG, "setupBottomNavigation: Navigation vers Matchs");
                        return launchMatchesActivity();
                    } else if (itemId == R.id.nav_maps) {
                        Log.d(TAG, "setupBottomNavigation: Navigation vers Carte");
                        return launchMapsActivity();
                    } else if (itemId == R.id.nav_teams) {
                        Log.d(TAG, "setupBottomNavigation: Navigation vers Équipes");
                        return true;
                    } else if (itemId == R.id.nav_profile) {
                        Log.d(TAG, "setupBottomNavigation: Navigation vers Profil");
                        return true;
                    } else if (itemId == R.id.nav_alerts) {
                        Log.d(TAG, "setupBottomNavigation: Navigation vers Alertes");
                        return true;
                    }
                } catch (Exception e) {
                    Log.e(TAG, "setupBottomNavigation: Erreur lors de la navigation", e);
                    Toast.makeText(this, "Erreur lors de la navigation", Toast.LENGTH_SHORT).show();
                }
                return false;
            });
            Log.d(TAG, "setupBottomNavigation: Configuration terminée avec succès");
        } catch (Exception e) {
            Log.e(TAG, "setupBottomNavigation: Erreur lors de la configuration", e);
            Toast.makeText(this, "Erreur lors de la configuration de la navigation", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchCountryDetails() {
        try {
            if (selectedCountry != null) {
                Intent intent = new Intent(this, CountryDetailsActivity.class);
                intent.putExtra("country_id", selectedCountry.getId());
                intent.putExtra("country_name", selectedCountry.getName());
                intent.putExtra("country_flag", selectedCountry.getFlagResourceId());
                intent.putExtra("country_timezone", selectedCountry.getTimeZone());
                intent.putExtra("country_group", selectedCountry.getGroup());
                intent.putExtra("country_stage", selectedCountry.getStage());
                startActivity(intent);
            }
        } catch (Exception e) {
            Log.e(TAG, "launchCountryDetails: Erreur lors du lancement de CountryDetailsActivity", e);
            Toast.makeText(this, "Erreur lors du lancement des détails du pays", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean launchMatchesActivity() {
        try {
            Log.d(TAG, "launchMatchesActivity: Début du lancement");
            if (selectedCountry == null) {
                Log.e(TAG, "launchMatchesActivity: selectedCountry est null");
                Toast.makeText(this, "Erreur: Données du pays non disponibles", Toast.LENGTH_SHORT).show();
                return false;
            }

            Intent intent = new Intent(this, MatchesActivity.class);
            intent.putExtra("country_id", selectedCountry.getId());
            Log.d(TAG, "launchMatchesActivity: Intent préparé avec l'ID du pays");
            startActivity(intent);
            Log.d(TAG, "launchMatchesActivity: MatchesActivity lancée avec succès");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "launchMatchesActivity: Erreur lors du lancement", e);
            Toast.makeText(this, "Erreur lors du lancement des matchs", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private boolean launchMapsActivity() {
        try {
            Log.d(TAG, "launchMapsActivity: Début du lancement");
            if (selectedCountry == null) {
                Log.e(TAG, "launchMapsActivity: selectedCountry est null");
                Toast.makeText(this, "Erreur: Données du pays non disponibles", Toast.LENGTH_SHORT).show();
                return false;
            }

            Intent intent = new Intent(this, MapsActivity.class);
            intent.putExtra("country_id", selectedCountry.getId());
            Log.d(TAG, "launchMapsActivity: Intent préparé avec l'ID du pays");
            startActivity(intent);
            Log.d(TAG, "launchMapsActivity: MapsActivity lancée avec succès");
            return true;
        } catch (Exception e) {
            Log.e(TAG, "launchMapsActivity: Erreur lors du lancement", e);
            Toast.makeText(this, "Erreur lors du lancement de la carte", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed: Appel de la méthode retour");
        super.onBackPressed();
    }
}