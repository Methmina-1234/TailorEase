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

    EditText etUsername, etPassword;
    Button btnLogin, btnSignUp, btnFingerprint;
    DatabaseHelper dbHelper;
    Executor executor;
    BiometricPrompt biometricPrompt;
    BiometricPrompt.PromptInfo promptInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnFingerprint = findViewById(R.id.btnFingerprint);

        dbHelper = new DatabaseHelper(this);

        // Traditional login
        btnLogin.setOnClickListener(view -> {
            String username = etUsername.getText().toString().trim();
            String password = etPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter both username and password", Toast.LENGTH_SHORT).show();
            } else if (dbHelper.checkUser(username, password)) {
                Toast.makeText(this, "Welcome!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, HomeActivity.class));
                finish();
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
        if (biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG)
                != BiometricManager.BIOMETRIC_SUCCESS) {
            btnFingerprint.setEnabled(false);
            Toast.makeText(this, "Fingerprint not supported/enabled", Toast.LENGTH_SHORT).show();
        }

        executor = ContextCompat.getMainExecutor(this);
        biometricPrompt = new BiometricPrompt(LoginActivity.this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                Toast.makeText(LoginActivity.this, "Fingerprint recognized! Logging in...", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Toast.makeText(LoginActivity.this, "Fingerprint not recognized", Toast.LENGTH_SHORT).show();
            }
        });

        promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Login using Fingerprint")
                .setSubtitle("Authenticate using your biometric credential")
                .setNegativeButtonText("Cancel")
                .build();

        btnFingerprint.setOnClickListener(view -> biometricPrompt.authenticate(promptInfo));
    }
}
