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

public class LoginActivity extends AppCompatActivity {

    // UI components
    EditText etUsername, etPassword;
    Button btnLogin, btnSignUp, btnFingerprint;

    // Database helper for checking login credentials
    DatabaseHelper dbHelper;

    // Biometric authentication components
    Executor executor;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind UI components with XML views
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

            // Check if fields are empty
            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            }
            // Validate against database
            else if (dbHelper.checkUser(username, password)) {
                Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity.class)); // Navigate to home
                finish(); // Close LoginActivity so user cannot go back
            } else {
                Toast.makeText(this, "Incorrect username or password", Toast.LENGTH_SHORT).show();
            }
        });

        // Sign up
        btnSignUp.setOnClickListener(view -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        // Fingerprint login
        BiometricManager biometricManager = BiometricManager.from(this);
        // Check if device supports biometric authentication
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                != BiometricManager.BIOMETRIC_SUCCESS) {
            btnFingerprint.setEnabled(false); // Disable button if not supported
            Toast.makeText(this, "Fingerprint not supported/enabled", Toast.LENGTH_SHORT).show();
        }

        // Executor runs biometric callbacks on the main thread
        executor = ContextCompat.getMainExecutor(this);

        // Configure biometric authentication
        biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(LoginActivity.this, "Fingerprint recognized! Logging in...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class)); // Navigate to home
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(LoginActivity.this, "Fingerprint not recognized", Toast.LENGTH_SHORT).show();
            }
        });

        // Biometric login popup information
        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login using Fingerprint")
                .setSubtitle("Authenticate using your biometric credential")
                .setNegativeButtonText("Cancel") // User can cancel biometric login
                .build();

        // When fingerprint button is clicked, show biometric authentication
        btnFingerprint.setOnClickListener(view -> biometricPrompt.authenticate(promptInfo));
    }
}
