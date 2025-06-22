package com.s23010167.tailorease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {

    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen); // Set your login screen layout here

        // Initialize the Login button
        btnLogin = findViewById(R.id.btnLogin); // Make sure the ID matches your XML
        View btnNewAccount = findViewById(R.id.btnNewAccount);


        // Set click listener to navigate to LoginActivity
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        btnNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
