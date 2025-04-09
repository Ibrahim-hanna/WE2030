package com.we2030;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.we2030.adapters.MatchAdapter;
import com.we2030.models.Country;
import com.we2030.models.Match;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MatchesActivity extends AppCompatActivity {
    private RecyclerView rvMatches;
    private MatchAdapter matchAdapter;
    private Country selectedCountry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_matches);

        try {
            // Récupération du pays sélectionné
            if (getIntent().hasExtra("country")) {
                selectedCountry = (Country) getIntent().getSerializableExtra("country");
            } else {
                throw new IllegalStateException("Aucun pays n'a été sélectionné");
            }

            if (selectedCountry == null) {
                throw new IllegalStateException("Le pays sélectionné est invalide");
            }

            // Initialisation des vues
            initializeViews();

            // Configuration des informations du pays
            setupCountryInfo();

            // Configuration du RecyclerView
            setupRecyclerView();

        } catch (Exception e) {
            Toast.makeText(this, "Une erreur est survenue: " + e.getMessage(), Toast.LENGTH_LONG).show();
            finish();
        }
    }

    private void initializeViews() {
        rvMatches = findViewById(R.id.rvMatches);
        if (rvMatches == null) {
            throw new IllegalStateException("RecyclerView non trouvé");
        }
    }

    private void setupCountryInfo() {
        ImageView ivCountryFlag = findViewById(R.id.ivCountryFlag);
        TextView tvCountryName = findViewById(R.id.tvCountryName);
        TextView tvGroupInfo = findViewById(R.id.tvGroupInfo);

        if (ivCountryFlag != null && tvCountryName != null && tvGroupInfo != null) {
            ivCountryFlag.setImageResource(selectedCountry.getFlagResourceId());
            tvCountryName.setText(selectedCountry.getName());
            tvGroupInfo.setText(String.format("Groupe %s", selectedCountry.getGroup()));
        }
    }

    private void setupRecyclerView() {
        rvMatches.setLayoutManager(new LinearLayoutManager(this));
        List<Match> matches = getMatchesForCountry(selectedCountry);
        matchAdapter = new MatchAdapter(matches);
        rvMatches.setAdapter(matchAdapter);
    }

    private List<Match> getMatchesForCountry(Country country) {
        List<Match> matches = new ArrayList<>();
        try {
            Calendar calendar = Calendar.getInstance();
            calendar.set(2030, Calendar.JUNE, 15); // Date de début de la Coupe du Monde 2030

            // Match 1
            matches.add(new Match(
                "1",
                "Phase de groupes",
                "15/06/2030",
                "20:00",
                country.getName(),
                "France",
                "Stade Mohammed V, Casablanca",
                country.getFlagResourceId(),
                R.drawable.flag_france
            ));

            // Match 2
            calendar.add(Calendar.DAY_OF_MONTH, 4);
            matches.add(new Match(
                "2",
                "Phase de groupes",
                "19/06/2030",
                "17:00",
                "Argentine",
                country.getName(),
                "Grand Stade de Marrakech",
                R.drawable.flag_argentina,
                country.getFlagResourceId()
            ));

            // Match 3
            calendar.add(Calendar.DAY_OF_MONTH, 4);
            matches.add(new Match(
                "3",
                "Phase de groupes",
                "23/06/2030",
                "20:00",
                country.getName(),
                "Brésil",
                "Grand Stade de Marrakech",
                country.getFlagResourceId(),
                R.drawable.flag_brazil
            ));

            // Huitièmes de finale
            calendar.add(Calendar.DAY_OF_MONTH, 4);
            matches.add(new Match(
                "4",
                "Huitièmes de finale",
                "27/06/2030",
                "20:00",
                country.getName(),
                "Espagne",
                "Stade Santiago Bernabéu, Madrid",
                country.getFlagResourceId(),
                R.drawable.flag_spain
            ));

            // Quarts de finale
            calendar.add(Calendar.DAY_OF_MONTH, 4);
            matches.add(new Match(
                "5",
                "Quarts de finale",
                "01/07/2030",
                "20:00",
                "Portugal",
                country.getName(),
                "Stade Ibn Battouta, Tanger",
                R.drawable.flag_portugal,
                country.getFlagResourceId()
            ));

            // Demi-finale
            calendar.add(Calendar.DAY_OF_MONTH, 4);
            matches.add(new Match(
                "6",
                "Demi-finale",
                "05/07/2030",
                "20:00",
                country.getName(),
                "France",
                "Stade Mohammed V, Casablanca",
                country.getFlagResourceId(),
                R.drawable.flag_france
            ));

            // Finale
            calendar.add(Calendar.DAY_OF_MONTH, 4);
            matches.add(new Match(
                "7",
                "Finale",
                "09/07/2030",
                "20:00",
                country.getName(),
                "Espagne",
                "Stade Mohammed V, Casablanca",
                country.getFlagResourceId(),
                R.drawable.flag_spain
            ));

        } catch (Exception e) {
            Toast.makeText(this, "Erreur lors de la création des matchs: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return matches;
    }
} 