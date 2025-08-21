package com.s23010167.tailorease;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricManager;

public class RegisterActivity_si extends AppCompatActivity {

    // Declare UI elements
    EditText etUsername, etEmail, etPassword, etConPassword; // Input fields
    CheckBox cbUseFingerprint;                               // Fingerprint option
    Button btnSignUp;                                        // Sign Up button
    DatabaseHelper dbHelper;                                 // SQLite helper

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_register); // Set Sinhala register layout

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
                Toast.makeText(RegisterActivity_si.this, "කරුණාකර සියලුම ක්ෂේත්\u200Dර පුරවන්න", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if password and confirm password match
            if (!password.equals(conPassword)) {
                Toast.makeText(RegisterActivity_si.this, "මුරපද නොගැලපේ.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Check if user already exists in DB
            if (dbHelper.userExists(username, email)) {
                Toast.makeText(RegisterActivity_si.this, "පරිශීලක දැනටමත් ලියාපදිංචි වී ඇත", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Check fingerprint preference BEFORE inserting
            boolean useFingerprint = cbUseFingerprint.isChecked();

            if (useFingerprint) {
                // Check if device supports/enrolled fingerprint
                BiometricManager biometricManager = BiometricManager.from(this);
                int canAuth = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG);

                if (canAuth != BiometricManager.BIOMETRIC_SUCCESS) {
                    Toast.makeText(this, "ඇඟිලි සලකුණ සහාය නොදක්වයි හෝ ලියාපදිංචි කර නැත.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            // Insert user into database
            boolean inserted = dbHelper.insertUser(username, email, password, useFingerprint);

            // Show success or failure messages
            if (inserted) {
                Toast.makeText(RegisterActivity_si.this, "ලියාපදිංචිය සාර්ථකයි", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity_si.this, LoginActivity_si.class)); // Go to login
                finish(); // Close register activity
            } else {
                Toast.makeText(RegisterActivity_si.this, "ලියාපදිංචිය අසාර්ථක විය", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
