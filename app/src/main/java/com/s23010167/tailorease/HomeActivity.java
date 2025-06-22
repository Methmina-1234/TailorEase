package com.s23010167.tailorease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    ImageButton imageButton1, imageButton2, imageButton3, imageButton4, imageButton6;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        imageButton1 = findViewById(R.id.imageButton1); // Customize
        imageButton2 = findViewById(R.id.imageButton2); // Readymade
        imageButton3 = findViewById(R.id.imageButton3); // Contact
        imageButton4 = findViewById(R.id.imageButton4); // Materials
        imageButton6 = findViewById(R.id.imageButton6); // About
        View imageButton5 = findViewById(R.id.imageButton5); // Orders


        imageButton1.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, CustomizeActivity.class));
        });

        imageButton2.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, ReadymadeActivity.class));
        });

        imageButton3.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, ContactActivity.class));
        });

        imageButton4.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, MaterialActivity.class));
        });

        imageButton5.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, OrderActivity.class));
        });

        imageButton6.setOnClickListener(view -> {
            startActivity(new Intent(HomeActivity.this, AboutActivity.class));
        });
    }
}
