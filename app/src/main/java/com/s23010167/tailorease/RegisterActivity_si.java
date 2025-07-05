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

    EditText etUsername, etEmail, etPassword, etConPassword;
    CheckBox cbUseFingerprint;
    Button btnSignUp;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_register);

        // Bind views
        etUsername = findViewById(R.id.etUsername);
        etEmail = findViewById(R.id.etEmail);
        etPassword = findViewById(R.id.etPassword);
        etConPassword = findViewById(R.id.etConPassword);
        cbUseFingerprint = findViewById(R.id.cbUseFingerprint);
        btnSignUp = findViewById(R.id.btnSignUp);

        dbHelper = new DatabaseHelper(this);

        btnSignUp.setOnClickListener(v -> {
            String username = etUsername.getText().toString().trim();
            String email = etEmail.getText().toString().trim();
            String password = etPassword.getText().toString().trim();
            String conPassword = etConPassword.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || conPassword.isEmpty()) {
                Toast.makeText(RegisterActivity_si.this, "කරුණාකර සියලුම ක්ෂේත්\u200Dර පුරවන්න", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(conPassword)) {
                Toast.makeText(RegisterActivity_si.this, "මුරපද නොගැලපේ.", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.userExists(username, email)) {
                Toast.makeText(RegisterActivity_si.this, "පරිශීලක දැනටමත් ලියාපදිංචි වී ඇත", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Check fingerprint preference BEFORE inserting
            boolean useFingerprint = cbUseFingerprint.isChecked();

            if (useFingerprint) {
                BiometricManager biometricManager = BiometricManager.from(this);
                int canAuth = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG);

                if (canAuth != BiometricManager.BIOMETRIC_SUCCESS) {
                    Toast.makeText(this, "ඇඟිලි සලකුණ සහාය නොදක්වයි හෝ ලියාපදිංචි කර නැත.", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            boolean inserted = dbHelper.insertUser(username, email, password, useFingerprint);
            if (inserted) {
                Toast.makeText(RegisterActivity_si.this, "ලියාපදිංචිය සාර්ථකයි", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity_si.this, LoginActivity_si.class));
                finish();
            } else {
                Toast.makeText(RegisterActivity_si.this, "ලියාපදිංචිය අසාර්ථක විය", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
