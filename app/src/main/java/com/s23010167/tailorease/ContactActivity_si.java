package com.s23010167.tailorease;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

public class ContactActivity_si extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_contact); // Load Sinhala contact layout

        // Call Button
        Button callButton = findViewById(R.id.button_call);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Phone number of TailorEase shop
                String phone = "tel:+94740224207";

                // Intent to open dialer app
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phone));

                // Launch dialer
                startActivity(intent);
            }
        });

        // Map Button
        Button mapButton = findViewById(R.id.button_map);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Intent to launch MapsActivity
                Intent intent = new Intent(ContactActivity_si.this, MapsActivity.class);

                // Start MapsActivity
                startActivity(intent);
            }
        });
    }
}
