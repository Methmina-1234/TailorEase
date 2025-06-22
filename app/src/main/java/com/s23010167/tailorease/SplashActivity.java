package com.s23010167.tailorease; // This is app's package name

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); // Always call the superclass method
        setContentView(R.layout.activity_splash); // Set the splash screen layout

        // Wait 3 seconds (3000 milliseconds), then open MainActivity
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // Create an Intent to go from SplashActivity to MainActivity
                Intent inten = new Intent(SplashActivity.this, MainActivity.class);

                // Start MainActivity
                startActivity(inten);

                // Finish SplashActivity so it doesn't stay in the back stack
                finish();
            }
        }, 3000); // 3-second delay
    }
}
