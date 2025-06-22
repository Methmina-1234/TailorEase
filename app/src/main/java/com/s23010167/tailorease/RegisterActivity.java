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

    EditText etUsername, etEmail, etPassword, etConPassword;
    CheckBox cbUseFingerprint;
    Button btnSignUp;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

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
                Toast.makeText(RegisterActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!password.equals(conPassword)) {
                Toast.makeText(RegisterActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                return;
            }

            if (dbHelper.userExists(username, email)) {
                Toast.makeText(RegisterActivity.this, "User already registered", Toast.LENGTH_SHORT).show();
                return;
            }

            // ✅ Check fingerprint preference BEFORE inserting
            boolean useFingerprint = cbUseFingerprint.isChecked();

            if (useFingerprint) {
                BiometricManager biometricManager = BiometricManager.from(this);
                int canAuth = biometricManager.canAuthenticate(BiometricManager.Authenticators.BIOMETRIC_STRONG);

                if (canAuth != BiometricManager.BIOMETRIC_SUCCESS) {
                    Toast.makeText(this, "Fingerprint not supported or not enrolled", Toast.LENGTH_LONG).show();
                    return;
                }
            }

            boolean inserted = dbHelper.insertUser(username, email, password, useFingerprint);
            if (inserted) {
                Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            } else {
                Toast.makeText(RegisterActivity.this, "Registration Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
