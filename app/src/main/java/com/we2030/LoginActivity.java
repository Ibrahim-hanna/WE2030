package com.we2030;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    private TextInputLayout emailLayout;
    private TextInputLayout passwordLayout;
    private MaterialButton loginButton;
    private TextView registerLink;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();

        initializeViews();
        setupListeners();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Log.d(TAG, "Utilisateur déjà connecté: " + currentUser.getEmail());
            redirectToCountrySelection();
        }
    }

    private void initializeViews() {
        emailLayout = findViewById(R.id.emailLayout);
        passwordLayout = findViewById(R.id.passwordLayout);
        loginButton = findViewById(R.id.btnLogin);
        registerLink = findViewById(R.id.tvRegister);
    }

    private void setupListeners() {
        loginButton.setOnClickListener(v -> attemptLogin());
        registerLink.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });
    }

    private void attemptLogin() {
        loginButton.setEnabled(false);

        emailLayout.setError(null);
        passwordLayout.setError(null);

        String email = emailLayout.getEditText().getText().toString().trim();
        String password = passwordLayout.getEditText().getText().toString();

        boolean cancel = false;
        View focusView = null;

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
            if (focusView == null) focusView = passwordLayout.getEditText();
            cancel = true;
        }

        if (cancel) {
            if (focusView != null) {
                 focusView.requestFocus();
            }
            loginButton.setEnabled(true);
        } else {
            Toast.makeText(this, "Connexion en cours...", Toast.LENGTH_SHORT).show();

            mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    loginButton.setEnabled(true);

                    if (task.isSuccessful()) {
                        Log.d(TAG, "signInWithEmail:success");
                        FirebaseUser user = mAuth.getCurrentUser();
                        Toast.makeText(LoginActivity.this, "Connexion réussie.", Toast.LENGTH_SHORT).show();
                        redirectToCountrySelection();
                    } else {
                        Log.w(TAG, "signInWithEmail:failure", task.getException());
                        Toast.makeText(LoginActivity.this, "Échec de la connexion: " +
                                     task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        passwordLayout.getEditText().requestFocus();
                    }
                });
        }
    }

    private void redirectToCountrySelection() {
         Intent intent = new Intent(this, CountrySelectionActivity.class);
         intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
         startActivity(intent);
         finish();
    }

    private boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isPasswordValid(String password) {
        return !TextUtils.isEmpty(password) && password.length() >= 6;
    }
} 