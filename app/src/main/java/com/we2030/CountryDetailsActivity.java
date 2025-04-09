package com.we2030;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class CountryDetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_details);

        // Récupérer les données du pays à partir de l'intent
        Intent intent = getIntent();
        String countryName = intent.getStringExtra("countryName");
        int flagResourceId = intent.getIntExtra("flagResourceId", -1);
        String timeZone = intent.getStringExtra("timeZone");
        String group = intent.getStringExtra("group");
        String stage = intent.getStringExtra("stage");

        // Vérifier si les données sont valides
        if (countryName == null || flagResourceId == -1 || timeZone == null || group == null || stage == null) {
            Toast.makeText(this, "Erreur: données du pays manquantes", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Mettre à jour l'interface utilisateur avec les données du pays
        ImageView flagImageView = findViewById(R.id.flagImageView);
        TextView countryNameTextView = findViewById(R.id.countryNameTextView);
        TextView timezoneTextView = findViewById(R.id.timezoneTextView);
        TextView groupTextView = findViewById(R.id.groupTextView);
        TextView stageTextView = findViewById(R.id.stageTextView);

        flagImageView.setImageResource(flagResourceId);
        countryNameTextView.setText(countryName);
        timezoneTextView.setText(timeZone);
        groupTextView.setText(group);
        stageTextView.setText(stage);
    }
} 