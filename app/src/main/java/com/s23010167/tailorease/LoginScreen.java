package com.s23010167.tailorease;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {

    Button btnLogin, btnNewAccount, btnGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        btnLogin = findViewById(R.id.btnLogin);
        btnNewAccount = findViewById(R.id.btnNewAccount);
        btnGuest = findViewById(R.id.btnGuest);

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(LoginScreen.this, LoginActivity.class));
        });

        btnNewAccount.setOnClickListener(v -> {
            startActivity(new Intent(LoginScreen.this, RegisterActivity.class));
        });

        btnGuest.setOnClickListener(v -> {
            showGuestDialog();
        });
    }

    private void showGuestDialog() {
        new AlertDialog.Builder(this)
                .setTitle("👋 Welcome, Guest!")
                .setMessage("You're entering TailorEase as a guest.\n\n🚫 Some premium features like ✂️ Customize, 🧾 Orders, and 📞 Contact will be *disabled*.\n\n✨ Want the full experience?\nCreate a free account to unlock everything!")
                .setPositiveButton("Continue as Guest 🚶‍♂️", (dialog, which) -> {
                    Intent intent = new Intent(LoginScreen.this, HomeActivity.class);
                    intent.putExtra("user_type", "guest");
                    startActivity(intent);
                })
                .setNegativeButton("Register Now ✍️", (dialog, which) -> {
                    startActivity(new Intent(LoginScreen.this, RegisterActivity.class));
                })
                .setCancelable(true)
                .show();
    }

}
