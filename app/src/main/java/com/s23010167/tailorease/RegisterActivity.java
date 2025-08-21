package com.s23010167.tailorease;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;

public class RegisterActivity extends AppCompatActivity {

    // Declare UI elements
    EditText etUsername, etEmail, etPassword, etConPassword; // Input fields
    CheckBox cbUseFingerprint;                               // Fingerprint option
    Button btnSignUp;                                        // Sign Up button
    DatabaseHelper dbHelper;                                 // SQLite helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // Set the register layout

        // Bind views to their XML IDs
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConPassword = findViewById(R.id.etConPassword);
        cbUseFingerprint = findViewById(R.id.cbUseFingerprint);
        btnSignUp = findViewById(R.id.btnSignUp);

        dbHelper = new DatabaseHelper(this); // Initialize database helper

        // Set click listener for Sign Up button
        btnSignUp.setOnClickListener(v -> {
            // Get input values from EditTexts
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String conPassword = etConPassword.getText().toString().trim();

            // Validate all fields are filled
            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || conPassword.isEmpty()) {
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if password and confirm password match
            if (!password.equals(conPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if user already exists in DB
            if (dbHelper.userExists(username, email)) {
                Toast.makeText(RegisterActivity.this, "User already registered", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check fingerprint preference BEFORE inserting
            boolean useFingerprint = cbUseFingerprint.isChecked();

            if (useFingerprint) {
                // Check if device supports/enrolled fingerprint
                BiometricManager biometricManager = BiometricManager.from(this);
                int canAuth = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG);

                if (canAuth != BiometricManager.BIOMETRIC_SUCCESS) {
                    Toast.makeText(this, "Fingerprint not supported or not enrolled", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            // Insert user into database
            boolean inserted = dbHelper.insertUser(username, email, password, useFingerprint);

            // Show success or failure messages
            if (inserted) {
                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class)); // Go to login
                finish(); // Close register activity
            } else {
                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
