package com.we2030;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    private TextInputLayout nameLayout;
    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private TextInputLayout confirmPasswordLayout;
    private MaterialButton registerButton;
    private TextView loginLink;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        initializeViews();
        setupListeners();
    }

    private void initializeViews() {
        nameLayout = findViewById(R.id.nameLayout);
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout);
        registerButton = findViewById(R.id.registerButton);
        loginLink = findViewById(R.id.loginLink);
    }

    private void setupListeners() {
        registerButton.setOnClickListener(v -> attemptRegister());
        loginLink.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });
    }

    private void attemptRegister() {
        registerButton.setEnabled(false);

        nameLayout.setError(null);
        emailLayout.setError(null);
        passwordLayout.setError(null);
        confirmPasswordLayout.setError(null);

        String name = nameLayout.getEditText().getText().toString().trim();
        String email = emailLayout.getEditText().getText().toString().trim();
        String password = passwordLayout.getEditText().getText().toString();
        String confirmPassword = confirmPasswordLayout.getEditText().getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (TextUtils.isEmpty(name)) {
            nameLayout.setError("Le nom est requis");
            focusView = nameLayout.getEditText();
            cancel = true;
        }

        if (TextUtils.isEmpty(email)) {
            emailLayout.setError("L'email est requis");
            focusView = emailLayout.getEditText();
            cancel = true;
        } else if (!isEmailValid(email)) {
            emailLayout.setError("Email invalide");
            focusView = emailLayout.getEditText();
            cancel = true;
        }

        if (TextUtils.isEmpty(password)) {
            passwordLayout.setError("Le mot de passe est requis");
            focusView = passwordLayout.getEditText();
            cancel = true;
        } else if (!isPasswordValid(password)) {
            passwordLayout.setError("Le mot de passe doit contenir au moins 6 caractères");
            focusView = passwordLayout.getEditText();
            cancel = true;
        }

        if (TextUtils.isEmpty(confirmPassword)) {
            confirmPasswordLayout.setError("La confirmation du mot de passe est requise");
            focusView = confirmPasswordLayout.getEditText();
            cancel = true;
        } else if (!password.equals(confirmPassword)) {
            confirmPasswordLayout.setError("Les mots de passe ne correspondent pas");
            focusView = confirmPasswordLayout.getEditText();
            cancel = true;
        }

        if (cancel) {
            if (focusView != null) {
                focusView.requestFocus();
            }
            registerButton.setEnabled(true);
        } else {
            Toast.makeText(this, "Inscription en cours...", Toast.LENGTH_SHORT).show();

            mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "createUserWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            saveUserToFirestore(user.getUid(), name, email);
                        } else {
                            Toast.makeText(RegisterActivity.this, "Erreur: Utilisateur non trouvé après inscription.",
                                       Toast.LENGTH_SHORT).show();
                            registerButton.setEnabled(true);
                        }
                    } else {
                        Log.w(TAG, "createUserWithEmail:failure", task.getException());
                        Toast.makeText(RegisterActivity.this, "Échec de l'inscription: " + task.getException().getMessage(),
                                Toast.LENGTH_LONG).show();
                        registerButton.setEnabled(true);
                    }
                });
        }
    }

    private void saveUserToFirestore(String userId, String name, String email) {
        Map<String, Object> fanData = new HashMap<>();
        fanData.put("nom", name);
        fanData.put("email", email);

        db.collection("fans").document(userId)
            .set(fanData)
            .addOnSuccessListener(aVoid -> {
                Log.d(TAG, "Données utilisateur enregistrées avec succès dans Firestore!");
                Toast.makeText(RegisterActivity.this, "Inscription réussie !", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            })
            .addOnFailureListener(e -> {
                Log.w(TAG, "Erreur lors de l'enregistrement des données utilisateur", e);
                Toast.makeText(RegisterActivity.this, "Erreur lors de la sauvegarde des données.",
                        Toast.LENGTH_SHORT).show();
                registerButton.setEnabled(true);
            });
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }
} 