package com.s23010167.tailorease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6;
    private boolean isGuestUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageButton1 = findViewById(R.id.imageButton1); // Customize
        imageButton2 = findViewById(R.id.imageButton2); // Readymade
        imageButton3 = findViewById(R.id.imageButton3); // Contact
        imageButton4 = findViewById(R.id.imageButton4); // Materials
        imageButton5 = findViewById(R.id.imageButton5); // Orders
        imageButton6 = findViewById(R.id.imageButton6); // About

        // Set guest flag
        String userType = getIntent().getStringExtra("user_type");
        isGuestUser = "guest".equals(userType);

        if (isGuestUser) {
            disableRestrictedFeatures();
        }

        imageButton1.setOnClickListener(view -> {
            if (checkGuestRestriction("Customize")) return;
            startActivity(new Intent(HomeActivity.this, CustomizeActivity.class));
        });

        imageButton2.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, ReadymadeActivity.class));
        });

        imageButton3.setOnClickListener(view -> {
            if (checkGuestRestriction("Contact")) return;
            startActivity(new Intent(HomeActivity.this, ContactActivity.class));
        });

        imageButton4.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, MaterialActivity.class));
        });

        imageButton5.setOnClickListener(view -> {
            if (checkGuestRestriction("Orders")) return;
            startActivity(new Intent(HomeActivity.this, OrderActivity.class));
        });

        imageButton6.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
        });
    }

    private void disableRestrictedFeatures() {
        imageButton1.setAlpha(0.5f);
        imageButton1.setEnabled(true); // keep clickable to show message

        imageButton3.setAlpha(0.5f);
        imageButton3.setEnabled(true);

        imageButton5.setAlpha(0.5f);
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
