package com.s23010167.tailorease;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class LoginScreen extends AppCompatActivity {

    // Buttons for login options
    Button btnLogin, btnNewAccount, btnGuest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);

        // Bind XML views to variables
        btnLogin = findViewById(R.id.btnLogin);
        btnNewAccount = findViewById(R.id.btnNewAccount);
        btnGuest = findViewById(R.id.btnGuest);

        //Login button click → navigate to LoginActivity
        btnLogin.setOnClickListener(v -> {
            startActivity(new Intent(LoginScreen.this, LoginActivity.class));
        });

        //New Account button click → navigate to RegisterActivity
        btnNewAccount.setOnClickListener(v -> {
            startActivity(new Intent(LoginScreen.this, RegisterActivity.class));
        });

        // Guest button click → show a warning dialog about limited access
        btnGuest.setOnClickListener(v -> {
            showGuestDialog();
        });
    }

    private void showGuestDialog() {
        AlertDialog dialog = new AlertDialog.Builder(this, R.style.CustomAlertDialog)
                .setTitle("👋 Welcome, Guest!")
                .setMessage("You're entering TailorEase as a guest.\n\n🚫 Some premium features like ✂️ Customize, 🧾 Orders, and 📞 Contact will be *disabled*.\n\n✨ Want the full experience?\nCreate a free account to unlock everything!")

                // Continue as guest → open HomeActivity with "guest" user_type
                .setPositiveButton("Continue as Guest 🚶‍♂️", (dialogInterface, which) -> {
                    Intent intent = new Intent(LoginScreen.this, HomeActivity.class);
                    intent.putExtra("user_type", "guest");
                    startActivity(intent);
                })

                // Register now → open RegisterActivity
                .setNegativeButton("Register Now ✍️", (dialogInterface, which) -> {
                    startActivity(new Intent(LoginScreen.this, RegisterActivity.class));
                })
                .setCancelable(true) // allow dismissing by tapping outside
                .create();

        dialog.show();

        // Dynamically adjust button text color based on current light/dark mode
        int nightModeFlags = getResources().getConfiguration().uiMode & android.content.res.Configuration.UI_MODE_NIGHT_MASK;
        int textColor = (nightModeFlags == android.content.res.Configuration.UI_MODE_NIGHT_YES)
                ? getResources().getColor(R.color.brand_light)   // Dark mode → lighter/orange text
                : getResources().getColor(R.color.brand_dark);   // Light mode → darker text

        // Apply the text color to dialog buttons
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(textColor);
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(textColor);
    }

}
