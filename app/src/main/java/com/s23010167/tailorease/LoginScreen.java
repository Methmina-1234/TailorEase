package com.s23010167.tailorease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {

    Button btnLogin, btnGuest;
    View btnNewAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        btnLogin = findViewById(R.id.btnLogin);
        btnGuest = findViewById(R.id.btnGuest);
        btnNewAccount = findViewById(R.id.btnNewAccount);

        btnLogin.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, LoginActivity.class);
            startActivity(intent);
        });

        btnNewAccount.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, RegisterActivity.class);
            startActivity(intent);
        });

        // Guest button: start HomeActivity with guest flag
        btnGuest.setOnClickListener(v -> {
            Intent intent = new Intent(LoginScreen.this, HomeActivity.class);
            intent.putExtra("user_type", "guest");  // Pass guest mode info
            startActivity(intent);
        });
    }
}
