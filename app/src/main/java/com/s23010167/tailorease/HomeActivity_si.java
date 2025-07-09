package com.s23010167.tailorease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity_si extends AppCompatActivity {

    ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton5, imageButton6;
    private boolean isGuestUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_home);

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
            if (checkGuestRestriction("සකසන්න")) return;
            startActivity(new Intent(HomeActivity_si.this, CustomizeActivity_si.class));
        });

        imageButton2.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity_si.this, ReadymadeActivity_si.class));
        });

        imageButton3.setOnClickListener(view -> {
            if (checkGuestRestriction("සම්බන්ධ වන්න")) return;
            startActivity(new Intent(HomeActivity_si.this, ContactActivity_si.class));
        });

        imageButton4.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity_si.this, MaterialActivity_si.class));
        });

        imageButton5.setOnClickListener(view -> {
            if (checkGuestRestriction("ඇනවුම්")) return;
            startActivity(new Intent(HomeActivity_si.this, OrderActivity_si.class));
        });

        imageButton6.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity_si.this, AboutActivity_si.class));
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
            Toast.makeText(this, featureName + " ආගන්තුක පරිශීලකයින් සඳහා අක්\u200Dරීය කර ඇත.\n" +
                    "සම්පූර්ණ විශේෂාංග වෙත ප්\u200Dරවේශ වීමට කරුණාකර ලියාපදිංචි වන්න.", Toast.LENGTH_LONG).show();
            return true;
        }
        return false;
    }
}
