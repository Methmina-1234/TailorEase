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
        setContentView(R.layout.activity_contact);

        // Call Button
        Button callButton = findViewById(R.id.button_call);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phone = "tel:+94740224207";
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse(phone));
                startActivity(intent);
            }
        });

        // Map Button
        Button mapButton = findViewById(R.id.button_map);
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ContactActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }
}
