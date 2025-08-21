package com.s23010167.tailorease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    // UI Elements
    ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6;

    // Flag to track if the current user is a guest
    private boolean isGuestUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Initialize ImageButtons
        imageButton1 = findViewById(R.id.imageButton1); // Customize
        imageButton2 = findViewById(R.id.imageButton2); // Readymade
        imageButton3 = findViewById(R.id.imageButton3); // Contact
        imageButton4 = findViewById(R.id.imageButton4); // Materials
        imageButton5 = findViewById(R.id.imageButton5); // Orders
        imageButton6 = findViewById(R.id.imageButton6); // About

        // Guest User Check
        String userType = getIntent().getStringExtra("user_type");
        isGuestUser = "guest".equals(userType);

        if (isGuestUser) {
            disableRestrictedFeatures();
        }

        // Button Click Listeners
        // Customize feature
        imageButton1.setOnClickListener(view -> {
            if (checkGuestRestriction("Customize")) return; // Block for guests
            startActivity(new Intent(HomeActivity.this, CustomizeActivity.class));
        });

        // Readymade feature (available for all users)
        imageButton2.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, ReadymadeActivity.class));
        });

        // Contact feature
        imageButton3.setOnClickListener(view -> {
            if (checkGuestRestriction("Contact")) return; // Block for guests
            startActivity(new Intent(HomeActivity.this, ContactActivity.class));
        });

        // Materials feature (available for all users)
        imageButton4.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, MaterialActivity.class));
        });

        // Orders feature
        imageButton5.setOnClickListener(view -> {
            if (checkGuestRestriction("Orders")) return; // Block for guests
            startActivity(new Intent(HomeActivity.this, OrderActivity.class));
        });

        // About feature (available for all users)
        imageButton6.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
        });
    }

    // Guest Feature Restrictions
    private void disableRestrictedFeatures() {
        // Make restricted buttons semi-transparent and still clickable to show message
        imageButton1.setAlpha(0.5f); // Customize
        imageButton1.setEnabled(true); // keep clickable to show message

        imageButton3.setAlpha(0.5f); // Contact
        imageButton3.setEnabled(true);

        imageButton5.setAlpha(0.5f); // Orders
        imageButton5.setEnabled(true);
    }

    private boolean checkGuestRestriction(String featureName) {
        if (isGuestUser) {
            Toast.makeText(this, featureName + " is disabled for guest users.\nPlease register to access full features.", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
}
