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
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.CustomAlertDialog)
                .setTitle("👋 Welcome, Guest!")
                .setMessage("You're entering TailorEase as a guest.\n\n🚫 Some premium features like ✂️ Customize, 🧾 Orders, and 📞 Contact will be *disabled*.\n\n✨ Want the full experience?\nCreate a free account to unlock everything!")
                .setPositiveButton("Continue as Guest 🚶‍♂️", (dialogInterface, which) -> {
                    Intent intent = new Intent(LoginScreen.this, HomeActivity.class);
                    intent.putExtra("user_type", "guest");
                    startActivity(intent);
                })
                .setNegativeButton("Register Now ✍️", (dialogInterface, which) -> {
                    startActivity(new Intent(LoginScreen.this, RegisterActivity.class));
                })
                .setCancelable(true)
                .create();

        dialog.show();

        // Determine current mode
        int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        int textColor = (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES)
                ? getResources().getColor(R.color.brand_light)   // dark mode → orange text
                : getResources().getColor(R.color.brand_dark);   // light mode → dark blue text

        // Set button text colors
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(textColor);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(textColor);
    }



}
