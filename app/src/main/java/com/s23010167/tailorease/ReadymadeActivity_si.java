package com.s23010167.tailorease;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import androidx.appcompat.app.AppCompatActivity;

public class ReadymadeActivity_si extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.si_activity_readymade); // Set Sinhala version of the Readymade layout

        // Find ImageButtons by ID (Sinhala layout)
        ImageButton trousers = findViewById(R.id.imageButton8);   // Trousers category
        ImageButton tshirt = findViewById(R.id.imageButton9);     // T-Shirts category
        ImageButton ladyDress = findViewById(R.id.imageButton10); // Lady Dress category
        ImageButton blazer = findViewById(R.id.imageButton11);    // Blazers category
        ImageButton kidsDress = findViewById(R.id.imageButton12); // Kids Dress category
        ImageButton bags = findViewById(R.id.imageButton13);      // Bags category

        // Set click listeners to open respective Sinhala activities
        trousers.setOnClickListener(v ->
                startActivity(new Intent(this, TrousersActivity_si.class))); // Open TrousersActivity_si

        tshirt.setOnClickListener(v ->
                startActivity(new Intent(this, TShirtActivity_si.class)));   // Open TShirtActivity_si

        ladyDress.setOnClickListener(v ->
                startActivity(new Intent(this, LadyDressActivity_si.class))); // Open LadyDressActivity_si

        blazer.setOnClickListener(v ->
                startActivity(new Intent(this, BlazerActivity_si.class)));    // Open BlazerActivity_si

        kidsDress.setOnClickListener(v ->
                startActivity(new Intent(this, KidDressActivity_si.class)));  // Open KidDressActivity_si

        bags.setOnClickListener(v ->
                startActivity(new Intent(this, BagActivity_si.class)));       // Open BagActivity_si
    }
}
