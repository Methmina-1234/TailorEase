package com.s23010167.tailorease;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen_si extends AppCompatActivity {

    Button btnLogin, btnNewAccount, btnGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_login_screen);

        btnLogin = findViewById(R.id.btnLogin);
        btnNewAccount = findViewById(R.id.btnNewAccount);
        btnGuest = findViewById(R.id.btnGuest);

        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(LoginScreen_si.this, LoginActivity_si.class));
        });

        btnNewAccount.setOnClickListener(v -> {
            startActivity(new Intent(LoginScreen_si.this, RegisterActivity_si.class));
        });

        btnGuest.setOnClickListener(v -> {
            showGuestDialog();
        });
    }

    private void showGuestDialog() {
        new AlertDialog.Builder(this)
                .setTitle("👋 සාදරයෙන් පිළිගනිමු, අමුත්තා!")
                .setMessage("ඔබ TailorEase වෙත ඇතුළු වන්නේ අමුත්තෙකු ලෙසයි.\n\n🚫 ✂\uFE0F අභිරුචිකරණය, \uD83E\uDDFE ඇණවුම්, සහ \uD83D\uDCDE සම්බන්ධතා වැනි සමහර වාරික විශේෂාංග *අක්\u200Dරිය* කෙරේ.\n\n✨ සම්පූර්ණ අත්දැකීමම අවශ්‍යද?\nසියල්ල අගුළු ඇරීමට නොමිලේ ගිණුමක් සාදන්න!")
                .setPositiveButton("ආගන්තුකයෙකු ලෙස ඉදිරියට යන්න 🚶‍♂️", (dialog, which) -> {
                    Intent intent = new Intent(LoginScreen_si.this, HomeActivity_si.class);
                    intent.putExtra("user_type", "guest");
                    startActivity(intent);
                })
                .setNegativeButton("දැන් ලියාපදිංචි වන්න ✍️", (dialog, which) -> {
                    startActivity(new Intent(LoginScreen_si.this, RegisterActivity_si.class));
                })
                .setCancelable(true)
                .show();
    }

}
