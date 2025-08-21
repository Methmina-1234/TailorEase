package com.s23010167.tailorease;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact); // Load the contact layout

        // Call Button
        Button callButton = findViewById(R.id.button_call);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Define the phone number
                String phone = "tel:+94740224207";

                // Create an intent to open the dialer app
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phone));

                // Launch the dialer
                startActivity(intent);
            }
        });

        // Map Button
        Button mapButton = findViewById(R.id.button_map);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Create an intent to start MapsActivity

                Intent intent = new Intent(ContactActivity.this, MapsActivity.class);

                // Launch the MapsActivity
                startActivity(intent);
            }
        });
    }
}
