package com.s23010167.tailorease;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.biometric.BiometricManager;
import androidx.core.content.ContextCompat;

import java.util.concurrent.Executor;

public class LoginActivity_si extends AppCompatActivity {

    // UI components
    EditText etUsername, etPassword;
    Button btnLogin, btnSignUp, btnFingerprint;

    // Database helper to validate users
    DatabaseHelper dbHelper;

    // Biometric authentication components
    Executor executor;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_login);

        // Bind XML views to variables
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnFingerprint = findViewById(R.id.btnFingerprint);

        // Initialize database helper
        dbHelper = new DatabaseHelper(this);

        // Traditional login
        btnLogin.setOnClickListener(view -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            // Check if username or password is empty
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "කරුණාකර පරිශීලක නාමය සහ මුරපදය යන දෙකම ඇතුළත් කරන්න.", Toast.LENGTH_SHORT).show();
            }
            // Validate against database
            else if (dbHelper.checkUser(username, password)) {
                Toast.makeText(this, "සාදරයෙන් පිළිගනිමු!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity_si.class)); // Navigate to home
                finish(); // Close login activity
            } else {
                Toast.makeText(this, "වැරදි පරිශීලක නාමය හෝ මුරපදය", Toast.LENGTH_SHORT).show();
            }
        });

        // Sign up
        btnSignUp.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity_si.class));
        });

        // Fingerprint login
        BiometricManager biometricManager = BiometricManager.from(this);
        // Check if device supports biometric login
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                != BiometricManager.BIOMETRIC_SUCCESS) {
            btnFingerprint.setEnabled(false); // Disable fingerprint button
            Toast.makeText(this, "ඇඟිලි සලකුණ සහාය නොදක්වයි/සක්\u200Dරීය කර ඇත", Toast.LENGTH_SHORT).show();
        }

        // Executor runs biometric callbacks on the main thread
        executor = ContextCompat.getMainExecutor(this);

        // Configure biometric authentication
        biometricPrompt = new BiometricPrompt(LoginActivity_si.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(LoginActivity_si.this, "ඇඟිලි සලකුණ හඳුනා ගැනිණි! ඇතුල් වෙමින් පවතී...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity_si.this, HomeActivity_si.class)); // Navigate to home
                finish(); // Close login activity
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(LoginActivity_si.this, "ඇඟිලි සලකුණ හඳුනාගෙන නොමැත", Toast.LENGTH_SHORT).show();
            }
        });

        // Configure the biometric popup message
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("ඇඟිලි සලකුණ භාවිතයෙන් ඇතුල් වන්න")
                .setSubtitle("ඔබේ ජෛවමිතික අක්තපත්\u200Dර භාවිතයෙන් සත්\u200Dයාපනය කරන්න")
                .setNegativeButtonText("අවලංගු කරන්න") // Cancel button
                .build();

        // Trigger biometric authentication on button click
        btnFingerprint.setOnClickListener(view -> biometricPrompt.authenticate(promptInfo));
    }
}
