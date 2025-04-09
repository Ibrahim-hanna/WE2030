package com.we2030;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.Gravity;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.we2030.adapters.MatchAdapter;
import com.we2030.models.Match;
import com.we2030.models.TeamStats;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroupDetailsActivity extends AppCompatActivity {
    private static final String TAG = "GroupDetailsActivity";
    private String groupName;
    private String countryName;
    private RecyclerView groupMatchesRecyclerView;
    private MatchAdapter matchAdapter;
    private Map<String, TeamStats> teamStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_details);

        try {
            setupToolbar();
            retrieveGroupData();
            initializeTeamStats();
            setupViews();
            setupMatchesRecyclerView();
        } catch (Exception e) {
            Log.e(TAG, "Erreur dans onCreate", e);
            Toast.makeText(this, "Erreur d'initialisation : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void setupToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

    private void retrieveGroupData() {
        Intent intent = getIntent();
        if (intent != null) {
            groupName = intent.getStringExtra("group_name");
            countryName = intent.getStringExtra("country_name");
            if (groupName == null) {
                Log.e(TAG, "Nom du groupe manquant");
                Toast.makeText(this, "Erreur: Données du groupe manquantes", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    private void initializeTeamStats() {
        teamStats = new HashMap<>();
        
        // Statistiques pour le Groupe A
        if (groupName.equals("A")) {
            teamStats.put("Maroc", new TeamStats("Maroc", 2, 1, 1, 0));
            teamStats.put("Espagne", new TeamStats("Espagne", 2, 1, 0, 1));
            teamStats.put("Portugal", new TeamStats("Portugal", 2, 0, 2, 0));
            teamStats.put("France", new TeamStats("France", 2, 0, 1, 1));
        }
        // Statistiques pour le Groupe B
        else if (groupName.equals("B")) {
            teamStats.put("Argentine", new TeamStats("Argentine", 2, 2, 0, 0));
            teamStats.put("Brésil", new TeamStats("Brésil", 2, 1, 1, 0));
            teamStats.put("Angleterre", new TeamStats("Angleterre", 2, 0, 1, 1));
            teamStats.put("Allemagne", new TeamStats("Allemagne", 2, 0, 0, 2));
        }
    }

    private void setupViews() {
        TextView groupNameTextView = findViewById(R.id.groupNameTextView);
        groupNameTextView.setText("Groupe " + groupName);

        // Trier les équipes par points
        List<TeamStats> sortedTeams = new ArrayList<>(teamStats.values());
        sortedTeams.sort((t1, t2) -> {
            // D'abord par points
            int pointsCompare = Integer.compare(t2.getPoints(), t1.getPoints());
            if (pointsCompare != 0) return pointsCompare;
            
            // Puis par différence de buts
            int goalDiffCompare = Integer.compare(t2.getGoalDifference(), t1.getGoalDifference());
            if (goalDiffCompare != 0) return goalDiffCompare;
            
            // Puis par buts marqués
            return Integer.compare(t2.getGoalsFor(), t1.getGoalsFor());
        });

        // Configuration des lignes du tableau
        TableRow[] rows = new TableRow[4];
        rows[0] = findViewById(R.id.team1Row);
        rows[1] = findViewById(R.id.team2Row);
        rows[2] = findViewById(R.id.team3Row);
        rows[3] = findViewById(R.id.team4Row);

        // Remplir le tableau avec les équipes triées
        for (int i = 0; i < sortedTeams.size() && i < 4; i++) {
            setupTeamRow(rows[i], sortedTeams.get(i), i + 1);
        }
    }

    private void setupTeamRow(TableRow row, TeamStats stats, int position) {
        if (stats == null || row == null) return;

        row.removeAllViews();
        Context context = row.getContext();

        // Position
        TextView positionView = new TextView(context);
        positionView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 0.5f));
        positionView.setText(String.valueOf(position));
        positionView.setGravity(Gravity.CENTER);
        row.addView(positionView);

        // Nom de l'équipe
        TextView teamName = new TextView(context);
        teamName.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 2f));
        teamName.setText(stats.getTeamName());
        if (stats.getTeamName().equals(countryName)) {
            teamName.setTypeface(null, Typeface.BOLD);
            teamName.setTextColor(getResources().getColor(R.color.primary));
        }
        row.addView(teamName);

        // Matchs joués
        addStatCell(row, String.valueOf(stats.getMatchesPlayed()));
        // Victoires
        addStatCell(row, String.valueOf(stats.getWins()));
        // Nuls
        addStatCell(row, String.valueOf(stats.getDraws()));
        // Défaites
        addStatCell(row, String.valueOf(stats.getLosses()));
        // Buts pour
        addStatCell(row, String.valueOf(stats.getGoalsFor()));
        // Buts contre
        addStatCell(row, String.valueOf(stats.getGoalsAgainst()));
        // Différence de buts
        addStatCell(row, String.format("%+d", stats.getGoalDifference()));
        // Points
        TextView pointsView = new TextView(context);
        pointsView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        pointsView.setText(String.valueOf(stats.getPoints()));
        pointsView.setGravity(Gravity.CENTER);
        pointsView.setTypeface(null, Typeface.BOLD);
        row.addView(pointsView);
    }

    private void addStatCell(TableRow row, String value) {
        TextView cell = new TextView(this);
        cell.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1f));
        cell.setText(value);
        cell.setGravity(android.view.Gravity.CENTER);
        row.addView(cell);
    }

    private void setupMatchesRecyclerView() {
        groupMatchesRecyclerView = findViewById(R.id.groupMatchesRecyclerView);
        groupMatchesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        matchAdapter = new MatchAdapter(getGroupMatches());
        groupMatchesRecyclerView.setAdapter(matchAdapter);
    }

    private List<Match> getGroupMatches() {
        List<Match> matches = new ArrayList<>();
        
        try {
            if (groupName.equals("A")) {
                // Matchs du Groupe A
                Match match = new Match("match1", "Phase de groupe", "20/06/2024", "15:00", 
                              "Maroc", "Portugal", getStadiumForMatch("Maroc", "Portugal"), 
                              R.drawable.flag_morocco, R.drawable.flag_portugal);
                match.setTeam1Score(2);
                match.setTeam2Score(2);
                match.setStatus("Terminé");
                matches.add(match);
                
                match = new Match("match2", "Phase de groupe", "22/06/2024", "20:00", 
                          "Espagne", "France", getStadiumForMatch("Espagne", "France"), 
                          R.drawable.flag_spain, R.drawable.flag_france);
                match.setTeam1Score(1);
                match.setTeam2Score(0);
                match.setStatus("Terminé");
                matches.add(match);
                
                match = new Match("match3", "Phase de groupe", "24/06/2024", "18:00", 
                          "Maroc", "Espagne", getStadiumForMatch("Maroc", "Espagne"), 
                          R.drawable.flag_morocco, R.drawable.flag_spain);
                match.setStatus("À venir");
                matches.add(match);
                
                match = new Match("match4", "Phase de groupe", "26/06/2024", "17:00", 
                          "Portugal", "France", getStadiumForMatch("Portugal", "France"), 
                          R.drawable.flag_portugal, R.drawable.flag_france);
                match.setStatus("À venir");
                matches.add(match);
                
                match = new Match("match5", "Phase de groupe", "28/06/2024", "20:00", 
                          "France", "Maroc", getStadiumForMatch("France", "Maroc"), 
                          R.drawable.flag_france, R.drawable.flag_morocco);
                match.setStatus("À venir");
                matches.add(match);
                
                match = new Match("match6", "Phase de groupe", "28/06/2024", "20:00", 
                          "Portugal", "Espagne", getStadiumForMatch("Portugal", "Espagne"), 
                          R.drawable.flag_portugal, R.drawable.flag_spain);
                match.setStatus("À venir");
                matches.add(match);
                
            } else if (groupName.equals("B")) {
                // Matchs du Groupe B
                Match match = new Match("match7", "Phase de groupe", "21/06/2024", "17:00", 
                              "Argentine", "Brésil", getStadiumForMatch("Argentine", "Brésil"), 
                              R.drawable.flag_argentina, R.drawable.flag_brazil);
                match.setTeam1Score(3);
                match.setTeam2Score(1);
                match.setStatus("Terminé");
                matches.add(match);
                
                match = new Match("match8", "Phase de groupe", "21/06/2024", "20:00", 
                          "Angleterre", "Allemagne", getStadiumForMatch("Angleterre", "Allemagne"),
                          R.drawable.flag_england, R.drawable.flag_germany);
                match.setTeam1Score(1);
                match.setTeam2Score(2);
                match.setStatus("Terminé");
                matches.add(match);
                
                match = new Match("match9", "Phase de groupe", "25/06/2024", "18:00", 
                          "Argentine", "Angleterre", getStadiumForMatch("Argentine", "Angleterre"),
                          R.drawable.flag_argentina, R.drawable.flag_england);
                match.setStatus("À venir");
                matches.add(match);
                
                match = new Match("match10", "Phase de groupe", "25/06/2024", "18:00", 
                          "Brésil", "Allemagne", getStadiumForMatch("Brésil", "Allemagne"),
                          R.drawable.flag_brazil, R.drawable.flag_germany);
                match.setStatus("À venir");
                matches.add(match);
                
                match = new Match("match11", "Phase de groupe", "29/06/2024", "20:00", 
                          "Allemagne", "Argentine", getStadiumForMatch("Allemagne", "Argentine"),
                          R.drawable.flag_germany, R.drawable.flag_argentina);
                match.setStatus("À venir");
                matches.add(match);
                
                match = new Match("match12", "Phase de groupe", "29/06/2024", "20:00", 
                          "Brésil", "Angleterre", getStadiumForMatch("Brésil", "Angleterre"),
                          R.drawable.flag_brazil, R.drawable.flag_england);
                match.setStatus("À venir");
                matches.add(match);
            }
            
            Log.d(TAG, "Nombre de matchs trouvés pour le groupe " + groupName + ": " + matches.size());
        } catch (Exception e) {
            Log.e(TAG, "Erreur lors de la création des matchs", e);
        }
        
        return matches;
    }

    private String getStadiumForMatch(String team1, String team2) {
        Log.d(TAG, "getStadiumForMatch appelé pour " + team1 + " vs " + team2);
        
        // Liste des stades marocains
        String[] moroccanStadiums = {
            "Complexe Mohammed V, Casablanca",
            "Stade Ibn Battouta, Tanger",
            "Grand Stade de Marrakech",
            "Complexe Sportif Moulay Abdallah, Rabat",
            "Stade Municipal de Fès",
            "Stade Moulay Hassan, Rabat",
            "Stade Adrar, Agadir",
            "Stade Municipal de Tétouan"
        };

        // Liste des stades espagnols
        String[] spanishStadiums = {
            "Santiago Bernabéu, Madrid",
            "Camp Nou, Barcelone",
            "Metropolitano, Madrid",
            "San Siro, Milan",
            "Allianz Arena, Munich",
            "Vicente Calderón, Madrid",
            "Estadio Benito Villamarín, Séville",
            "Estadio Mestalla, Valence"
        };

        // Liste des stades portugais
        String[] portugueseStadiums = {
            "Estádio da Luz, Lisbonne",
            "Estádio do Dragão, Porto",
            "Estádio José Alvalade, Lisbonne",
            "Estádio Municipal de Braga",
            "Estádio Algarve, Faro",
            "Estádio da Bola, Porto",
            "Estádio Municipal de Aveiro",
            "Estádio Municipal de Leiria"
        };

        // Mélanger les stades
        List<String> allStadiums = new ArrayList<>();
        allStadiums.addAll(Arrays.asList(moroccanStadiums));
        allStadiums.addAll(Arrays.asList(spanishStadiums));
        allStadiums.addAll(Arrays.asList(portugueseStadiums));
        Collections.shuffle(allStadiums);

        // Retourner un stade aléatoire
        String selectedStadium = allStadiums.get(0);
        Log.d(TAG, "Stade sélectionné: " + selectedStadium);
        return selectedStadium;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            Log.d(TAG, "Bouton retour pressé");
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "onBackPressed appelé");
        finish();
    }
} 