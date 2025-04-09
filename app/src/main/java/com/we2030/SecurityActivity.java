package com.we2030;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class SecurityActivity extends AppCompatActivity {
    private Button policeButton;
    private Button hospitalButton;
    private Button embassyButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        policeButton = findViewById(R.id.policeButton);
        hospitalButton = findViewById(R.id.hospitalButton);
        embassyButton = findViewById(R.id.embassyButton);

        setupClickListeners();
    }

    private void setupClickListeners() {
        // Police nationale marocaine
        policeButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:190"));
            startActivity(intent);
        });

        // Services d'urgence médicaux
        hospitalButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:150"));
            startActivity(intent);
        });

        // Ambassade (exemple avec l'ambassade de France)
        embassyButton.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:ambassade.rabat@diplomatie.gouv.fr"));
            intent.putExtra(Intent.EXTRA_SUBJECT, "Urgence - Support Consulaire");
            startActivity(Intent.createChooser(intent, getString(R.string.email)));
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
} 