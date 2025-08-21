package com.s23010167.tailorease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Buttons to select app language
    Button btnEnglish, btnSinhala;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Bind English button and set click listener
        btnEnglish = findViewById(R.id.btnEnglish);
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open English login screen
                Intent intent = new Intent(MainActivity.this, LoginScreen.class);
                startActivity(intent);
            }
        });

        // Bind Sinhala button and set click listener
        btnSinhala = findViewById(R.id.btnSinhala);
        btnSinhala.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open Sinhala login screen
                Intent intent = new Intent(MainActivity.this, LoginScreen_si.class);
                startActivity(intent);
            }
        });
    }
}
