package com.s23010167.tailorease; // App's package name

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Always call the superclass method
        setContentView(R.layout.activity_splash); // Set the splash screen layout

        // Handler used to delay the transition to the next activity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an Intent to navigate from SplashActivity to MainActivity
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);

                // Start MainActivity
                startActivity(intent);

                // Close SplashActivity so the user cannot return to it with the back button
                finish();
            }
        }, 3000); // Delay of 3000 milliseconds (3 seconds)
    }
}
